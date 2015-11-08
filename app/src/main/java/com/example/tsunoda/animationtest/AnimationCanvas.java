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
import android.widget.EdgeEffect;

public class AnimationCanvas extends View {
    private Paint myPaint = new Paint();

    private Bitmap[] droid;
    private int droidPose;
    private Bitmap backgroundImageRaw;
    private Bitmap backgroundImage;
    private int bgPosX, bgPosY;
    private int bgVel;
    private int droidPosX, droidPosY;
    private double droidPosOrg, droidPosPhase, droidPosAmp, droidPosPitch;
    private double droidPosEdgeSize;
    private int droidPoseCount;

    private static final int BGWIDTH = 1920;         // 背景画像のリサイズ時に画面サイズがわからないので、やむを得ず定義
    private static final int BGHEIGHT = 1200;        // 背景画像のリサイズ時に画面サイズがわからないので、やむを得ず定義
    private static final long DELAY_MILLIS = 10;    // 10mseec以下はあまり変わらない(Nexus7の場合)

    public AnimationCanvas(Context context) {
        super(context);

        // リソース取得
        Resources res = this.getContext().getResources();

        // 背景画像の読み込み
        backgroundImageRaw = BitmapFactory.decodeResource(res, R.drawable.bg);
        backgroundImage = Bitmap.createScaledBitmap(backgroundImageRaw, BGWIDTH, BGHEIGHT, false);

        // 位置、速さ(背景)
        bgPosX = 0;
        bgPosY = 0;
        bgVel = 3;

        // droid画像の読み込み
        droid = new Bitmap[4];
        droid[0] = BitmapFactory.decodeResource(res, R.drawable.droid01);
        droid[1] = BitmapFactory.decodeResource(res, R.drawable.droid02);
        droid[2] = BitmapFactory.decodeResource(res, R.drawable.droid03);
        droid[3] = BitmapFactory.decodeResource(res, R.drawable.droid04);
        droidPose = 0;
        droidPoseCount = 0;

        // 位置、上下動
        droidPosOrg = 475;
        droidPosPhase = 0;
        droidPosAmp = 30;
        droidPosPitch = 0.01;
        droidPosEdgeSize = Math.PI / 10;

        droidPosX = BGWIDTH / 2;
        droidPosY = (int)droidPosOrg;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 背景
        canvas.drawBitmap(backgroundImage, bgPosX, bgPosY, myPaint);
        canvas.drawBitmap(backgroundImage, bgPosX - BGWIDTH, bgPosY, myPaint);

        // 文字の描画
        myPaint.setTextSize(100);
        myPaint.setTextAlign(Paint.Align.CENTER);
        myPaint.setColor(Color.BLUE);
        canvas.drawText("どろいど君 一人旅", this.getWidth() / 2, 150, myPaint);

        // 画像の描画
        canvas.drawBitmap(droid[droidPose], droidPosX, droidPosY, myPaint);
    }

    /**
     * 更新処理
     */
    private void update() {
        // 背景処理
        bgPosX += bgVel;
        bgPosX %= BGWIDTH;

        // droid処理
        droidPosPhase += droidPosPitch;
        droidPosPhase %= (Math.PI * 2);
        droidPosY = (int)(droidPosOrg + Math.sin(droidPosPhase) * droidPosAmp);
        if (droidPosPhase > Math.PI / 2 - droidPosEdgeSize && droidPosPhase < Math.PI / 2 + droidPosEdgeSize || droidPosPhase > Math.PI * 3 / 2 - droidPosEdgeSize && droidPosPhase < Math.PI * 3 / 2 + droidPosEdgeSize) {
            droidPose = 2 + (droidPoseCount / 5) % 2;
            droidPoseCount++;
        }
        else if(droidPosPhase < Math.PI) {
            droidPose = 0;
        }
        else {
            droidPose = 1;
        }
    }

    /**
     * タイマーハンドラー
     */
    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            update();
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
