package org.pokemonshootinggame.game;

import android.graphics.Bitmap;

import org.pokemonshootinggame.R;
import org.pokemonshootinggame.framework.AppManager;

public class ItemEvolution extends Item {
    protected Bitmap image;
    private int width, height;

    public ItemEvolution(int x, int y) {
        super(AppManager.getInstance().getBitmap(R.drawable.item1));
        image = AppManager.getInstance().getBitmap(R.drawable.item2);
        width = image.getWidth()/4;
        height = image.getHeight();
        this.initSpriteData(width, height, 5,4);

        m_x=x;
        m_y=y;
    }
    @Override
    public void update(long gameTime) {
        super.update(gameTime);
        m_boundBox.set(m_x,m_y,m_x+width,m_y+height);
    }

    @Override
    public void getItem(){
        //아이템을 얻으면 진화
        if(!AppManager.getInstance().getGameState().getPlayer().evolved)
            AppManager.getInstance().getGameState().changePlayerState();
        //이미 진화한 상태면 special attack 사용 횟수 증가시킴
        else
            AppManager.getInstance().getGameState().chargeSpecial();

    }
}
