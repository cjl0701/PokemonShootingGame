package org.pokemonshootinggame.game;

import android.graphics.Bitmap;
import android.graphics.Rect;

import org.pokemonshootinggame.R;
import org.pokemonshootinggame.framework.AppManager;
import org.pokemonshootinggame.framework.SpriteAnimation;

public class SpecialAttack extends SpriteAnimation {
    Rect m_boundBox = new Rect();
    Player player;

    public SpecialAttack(Bitmap bitmap) {
        super(bitmap);
        player = UnitFactory.createPlayer(AppManager.getInstance().getPlayerType());

        this.initSpriteData(bitmap.getWidth() / 4, bitmap.getHeight(), 10, 4);

        mbReplay = false; //애니메이션이 반복하지 않게 함함

        m_x = 0;
        m_y = 0;
    }

    @Override
    public void update(long gameTime) {
        super.update(gameTime);
        m_boundBox.set(m_x, m_y, m_x + getWidth() / 4, m_y+getHeight());
    }
}
