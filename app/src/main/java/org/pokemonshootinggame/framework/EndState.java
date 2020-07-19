package org.pokemonshootinggame.framework;

import android.content.ContentValues;
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
import org.pokemonshootinggame.game.Enemy_boss;
import org.pokemonshootinggame.game.UnitFactory;

public class EndState implements IState {
    Bitmap gameover;
    Bitmap m_Restart;
    Bitmap background;
    int x, y;
    SQLiteDatabase db =AppManager.getInstance().getDBHelper().getWritableDatabase(); //db SQLite 생성

    @Override
    public void init() {
        gameover = AppManager.getInstance().getBitmap(R.drawable.gameover);
        m_Restart = AppManager.getInstance().getBitmap(R.drawable.restart);
        background = AppManager.getInstance().getBitmap(R.drawable. end_background);

        //db 데이터 insert 부분
        Cursor cursor = db.query(  "RankBoard",new String[]{ "PK", "score" },null, null, null, null, null );
        cursor.moveToLast();
        ContentValues row = new ContentValues( );
        row.put ("PK", cursor.getCount()); // name
        row.put ("score", AppManager.getInstance().getCount()); // score

        // 데이터베이스에 점수를 기록
        db.insert ( "RankBoard", null, row );
        db.close();
    }

    @Override
    public void destroy() {
        AppManager.getInstance().setCount(0);
        AppManager.getInstance().setStage(0);
        AppManager.getInstance().setPlayerType(0);
        Enemy_boss.m_stage=0;
    }

    @Override
    public void update() { }

    @Override
    public void render(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        Paint p = new Paint();
        p.setTextSize(100);
        p.setColor(Color.BLACK);

        background = Bitmap.createScaledBitmap(background, width, height ,true);
        gameover = Bitmap.createScaledBitmap(gameover, 500, 500 ,true);
        m_Restart = Bitmap.createScaledBitmap(m_Restart, 150, 150 ,true);
        canvas.drawBitmap(background,0,0,null);
        canvas.drawBitmap(gameover, 270, 550, null);
        canvas.drawText("적 처치수 : "+AppManager.getInstance().getCount(), 150, 1300, p);
        canvas.drawBitmap(m_Restart, 800, 1400, null);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int px = (int)event.getX( );
        int py = (int)event.getY( );

        Rect button = new Rect(800,1400,950,1550);

        if(event.getAction() == MotionEvent.ACTION_UP && button.contains(px,py))
        {
            UnitFactory.setPlayer(null);
            AppManager.getInstance().getGameView().changeGameState(new IntroState());
        }
        return true;
    }
}