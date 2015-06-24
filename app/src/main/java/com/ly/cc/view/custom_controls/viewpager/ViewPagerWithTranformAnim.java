package com.ly.cc.view.custom_controls.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuzhifang on 15/6/24.
 */
public class ViewPagerWithTranformAnim extends ViewPager {
    private View mLeft;
    private View mRight;

    private float mTrans;
    private float mScale;

    private static final float MIN_SCALE = 0.7F;

    private Map<Integer, View> mChildren = new HashMap<Integer, View>();

    public void setViewForPosition(View view, int position) {
        mChildren.put(position, view);
    }

    public void removeViewFromPosition(Integer position) {
        mChildren.remove(position);
    }

    public ViewPagerWithTranformAnim(Context context) {
        super(context);
    }

    public ViewPagerWithTranformAnim(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        mLeft = mChildren.get(position);
        mRight = mChildren.get(position + 1);

        animStack(mLeft, mRight, offset, offsetPixels);
        super.onPageScrolled(position, offset, offsetPixels);
    }

    private void animStack(View left, View right, float offset, int offsetPixels) {
        if (right != null) {
            //从0到1页，offset:0~1
            mScale = (1 - MIN_SCALE) * offset + MIN_SCALE;

            //-width~0
            mTrans = -getWidth()-getPageMargin()+offsetPixels;

            ViewHelper.setScaleX(right,mScale);
            ViewHelper.setScaleY(right, mScale);

            ViewHelper.setTranslationX(right,mTrans);
        }

        if(left!=null){
            left.bringToFront();
        }
    }
}
