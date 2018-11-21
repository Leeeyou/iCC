package com.ly.cc.view.activity.custom.imagePainting;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.ly.cc.R;
import com.ly.cc.view.custom.image_painting.RoundImageView;

/**
 * Created by LeeeYou on 2015/5/26.
 */
public class RoundImageByBitmapShaderActivity extends Activity {


    RoundImageView riv_1;

    RoundImageView riv_2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_painting);

        riv_1 = findViewById(R.id.riv_1);
        riv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                riv_1.setBorderRadius(100);
            }
        });

        riv_2 = findViewById(R.id.riv_2);
        riv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                riv_2.setType(RoundImageView.TYPE_ROUND);
            }
        });
    }


}
