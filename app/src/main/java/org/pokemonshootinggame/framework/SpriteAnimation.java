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
        // 게임 상의 시간을 받아 이전에 그렸던 시간과 비교해서 다음 이미지를 그릴 시간이 되면 프레임을 변경
        if (gameTime > m_frameTimer + m_fps) {
            m_frameTimer = gameTime;
            m_currentFrame += 1;
            //프레임의 순환
            if (m_currentFrame >= m_iFrames) m_currentFrame = 0;
        }
        //그릴 영역의 이동
        m_rect.left = m_currentFrame * m_spriteWidth;
        m_rect.right = m_rect.left + m_spriteWidth;
    }
}