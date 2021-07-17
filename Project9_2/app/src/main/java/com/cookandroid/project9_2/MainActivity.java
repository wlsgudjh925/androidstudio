package com.cookandroid.project9_2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    // 위젯 변수
    ImageButton idZoomin, idZoomout, idRotate, idBright, idDark, idGray;
    // MyGraphicView 클래스
    MyGraphicView graphicView;

    // 전역 변수
    static float scaleX = 1, scaleY = 1; // 확대/축소 비율
    static float angle = 0; // 각도
    static float color = 1; // 색상
    static float satur = 1; // 채도(기본값 1) - 0 ~ 1 사이 : 낮은 채도, 1 이상 : 높은 채도, 0 : 회색조

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("201735969 박진형");

        // 이미지 레이아웃(pictureLayout) 인플레이트
        LinearLayout pictureLayout = (LinearLayout) findViewById(R.id.pictureLayout);
        graphicView = (MyGraphicView) new MyGraphicView(this);
        pictureLayout.addView(graphicView); // MyGraphicView 추가

        // clickIcons() 메소드 호출
        clickIcons();
    }

    // 6개의 버튼을 클릭했을 때 동작하는 메소드
    private void clickIcons() {
        // 확대 버튼 클릭
        idZoomin = (ImageButton) findViewById(R.id.idZoomin);
        idZoomin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // x비율, y비율 모두 0.2 증가
                scaleX = scaleX + 0.2f;
                scaleY = scaleY + 0.2f;
                graphicView.invalidate(); // 화면이 무효화된 후 onDraw() 자동 실행
            }
        });

        // 축소 버튼 클릭
        idZoomout = (ImageButton) findViewById(R.id.idZoomout);
        idZoomout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // x비율, y비율 모두 0.2 감소
                scaleX = scaleX - 0.2f;
                scaleY = scaleY - 0.2f;
                graphicView.invalidate(); // 화면이 무효화된 후 onDraw() 자동 실행
            }
        });

        // 회전 버튼 클릭
        idRotate = (ImageButton) findViewById(R.id.idRotate);
        idRotate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                angle = angle + 20; // 각도 20 증가
                graphicView.invalidate(); // 화면이 무효화된 후 onDraw() 자동 실행
            }
        });

        // 밝게 버튼 클릭
        idBright = (ImageButton) findViewById(R.id.idBright);
        idBright.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                color = color + 0.2f; // 색상 밝기 0.2 증가
                graphicView.invalidate(); // 화면이 무효화된 후 onDraw() 자동 실행
            }
        });

        // 어둡게 버튼 클릭
        idDark = (ImageButton) findViewById(R.id.idDark);
        idDark.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                color = color - 0.2f; // 색상 밝기 0.2 감소
                graphicView.invalidate(); // 화면이 무효화된 후 onDraw() 자동 실행
            }
        });

        // 회색 버튼 클릭
        idGray = (ImageButton) findViewById(R.id.idGray);
        idGray.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 채도가 0이면 1로 변경
                if (satur == 0)
                    satur = 1;
                    // 채도가 1이면 0으로 변경
                else
                    satur = 0;
                graphicView.invalidate(); // 화면이 무효화된 후 onDraw() 자동 실행
            }
        });

    }

    // View 클래스를 상속받아 MyGraphicView 클래스를 재정의
    private static class MyGraphicView extends View {
        // 생성자
        public MyGraphicView(Context context) {
            super(context);
        }

        // 클래스가 생성되거나 무효화(invalidate)되면 호출되는 메소드(일반적으로 화면에 그려질 내용)
        @Override
        protected void onDraw(Canvas canvas) {
            // 상위 클래스의 onDraw()를 가장 먼저 호출
            super.onDraw(canvas);

            // 중심 좌표
            int cenX = this.getWidth() / 2;
            int cenY = this.getHeight() / 2;

            // scale() : 확대/축소(x비율, y비율, 중심 x좌표, 중심 y좌표)
            canvas.scale(scaleX, scaleY, cenX, cenY);

            // rotate() : 회전(각도, 중심 x좌표, 중심 y좌표)
            canvas.rotate(angle, cenX, cenY);

            // Paint 클래스
            Paint paint = new Paint();
            // ColorMatrix에 사용할 배열(4 X 5) 생성
            float[] array = {   color,  0,      0,      0,  0,
                    0,      color,  0,      0,  0,
                    0,      0,      color,  0,  0,
                    0,      0,      0,      1,  0   };
            ColorMatrix cm = new ColorMatrix(array); // 컬러매트릭스 생성
            // 회색 이미지 : 채도 값이 0
            if (satur == 0)
                // setSaturation() : 채도 설정(앞에 설정된 컬러매트릭스 값 무시)
                cm.setSaturation(satur);

            // 페인트에 적용
            paint.setColorFilter(new ColorMatrixColorFilter(cm));

            // /res/drawable 폴더의 이미지 파일 접근(BitmapFactory.decodeResource() 메소드)
            Bitmap picture = BitmapFactory.decodeResource(getResources(), R.drawable.picture);

            // 이미지의 시작 좌표
            int picX = (this.getWidth() - picture.getWidth()) / 2;
            int picY = (this.getHeight() - picture.getHeight()) / 2;

            // drawBitmap : 이미지를 화면에 출력(picture, 시작 x좌표, 시작 y좌표, 페인트)
            canvas.drawBitmap(picture, picX, picY, paint);

            // 비트맵 리소스를 해제
            picture.recycle();
        }
    }
}