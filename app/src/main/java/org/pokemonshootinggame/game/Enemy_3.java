package org.pokemonshootinggame.game;

import org.pokemonshootinggame.R;
import org.pokemonshootinggame.framework.AppManager;

public class Enemy_3 extends Enemy {
    public Enemy_3() {
        super(AppManager.getInstance().getBitmap(R.drawable.enemy3));

        hp=30;
        speed=1f;

        //애니메이션 정보 설정
        this.initSpriteData(m_bitmap.getWidth()/6,m_bitmap.getHeight(),5,6);

        width = m_bitmap.getWidth()/6;
        height = m_bitmap.getHeight();
    }

    @Override
    public void update(long gameTime) {
        super.update(gameTime); //프레임 변경, 이동
        m_boundBox.set(m_x+10, m_y, m_x +width-10, m_y+height); //이동할 때마다 박스 영역의 값을 갱신
    }
}
