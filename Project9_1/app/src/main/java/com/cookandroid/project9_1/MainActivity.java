package com.cookandroid.project9_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    final static int LINE=1, CIRCLE=2,RECT=3,color_RED=4,color_GREEN=5,color_BLUE=6;
    //final => 상수의 의미
    //static=> 전역의 의미
    // final static => 전역 상수
    int current_shape=LINE;//기본 값
    int current_color=color_RED;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyGraphicView(this));
        setTitle("201735969 박진형");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0,1,0,"선 그리기");
        menu.add(0,2,0,"원 그리기");
        menu.add(0,3,0,"사각형 그리기");

        SubMenu sub=menu.addSubMenu("색상 변경");
        sub.add(0,4,0,"빨간색");
        sub.add(0,5,0,"초록색");
        sub.add(0,6,0,"파란색");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId())
        {
            case 1:
                current_shape=LINE;
                break;
            case 2:
                current_shape=CIRCLE;
                break;
            case 3:
                current_shape=RECT;
                break;
            case 4:
                current_color=color_RED;
                break;
            case 5:
                current_color=color_GREEN;
                break;
            case 6:
                current_color=color_BLUE;
                break;
        }
        return true;
    }

    private class MyGraphicView extends View {
        int startX=-1,startY=-1,stopX=-1,stopY=-1;
        public MyGraphicView(Context context) {
            super(context);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch(event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    startX=(int)event.getX();
                    startY=(int)event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_MOVE:
                    stopX=(int)event.getX();
                    stopY=(int)event.getY();
                    this.invalidate();
                    break;
            }

            return true;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint paint=new Paint();
            paint.setStrokeWidth(10);
            paint.setStyle(Paint.Style.STROKE);

            switch(current_color)
            {
                case color_RED:
                    paint.setColor(Color.RED);
                    break;
                case color_GREEN:
                    paint.setColor(Color.GREEN);
                    break;
                case color_BLUE:
                    paint.setColor(Color.BLUE);
                    break;
            }
            switch(current_shape)
            {
                case LINE:
                    canvas.drawLine(startX,startY,stopX,stopY,paint);
                    break;
                case CIRCLE:
                    int radius=(int)Math.sqrt(Math.pow(stopX-startX,2)+Math.pow(stopY-startY,2));
                    canvas.drawCircle(startX,startY,radius,paint);
                    break;
                case RECT:
                    canvas.drawRect(new Rect(startX,startY,stopX,stopY),paint);
                    break;

            }

        }
    }
}