package org.pokemonshootinggame.game;

import org.pokemonshootinggame.R;
import org.pokemonshootinggame.framework.AppManager;

public class Enemy_3 extends Enemy {
    public Enemy_3() {
        super(AppManager.getInstance().getBitmap(R.drawable.tempenemy3));
        m_hp=30;
        m_speed=1f;
        width = m_bitmap.getWidth();
        height = m_bitmap.getHeight();
    }
}
