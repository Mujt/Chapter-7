package com.bytedance.videoplayer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.VideoView;

public class VideoPlayerActivity extends AppCompatActivity {

    private VideoView videoView;
    private SeekBar seekBar;
    private Button start;
    private Button pause;
    private Button change;
    //private

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_play);
        videoView = findViewById(R.id.play);
        seekBar = findViewById(R.id.seekBar);
        start = findViewById(R.id.buttonPlay);
        pause = findViewById(R.id.buttonPause);
        change = findViewById(R.id.landscape);

        videoView.setVideoPath(String.valueOf(getResources().openRawResourceFd(R.raw.yuminhong)));
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.start();
            }
        });



        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.pause();
            }
        });


    }


}
