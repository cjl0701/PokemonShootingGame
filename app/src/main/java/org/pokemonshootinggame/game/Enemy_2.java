package org.pokemonshootinggame.game;

import org.pokemonshootinggame.R;
import org.pokemonshootinggame.framework.AppManager;

public class Enemy_2 extends Enemy {
    public Enemy_2() {
        super(AppManager.getInstance().getBitmap(R.drawable.tempenemy2));
        hp=20;
        speed=2f;
        width = m_bitmap.getWidth();
        height = m_bitmap.getHeight();
    }

    @Override
    public void update(long gameTime) {
        super.update(gameTime); //프레임 변경, 이동
        m_boundBox.set(m_x+10, m_y, m_x +width-10, m_y+height); //이동할 때마다 박스 영역의 값을 갱신
    }
}
