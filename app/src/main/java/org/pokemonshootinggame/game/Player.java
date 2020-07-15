package org.pokemonshootinggame.game;

import android.graphics.Bitmap;
import android.graphics.Rect;

import org.pokemonshootinggame.framework.AppManager;
import org.pokemonshootinggame.framework.GraphicObject;
import org.pokemonshootinggame.framework.SpriteAnimation;

public class Player extends GraphicObject {
    Rect m_boundBox;
    private int m_life=3;

    private int width;
    private int height;

    public Player(Bitmap bitmap) {
        super(bitmap);

        //초기 위치 값을 설정
        int displayWidth = AppManager.getInstance().getDisplayWidth();
        int displayHeight = AppManager.getInstance().getDisplayHeight();
        this.setPosition((displayWidth-bitmap.getWidth())/2, (int)(displayHeight*0.8));

        m_boundBox = new Rect();
        width = m_bitmap.getWidth();
        height = m_bitmap.getHeight();
    }

    //프레임워크 update에서 지속적으로 호출할 메서드
    public void update(long gameTime) {
        m_boundBox.set(m_x+10, m_y, m_x +width-10, m_y+height); //이동할 때마다 박스 영역의 값을 갱신
    }

    public int getLife(){ return m_life; }
    public void addLife(){ m_life++; }
    public void destroyPlayer(){ m_life--; }
}