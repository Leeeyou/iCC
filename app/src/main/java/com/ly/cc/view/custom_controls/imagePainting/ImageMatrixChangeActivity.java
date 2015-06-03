package com.ly.cc.view.custom_controls.imagePainting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.ly.cc.R;

public class ImageMatrixChangeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_matrix_change);
    }

    public void btnMesh(View view) {
        startActivity(new Intent(this, MeshViewTestActivity.class));
    }

    public void btnReflect(View view) {
        startActivity(new Intent(this, ReflectViewTestActivity.class));
    }

    public void btnShader(View view) {
        startActivity(new Intent(this, BitmapShaderTestActivity.class));
    }

    public void btnXfermode(View view) {
        startActivity(new Intent(this, RoundRectXfermodTestActivity.class));
    }

    public void btnMatrix(View view) {
        startActivity(new Intent(this, ImageMatrixTestActivity.class));
    }
}
