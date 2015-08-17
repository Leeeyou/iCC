package com.ly.cc.view.custom_controls.imagePainting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.ly.cc.R;

public class ImageColorChangeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_color_change);
    }

    public void btnPrimaryColor(View view) {
        startActivity(new Intent(this, PrimaryColorActivity.class));
    }

    public void btnColorMatrix(View view) {
        startActivity(new Intent(this, ColorMatrixActivity.class));
    }

    public void btnPixelsEffect(View view) {
        startActivity(new Intent(this, PixelsEffectActivity.class));
    }


}
