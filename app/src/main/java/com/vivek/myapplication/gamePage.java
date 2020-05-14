package com.vivek.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;

public class gamePage extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyCanvas myCanvasView = new MyCanvas(this);
        myCanvasView.setSystemUiVisibility(SYSTEM_UI_FLAG_FULLSCREEN);
        myCanvasView.setContentDescription("it is canvas for dot dash game");
        setContentView(myCanvasView);

    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }
}
