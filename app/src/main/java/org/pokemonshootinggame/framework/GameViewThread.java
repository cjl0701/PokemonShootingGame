package org.pokemonshootinggame.framework;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

//surfaceHolder의 callback이 작업할 스레드
public class GameViewThread extends Thread {
    //접근을 위한 멤버 변수
    private SurfaceHolder m_surfaceHolder;
    private GameView m_gameView;

    //스레드 실행 상태 멤버 변수
    private boolean m_run = false;

    public GameViewThread(SurfaceHolder surfaceHolder, GameView gameView) {
        this.m_surfaceHolder = surfaceHolder;
        this.m_gameView = gameView;
    }

    public void setRunning(boolean run){
        m_run = run;
    }

    @SuppressLint("WrongCall")
    @Override
    public void run() {
        Canvas canvas;
        while(m_run){
            canvas=null;
            try{
                m_gameView.update();
                //SurfaceHolder를 통해 Surface에 접근해서 가져옴
                canvas=m_surfaceHolder.lockCanvas(null);
                //동기화를 유지하면서 canvas에 ~을 버퍼에 그린다.
                synchronized (m_surfaceHolder){ //m_surfaceHolder을 기준으로 한 동기화 블록
                    m_gameView.onDraw(canvas); // 그림을 그림
                }
            }finally{
                //canvas의 내용을 view에 전송한다.
                if(canvas!=null)
                    m_surfaceHolder.unlockCanvasAndPost(canvas); //Surface를 화면에 표시
            }
        }
    }
}

