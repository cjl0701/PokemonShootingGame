package org.pokemonshootinggame.game;

import android.graphics.Bitmap;

import org.pokemonshootinggame.R;
import org.pokemonshootinggame.framework.AppManager;
import org.pokemonshootinggame.framework.SpriteAnimation;

//미사일과 적이 충돌했을 때 GameState 클래스의 CheckCollision 메서드에서 나타낼 효과
public class EffectExplosion extends SpriteAnimation {
    public EffectExplosion(int x, int y) {
        super(AppManager.getInstance().getBitmap(R.drawable.explosion));
        Bitmap image = AppManager.getInstance().getBitmap(R.drawable.explosion);
        this.initSpriteData(image.getWidth()/6, image.getHeight(), 5, 6);

        m_x=x;
        m_y=y;

        mbReplay = false; //애니메이션이 반복하지 않게 함함
    }
}
