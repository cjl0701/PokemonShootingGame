package org.pokemonshootinggame.game;

import android.graphics.Bitmap;

import org.pokemonshootinggame.R;
import org.pokemonshootinggame.framework.AppManager;

public class Player_1_evolved extends Player {
    public Player_1_evolved(int life, int x, int y) {
        super(AppManager.getInstance().getBitmap(R.drawable.lightu));
        setPosition(x,y);
        m_life=life;
        m_power=2;
        m_speed=10;
        m_msSpeed=20;
        evolved=true;
        width = m_bitmap.getWidth();
        height = m_bitmap.getHeight();
    }

    @Override
    public void attack() { //짧은 시간 텀, 여러 갈래로 쏘거나, 충돌에도 사라지지 않고 지속적 공격
        if (System.currentTimeMillis() - lastShoot >= 1000) {
            lastShoot = System.currentTimeMillis();
            AppManager.getInstance().getGameState().getPmsList().add(new Missile_Player(this,m_x+30, m_y-50));
        }
    }

    @Override
    public Player evolve() {
        return this;
    } //진화x

    @Override
    public Bitmap getMsBitmap() {return AppManager.getInstance().getBitmap(R.drawable.thunder2); }
}
