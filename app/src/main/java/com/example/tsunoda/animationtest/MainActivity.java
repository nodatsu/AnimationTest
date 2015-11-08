package com.example.tsunoda.animationtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AnimationCanvas animationCanvas = new AnimationCanvas( getApplication() );
        setContentView(animationCanvas);
    }
}
