package org.pokemonshootinggame.game;

import org.pokemonshootinggame.framework.AppManager;

public class UnitFactory {
    static int m_stage;
    static Player m_player = null;

    //싱글톤 패턴
    public static Player createPlayer(int type){
        if(m_player == null){
            if(type==1)
                m_player=new Player_1();
            else if(type==2)
                m_player=new Player_2();
            else
                m_player = new Player_3();
        }

        return m_player;
    }

    public static Enemy createEnemy(int type){
        Enemy enemy = null;
        if(type==1)
            enemy = new Enemy_1(130,m_stage+4);
        else if(type==2)
            enemy = new Enemy_2(130,m_stage+2);
        else if(type==3)
            enemy = new Enemy_3(100,m_stage+3);
        else if(type==4)
            enemy = new Enemy_boss(250+50*m_stage, 10+10*m_stage, 8+3*m_stage); // 보스의 객체는 하나, speed는 등장속도

        return enemy;
    }

    public static Item createItem(int type, int x, int y) {
        Item item = null;

        if(type == 0)
            item = new ItemAddLife(x, y);
        else if(type == 1)
            item = new ItemEvolution(x, y);

        return item;
    }

    public static void updateStage(){
        m_stage = AppManager.getInstance().getStage();
    }

    public static void setPlayer(Player player) { m_player = player; }
}
