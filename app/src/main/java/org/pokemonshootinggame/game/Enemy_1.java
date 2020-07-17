package org.pokemonshootinggame.game;

import org.pokemonshootinggame.R;
import org.pokemonshootinggame.framework.AppManager;

public class Enemy_1 extends Enemy {
    public Enemy_1(int width, int speed) {
        super(AppManager.getInstance().getResizeBitmap(R.drawable.rocketdan_naong, width, (int)(width / Enemy.Naong_ImgProportion)));
        m_speed = speed;
        this.width = m_bitmap.getWidth();
        this.height = m_bitmap.getHeight();

        CreateType = TYPE_NORMAL;
    }
}