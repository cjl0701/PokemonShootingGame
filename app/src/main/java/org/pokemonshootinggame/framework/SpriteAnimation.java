package org.pokemonshootinggame.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

//게임에서 움직임을 표현하는 SpriteAnimation
public class SpriteAnimation extends GraphicObject {
    private Rect m_rect; //그려줄 사각 영역
    private int m_fps; //초당 프레임
    private int m_iFrames; //프레임 개수
    private long m_frameTimer;
    private int m_currentFrame; //최근 프레임
    private int m_spriteWidth; //개별 프레임의 넓이
    private int m_spriteHeight; //개별 프레임의 높이
    protected boolean mbReplay = true; //반복 여부 판별
    protected boolean mbEnd = false; //애니메이션 종료 정보 저장

    public SpriteAnimation(Bitmap bitmap) {
        super(bitmap);
        m_rect = new Rect(0, 0, 0, 0);
        m_frameTimer = 0;
        m_currentFrame = 0;
    }

    public void initSpriteData(int width, int height, int fps, int iFrame) {
        m_spriteWidth = width;
        m_spriteHeight = height;
        m_rect.top = 0;
        m_rect.bottom = m_spriteHeight;
        m_rect.left = 0;
        m_rect.right = m_spriteWidth;
        m_fps = 1000 / fps; //밀리초 단위 프레임
        m_iFrames = iFrame;
    }

    @Override
    public void draw(Canvas canvas) {
        Rect dest = new Rect(m_x, m_y, m_x + m_spriteWidth, m_y + m_spriteHeight);
        canvas.drawBitmap(m_bitmap, m_rect, dest, null);
    }

    //시간이 지남에 따라 그려야 하는 프레임을 바꿈
    public void update(long gameTime) {
        if (!mbEnd) { //애니메이션이 종료되지 않았다면
            //게임 상의 시간을 받아 이전에 그렸던 시간과 비교해서 다음 이미지를 그릴 시간이 되면 프레임을 변경
            if (gameTime > m_frameTimer + m_fps) {
                m_frameTimer = gameTime;
                m_currentFrame += 1;

                //프레임의 순환
                if (m_currentFrame >= m_iFrames) {
                    if (mbReplay) //mbReplay가 true일 때만 마지막 프레임까지 도달했을 때 다시 첫 번째 프레임으로 바꿔 애니메이션을 반복
                        m_currentFrame = 0;
                    else //true가 아닐 때 애니메이션이 종료되면 mbEnd를 true로 변경해서 애니메이션이 종료되었다는 정보를 저장
                        mbEnd = true;
                }
            }
            //그릴 영역의 이동
            m_rect.left = m_currentFrame * m_spriteWidth;
            m_rect.right = m_rect.left + m_spriteWidth;
        }
    }

    public boolean getAnimationEnd(){ return mbEnd; }
}