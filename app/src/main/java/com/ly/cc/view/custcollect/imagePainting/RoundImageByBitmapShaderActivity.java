package com.ly.cc.view.custcollect.imagePainting;

import android.app.Activity;
import android.os.Bundle;

import com.ly.cc.R;
import com.ly.cc.custview.imagePainting.RoundImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by LeeeYou on 2015/5/26.
 */
public class RoundImageByBitmapShaderActivity extends Activity {

    @InjectView(R.id.riv_1)
    RoundImageView riv_1;

    @InjectView(R.id.riv_2)
    RoundImageView riv_2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_painting);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.riv_1)
    public void onClickChange1() {
        riv_1.setBorderRadius(100);
    }

    @OnClick(R.id.riv_2)
    public void onClickChange2() {
        riv_2.setType(RoundImageView.TYPE_ROUND);
    }

}
