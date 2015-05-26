package com.ly.cc.custview.slidemenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.ly.cc.R;
import com.nineoldandroids.view.ViewHelper;

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
        this(context, attrs, 0);
    }

    public SlidingMenuQQ5(Context context) {
        this(context, null);
    }

    /**
     * 当使用了自定义属性时，会调用此构造方法
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public SlidingMenuQQ5(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.slidingMenu, defStyleAttr, 0);
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArray.getIndex(i);
            switch (index) {
                case R.styleable.slidingMenu_rightPadding:
                    mMenuRightPadding = typedArray.getDimensionPixelSize(index, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics()));
                    break;
            }
        }

        typedArray.recycle();

        WindowManager systemService = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        systemService.getDefaultDisplay().getMetrics(metrics);

        mScreenWidhth = metrics.widthPixels;
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

        if (changed) {
            this.scrollTo(mMenuWidth, 0);//通过设置偏移量，将menu隐藏
        }

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
                    isOpen = false;
                } else {
                    this.smoothScrollTo(0, 0);
                    isOpen = true;
                }
                return true;
        }

        return super.onTouchEvent(ev);
    }

    boolean isOpen = false;

    public void openMenu() {
        if (isOpen)
            return;

        this.smoothScrollTo(0, 0);
        isOpen = true;
    }

    public void closeMenu() {
        if (!isOpen) {
            return;
        }

        this.smoothScrollTo(mMenuWidth, 0);
        isOpen = false;
    }

    public void toggleMenu() {
        if (isOpen)
            closeMenu();
        else
            openMenu();
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        // l就是getScrollX()的值
        float scale = l * 1.0f / mMenuWidth; //1~0 偏移量

        //调用属性动画，设置TraslationX
        ViewHelper.setTranslationX(mMenu, mMenuWidth * scale * 0.7f);

        float rightScale = 0.7f + 0.3f * scale;
        //设置content缩放的中心点
        ViewHelper.setPivotX(mContent, 0);
        ViewHelper.setPivotY(mContent, mContent.getHeight() / 2);
        ViewHelper.setScaleX(mContent, rightScale);
        ViewHelper.setScaleY(mContent, rightScale);

        float leftScale = 1.0f - scale * 0.3f;
        ViewHelper.setScaleX(mMenu, leftScale);
        ViewHelper.setScaleY(mMenu, leftScale);

        float leftAlpha = 0.6f + 0.4f * (1 - scale);
        ViewHelper.setAlpha(mMenu, leftAlpha);
    }
}
