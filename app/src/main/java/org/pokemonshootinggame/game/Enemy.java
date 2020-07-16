package org.pokemonshootinggame.game;

import android.graphics.Bitmap;
import android.graphics.Rect;

import org.pokemonshootinggame.framework.AppManager;
import org.pokemonshootinggame.framework.GraphicObject;
import org.pokemonshootinggame.framework.SpriteAnimation;

public class Enemy extends GraphicObject {
    protected int m_hp;
    protected float m_speed;
    public static final int MOVE_PATTERN_1 = 0;
    public static final int MOVE_PATTERN_2 = 1;
    public static final int MOVE_PATTERN_3 = 2;
    protected int moveType;
    private int displayHeight;
    private int tp; //패턴의 움직임이 바뀌는 지점

    public static final int STATE_NORMAL = 0;
    public static final int STATE_OUT = 1;
    public int state = STATE_NORMAL;

    Rect m_boundBox = new Rect();
    protected int width;
    protected int height;

    private long lastShoot = System.currentTimeMillis(); //발사 시간 정보 저장

    public Enemy(Bitmap bitmap) {
        super(bitmap);
        displayHeight = AppManager.getInstance().getDisplayHeight();
        tp = displayHeight / 2 + 40;
    }

    public void update(long gameTime) { //이동
        attack(); //미사일 발사
        move();
        if (m_y > displayHeight) state = STATE_OUT; //화면 밖에 나가면 삭제
        m_boundBox.set(m_x+10, m_y, m_x +width-10, m_y+height); //이동할 때마다 박스 영역의 값을 갱신
    }

    void move() {
        if (moveType == MOVE_PATTERN_1) {
            if (m_y <= tp) m_y += m_speed; //중간 지점까지 기본 속도
            else m_y += m_speed * 2; //중간 지점 이후 빠른 속도

        } else if (moveType == MOVE_PATTERN_2) {
            if (m_y <= tp) m_y += m_speed; //중간 지점까지 일자로 이동
            else {//중간 지점 이후 대각선 이동
                m_x += m_speed;
                m_y += m_speed;
            }
        } else if (moveType == MOVE_PATTERN_3) {
            if (m_y <= tp) m_y += m_speed; //중간 지점까지 일자로 이동
            else {//중간 지점 이후 대각선 이동
                m_x -= m_speed;
                m_y += m_speed;
            }
        }
    }

    //이전에 발사했던 시간을 저장해서 현재 시간과 이전에 발사했던 시간을 비교 해서 시간이 어느정도 흐르면 미사일을 다시 발사
    void attack() {
        //일정 간격을 두고 미사일 객체를 생성하고, GameState의 멤버 변수인 enemmlist에 추가
        //이를 위해 GameState를 AppManager에 추가해서 GameState를 전역 변수처럼 접근할 수 있게
        if (System.currentTimeMillis() - lastShoot >= 4000) {
            lastShoot = System.currentTimeMillis();
            AppManager.getInstance().getGameState().getEnemmsList().add(new Missile_Enemy(m_x, m_y+height));
        }
    }
}
