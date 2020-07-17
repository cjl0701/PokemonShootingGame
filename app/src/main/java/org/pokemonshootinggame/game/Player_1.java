package org.pokemonshootinggame.game;

import android.graphics.Bitmap;

import org.pokemonshootinggame.R;
import org.pokemonshootinggame.framework.AppManager;

public class Player_1 extends Player {
    public Player_1() {
        super(AppManager.getInstance().getBitmap(R.drawable.pikachu));
        m_life=3;
        m_power=1;
        m_speed=10;
        m_msSpeed=10;
        evolved=false;
        width = m_bitmap.getWidth();
        height = m_bitmap.getHeight();
    }

    //이전에 발사했던 시간을 저장해서 현재 시간과 이전에 발사했던 시간을 비교 해서 시간이 어느정도 흐르면 미사일을 다시 발사
    public void attack() {
        //일정 간격을 두고 미사일 객체를 생성하고, GameState의 멤버 변수인 pmsList에 추가
        //이를 위해 GameState를 AppManager에 추가해서 GameState를 전역 변수처럼 접근할 수 있게
        if (System.currentTimeMillis() - lastShoot >= 3000) {
            lastShoot = System.currentTimeMillis();
            AppManager.getInstance().getGameState().getPmsList().add(new Missile_Player(this, m_x+30, m_y-50));
        }
    }

    @Override
    public Player evolve() { return new Player_1_evolved(m_life, m_x,m_y); }

    @Override
    public Bitmap getMsBitmap() {return AppManager.getInstance().getBitmap(R.drawable.thunder1); }
}
