package org.pokemonshootinggame.game.gamestate;

import org.pokemonshootinggame.framework.AppManager;

public class GameState_Stage3 extends GameState{
    public GameState_Stage3() {
        m_stage = STAGE3;
        AppManager.getInstance().setGameState(this);
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
