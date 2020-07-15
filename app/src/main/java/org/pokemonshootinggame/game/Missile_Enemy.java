package org.pokemonshootinggame.game;

import org.pokemonshootinggame.R;
import org.pokemonshootinggame.framework.AppManager;

public class Missile_Enemy extends Missile{
    private int width;
    private int height;
    private int displayHeight;

    public Missile_Enemy(int x, int y) {
        super(AppManager.getInstance().getBitmap(R.drawable.missile_2));
        this.setPosition(x, y);//x, y는 미사일 발사 위치

        width = m_bitmap.getWidth();
        height = m_bitmap.getHeight();
        displayHeight = AppManager.getInstance().getDisplayHeight();
    }

    public void update() {
        m_y += 5; //미사일이 아래로 발사되는 효과
        if (m_y > displayHeight) state = STATE_OUT; //화면 밖을 벗어나면 제거

        m_boundBox.set(m_x+10, m_y, m_x +width-10, m_y+height); //이동할 때마다 박스 영역의 값을 갱신
    }
}
