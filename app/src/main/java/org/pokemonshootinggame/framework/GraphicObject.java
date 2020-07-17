package org.pokemonshootinggame.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;

//그림을 표시하는 데 필요한 멤버 변수와 처리 과정을 하나의 클래스로 만들면 편리
//그래픽을 쉽게 그리기 위한 GraphicObject
public class GraphicObject {
    protected Bitmap m_bitmap;
    protected int m_x;
    protected int m_y;

    public GraphicObject(Bitmap bitmap) {
        this.m_bitmap = bitmap;
        m_x=0;
        m_y=0;
    }

    public void draw(Canvas canvas){ canvas.drawBitmap(m_bitmap,m_x,m_y,null); }

    public void setPosition(int x, int y){
        m_x=x;
        m_y=y;
    }

    public int getX() {
        return m_x;
    }
    public int getY() { return m_y; }
    public void setX(int x) { m_x = x; }
    public void setY(int y) { m_y = y; }
    public int getWidth() { return m_bitmap.getWidth(); }
    public int getHeight() { return m_bitmap.getHeight(); }

    public void setBitmap(Bitmap bitmap) { this.m_bitmap=bitmap; }
}