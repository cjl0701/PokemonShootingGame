package org.pokemonshootinggame.framework;

import android.content.Context;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import org.pokemonshootinggame.game.GameState;

//SurfaceView를 이용한 빠른 그래픽 처리 기반. 화면 업데이트를 백그라운드에서 처리
//SurfaceHolder가 Surface에 미리 그리고 이 Surface가 SurfaceView에 반영되는 방식
//SurfaceHolder.Callback의 순수 가상 메서드 구현 필요. 실제 작업 수행은 SurfaceHolder.Callback이 수행.
public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private IState m_state; //state 패턴에 쓰이는 현재 상태
    private GameViewThread m_thread; //뷰에 렌더링할 스레드. SurfaceHolder의 callback 에서 이 thread를 사용해서 작업을 수행
    private GraphicObject m_image; //그림을 그리는 클래스
    private SpriteAnimation m_spr; //애니메이션 구성

    public GameView(Context context) {
        super(context);

        setFocusable(true); //키 입력 처리를 받기 위해서

        AppManager.getInstance().setGameView(this);
        AppManager.getInstance().setResources(getResources());

        getHolder().addCallback(this); // SurfaceHolder에 이 클래스에 있는 callback을 등록

        changeGameState(new GameState()); //실행 테스트

        m_thread = new GameViewThread(getHolder(), this);

        /*//배경화면
        int displayWidth = context.getResources().getDisplayMetrics().widthPixels;
        Bitmap bitmap = AppManager.getInstance().getBitmap(R.drawable.background2);
        bitmap = Bitmap.createScaledBitmap(bitmap, displayWidth, bitmap.getHeight(), true);
        m_image = new GraphicObject(bitmap);*/


        /*//스프라이트 이미지
        bitmap = AppManager.getInstance().getBitmap(R.drawable.item1);
        m_spr = new SpriteAnimation(bitmap);
        m_spr.initSpriteData(bitmap.getWidth() / 4, bitmap.getHeight(), 5, 4);
         */
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.drawColor(Color.BLACK);
        //m_image.draw(canvas);
        m_state.render(canvas);
        //m_spr.draw(canvas);
    }

    //데이터의 자동 갱신
    //update 메서드를 스레드에서 지속적으로 실행해야만 갱신이 수행되므로
    //GameViewThread의 run 메서드에서 update() 메서드를 실행하게 함
    //화면과 관련된 이벤트가 발생하지 않아도 게임 루프가 계속 돌아가는 기반
    public void update() {
        m_state.update();
        //long gameTime = System.currentTimeMillis();
        //m_spr.update(gameTime);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        //surface가 생성되면 Rendering할 Thread를 시작
        //스레드를 실행 상태로 만든다.
        m_thread.setRunning(true);
        //스레드 실행
        m_thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        //스레드를 종료
        boolean retry = true;
        m_thread.setRunning(false);
        while (retry) {
            //스레드를 중지시킨다.
            try {
                m_thread.join();//m_thread의 종료까지 기다림(일시 정지)
                retry = false;
            } catch (InterruptedException e) {
                //스레드가 종료되도록 계속 시도
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return m_state.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return m_state.onTouchEvent(event);
    }

    public void changeGameState(IState state) {
        if (m_state != null)
            m_state.destroy();
        state.init();
        m_state = state;
    }
}
