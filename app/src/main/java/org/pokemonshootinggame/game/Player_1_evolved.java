package org.pokemonshootinggame.game;

import android.graphics.Bitmap;

import org.pokemonshootinggame.R;
import org.pokemonshootinggame.framework.AppManager;

public class Player_1_evolved extends Player {

    public Player_1_evolved() {
        super(AppManager.getInstance().getBitmap(R.drawable.tempplayer));
    }

    @Override
    public void attack() { //짧은 시간 텀, 여러 갈래로 쏘거나, 충돌에도 사라지지 않고 지속적 공격

    }

    @Override
    public Player evolve() {
        return null;
    } //진화x
}
