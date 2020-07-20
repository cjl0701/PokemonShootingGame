package org.pokemonshootinggame.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import org.pokemonshootinggame.R;
import org.pokemonshootinggame.framework.AppManager;
import org.pokemonshootinggame.framework.GraphicObject;

public class BackGround extends GraphicObject {
    static final float SCROLL_SPEED = 1.5f; //기존 0.5f
    private float m_scroll = -2000 + 480;

    Bitmap m_layer2;
    static final float SCROLL_SPEED_2 = 1.5f;//기존 0.5f
    private float m_scroll_2 = -2000 + 480;

    public BackGround(int backType) {
        super(null);

        int displayWidth = AppManager.getInstance().getDisplayWidth();
        Bitmap bitmap;

        if (backType == 0)
            bitmap = AppManager.getInstance().getBitmap(R.drawable.background1);
        else //else if(backType==1)
            bitmap = AppManager.getInstance().getBitmap(R.drawable.background2);
        m_bitmap = Bitmap.createScaledBitmap(bitmap, displayWidth, bitmap.getHeight(), true);

        bitmap = AppManager.getInstance().getBitmap(R.drawable.background_2);
        m_layer2 = Bitmap.createScaledBitmap(bitmap, displayWidth, bitmap.getHeight(), true);

        setPosition(0, (int) m_scroll);
    }

    //배경 이미지를 이동
    public void update(long gameTime) {
        //플레이어 캐릭터가 위로 이동하는 느낌을 표현하기 위해 배경 이미지를 지속적으로 Y 값을 증가
        m_scroll = m_scroll + SCROLL_SPEED;
        //스탑 스크롤 방식 : 스크롤의 끝에 가면 더 이상 스크롤링을 하지 않음
        if (m_scroll >= 0) m_scroll = 0;
        setPosition(0, (int) m_scroll);

        m_scroll_2 = m_scroll_2 + SCROLL_SPEED_2;
        if (m_scroll_2 >= 0) m_scroll_2 = 0;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(m_bitmap, m_x, m_y, null);
        canvas.drawBitmap(m_layer2, m_x, m_scroll_2, null);
    }

    public float getScroll(){
        return m_scroll;
    }
}
