package org.pokemonshootinggame.game;

import org.pokemonshootinggame.R;
import org.pokemonshootinggame.framework.AppManager;

public class _item_Life extends _item {
    public _item_Life(int x, int y)  {
        super (AppManager.getInstance( ).getBitmap(R.drawable.item2));
        this .initSpriteData(120, 120, 3, 4);
        m_x = x;
        m_y = y;
        m_hp = 10;
        m_speed = 3f;
    }
}
