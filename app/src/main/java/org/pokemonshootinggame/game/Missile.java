package org.pokemonshootinggame.game;

import android.graphics.Bitmap;
import android.graphics.Rect;

import org.pokemonshootinggame.framework.GraphicObject;

public class Missile extends GraphicObject {
    public static final int STATE_NORMAL = 0;
    public static final int STATE_OUT = 1;
    public int state = STATE_NORMAL;
    Rect m_boundBox = new Rect();

    public Missile(Bitmap bitmap) {
        super(bitmap);
    }

}

