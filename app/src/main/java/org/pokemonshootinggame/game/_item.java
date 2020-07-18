package org.pokemonshootinggame.game;

import android.graphics.Bitmap;
import android.graphics.Rect;

import org.pokemonshootinggame.framework.AppManager;
import org.pokemonshootinggame.framework.SpriteAnimation;

public class _item extends SpriteAnimation {
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
    public boolean bOut = false;
    protected int width;
    protected int height;


    public _item(Bitmap bitmap) {
        super(bitmap);
        displayHeight = AppManager.getInstance().getDisplayHeight();
        tp = displayHeight / 2 + 40;
    }

    public void update(long gameTime) {
        super .update(gameTime);
        move(); //이동
        //아이템에 대한 스크롤
        m_y += 2;
        if (m_y > displayHeight) {
            bOut = true;
            state = STATE_OUT; //화면 밖에 나가면 삭제
        }
        m_boundBox.set(m_x+10, m_y, m_x +width-10, m_y+height); //이동할 때마다 박스 영역의 값을 갱신
    }

    void move() {
        if (moveType == MOVE_PATTERN_1) {
            if (m_y <= tp) m_y += m_speed; //중간 지점까지 기본 속도
            else m_y += m_speed * 2; //중간 지점 이후 빠른 속도

        }

        else if (moveType == MOVE_PATTERN_2) {
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

}
