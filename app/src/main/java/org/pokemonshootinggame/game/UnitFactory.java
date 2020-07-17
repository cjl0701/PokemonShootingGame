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
            enemy = new Enemy_1(130,4);
        else if(type==2)
            enemy = new Enemy_2(130,2);
        else if(type==3)
            enemy = new Enemy_3(100,3);
        else if(type==4)
            enemy = new Enemy_boss(250, 10, 8); // 보스의 객체는 하나, speed는 등장속도

        return enemy;
    }
}
