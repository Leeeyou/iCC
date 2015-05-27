package com.ly.cc.custview.imagePainting;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.ly.cc.R;

import java.lang.ref.WeakReference;

/**
 * Created by LeeeYou on 2015/5/27.
 */
public class RoundImageViewByXfermode extends ImageView {

    private Paint paint;
    private Xfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    private Bitmap mMaskBitmap;

    private WeakReference<Bitmap> mWeakBitmap;

    private int type;
    private static final int TYPE_CIRCLE = 0;
    private static final int TYPE_ROUND = 1;

    private static final int BORDER_RADIUS_DEFAULT = 10;

    private int mBorderRadius;

    public RoundImageViewByXfermode(Context context) {
        this(context, null);
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    public RoundImageViewByXfermode(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);

        mBorderRadius = ta.getDimensionPixelSize(R.styleable.RoundImageView_borderRadius,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, BORDER_RADIUS_DEFAULT, getResources().getDisplayMetrics()));

        type = ta.getInt(R.styleable.RoundImageView_type, TYPE_CIRCLE);

        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (type == TYPE_CIRCLE) {
            final int width = Math.min(getMeasuredWidth(), getMeasuredHeight());
            setMeasuredDimension(width, width);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap = mWeakBitmap == null ? null : mWeakBitmap.get();

        if (null == bitmap || bitmap.isRecycled()) {
            Drawable drawable = getDrawable();

            final int intrinsicWidth = drawable.getIntrinsicWidth();
            final int intrinsicHeight = drawable.getIntrinsicHeight();

            if (drawable != null) {
                bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                float scale;
                final Canvas drawCanvas = new Canvas(bitmap);

                if (type == TYPE_ROUND) {
                    scale = Math.max(getWidth() * 1.0f / intrinsicWidth, getHeight() * 1.0f / intrinsicHeight);
                } else {
                    scale = getWidth() * 1.0f / Math.min(intrinsicWidth, intrinsicHeight);
                }

                drawable.setBounds(0, 0, (int) (scale * intrinsicWidth), (int) (scale * intrinsicHeight));
                drawable.draw(drawCanvas);

                if (mMaskBitmap == null || mMaskBitmap.isRecycled()) {
                    mMaskBitmap = getBitmap();
                }

                // Draw Bitmap
                paint.reset();
                paint.setFilterBitmap(false);
                paint.setXfermode(mXfermode);
                drawCanvas.drawBitmap(mMaskBitmap, 0, 0, paint);

                paint.setXfermode(null);
                canvas.drawBitmap(bitmap, 0, 0, null); //将准备好的bitmap绘制出来
                mWeakBitmap = new WeakReference<>(bitmap);//bitmap缓存起来，避免每次调用onDraw，分配内存
            }
        }

        if (bitmap != null) {
            paint.setXfermode(null);
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
            return;
        }
    }

    private Bitmap getBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);

        if (type == TYPE_ROUND) {
            canvas.drawRoundRect(new RectF(0, 0, getWidth(), getHeight()), mBorderRadius, mBorderRadius, paint);
        } else {
            final int center = getWidth() / 2;

//            int innerCircle = center - dp2px(16); //设置内圆半径
//            int ringWidth = dp2px(10); //设置圆环宽度

//            paint.setAntiAlias(true);
//            paint.setStyle(Paint.Style.STROKE);
//            paint.setStrokeWidth(5);

//            paint.setARGB(155, 167, 190, 206);
//            paint.setStrokeWidth(2);
//            canvas.drawCircle(center, center, innerCircle, paint);

//            paint.setARGB(255, 212, 225, 233);
//            paint.setStrokeWidth(ringWidth);
            canvas.drawCircle(center, center, center, paint);

//            paint.setColor(Color.argb(155, 0, 0, 0));
//            paint.setStrokeWidth(20);
//            canvas.drawCircle(center, center, innerCircle + ringWidth, paint);
        }

        return bitmap;
    }

    private int dp2px(int borderRadius) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, borderRadius, getResources().getDisplayMetrics());
    }

    @Override
    public void invalidate() {
        mWeakBitmap = null;

        if (mMaskBitmap != null) {
            mMaskBitmap.recycle();
            mMaskBitmap = null;
        }
        super.invalidate();
    }


}
