package org.pokemonshootinggame.game.item;

import android.graphics.Bitmap;
import android.graphics.Rect;

import org.pokemonshootinggame.framework.AppManager;
import org.pokemonshootinggame.framework.SpriteAnimation;

public abstract class Item extends SpriteAnimation {
    Rect m_boundBox = new Rect();
    public boolean bOut = false;

    public Item(Bitmap bitmap) {
        super(bitmap);
    }

    @Override
    public void update(long gameTime){
        super.update(gameTime);
        m_y+=2;
        if(m_y > AppManager.getInstance().getDisplayHeight()) bOut = true;
    }


    public abstract void getItem(); //플레이어가 아이템과 충돌했을 때 실행할 메서드

    public Rect getBoundBox(){
        return m_boundBox;
    }
}
