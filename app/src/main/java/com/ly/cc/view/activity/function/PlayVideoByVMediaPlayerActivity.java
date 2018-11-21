package com.ly.cc.view.activity.function;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.ly.cc.R;
import com.ly.cc.utils.T;

import java.io.IOException;

public class PlayVideoByVMediaPlayerActivity extends Activity {

    MediaPlayer mp;

    SurfaceView sv;
    Button btn_play;
    Button btn_pause;
    Button btn_stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_play_video_by_media_player);

        btn_pause = findViewById(R.id.btn_pause);
        btn_play = findViewById(R.id.btn_play);
        sv = findViewById(R.id.sv_video);
        btn_stop = findViewById(R.id.btn_stop);


        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.reset();
                try {
                    mp.setDataSource("/storage/emulated/0/Download/bfxcr.mp4");
                    mp.setDisplay(sv.getHolder());
                    mp.prepare();
                    mp.start();
                    btn_pause.setText("暂停");
                    btn_pause.setEnabled(true);
                } catch (IllegalArgumentException | SecurityException | IllegalStateException | IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mp.isPlaying()) {
                    mp.pause();
                    btn_pause.setText("继续");
                } else {
                    mp.start();
                    btn_pause.setText("暂停");
                }
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mp.isPlaying()) {
                    mp.stop();
                    btn_pause.setEnabled(false);
                }
            }
        });

        mp = new MediaPlayer();
        mp.setOnCompletionListener(mediaPlayer -> T.showShort(this, "视频播放完毕"));
    }

    @Override
    protected void onDestroy() {
        if (mp.isPlaying()) {
            mp.stop();
        }
        mp.release();
        super.onDestroy();
    }
}
