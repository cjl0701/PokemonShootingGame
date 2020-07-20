package org.pokemonshootinggame.game.player;

import android.graphics.Bitmap;

import org.pokemonshootinggame.R;
import org.pokemonshootinggame.framework.AppManager;
import org.pokemonshootinggame.game.missile.Missile_Player;
import org.pokemonshootinggame.game.SpecialAttack;

public class Player_3 extends Player {
    public Player_3() {
        super(AppManager.getInstance().getBitmap(R.drawable.firi));
        m_life = 3;
        m_power = 1;
        m_speed = 15;
        m_msSpeed = 15;
        m_msTerm = 1500;
        evolved=false;
        width = m_bitmap.getWidth();
        height = m_bitmap.getHeight();
    }

    //현재 동일하지만 캐릭터별로 속도 등 조절 가능
    @Override
    public void attack() {
        if (System.currentTimeMillis() - lastShoot >= m_msTerm) {
            lastShoot = System.currentTimeMillis();
            AppManager.getInstance().getGameState().getPmsList().add(new Missile_Player(this, m_x+30, m_y-50));
        }
    }

    @Override
    public Player evolve() { return new Player_3_evolved(m_life, m_x,m_y); }

    @Override
    public Bitmap getMsBitmap() {return AppManager.getInstance().getBitmap(R.drawable.fire1); }//미사일 비트맵

    //1단계 필살기 확장 가능. 이미지 없어서 일단 생략
    @Override
    public SpecialAttack getSpecial() {
        return new SpecialAttack(AppManager.getInstance().getBitmap(R.drawable.thunderbomb));
    }
}