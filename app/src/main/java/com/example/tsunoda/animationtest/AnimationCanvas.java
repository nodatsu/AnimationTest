package com.example.tsunoda.animationtest;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import android.os.Handler;
import android.os.Message;

public class AnimationCanvas extends View {
    private Paint myPaint = new Paint();

    private Bitmap poseRight, poseLeft, currentPose;
    private int posX, posY;
    private int vel;
    private static final long DELAY_MILLIS = 10;    // 10mseec以下はあまり変わらない(Nexus7の場合)

    public AnimationCanvas(Context context) {
        super(context);

        // リソース取得
        Resources res = this.getContext().getResources();

        // 画像の読み込み
        poseRight = BitmapFactory.decodeResource(res, R.drawable.droid1);
        poseLeft = BitmapFactory.decodeResource(res, R.drawable.droid2);
        currentPose = poseRight;

        // 位置、速さ
        posX = 0;
        posY = 0;
        vel = 5;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 画面背景
        canvas.drawColor(Color.WHITE);

        // 文字の描画
        myPaint.setTextSize(100);
        myPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Animation Canvas", this.getWidth() / 2, this.getHeight() / 2, myPaint);

        // 画像の描画
        canvas.drawBitmap(currentPose, posX, posY, myPaint);
    }

    /**
     * 移動処理
     */
    private void move() {
        posX += vel;
        if (posX > this.getWidth() - currentPose.getWidth()) {
            vel *= -1;
            currentPose = poseLeft;
            posY += currentPose.getHeight();
        }
        if (posX < 0) {
            vel *= -1;
            currentPose = poseRight;
            posY += currentPose.getHeight();
        }
        if (posY > this.getHeight() - currentPose.getHeight()) {
            posY = 0;
        }
    }

    /**
     * タイマーハンドラー
     */
    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // 移動処理
            move();

            // 再描画
            invalidate();

            sendEmptyMessageDelayed(0, DELAY_MILLIS);
        }
    };

    /**
     * WindowにAttachされた時の処理
     */
    protected void onAttachedToWindow() {
        myHandler.sendEmptyMessageDelayed(0, DELAY_MILLIS);
        super.onAttachedToWindow();
    }

    /**
     * WindowからDetachされた時の処理
     */
    protected void onDetachedFromWindow() {
        myHandler.removeCallbacksAndMessages(null);
        super.onDetachedFromWindow();
    }
}
