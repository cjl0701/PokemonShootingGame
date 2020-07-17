package org.pokemonshootinggame.game;

public class UnitFactory {
    public static Player createPlayer(int type){
        Player player = null;

        if(type==1)
            player=new Player_1();
        else if(type==2)
            player=new Player_2();
        else
            player = new Player_3();

        return player;
    }

    public static Enemy createEnemy(int type){
        Enemy enemy = null;

        if(type==1)
            enemy = new Enemy_1();
        else if(type==2)
            enemy = new Enemy_2();
        else
            enemy = new Enemy_3();

        return enemy;
    }
}
