package com.example.tsunoda.animationtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import android.os.Handler;
import android.os.Message;

public class AnimationCanvas extends View {
    private Paint mPaint = new Paint();

    private int posX, posY;
    private int velX, velY;
    private boolean isAttached;
    private static final long DELAY_MILLIS = 60;

    public AnimationCanvas(Context context) {
        super(context);

        posX = 150;
        posY = 150;
        velX = 2;
        velY = 1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawText("Animation Canvas", 50, 50, mPaint);

        canvas.drawCircle(posX, posY, 25, mPaint);
    }

    private void move() {
        posX += velX;
        posY += velY;
    }

    /**
     * タイマーハンドラー
     */
    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (isAttached) {
                // 移動処理
                move();

                // 再描画
                invalidate();
                sendEmptyMessageDelayed(0, DELAY_MILLIS);
            }
        }
    };

    /**
     * WindowにAttachされた時の処理
     */
    protected void onAttachedToWindow() {
        isAttached = true;
        myHandler.sendEmptyMessageDelayed(0, DELAY_MILLIS);
        super.onAttachedToWindow();
    }

    /**
     * WindowからDetachされた時の処理
     */
    protected void onDetachedFromWindow() {
        isAttached = false;
        super.onDetachedFromWindow();
    }
}
