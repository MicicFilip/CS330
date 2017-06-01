package com.example.micic.cs330_v04;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import util.Mp3Player;

/**
 * Created by Filip on 01-Jun-17.
 */

public class Mp3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp3);

        Button start = (Button)findViewById(R.id.btnStartMusic);
        start.setOnClickListener(startMusic);

        Button stop = (Button)findViewById(R.id.btnStopMusic);
        stop.setOnClickListener(stopMusic);
    }

    private View.OnClickListener startMusic = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startService(new Intent("util.Mp3Player"));
        }
    };

    private View.OnClickListener stopMusic = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startService(new Intent("util.Mp3Player"));
            finish();
        }
    };
}
