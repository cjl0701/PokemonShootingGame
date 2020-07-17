package org.pokemonshootinggame.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import org.pokemonshootinggame.framework.AppManager;
import org.pokemonshootinggame.framework.GraphicObject;

import java.util.Random;

import static android.graphics.Color.*;

public class Enemy extends GraphicObject {
    // 기본 설정
    protected int m_max_hp;
    protected int m_hp;
    protected float HP_rate;
    protected int m_speed;

    public static final int TYPE_NORMAL = 1000;
    public static final int TYPE_BOSS = 1001;
    protected int CreateType;

    public static final int MOVE_PATTERN_1 = 0;
    public static final int MOVE_PATTERN_2 = 1;
    public static final int MOVE_PATTERN_3 = 2;
    protected int moveType;
    private int displayHeight;
    private int tp; //패턴의 움직임이 바뀌는 지점

    public static final int STATE_NORMAL = 0;
    public static final int STATE_OUT = 1;
    public static final int STATE_DEAD = 2;
    public static final int STATE_STOP = 3;
    public int state = STATE_NORMAL;

    // 적 이미지 비율 (width / height)
    public static final float Naong_ImgProportion = 0.95f;
    public static final float Roy_ImgProportion = 0.33f;
    public static final float Rosa_ImgProportion = 0.53f;
    public static final float Boss_ImgProportion = 0.37f;

    protected Rect m_boundBox = new Rect();
    protected Rect HP_bar = new Rect();
    protected Paint HP_bar_color = new Paint();
    // 이미지의 폭과 높이
    protected int width;
    protected int height;

    private long lastShoot_Normal = System.currentTimeMillis(); //발사 시간 정보 저장
    private long lastShoot_Boss = System.currentTimeMillis();

    public Enemy(Bitmap bitmap) {
        super(bitmap);
        displayHeight = AppManager.getInstance().getDisplayHeight();
        tp = displayHeight / 2 + 40;
    }

    public void update(long gameTime) { //이동
        attack(); //미사일 발사
        move();
        if (m_y > displayHeight) state = STATE_OUT; //화면 밖에 나가면 삭제
        if (m_hp <= 0 && this.CreateType == TYPE_BOSS) { // 일반 몬스터는 HP=0이기 때문에 보스인 경우에만 해당
            m_hp = 0;
            state = STATE_DEAD;
        }
        m_boundBox.set(m_x, m_y, m_x+width, m_y+height); //이동할 때마다 박스 영역의 값을 갱신
        // HP 상태바 관리
        if (this.CreateType == TYPE_BOSS) { // 보스 몬스터는 빨간색 상태바
            HP_rate = ((float)m_hp / (float)m_max_hp);
            HP_bar.set(m_x, m_y-40, m_x+(int)(HP_rate*width), m_y-10);
            HP_bar_color.setColor(RED);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(m_bitmap,m_x,m_y,null);
        if (this.CreateType == TYPE_BOSS) // 보스만 HP 상태바 그리기
            canvas.drawRect(HP_bar, HP_bar_color);
    }

    void move() {
        if (CreateType == TYPE_NORMAL) { // 일반 몬스터 이동 경로
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
        else if (CreateType == TYPE_BOSS) { // 보스 이동 경로
            if (m_y <= 70) m_y += m_speed;
            else
                state = STATE_STOP;
        }
    }

    //이전에 발사했던 시간을 저장해서 현재 시간과 이전에 발사했던 시간을 비교 해서 시간이 어느정도 흐르면 미사일을 다시 발사
    void attack() {
        Random random = new Random();
        if (CreateType == TYPE_NORMAL) { // 일반 몬스터이면
            //일정 간격을 두고 미사일 객체를 생성하고, GameState의 멤버 변수인 enemmlist에 추가
            //이를 위해 GameState를 AppManager에 추가해서 GameState를 전역 변수처럼 접근할 수 있게
            if (System.currentTimeMillis() - lastShoot_Normal >= random.nextInt(4000)+3000) { // 3s~7s마다 발사
                lastShoot_Normal = System.currentTimeMillis();
                AppManager.getInstance().getGameState().getEnemmsList().add(new Missile_Enemy(m_x+((this.width)-100)/2, m_y+height/2, 60, false));
            }
        }
        else if (CreateType == TYPE_BOSS && state == STATE_STOP) { // 보스이면
            if (System.currentTimeMillis() - lastShoot_Boss >= random.nextInt(3000)+5000) { // 5s~8s마다 발사
                lastShoot_Boss = System.currentTimeMillis();
                AppManager.getInstance().getGameState().getEnemmsList().add(new Missile_Enemy(m_x+((this.width)-200)/2, m_y+height/2, 120, true));
            }
        }
    }

    public int getHP() {
        return m_hp;
    }
    public Rect getHpBar() { return HP_bar; }
    public Paint getHPBarColor() {
        return HP_bar_color;
    }
    public int getSpeed() {
        return m_speed;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}