package com.ly.cc.custview.topview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
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
    private Drawable leftBackground;
    private boolean leftVisible;

    private String rightText;
    private float rightTextSize;
    private int rightTextColor;
    private Drawable rightBackground;
    private boolean rightVisible;

    private String centerText;
    private float centerTextSize;
    private int centerTextColor;
    private Drawable centerBackground;
    private boolean centerVisible;


    private TopBarOnClickListener mClickListener;

    public interface TopBarOnClickListener {
        void leftClick();

        void rightClick();
    }

    public void setTopBarClickListener(TopBarOnClickListener listener) {
        mClickListener = listener;
    }

    public MyTopTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.topTitle);

        initStyleParams(ta);

        initControls(context);

        initBackground();

        setLayoutParams();

        setClickListener();
    }

    private void setLayoutParams() {
        LayoutParams leftParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        addView(leftBtn, leftParams);

        LayoutParams rightParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        addView(rightBtn, rightParams);

        LayoutParams centerParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        centerParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
        addView(centerTV, centerParams);
    }

    private void initBackground() {
        setBackgroundColor(getResources().getColor(R.color.bg_title_bar));
    }

    private void initControls(Context context) {
        leftBtn = new Button(context);
        rightBtn = new Button(context);
        centerTV = new TextView(context);

        if (!TextUtils.isEmpty(leftText)) {
            leftBtn.setText(leftText);
        }
        if (leftBackground != null) {
            leftBtn.setBackground(leftBackground);
        }
        leftBtn.setTextSize(leftTextSize);
        leftBtn.setTextColor(leftTextColor);
        leftBtn.setVisibility(leftVisible ? View.VISIBLE : View.GONE);


        if (!TextUtils.isEmpty(rightText)) {
            rightBtn.setText(rightText);
        }
        if (rightBackground != null) {
            rightBtn.setBackground(rightBackground);
        }
        rightBtn.setTextSize(rightTextSize);
        rightBtn.setTextColor(rightTextColor);
        rightBtn.setVisibility(rightVisible ? View.VISIBLE : View.GONE);


        if (!TextUtils.isEmpty(centerText)) {
            centerTV.setText(centerText);
        }
        if (centerBackground != null) {
            centerTV.setBackground(centerBackground);
        }
        centerTV.setTextSize(centerTextSize);
        centerTV.setTextColor(centerTextColor);
        centerTV.setVisibility(centerVisible ? View.VISIBLE : View.GONE);
        centerTV.setGravity(Gravity.CENTER);
    }

    private void initStyleParams(TypedArray ta) {
        leftText = ta.getString(R.styleable.topTitle_leftText);
        leftTextSize = ta.getDimension(R.styleable.topTitle_leftTextSize, 10);
        leftTextColor = ta.getColor(R.styleable.topTitle_leftTextColor, 0);
        leftBackground = ta.getDrawable(R.styleable.topTitle_leftBackground);
        leftVisible = ta.getBoolean(R.styleable.topTitle_leftVisible, true);

        rightText = ta.getString(R.styleable.topTitle_rightText);
        rightTextSize = ta.getDimension(R.styleable.topTitle_rightTextSize, 10);
        rightTextColor = ta.getColor(R.styleable.topTitle_rightTextColor, 0);
        rightBackground = ta.getDrawable(R.styleable.topTitle_rightBackground);
        rightVisible = ta.getBoolean(R.styleable.topTitle_rightVisible, true);

        centerText = ta.getString(R.styleable.topTitle_centerText);
        centerTextSize = ta.getDimension(R.styleable.topTitle_centerTextSize, 12);
        centerTextColor = ta.getColor(R.styleable.topTitle_centerTextColor, 0);
        centerBackground = ta.getDrawable(R.styleable.topTitle_centerBackground);
        centerVisible = ta.getBoolean(R.styleable.topTitle_centerVisible, true);

        ta.recycle();
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
                mClickListener.rightClick();
            }
        });

//        leftBtn.setOnClickListener((v -> mClickListener.leftClick()));
//        leftBtn.setOnClickListener((v -> mClickListener.rightClick()));
    }
}
