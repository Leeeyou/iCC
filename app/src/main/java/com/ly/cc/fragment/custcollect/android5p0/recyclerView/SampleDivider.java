package com.ly.cc.fragment.custcollect.android5p0.recyclerView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by kongbei on 2015/1/11.
 */
public class SampleDivider extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = {android.R.attr.listDivider};

    private Drawable drawable;

    public SampleDivider(Context ctx) {
        TypedArray ta = ctx.obtainStyledAttributes(ATTRS);
        drawable = ta.getDrawable(0);
        ta.recycle();
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        int left = parent.getPaddingLeft();

        int right = parent.getWidth() - parent.getPaddingRight();

        int count = parent.getChildCount();

        for (int i = 0; i < count; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + layoutParams.bottomMargin;
            int bottom = top + drawable.getIntrinsicHeight();

            drawable.setBounds(left, top, right, bottom);
            drawable.draw(c);
        }
    }
}
