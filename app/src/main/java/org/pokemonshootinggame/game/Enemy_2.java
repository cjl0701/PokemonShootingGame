package org.pokemonshootinggame.game;

import org.pokemonshootinggame.R;
import org.pokemonshootinggame.framework.AppManager;

public class Enemy_2 extends Enemy {
    public Enemy_2(int width, int speed) {
        super(AppManager.getInstance().getResizeBitmap(R.drawable.enemy_rosa, width, (int)(width / Enemy.Rosa_ImgProportion)));
        m_speed = speed;
        this.width = m_bitmap.getWidth();
        this.height = m_bitmap.getHeight();

        CreateType = TYPE_NORMAL;
    }
}
