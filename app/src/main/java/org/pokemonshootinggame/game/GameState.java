package org.pokemonshootinggame.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;

import org.pokemonshootinggame.R;
import org.pokemonshootinggame.framework.AppManager;
import org.pokemonshootinggame.framework.IState;

import java.util.ArrayList;
import java.util.Random;

public class GameState implements IState {
    private Player m_player;
    private int m_playerSpeed;
    private BackGround m_backGround;
    private ArrayList<Enemy> m_enemyList = new ArrayList<>();
    private ArrayList<Missile_Player> m_pmsList = new ArrayList<>();
    private ArrayList<Missile_Enemy> m_enemmsList = new ArrayList<>();
    private long lastRegenEnemy = System.currentTimeMillis();
    private Random randEnemy = new Random();
    private int displayWidth;
    private int characterSize;

    @Override
    public void init() {
        AppManager.getInstance().setGameState(this);
        Bitmap player = AppManager.getInstance().getBitmap(R.drawable.player);
        m_player = new Player(player);
        m_playerSpeed = 5;
        m_backGround = new BackGround(1); //0,1에 따라 배경화면 바뀜
        //m_keypad=new GraphicObject(AppManager.getInstance().getBitmap(R.drawable.keypad));
        //m_keypad.setPosition(0,460-120);

        displayWidth = AppManager.getInstance().getDisplayWidth();
        characterSize = player.getWidth() / 6;
    }

    @Override
    public void destroy() { }

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
            //화면 밖으로 나간 객체를 각 리스트에서 제거
            if (pms.state == Missile.STATE_OUT) m_pmsList.remove(i);
        }

        //적
        for (int i = m_enemyList.size() - 1; i >= 0; i--) {
            Enemy enemy = m_enemyList.get(i);
            enemy.update(gameTime); //이동
            //화면 밖으로 나간 객체를 각 리스트에서 제거
            if (enemy.state == Enemy.STATE_OUT) m_enemyList.remove(i);
        }

        //적 미사일
        for ( int i = m_enemmsList .size( )-1; i >= 0; i--)  {
            Missile_Enemy enemms = m_enemmsList.get(i);
            enemms.update(); //미사일 이동
            if(enemms.state==Missile.STATE_OUT) m_enemmsList.remove(i);
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
        m_player.draw(canvas);
        //m_keypad.draw(canvas);

        //플레이어의 생명 표시
        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.BLACK);
        canvas.drawText("남은 목숨: " + String.valueOf(m_player.getLife()), 0, 100, paint);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //키 입력에 따른 플레이어 이동
        int x = m_player.getX();
        int y = m_player.getY();

        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT)
            m_player.setPosition(x - m_playerSpeed, y);
        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT)
            m_player.setPosition(x + m_playerSpeed, y);
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP)
            m_player.setPosition(x, y - m_playerSpeed);
        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN)
            m_player.setPosition(x, y + m_playerSpeed);
        if (keyCode == KeyEvent.KEYCODE_SPACE)
            m_pmsList.add(new Missile_Player(x, y + 30)); //미사일이 플레이어 위에

        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) { return false; }

    public void makeEnemy() {
        if (System.currentTimeMillis() - lastRegenEnemy >= 3000) { //생성 시점 이후 3초가 넘으면
            lastRegenEnemy = System.currentTimeMillis();

            int enemyType = randEnemy.nextInt(3);
            Enemy enemy = null;
            if (enemyType == 0) enemy = new Enemy_1();
            else if (enemyType == 1) enemy = new Enemy_2();
            else if (enemyType == 2) enemy = new Enemy_3();

            enemy.setPosition(randEnemy.nextInt(displayWidth - characterSize), -60);
            enemy.moveType = randEnemy.nextInt(3);

            m_enemyList.add(enemy);
        }
    }

    //충돌하면 삭제
    public void checkCollision() {
        //플레이어 미사일과 적의 충돌 처리
        for (int i = m_pmsList.size() - 1; i >= 0; i--) {
            for (int j = m_enemyList.size() - 1; j >= 0; j--) {
                if (CollisionManager.checkBoxToBox(m_pmsList.get(i).m_boundBox, m_enemyList.get(j).m_boundBox)) {
                    m_pmsList.remove(i);
                    m_enemyList.remove(j);
                    return; //일단 루프에서 빠져나옴
                }
            }
        }

        //캐릭터와 적의 충돌 처리
        //플레이어의 생명 값을 내리는 destroyPalyer 메서드 호출하고,
        //getLife 메서드를 통해 현재 플레이어의 생명이 0 이면 게임을 종료
        for (int i = m_enemyList.size() - 1; i >= 0; i--) {
            if (CollisionManager.checkBoxToBox(m_player.m_boundBox, m_enemyList.get(i).m_boundBox)) {
                m_enemyList.remove(i); //충돌한 적 제거
                m_player.destroyPlayer();
                if (m_player.getLife() <= 0) System.exit(0);
            }
        }

        //적 미사일과 플레이어의 충돌 처리
        for ( int i = m_enemmsList .size( )-1; i >= 0; i--){
            if(CollisionManager.checkBoxToBox(m_player.m_boundBox, m_enemmsList.get(i).m_boundBox)){
                m_enemmsList.remove(i);
                m_player.destroyPlayer();
                if (m_player.getLife() <= 0) System.exit(0);
            }
        }
    }

    public ArrayList<Missile_Enemy> getEnemmsList() { return m_enemmsList; }
}
