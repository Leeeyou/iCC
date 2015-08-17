package com.ly.cc.view.function;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.ly.cc.R;
import com.ly.cc.utils.T;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class PlayVideoByVMediaPlayerActivity extends Activity {

    MediaPlayer mp;

    @InjectView(R.id.sv_video)
    SurfaceView sv;

    @InjectView(R.id.btn_play)
    Button btn_play;

    @InjectView(R.id.btn_pause)
    Button btn_pause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_play_video_by_media_player);
        ButterKnife.inject(this);

        mp = new MediaPlayer();
        mp.setOnCompletionListener(mediaPlayer -> T.showShort(this, "视频播放完毕"));
    }

    @OnClick(R.id.btn_play)
    public void onClickPlay() {
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

    @OnClick(R.id.btn_stop)
    public void onClickStop() {
        if (mp.isPlaying()) {
            mp.stop();
            btn_pause.setEnabled(false);
        }
    }

    @OnClick(R.id.btn_pause)
    public void onClickPause() {
        if (mp.isPlaying()) {
            mp.pause();
            btn_pause.setText("继续");
        } else {
            mp.start();
            btn_pause.setText("暂停");
        }
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
