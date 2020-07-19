package org.pokemonshootinggame.game;

import org.pokemonshootinggame.framework.AppManager;

public class GameState_Stage1 extends GameState{
    public GameState_Stage1() {
        m_stage = STAGE1;
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