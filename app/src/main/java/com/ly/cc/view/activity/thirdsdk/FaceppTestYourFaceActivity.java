package com.ly.cc.view.activity.thirdsdk;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.facepp.error.FaceppParseException;
import com.ly.cc.R;
import com.ly.cc.event.FaceppParseExceptionEvent;
import com.ly.cc.event.ShowAgeEvent;
import com.ly.cc.utils.FaceppDetect;
import com.ly.cc.utils.L;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;

@EActivity(R.layout.activity_facepp_test_your_face)
public class FaceppTestYourFaceActivity extends ActionBarActivity {

    @ViewById
    ImageView iv_photo;

    @ViewById
    TextView tv_tip;

    @ViewById
    FrameLayout id_waiting;

    @ViewById
    TextView tv_age_gender;

    private String mImagePath;

    private Bitmap mPhotoBitmap;

    private Paint mPaint;

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    private final int PICK_IMAGE = 0x110;

    @Click(R.id.btn_get_image)
    public void onClickGetImage() {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, PICK_IMAGE);
    }

    @Click(R.id.btn_detect)
    public void onClickDetect() {
        id_waiting.setVisibility(View.VISIBLE);

        if (TextUtils.isEmpty(mImagePath)) {
            mPhotoBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.t4);
        }

        FaceppDetect.detect(mPhotoBitmap, new FaceppDetect.CallBack() {
            @Override
            public void success(JSONObject result) {
                EventBus.getDefault().post(new ShowAgeEvent(result));
            }

            @Override
            public void error(FaceppParseException e) {
                EventBus.getDefault().post(new FaceppParseExceptionEvent(e));
            }
        });
    }

    public void onEventMainThread(FaceppParseExceptionEvent event) {
        id_waiting.setVisibility(View.GONE);
        if (event.getE() != null) {
            tv_tip.setText(event.getE().getErrorMessage());
        } else {
            L.e("检测出错了");
        }
    }

    public void onEventMainThread(ShowAgeEvent event) {
        id_waiting.setVisibility(View.GONE);

        JSONObject jsonObject = event.getJsonObject();
        prepareResultBitmap(jsonObject);

        iv_photo.setImageBitmap(mPhotoBitmap);
    }

    private void prepareResultBitmap(JSONObject jsonObject) {

        Bitmap bitmap = Bitmap.createBitmap(mPhotoBitmap.getWidth(), mPhotoBitmap.getHeight(), mPhotoBitmap.getConfig());
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(mPhotoBitmap, 0, 0, null);//画原图
        try {
            final JSONArray faces = jsonObject.getJSONArray("face");
            int faceCount = faces.length();
            tv_tip.setText("find " + faceCount);

            for (int i = 0; i < faceCount; i++) {
                JSONObject face = faces.getJSONObject(i);
                JSONObject position = face.getJSONObject("position");

                float x = (float) position.getJSONObject("center").getDouble("x");
                float y = (float) position.getJSONObject("center").getDouble("y");

                float w = (float) position.getDouble("width");
                float h = (float) position.getDouble("height");

                x = x / 100 * bitmap.getWidth();
                y = y / 100 * bitmap.getHeight();

                w = w / 100 * bitmap.getWidth();
                h = h / 100 * bitmap.getHeight();

                mPaint.setColor(Color.WHITE);
                mPaint.setStrokeWidth(3);

                // draw box
                canvas.drawLine(x - w / 2, y - h / 2, x - w / 2, y + h / 2, mPaint);
                canvas.drawLine(x - w / 2, y - h / 2, x + w / 2, y - h / 2, mPaint);
                canvas.drawLine(x + w / 2, y - h / 2, x + w / 2, y + h / 2, mPaint);
                canvas.drawLine(x - w / 2, y + h / 2, x + w / 2, y + h / 2, mPaint);

                int age = face.getJSONObject("attribute").getJSONObject("age").getInt("value");
                String gender = face.getJSONObject("attribute").getJSONObject("gender").getString("value");

                Bitmap ageBitmap = buildAgeBitmap(age, "Male".equals(gender));

                final int width = ageBitmap.getWidth();
                final int height = ageBitmap.getHeight();

                if (bitmap.getWidth() < iv_photo.getWidth() && bitmap.getHeight() < iv_photo.getHeight()) {
                    float ratio = Math.max(bitmap.getWidth() * 1.0f / iv_photo.getWidth(), bitmap.getHeight() * 1.0f / iv_photo.getHeight());
                    ageBitmap = Bitmap.createScaledBitmap(ageBitmap, (int) (width * ratio), (int) (height * ratio), false);
                }

                canvas.drawBitmap(ageBitmap, x - ageBitmap.getWidth() / 2, y - h / 2 - ageBitmap.getHeight(), null);

                mPhotoBitmap = bitmap;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private Bitmap buildAgeBitmap(int age, boolean isMale) {
        tv_age_gender.setText(String.valueOf(age));
        tv_age_gender.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(isMale ? R.drawable.male : R.drawable.female), null, null, null);

        tv_age_gender.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(tv_age_gender.getDrawingCache());
        tv_age_gender.destroyDrawingCache();

        return bitmap;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE) {
                Uri uri = data.getData();

                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                mImagePath = cursor.getString(columnIndex);
                cursor.close();

                resizePhoto();

                iv_photo.setImageBitmap(mPhotoBitmap);
                tv_tip.setText("click detect ==>");
            }
        }
    }

    private void resizePhoto() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        double ratio = Math.max(options.outWidth * 1.0d / 1024f, options.outHeight * 1.0d / 1024f);

        options.inSampleSize = (int) Math.ceil(ratio);
        options.inJustDecodeBounds = false;
        mPhotoBitmap = BitmapFactory.decodeFile(mImagePath, options);
    }


}
