package org.pokemonshootinggame.game;

import android.graphics.Bitmap;

import org.pokemonshootinggame.R;
import org.pokemonshootinggame.framework.AppManager;

public class ItemAddLife extends Item {
    protected Bitmap image;
    private int width, height;

    //적이 파괴되고 나서 생성되는 아이템이니 적이 파괴된 위치 값을 생성자의 인자로 받아오게 함
    public ItemAddLife(int x, int y) {
        super(AppManager.getInstance().getBitmap(R.drawable.item2));
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
        AppManager.getInstance().getGameState().getPlayer().m_life++;
    }
}
