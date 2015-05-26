package com.ly.cc.fragment.custcollect.imagePainting;

import android.app.Activity;
import android.os.Bundle;

import com.ly.cc.R;

/**
 * Created by LeeeYou on 2015/5/26.
 */
public class RoundImageByBitmapShaderActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_painting);
    }

    private static final String STATE_INSTANCE = "state_instance";
    private static final String STATE_TYPE = "state_type";
    private static final String STATE_BORDER_RADIUS = "state_border_radius";

}
