package com.ly.cc.custview.topview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ly.cc.R;

/**
 * TODO: document your custom view class.
 */
public class MyTopTitleView extends RelativeLayout {

    private Button leftBtn, rightBtn;
    private TextView centerTV;

    private String leftText;
    private float leftTextSize;
    private int leftTextColor;

    private String rightText;
    private float rightTextSize;
    private int rightTextColor;

    private String centerText;
    private float centerTextSize;
    private int centerTextColor;

    private LayoutParams leftLayoutParams;
    private LayoutParams rightLayoutParams;
    private LayoutParams centerLayoutParams;

    private TopBarOnClickListener mClickListener;

    public interface TopBarOnClickListener {
        void leftClick();

        void righClick();
    }

    public void setTopBarClickListener(TopBarOnClickListener listener) {
        mClickListener = listener;
    }

    public MyTopTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.topTitle);

        leftText = ta.getString(R.styleable.topTitle_leftText);
        leftTextSize = ta.getDimension(R.styleable.topTitle_leftTextSize, 0);
        leftTextColor = ta.getColor(R.styleable.topTitle_leftTextColor, 0);

        rightText = ta.getString(R.styleable.topTitle_rightText);
        rightTextSize = ta.getDimension(R.styleable.topTitle_rightTextSize, 0);
        rightTextColor = ta.getColor(R.styleable.topTitle_rightTextColor, 0);

        centerText = ta.getString(R.styleable.topTitle_centerText);
        centerTextSize = ta.getDimension(R.styleable.topTitle_centerTextSize, 0);
        centerTextColor = ta.getColor(R.styleable.topTitle_centerTextColor, 0);

        ta.recycle();

        leftBtn = new Button(context);
        rightBtn = new Button(context);
        centerTV = new TextView(context);


        leftBtn.setText(leftText);
        leftBtn.setTextSize(leftTextSize);
        leftBtn.setTextColor(leftTextColor);

        rightBtn.setText(rightText);
        rightBtn.setTextSize(rightTextSize);
        rightBtn.setTextColor(rightTextColor);

        centerTV.setText(centerText);
        centerTV.setTextSize(centerTextSize);
        centerTV.setTextColor(centerTextColor);
        centerTV.setGravity(Gravity.CENTER);

        setBackgroundColor(getResources().getColor(R.color.bg_title_bar));

        leftLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        leftLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        addView(leftBtn, leftLayoutParams);

        rightLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        rightLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        addView(rightBtn, rightLayoutParams);

        centerLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        centerLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
//        centerLayoutParams.addRule(RelativeLayout.RIGHT_OF, leftBtn.getId());
//        centerLayoutParams.addRule(RelativeLayout.LEFT_OF, rightBtn.getId());
        addView(centerTV, centerLayoutParams);

        setClickListener();

    }

    private void setClickListener() {
        leftBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.leftClick();
            }
        });

        rightBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.righClick();
            }
        });
    }
}
