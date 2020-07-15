package org.pokemonshootinggame.framework;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.pokemonshootinggame.game.GameState;

//이 프레임워크를 사용하는 애플리케이션을 관리
//AppManager를 통해 어느 클래스에서도 뷰와 리소스 등에 접근할 수 있도록
//SingleTon 패턴을 적용
public class AppManager {
    private static AppManager s_instance;

    private GameView m_gameView; //Main GameView
    private Resources m_resources; //Main GameView의 Resources
    private GameState m_gameState;

    private int displayWidth;
    private int displayHeight;

    private AppManager() { super(); } //외부에서 new 연산자로 인스턴스 생성 불가능

    public static AppManager getInstance() {
        if (s_instance == null)
            s_instance = new AppManager();
        return s_instance;
    }

    //자주 쓰이는 기능 추가
    public Bitmap getBitmap(int r) { return BitmapFactory.decodeResource(m_resources, r); }
    public int getDisplayWidth() { return displayWidth; }
    public int getDisplayHeight() { return displayHeight; }

    //Getter & Setter
    public GameView getGameView() {
        return m_gameView;
    }

    public void setGameView(GameView gameView) {
        this.m_gameView = gameView;
    }

    public Resources getResources() {
        return m_resources;
    }

    public void setResources(Resources resources) {
        this.m_resources = resources;
        displayHeight = m_resources.getDisplayMetrics().heightPixels;
        displayWidth = m_resources.getDisplayMetrics().widthPixels;
    }
    public GameState getGameState() { return m_gameState; }

    public void setGameState(GameState gameState) { this.m_gameState = gameState; }
}
