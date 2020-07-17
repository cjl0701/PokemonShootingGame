package org.pokemonshootinggame.framework;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.MotionEvent;

import org.pokemonshootinggame.R;
import org.pokemonshootinggame.game.BackGround;
//import org.pokemonshootinggame.game.DBHelper;
import org.pokemonshootinggame.game.GameState;
import org.pokemonshootinggame.framework.AppManager;
import org.pokemonshootinggame.game.Player_1;

public class IntroState implements IState {
    private BackGround m_backGround;

    Bitmap bitmap;
    Bitmap player_1;
    Bitmap player_2;
    Bitmap player_3;
    int x, y;
    // DBHelper db = AppManager.getInstance().getGameActivity().getDB();


    @Override
    public void init() {
        bitmap =  AppManager.getInstance().getBitmap(R.drawable.background_pkm);
        player_1 =  AppManager.getInstance().getBitmap(R.drawable.player_1);
        player_2 =  AppManager.getInstance().getBitmap(R.drawable.player_2);
        player_3 =  AppManager.getInstance().getBitmap(R.drawable.player_3);
    }

    @Override
    public void destroy() { }

    @Override
    public void update() { }

    @Override
    public void render(Canvas canvas) {
        int width = AppManager.getInstance().getDisplayWidth();
        int width_player = player_3.getWidth();
        int height_player = player_3.getHeight();
        //Cursor cursor = db.query(  new String[]{ "name", "score" },null, null, null, null, "socre");
        //cursor.moveToFirst();

        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.BLACK);

        bitmap = Bitmap.createScaledBitmap(bitmap, width, 400, true);
        player_1 =Bitmap.createScaledBitmap(player_1, width_player, height_player, true);
        player_2 = Bitmap.createScaledBitmap(player_2, width_player, height_player, true);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 50, null);
        canvas.drawText("플레이어 선택"+width/6 , 270, 650, paint);

        canvas.drawBitmap(player_1,width/6-120, 800,null);
        canvas.drawBitmap(player_2,width/6*3-120, 800,null);
        canvas.drawBitmap(player_3,width/6*5-120, 800,null);

        canvas.drawText("개인 최고 기록 :  "/*+cursor.getString(1)*/ , 100, 1300, paint);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Canvas canvas= new Canvas();
        int width = canvas.getWidth();

        int width_player = player_1.getWidth();
        int height_player = player_1.getHeight();

        int px = (int)event.getX( );
        int py = (int)event.getY( );

        Rect button_1 = new Rect(180-120,800,180-120+width_player,800+height_player);
        Rect button_2 = new Rect(180*3-120,800,180*3-120+width_player,800+height_player);
        Rect button_3 = new Rect(180*5-120,800,180*5-120+width_player,800+height_player);

        if(button_1.contains(px,py))
        {
            AppManager.getInstance().setPlayerType(1);
            AppManager.getInstance().getGameView().changeGameState(new GameState());
        }
        if(button_2.contains(px,py))
        {
            AppManager.getInstance().setPlayerType(2);
            AppManager.getInstance().getGameView().changeGameState(new GameState());
        }
        if(button_3.contains(px,py))
        {
            //AppManager.getInstance().getGameState().setPlayer(new Player_1()); //Player_1,2,3가 나오면 수정 필요!!!!!!!!!!!
            AppManager.getInstance().setPlayerType(3);
            AppManager.getInstance().getGameView().changeGameState(new GameState());
        }

        return true;
    }
}