package org.pokemonshootinggame.game;

import android.graphics.Rect;

//충돌에 대한 로직을 담당
public class CollisionManager {

    public static boolean checkBoxToBox(Rect rt1, Rect rt2){
        if(Rect.intersects(rt1, rt2))
            return true;// 충돌이면
        return false;
    }
}
