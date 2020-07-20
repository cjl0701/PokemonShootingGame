package org.pokemonshootinggame.game.gamestate;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.KeyEvent;
import android.view.MotionEvent;

import org.pokemonshootinggame.R;
import org.pokemonshootinggame.framework.AppManager;
import org.pokemonshootinggame.framework.ClearState;
import org.pokemonshootinggame.framework.EndState;
import org.pokemonshootinggame.framework.IState;
import org.pokemonshootinggame.framework.SoundManager;
import org.pokemonshootinggame.game.BackGround;
import org.pokemonshootinggame.game.CollisionManager;
import org.pokemonshootinggame.game.EffectExplosion;
import org.pokemonshootinggame.game.SpecialAttack;
import org.pokemonshootinggame.game.UnitFactory;
import org.pokemonshootinggame.game.enemy.Enemy;
import org.pokemonshootinggame.game.enemy.Enemy_boss;
import org.pokemonshootinggame.game.item.Item;
import org.pokemonshootinggame.game.missile.Missile;
import org.pokemonshootinggame.game.missile.Missile_Enemy;
import org.pokemonshootinggame.game.missile.Missile_Player;
import org.pokemonshootinggame.game.player.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GameState implements IState {
    protected Player m_player;
    protected BackGround m_backGround;
    protected ArrayList<Enemy> m_enemyList = new ArrayList<>();
    protected ArrayList<Missile_Player> m_pmsList = new ArrayList<>();
    protected ArrayList<Missile_Enemy> m_enemmsList = new ArrayList<>();
    protected ArrayList<EffectExplosion> m_expList = new ArrayList<EffectExplosion>( );
    protected ArrayList<Item> m_itemList = new ArrayList<>();
    protected ArrayList<SpecialAttack> m_spList = new ArrayList<>();
    protected boolean m_useOfSp = false; //Special attack 사용을 위한 flag
    protected long lastRegenEnemy = System.currentTimeMillis();
    protected long lastRegenItem = System.currentTimeMillis();
    protected long lastSpDamage = System.currentTimeMillis(); //Special Attack 시점
    protected Random rand = new Random();
    protected int displayWidth;
    protected SensorManager sensorManager; //센서 이동
    protected int m_count=0; //적 처치수
    protected int m_stage; //현재 스테이지
    protected static final int STAGE1 = 1;
    protected static final int STAGE2 = 2;
    protected static final int STAGE3 = 3;

    protected static final int SOUND_EFFECT_1 = 1;
    protected static final int SOUND_EFFECT_2 = 2;
    protected static final int SOUND_EFFECT_3 = 2;

    @Override
    public void init() {
        AppManager.getInstance().setStage(m_stage);
        UnitFactory.updateStage();
        m_player = UnitFactory.createPlayer(AppManager.getInstance().getPlayerType()); //1,2,3에 따라 플레이어 생성, 싱글톤 패턴
        if(AppManager.getInstance().getSpList()!=null) //이전 스테이지의 Special attack
                m_spList = AppManager.getInstance().getSpList();
        m_backGround = new BackGround(m_stage % 2); //0,1에 따라 배경화면 바뀜

        displayWidth = AppManager.getInstance().getDisplayWidth();

        //Device에서 SensorManager를 가져옴
        sensorManager = (SensorManager) AppManager.getInstance().getGameView().getContext().getSystemService((Context.SENSOR_SERVICE));
        //SensorManager에 Listener로 생성한 클래스를 등록
        sensorManager.registerListener(new SensorHandler(), sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);

        SoundManager.getInstance().addSound(SOUND_EFFECT_1, R.raw.effect1);
        SoundManager.getInstance().addSound(SOUND_EFFECT_2, R.raw.effect2);
        SoundManager.getInstance().addSound(SOUND_EFFECT_3, R.raw.effect3);
    }

    //update->render
    @Override
    public void update() {
        //애니메이션 동작을 위해
        long gameTime = System.currentTimeMillis();
        m_player.update(gameTime);
        m_backGround.update(gameTime);

        //플레이어 미사일
        for (int i = m_pmsList.size() - 1; i >= 0; i--) {
            Missile_Player pms = m_pmsList.get(i);
            pms.update(); //미사일 이동
            if (pms.state == Missile.STATE_OUT) m_pmsList.remove(i); //화면 밖으로 나간 객체를 각 리스트에서 제거
        }

        //적
        for (int i = m_enemyList.size() - 1; i >= 0; i--) {
            Enemy enemy = m_enemyList.get(i);
            enemy.update(gameTime); //이동
            if (enemy.state == Enemy.STATE_OUT)
                m_enemyList.remove(i);
            //보스 클리어
            if(enemy.state == Enemy.STATE_DEAD) {
                SoundManager.getInstance().play(SOUND_EFFECT_2);
                AppManager.getInstance().getGameView().changeGameState(new ClearState());
            }
        }

        //적 미사일
        for (int i = m_enemmsList.size() - 1; i >= 0; i--) {
            Missile_Enemy enemms = m_enemmsList.get(i);
            enemms.update(); //미사일 이동
            if (enemms.state == Missile.STATE_OUT) m_enemmsList.remove(i);
        }

        //폭발 이미지
        for (int i = m_expList.size( ) -1; i >= 0; i--){
            EffectExplosion exp = m_expList.get(i);
            exp.update(gameTime);
            if(exp.getAnimationEnd()) m_expList.remove(i);
        }

        //아이템
        for(int i=m_itemList.size()-1; i>=0; i--){
            Item item = m_itemList.get(i);
            item.update(gameTime);
            if(item.bOut == true) m_itemList.remove(i);
        }

        //Special Attack 사용
        if(m_useOfSp) {
            SpecialAttack sp = m_spList.get(0);
            sp.setPosition(m_player.getX()+m_player.getWidth()/2-sp.getWidth()/4/2, m_player.getY()-sp.getHeight());
            sp.update(gameTime);
            if (sp.getAnimationEnd()) {
                m_spList.remove(0);
                m_useOfSp=false;
            }
        }

        makeEnemy();
        checkCollision();
    }

    @Override
    public void render(Canvas canvas) {
        m_backGround.draw(canvas);
        for (Missile_Player pms : m_pmsList) pms.draw(canvas);
        for (Enemy enemy : m_enemyList) enemy.draw(canvas);
        for (Missile_Enemy enemms : m_enemmsList) enemms.draw(canvas);
        for (EffectExplosion exp : m_expList) exp.draw(canvas);
        for(Item item : m_itemList) item.draw(canvas);
        m_player.draw(canvas);

        if(m_useOfSp) //Special Attack 사용
            m_spList.get(0).draw(canvas);

        //플레이어의 생명 표시
        Paint paint = new Paint();
        paint.setTextSize(80);
        paint.setColor(Color.BLACK);
        canvas.drawText("남은 목숨: " + m_player.getLife(), 0, 100, paint);
        canvas.drawText("필살기 개수: "+ m_spList.size(), 0, 200, paint);
    }

    //충돌 처리
    public void checkCollision() {
        //플레이어 미사일과 적의 충돌 처리
        Iterator<Enemy> iter; //컬렉션을 순회하면서 원소를 삭제할 수 있는 유일하게 안전한 방법
        for (int i = m_pmsList.size() - 1; i >= 0; i--) {
            for (iter = m_enemyList.iterator(); iter.hasNext(); ) {
                Enemy enemy = iter.next();
                if (CollisionManager.checkBoxToBox(m_pmsList.get(i).getBoundBox(), enemy.getBoundBox())) {
                    //boss
                    if (enemy.getCreateType() == Enemy.TYPE_BOSS) {
                        enemy.setHP(enemy.getHP()-m_player.getPower()); //boss의 체력을 player의 공격력만큼 감소
                        m_pmsList.remove(i);
                        return;
                    }
                    //일반 enemy
                    else {
                        m_expList.add(new EffectExplosion(enemy.getX(),enemy.getY()));
                        makeItem(m_itemList, enemy.getX(), enemy.getY());
                        iter.remove(); //적 제거
                        SoundManager.getInstance().play(SOUND_EFFECT_1);
                        m_count++;
                        //진화상태일 경우 플레이어 미사일은 제거되지 않는다.
                        if (!m_pmsList.get(i).isEvolvedMs()) {
                            m_pmsList.remove(i);
                            return;
                        }
                    }
                }
            }
        }

        //플레이어와 적의 충돌 처리
        //플레이어의 생명 값을 내리는 destroyPalyer 메서드 호출하고,
        //getLife 메서드를 통해 현재 플레이어의 생명이 0 이면 게임을 종료
        for (int i = m_enemyList.size() - 1; i >= 0; i--) {
            if (CollisionManager.checkBoxToBox(m_player.getBoundBox(), m_enemyList.get(i).getBoundBox())) {
                if (m_enemyList.get(i).getCreateType() != Enemy.TYPE_BOSS){//보스가 아니면
                    m_expList.add(new EffectExplosion (m_enemyList.get(i).getX(), m_enemyList.get(i).getY()));
                    m_enemyList.remove(i); //충돌한 적 제거
                }
                m_player.destroyPlayer();
                AppManager.getInstance().vibrate();
                if (m_player.getLife() <= 0) AppManager.getInstance().getGameView().changeGameState(new EndState());
            }
        }

        //플레이어와 적 미사일의 충돌 처리
        for (int i = m_enemmsList.size() - 1; i >= 0; i--) {
            if (CollisionManager.checkBoxToBox(m_player.getBoundBox(), m_enemmsList.get(i).getBoundBox())) {
                m_enemmsList.remove(i);
                m_player.destroyPlayer();
                AppManager.getInstance().vibrate();
                if (m_player.getLife() <= 0) AppManager.getInstance().getGameView().changeGameState(new EndState());
            }
        }

        //플레이어와 아이템의 충돌
        for(int i =m_itemList.size()-1; i>=0; i--){
            if(CollisionManager.checkBoxToBox(m_player.getBoundBox(), m_itemList.get(i).getBoundBox())){
                m_itemList.get(i).getItem();
                m_itemList.remove(i);
            }
        }

        //Special Attack과 enemy의 충돌
        if(m_useOfSp) {
            SpecialAttack sp = m_spList.get(0);
            for (iter = m_enemyList.iterator(); iter.hasNext(); ) {
                Enemy enemy = iter.next();
                if (CollisionManager.checkBoxToBox(sp.getBoundBox(), enemy.getBoundBox())) {
                    //boss
                    if (enemy.getCreateType() == Enemy.TYPE_BOSS) {
                        if (System.currentTimeMillis() - lastSpDamage >= 700) { //공격 시점 이후 0.7초가 넘으면
                            lastSpDamage = System.currentTimeMillis();
                            enemy.setHP(enemy.getHP()-m_player.getPower()); //boss의 체력을 player의 공격력만큼 감소
                        }
                    }
                    //일반 enemy
                    else {
                        m_expList.add(new EffectExplosion(enemy.getX(), enemy.getY()));
                        makeItem(m_itemList, enemy.getX(), enemy.getY());
                        SoundManager.getInstance().play(SOUND_EFFECT_1);
                        iter.remove(); //적 제거
                        m_count++;
                    }
                }
            }
        }
    }

    public void makeEnemy() {
        if (System.currentTimeMillis() - lastRegenEnemy >= 3000/m_stage) { //생성 시점 이후 N초가 넘으면
            lastRegenEnemy = System.currentTimeMillis();

            int enemyType = rand.nextInt(3) + 1; //1,2,3
            Enemy enemy = UnitFactory.createEnemy(enemyType);

            assert enemy != null;
            enemy.setPosition(rand.nextInt(displayWidth - enemy.getWidth()), -60);
            enemy.setMoveType(rand.nextInt(5));

            m_enemyList.add(enemy);
        }
        if (m_backGround.getScroll() == 0) { // 스크롤이 가장 위에 도달하면 보스 생성
            if(m_stage > Enemy_boss.m_stage) {
                Enemy_boss.m_stage = m_stage; //스테이지 당 하나만 생성되도록

                Enemy boss = UnitFactory.createEnemy(4);
                boss.setPosition((displayWidth - boss.getWidth()) / 2, -boss.getHeight());

                m_enemyList.add(boss);
            }
        }
    }

    public void makeItem(ArrayList<Item> itemList, int x, int y){
        if (System.currentTimeMillis() - lastRegenItem >= 4000) { //생성 시점 이후 5초가 넘으면
            lastRegenItem = System.currentTimeMillis();

            int itemType = rand.nextInt(2);
            Item item = UnitFactory.createItem(itemType, x, y);
            assert  item != null;
            m_itemList.add(item);
        }
    }

    //방향 센서 이동을 위한 SensorEventListener 구현
    private class SensorHandler implements SensorEventListener {
        private int verticalMax = AppManager.getInstance().getDisplayHeight() - m_player.getHeight();
        private int HorizontalMax = AppManager.getInstance().getDisplayWidth() - m_player.getWidth();
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) { //센서의 값이 바뀔 때 호출
            //받아온 센서 값을 처리
            synchronized (this) {
                switch (sensorEvent.sensor.getType()) {
                    case Sensor.TYPE_ORIENTATION:
                        float pitch = sensorEvent.values[1];
                        float roll = sensorEvent.values[2];

                        //플레이어 움직이기
                        m_player.setX((int) (m_player.getX()-roll));
                        m_player.setY((int) (m_player.getY()-pitch));

                        //화면 크기에 대한 처리
                        if (m_player.getX() <= 0) m_player.setX(0);
                        if (m_player.getY() <= 0) m_player.setY(0);
                        if (m_player.getX() >= HorizontalMax) m_player.setX(HorizontalMax);
                        if (m_player.getY() >= verticalMax) m_player.setY(verticalMax);
                        break;
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) { } //센서의 정확도 값이 바뀔 때 호출
    }

    //Special Attack 발동
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(m_spList.size() > 0) {
            m_useOfSp = true; //Special attack 사용을 위한 flag
            SoundManager.getInstance().play(SOUND_EFFECT_2);
        }
        return false;
    }

    //애뮬레이터를 사용할 경우
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //키 입력에 따른 플레이어 이동
        int x = m_player.getX();
        int y = m_player.getY();

        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT)
            m_player.setPosition(x - m_player.getSpeed(), y);
        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT)
            m_player.setPosition(x + m_player.getSpeed(), y);
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP)
            m_player.setPosition(x, y - m_player.getSpeed());
        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN)
            m_player.setPosition(x, y + m_player.getSpeed());
        if (keyCode == KeyEvent.KEYCODE_SPACE)
            if(m_spList.size() > 0) {
                m_useOfSp=true;
            }

        return true;
    }

    public ArrayList<Missile_Player> getPmsList() {
        return m_pmsList;
    }

    public ArrayList<Missile_Enemy> getEnemmsList() {
        return m_enemmsList;
    }

    public void changePlayerState() { //진화석과 충돌하면 진화 -> state 패턴 적용
        m_player = m_player.evolve();
        UnitFactory.setPlayer(m_player);
    }

    @Override
    public void destroy() {
        AppManager.getInstance().setCount(m_count);
        AppManager.getInstance().setSpecialAttack(m_spList);
        AppManager.getInstance().setGameState(null);
    }

    public Player getPlayer() {
        return m_player;
    }

    public void chargeSpecial(){
        m_spList.add(m_player.getSpecial());
    }
}
