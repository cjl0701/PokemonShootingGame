package org.pokemonshootinggame.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

import org.pokemonshootinggame.R;

public class IntroState implements IState {
    Bitmap icon;
    int x, y;

    @Override
    public void init() {
        icon = AppManager.getInstance().getBitmap(R.drawable.icon);
    }

    @Override
    public void destroy() { }

    @Override
    public void update() { }

    @Override
    public void render(Canvas canvas) {
        int width = canvas.getWidth();
        int height = icon.getHeight();
        icon = Bitmap.createScaledBitmap(icon, width, height, true);
        canvas.drawBitmap(icon, x, y, null);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        AppManager.getInstance().getGameView().changeGameState(new CreditState());
        return true;
    }
}
