package org.pokemonshootinggame.framework;

import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

public interface IState {
    public void init(); //상태가 생성되었을 때
    public void destroy(); //상태가 소멸될 때
    public void update(); //지속적으로 수행할 것들
    public void render(Canvas canvas); //그려야 할 것들
    public boolean onKeyDown(int keyCode, KeyEvent event); //키 입력 처리
    public boolean onTouchEvent(MotionEvent event); //터치 입력 처리
}
