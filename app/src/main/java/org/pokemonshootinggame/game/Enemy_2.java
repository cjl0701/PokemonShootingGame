package org.pokemonshootinggame.game;

import org.pokemonshootinggame.R;
import org.pokemonshootinggame.framework.AppManager;

public class Enemy_2 extends Enemy {
    public Enemy_2() {
        super(AppManager.getInstance().getBitmap(R.drawable.tempenemy2));
        m_hp=20;
        m_speed=2f;
        width = m_bitmap.getWidth();
        height = m_bitmap.getHeight();
    }
}
