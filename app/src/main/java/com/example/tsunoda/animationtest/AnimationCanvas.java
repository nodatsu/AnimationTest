package com.example.tsunoda.animationtest;

/**
 * Created by tsunoda on 2015/11/08.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class AnimationCanvas extends View {
    private Paint mPaint = new Paint();

    public AnimationCanvas(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawText("Hello Custom View!", 50, 50, mPaint);
    }
}
