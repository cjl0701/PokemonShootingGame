package org.pokemonshootinggame.game;

import android.graphics.Bitmap;

import org.pokemonshootinggame.R;
import org.pokemonshootinggame.framework.AppManager;

public class _item_Evolution extends _item {
    public _item_Evolution(int x, int y)  {
        super (AppManager.getInstance( ).getBitmap(R.drawable.item1));
        Bitmap item = AppManager.getInstance( ).getBitmap(R.drawable.item1);
        this .initSpriteData(120, 120, 5, 5);
        m_x = x;
        m_y = y;
        m_hp = 10;
        m_speed = 3f;
    }
}
