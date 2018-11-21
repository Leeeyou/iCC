/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package com.ly.cc.view.activity.custom.android5p0;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.ly.cc.R;

import me.zhanghai.android.materialprogressbar.HorizontalProgressDrawable;
import me.zhanghai.android.materialprogressbar.IndeterminateHorizontalProgressDrawable;
import me.zhanghai.android.materialprogressbar.IndeterminateProgressDrawable;

public class ProgressBarActivity extends AppCompatActivity {

    ProgressBar horizontalProgress;
    ProgressBar indeterminateHorizontalProgress;
    ProgressBar indeterminateProgressLarge;
    ProgressBar indeterminateProgress;
    ProgressBar indeterminateProgressSmall;
    ProgressBar toolbarHorizontalProgress;
    ProgressBar toolbarIndeterminateHorizontalProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_progress_bar);
        horizontalProgress = findViewById(R.id.horizontal_progress_library);
        indeterminateHorizontalProgress = findViewById(R.id.indeterminate_horizontal_progress_library);
        indeterminateProgressLarge = findViewById(R.id.indeterminate_progress_large_library);
        indeterminateProgress = findViewById(R.id.indeterminate_progress_library);
        indeterminateProgressSmall = findViewById(R.id.indeterminate_progress_small_library);
        toolbarHorizontalProgress = findViewById(R.id.horizontal_progress_toolbar);
        toolbarIndeterminateHorizontalProgress = findViewById(R.id.indeterminate_horizontal_progress_toolbar);


        horizontalProgress.setProgressDrawable(new HorizontalProgressDrawable(this));

        indeterminateHorizontalProgress.setIndeterminateDrawable(new IndeterminateHorizontalProgressDrawable(this));

        indeterminateProgressLarge.setIndeterminateDrawable(new IndeterminateProgressDrawable(this));
        indeterminateProgress.setIndeterminateDrawable(new IndeterminateProgressDrawable(this));
        indeterminateProgressSmall.setIndeterminateDrawable(new IndeterminateProgressDrawable(this));

        HorizontalProgressDrawable toolbarDrawable = new HorizontalProgressDrawable(this);
        toolbarDrawable.setShowTrack(false);
        toolbarDrawable.setUseIntrinsicPadding(false);
        toolbarHorizontalProgress.setProgressDrawable(toolbarDrawable);

        IndeterminateHorizontalProgressDrawable toolbarIndeterminateDrawable = new IndeterminateHorizontalProgressDrawable(this);
        toolbarIndeterminateDrawable.setShowTrack(false);
        toolbarIndeterminateDrawable.setUseIntrinsicPadding(false);
        toolbarIndeterminateHorizontalProgress.setIndeterminateDrawable(toolbarIndeterminateDrawable);
    }


}
