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
import org.pokemonshootinggame.game.gamestate.GameState_Stage1;

public class IntroState implements IState {
    private BackGround m_backGround;

    Bitmap bitmap;
    Bitmap bitmap2;
    Bitmap player_1;
    Bitmap player_2;
    Bitmap player_3;

    int x, y;
    int score;
    int frequency;
    SQLiteDatabase db = AppManager.getInstance().getDBHelper().getWritableDatabase(); //gameview에서 생성한 객채 가져오기

    @Override
    public void init() {
        bitmap = AppManager.getInstance().getBitmap(R.drawable.background_pkm);
        bitmap2 = AppManager.getInstance().getBitmap(R.drawable.background_pkm2);

        player_1 = AppManager.getInstance().getBitmap(R.drawable.player_1);
        player_2 = AppManager.getInstance().getBitmap(R.drawable.player_2);
        player_3 = AppManager.getInstance().getBitmap(R.drawable.player_3);

        // db 데이터를 가져오는 부분
        try {
            Cursor cursor = db.query("RankBoard", new String[]{"PK", "score"}, null, null, null, null, "score");

            if (cursor.moveToFirst() == false) {
                score = 0;
                frequency = 0;
            } else {
                cursor.moveToLast();
                frequency = cursor.getCount();
                score = cursor.getInt(1);
            }
        } catch (Exception e3) {
            System.out.println("실패 : " + e3);
        }
        //SoundManager.getInstance().addSound(1, R.raw.introbackground);
        //SoundManager.getInstance().play(1);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void update() {
    }

    @Override
    public void render(Canvas canvas) {
        int width = AppManager.getInstance().getDisplayWidth();
        int height = AppManager.getInstance().getDisplayHeight();
        int width_player = player_3.getWidth();
        int height_player = player_3.getHeight();


        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.BLACK);

        bitmap = Bitmap.createScaledBitmap(bitmap, width, 400, true);
        bitmap2 = Bitmap.createScaledBitmap(bitmap2, width, height, true);

        player_1 = Bitmap.createScaledBitmap(player_1, width_player, height_player, true);
        player_2 = Bitmap.createScaledBitmap(player_2, width_player, height_player, true);
//        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap2, 0, 0, null);
        canvas.drawBitmap(bitmap, 0, 50, null);
        canvas.drawText("플레이어 선택", 270, 650, paint);

        canvas.drawBitmap(player_1, width / 6 - 120, 800, null);
        canvas.drawBitmap(player_2, width / 6 * 3 - 120, 800, null);
        canvas.drawBitmap(player_3, width / 6 * 5 - 120, 800, null);

        canvas.drawText("개인 최고 기록 :  " + score, 100, 1300, paint);
        canvas.drawText("시도 횟수 :" + frequency, 100, 1500, paint);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Canvas canvas = new Canvas();
            int width = canvas.getWidth();

            int width_player = player_1.getWidth();
            int height_player = player_1.getHeight();

            int px = (int) event.getX();
            int py = (int) event.getY();

            Rect button_1 = new Rect(180 - 120, 800, 180 - 120 + width_player, 800 + height_player);
            Rect button_2 = new Rect(180 * 3 - 120, 800, 180 * 3 - 120 + width_player, 800 + height_player);
            Rect button_3 = new Rect(180 * 5 - 120, 800, 180 * 5 - 120 + width_player, 800 + height_player);

            if (button_1.contains(px, py)) {
                AppManager.getInstance().setPlayerType(1);
                AppManager.getInstance().getGameView().changeGameState(new GameState_Stage1());
            }
            if (button_2.contains(px, py)) {
                AppManager.getInstance().setPlayerType(2);
                AppManager.getInstance().getGameView().changeGameState(new GameState_Stage1());
            }
            if (button_3.contains(px, py)) {
                AppManager.getInstance().setPlayerType(3);
                AppManager.getInstance().getGameView().changeGameState(new GameState_Stage1());
            }
        }
        return true;
    }
}