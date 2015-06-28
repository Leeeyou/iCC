/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package com.ly.cc.view.custom_controls.android5p0;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ly.cc.R;

import butterknife.ButterKnife;

public class MaterialDesignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_material_design);
        ButterKnife.inject(this);
    }


}
