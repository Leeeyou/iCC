package com.ly.cc.cardView;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.SeekBar;

import com.ly.cc.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CardViewActivity extends Activity {
    @InjectView(R.id.cardview)
    CardView mCardView;

    // @VisibleForTesting
    @InjectView(R.id.cardview_radius_seekbar)
    SeekBar mRadiusSeekBar;

    // @VisibleForTesting
    @InjectView(R.id.cardview_elevation_seekbar)
    SeekBar mElevationSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        ButterKnife.inject(this);

        mRadiusSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCardView.setRadius(progress);
            }
        });

        mElevationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCardView.setElevation(progress);
            }
        });

    }
}
