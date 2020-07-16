package org.pokemonshootinggame.game;

import org.pokemonshootinggame.R;
import org.pokemonshootinggame.framework.AppManager;

public class Enemy_1 extends Enemy {
    public Enemy_1() {
        super(AppManager.getInstance().getBitmap(R.drawable.tempenemy1));
        m_hp = 10;
        m_speed = 3f;
        width = m_bitmap.getWidth();
        height = m_bitmap.getHeight();
    }
}
