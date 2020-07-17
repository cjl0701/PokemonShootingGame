package org.pokemonshootinggame.game;

import android.graphics.Bitmap;
import android.graphics.Rect;

import org.pokemonshootinggame.framework.GraphicObject;

public class Missile extends GraphicObject {
    public static final int STATE_NORMAL = 0;
    public static final int STATE_OUT = 1;
    public int state = STATE_NORMAL;
    Rect m_boundBox = new Rect();
    // 이미지 비율(width / height)
    public static final float Enemy_ImgProportion = 1f;

    public Missile(Bitmap bitmap) {
        super(bitmap);
    }

}

