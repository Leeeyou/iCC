package com.ly.cc.view.activity.custom.viewpager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ly.cc.R;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerWithTranformAnimActivity extends Activity {

    ViewPagerWithTranformAnim vp_transform_anim;

    private int[] mImgIds = new int[]{R.drawable.guide_image1,R.drawable.guide_image2,R.drawable.guide_image3};
    private List<ImageView>  mImages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_with_tranform_anim);
        vp_transform_anim = findViewById(R.id.vp_transform_anim);

//        vp_transform_anim.setPageTransformer(new zoomo);


        vp_transform_anim.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mImgIds.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView iv = new ImageView(ViewPagerWithTranformAnimActivity.this);
                iv.setImageResource(mImgIds[position]);
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                container.addView(iv);
                mImages.add(iv);
                vp_transform_anim.setViewForPosition(iv,position);
                return iv;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mImages.get(position));
                vp_transform_anim.removeViewFromPosition(position);
            }
        });
    }



}
