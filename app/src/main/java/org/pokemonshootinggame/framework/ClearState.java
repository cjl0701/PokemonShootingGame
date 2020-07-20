package org.pokemonshootinggame.framework;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;

import org.pokemonshootinggame.R;
import org.pokemonshootinggame.game.gamestate.GameState_Stage2;
import org.pokemonshootinggame.game.gamestate.GameState_Stage3;

public class ClearState implements IState{
    static final int STAGE_1 = 1;
    static final int STAGE_2 = 2;
    static final int STAGE_3 = 3;


    Bitmap bitmap;
    SQLiteDatabase db = AppManager.getInstance().getDBHelper().getWritableDatabase();
    public void init(){
        bitmap =  AppManager.getInstance().getBitmap(R.drawable.clearstage);

    } //상태가 생성되었을 때
    public void destroy(){

    } //상태가 소멸될 때
    public void update(){

    } //지속적으로 수행할 것들
    public void render(Canvas canvas){
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        Paint p=new Paint();
        p.setTextSize(100);
        p.setColor(Color.WHITE);
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.drawText("STAGE\n CLEAR" , 200, 250, p);
        canvas.drawText("현재 기록 : " +AppManager.getInstance().getCount() , 250, 400, p);

    } //그려야 할 것들
    public boolean onKeyDown(int keyCode, KeyEvent event){
        return false;
    } //키 입력 처리
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_UP){
            if(AppManager.getInstance().getStage()==STAGE_1)
                AppManager.getInstance().getGameView().changeGameState(new GameState_Stage2());
            else if(AppManager.getInstance().getStage()==STAGE_2)
                AppManager.getInstance().getGameView().changeGameState(new GameState_Stage3());
            else if(AppManager.getInstance().getStage()==STAGE_3)
                AppManager.getInstance().getGameView().changeGameState(new EndState());
        }

        return true;
    } //터치 입력 처리

}
