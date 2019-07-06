package com.bytedance.videoplayer;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = findViewById(R.id.imageView);
        String url = "https://s3.pstatp.com/toutiao/static/img/logo.271e845.png";
        Glide.with(this).load(url).into(imageView);
    }*/

    private VideoView videoView;
    private SeekBar seekBar;
    private Button start;
    private Button pause;
    private Button change;
    private Button portrait;
    private boolean type = false;
    private boolean stop = false;
    private static int progress=0;
    //private MediaPlayer player;
    //private

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_play);
        videoView = findViewById(R.id.play);
        //videoView.setBackgroundColor(Color.BLACK);
        seekBar = findViewById(R.id.seekBar);
        start = findViewById(R.id.buttonPlay);
        pause = findViewById(R.id.buttonPause);
        change = findViewById(R.id.landscape);
        portrait = findViewById(R.id.portrait);
        //player = new MediaPlayer();

        videoView.setVideoPath(getVideoPath(R.raw.yuminhong));

        /*Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/Test_Movie.m4v");

        //调用系统自带的播放器

        Intent intent = new Intent(Intent.ACTION_VIEW);

        Log.v("URI:::::::::", uri.toString());

        intent.setDataAndType(uri, "video/mp4");

        startActivity();*/

        //seekBar.setMin(0);
        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {

                //videoView.requestFocus();
                seekBar.setMax(videoView.getDuration());
                System.out.println("During:"+videoView.getDuration()+"MAX:"+seekBar.getMax());
            }
        });


        //videoView.requestFocus()


        new Thread() {
            @Override
            public void run() {
                System.out.println("asdasfdasd");
                try {
                    System.out.println(videoView.isPlaying());
                    while (true) {
                        int current = videoView.getCurrentPosition();
                        System.out.println(current);
                        progress = current;
                        seekBar.setProgress(current);
                        type = true;
                        sleep(500);
                        if (stop == true) {
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.start();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                System.out.println("seek click");
                int process = seekBar.getProgress();
                progress = process;
                if (videoView != null && type == false) {
                    videoView.seekTo(process);
                }
                if (type) {
                    type = false;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.pause();
            }
        });

        change.setOnClickListener(this);
        portrait.setOnClickListener(this);

    }

    private String getVideoPath(int resId) {
        return "android.resource://" + this.getPackageName() + "/" + resId;
    }

    @Override
    public void onClick(View view) {
        if (view == change) {
            //videoView.seekTo(progress);
            //int width = videoView.getLayoutParams().width;
            //videoView.set
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            //videoView.seekTo(progress);
        }
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        ViewGroup.LayoutParams lp = videoView.getLayoutParams();
        lp.width = width;
        lp.height = (int) (height * (1 - 0.618));;
        videoView.setLayoutParams(lp);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
        String message=newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE ? "屏幕设置为：横屏" : "屏幕设置为：竖屏";
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stop = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (progress != 0) {
            videoView.seekTo(progress);
        }
        super.onStart();
    }
}
