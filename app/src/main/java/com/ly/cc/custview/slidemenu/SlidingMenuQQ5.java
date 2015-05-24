package com.ly.cc.custview.slidemenu;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by liuzhifang on 15/5/24.
 * 1、onMeasure 决定内部view（子view）的宽和高，以及自己的宽和高
 * 2、onLayout 决定子view放置的位置
 * 3、onTouchEvent 监听用户的手势
 */
public class SlidingMenuQQ5 extends HorizontalScrollView {

    private LinearLayout mWapper;
    private ViewGroup mMenu;
    private ViewGroup mContent;
    private int mScreenWidhth;

    private int mMenuRightPadding = 50;//dp

    private boolean once = false;


    public SlidingMenuQQ5(Context context, AttributeSet attrs) {
        super(context, attrs);

        WindowManager systemService = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        systemService.getDefaultDisplay().getMetrics(metrics);

        mScreenWidhth = metrics.widthPixels;

        //dp转px
        mMenuRightPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (!once) {
            mWapper = (LinearLayout) getChildAt(0);
            mMenu = (ViewGroup) mWapper.getChildAt(0);
            mContent = (ViewGroup) mWapper.getChildAt(1);

            mMenuWidth = mMenu.getLayoutParams().width = mScreenWidhth - mMenuRightPadding;

            mContent.getLayoutParams().width = mScreenWidhth;

            once = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    private int mMenuWidth;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (changed)
            this.scrollTo(mMenuWidth, 0);//通过设置偏移量，将menu隐藏

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();

        switch (action) {
            case MotionEvent.ACTION_UP:
                //隐藏在左边的宽度
                int scrollX = getScrollX();
                if (scrollX >= mMenuWidth / 2) {
                    this.smoothScrollTo(mMenuWidth, 0);
                } else {
                    this.smoothScrollTo(0, 0);
                }
                return true;
        }

        return super.onTouchEvent(ev);
    }
}
