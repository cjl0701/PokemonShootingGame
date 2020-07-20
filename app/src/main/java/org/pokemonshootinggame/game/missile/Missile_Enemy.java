package org.pokemonshootinggame.game.missile;

import org.pokemonshootinggame.R;
import org.pokemonshootinggame.framework.AppManager;
import org.pokemonshootinggame.game.UnitFactory;

public class Missile_Enemy extends Missile {
    // 미사일 이미지 폭, 높이
    private int width;
    private int height;
    private int displayHeight;

    private boolean isBoss;

    public Missile_Enemy(int x, int y, int width, boolean isBoss) {
        super(AppManager.getInstance().getResizeBitmap(R.drawable.pokeball3, width, (int)(width / Missile.Enemy_ImgProportion)));
        this.setPosition(x, y);//x, y는 미사일 발사 위치

        this.width = m_bitmap.getWidth();
        this.height = m_bitmap.getHeight();
        displayHeight = AppManager.getInstance().getDisplayHeight();

        this.isBoss = isBoss;
    }

    public void update() {
        int level = AppManager.getInstance().getStage();
        //이동할 때마다 박스 영역의 값을 갱신
        //화면 밖을 벗어나면 제거
        if (isBoss) { // 보스 미사일인 경우
            // 보스 미사일은 플레이어를 따라감
            if (this.m_x+this.width/2 > UnitFactory.createPlayer(1).getX()+
                    UnitFactory.createPlayer(1).getWidth()/2)
                m_x += -1;
            else
                m_x += 1;
            m_y += 3+level; //미사일이 아래로 발사되는 효과
        }
        else { // 일반 몬스터인 경우
            m_y += 5+level; //미사일이 아래로 발사되는 효과
        }
        if (m_y > displayHeight) state = STATE_OUT; //화면 밖을 벗어나면 제거
        m_boundBox.set(m_x, m_y, m_x + width, m_y + height); //이동할 때마다 박스 영역의 값을 갱신
    }
}