package org.pokemonshootinggame.game.enemy;

import org.pokemonshootinggame.R;
import org.pokemonshootinggame.framework.AppManager;

public class Enemy_3 extends Enemy {
    public Enemy_3(int width, int speed) {
        super(AppManager.getInstance().getResizeBitmap(R.drawable.enemy_roy, width, (int)(width / Enemy.Roy_ImgProportion)));
        m_speed = speed;
        this.width = m_bitmap.getWidth();
        this.height = m_bitmap.getHeight();

        CreateType = TYPE_NORMAL;
    }

}
