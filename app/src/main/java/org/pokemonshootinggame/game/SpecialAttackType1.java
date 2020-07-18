package org.pokemonshootinggame.game;

import android.graphics.Bitmap;

import org.pokemonshootinggame.R;
import org.pokemonshootinggame.framework.AppManager;
import org.pokemonshootinggame.framework.SpriteAnimation;

public class SpecialAttackType1 extends SpriteAnimation {
    public SpecialAttackType1() {
        super(AppManager.getInstance().getBitmap(R.drawable.specialattack1));
        Bitmap image = AppManager.getInstance().getBitmap(R.drawable.specialattack1);
        this.initSpriteData(image.getWidth()/5, image.getHeight(), 5, 6);

        m_x=(AppManager.getInstance().getDisplayWidth()-image.getWidth()/6)/2;
        m_y=(AppManager.getInstance().getDisplayHeight()-image.getHeight())/2;

        mbReplay = true;
    }
}
