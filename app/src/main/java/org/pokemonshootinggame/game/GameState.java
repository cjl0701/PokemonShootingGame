package org.pokemonshootinggame.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;

import org.pokemonshootinggame.framework.AppManager;
import org.pokemonshootinggame.framework.IState;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GameState implements IState {
    private Player m_player;
    private BackGround m_backGround;
    private ArrayList<Enemy> m_enemyList = new ArrayList<>();
    private ArrayList<Missile_Player> m_pmsList = new ArrayList<>();
    private ArrayList<Missile_Enemy> m_enemmsList = new ArrayList<>();
    //private ArrayList<EffectExplosion> m_explist = new ArrayList<EffectExplosion>( );
    private long lastRegenEnemy = System.currentTimeMillis();
    private Random randEnemy = new Random();
    private int displayWidth;

    @Override
    public void init() {
        AppManager.getInstance().setGameState(this);
        m_player = UnitFactory.createPlayer(1); //1,2,3에 따라 플레이어 생성
        m_backGround = new BackGround(1); //0,1에 따라 배경화면 바뀜

        displayWidth = AppManager.getInstance().getDisplayWidth();
    }

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
            if (enemy.state == Enemy.STATE_OUT || enemy.state == Enemy.STATE_DEAD)
                m_enemyList.remove(i);
        }

        //적 미사일
        for (int i = m_enemmsList.size() - 1; i >= 0; i--) {
            Missile_Enemy enemms = m_enemmsList.get(i);
            enemms.update(); //미사일 이동
            if (enemms.state == Missile.STATE_OUT) m_enemmsList.remove(i);
        }
        //폭발 이미지
//        for (int i = m_explist .size( ) – 1; i >= 0; i--){
//            EffectExplosion exp = m_explist .get(i);
//            exp.Update(gameTime);
//        }
        makeEnemy();
        checkCollision();
    }

    @Override
    public void render(Canvas canvas) {
        m_backGround.draw(canvas);
        for (Missile_Player pms : m_pmsList) pms.draw(canvas);
        for (Enemy enemy : m_enemyList) enemy.draw(canvas);
        for (Missile_Enemy enemms : m_enemmsList) enemms.draw(canvas);
        //for (EffecExplosion exp : m_explist)  {  exp.Draw(canvas);  }
        m_player.draw(canvas);

        //플레이어의 생명 표시
        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.BLACK);
        canvas.drawText("남은 목숨: " + m_player.getLife(), 0, 100, paint);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //키 입력에 따른 플레이어 이동
        int x = m_player.getX();
        int y = m_player.getY();

        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT)
            m_player.setPosition(x - m_player.m_speed, y);
        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT)
            m_player.setPosition(x + m_player.m_speed, y);
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP)
            m_player.setPosition(x, y - m_player.m_speed);
        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN)
            m_player.setPosition(x, y + m_player.m_speed);

        return true;
    }

    public void makeEnemy() {
        if (System.currentTimeMillis() - lastRegenEnemy >= 3000) { //생성 시점 이후 3초가 넘으면
            lastRegenEnemy = System.currentTimeMillis();

            int enemyType = randEnemy.nextInt(3) + 1; //1,2,3
            Enemy enemy = UnitFactory.createEnemy(enemyType);

            assert enemy != null;
            enemy.setPosition(randEnemy.nextInt(displayWidth - enemy.width), -60);
            enemy.moveType = randEnemy.nextInt(3);

            m_enemyList.add(enemy);
        }
        if (m_backGround.getScroll() == 0 && Enemy_boss.BossCnt == 0) { // 스크롤이 가장 위에 도달하면 보스 생성
            Enemy boss = UnitFactory.createEnemy(4);
            boss.setPosition((displayWidth - boss.width) / 2, -boss.height);

            m_enemyList.add(boss);
        }
    }

    //충돌하면 삭제
    public void checkCollision() {
        //플레이어 미사일과 적의 충돌 처리
        Iterator<Enemy> iter; //컬렉션을 순회하면서 원소를 삭제할 수 있는 유일하게 안전한 방법
        for (int i = m_pmsList.size() - 1; i >= 0; i--) {
            for (iter = m_enemyList.iterator(); iter.hasNext(); ) {
                //m_explist.add( new EffectExplosion ( m_enemlist .get(j).GetX( ), m_enemlist .get(j).GetY( )));
                Enemy enemy = iter.next();

                if (CollisionManager.checkBoxToBox(m_pmsList.get(i).m_boundBox, enemy.m_boundBox)) { //boss
                    if (enemy.CreateType == Enemy.TYPE_BOSS) {
                        enemy.m_hp -= m_player.m_power;
                        m_pmsList.remove(i);
                        return;
                    } else { //일반 enemy
                        iter.remove(); //적 제거

                        //진화상태일 경우 플레이어 미사일은 제거되지 않는다.(보스몹 제외)
                        if (!m_pmsList.get(i).isEvolvedMs() || enemy.CreateType == Enemy.TYPE_BOSS) {
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
            if (CollisionManager.checkBoxToBox(m_player.m_boundBox, m_enemyList.get(i).m_boundBox)) {
                //m_explist.add( new EffectExplosion ( m_enemlist .get(j).GetX( ), m_enemlist .get(j).GetY( )));
                if (m_enemyList.get(i).CreateType != Enemy.TYPE_BOSS)//보스가 아니면
                    m_enemyList.remove(i); //충돌한 적 제거

                m_player.destroyPlayer();
                AppManager.getInstance().vibrate();
                if (m_player.getLife() <= 0) System.exit(0);
            }
        }

        //플레이어와 적 미사일의 충돌 처리
        for (int i = m_enemmsList.size() - 1; i >= 0; i--) {
            if (CollisionManager.checkBoxToBox(m_player.m_boundBox, m_enemmsList.get(i).m_boundBox)) {
                m_enemmsList.remove(i);
                m_player.destroyPlayer();
                AppManager.getInstance().vibrate();
                if (m_player.getLife() <= 0) System.exit(0);
            }
        }

        //진화 아이템과 충돌 대신 임시로
        if (m_player.getLife() == 2) changePlayerState();
    }

    public ArrayList<Missile_Player> getPmsList() {
        return m_pmsList;
    }

    public ArrayList<Missile_Enemy> getEnemmsList() {
        return m_enemmsList;
    }

    public void changePlayerState() { //진화석과 충돌하면 진화 -> state 패턴 적용
        m_player = m_player.evolve();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public void destroy() {
    }

    public Player getPlayer() {
        return m_player;
    }
}
