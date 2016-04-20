package com.ly.cc.view.function;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import com.ly.cc.R;
import com.ly.cc.utils.L;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PlayVideoByVideoViewActivity extends Activity {

    @InjectView(R.id.vv_play_video)
    VideoView vv_play_video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_play_video_by_video_view);
        ButterKnife.inject(this);

        MediaController mc = new MediaController(this);

        vv_play_video.setMediaController(mc);

//        vv_play_video.setVideoURI(Uri.parse("/storage/emulated/0/Download/bfxcr.mp4"));
        vv_play_video.setVideoURI(Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"));

        vv_play_video.requestFocus();
    }

    private int currentPosition;
    private boolean isPlaying = true;

    @Override
    protected void onPause() {
        super.onPause();
        currentPosition = vv_play_video.getCurrentPosition();

        isPlaying = vv_play_video.isPlaying();
        vv_play_video.pause();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        if (currentPosition <= 0) {
            vv_play_video.start();
        } else {
            vv_play_video.seekTo(currentPosition);
            if (isPlaying) {
                vv_play_video.start();
            }
        }
    }
}
