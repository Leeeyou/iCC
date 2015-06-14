package com.ly.cc.utils;

import android.graphics.Bitmap;

import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;
import com.ly.cc.ConstantValue;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

/**
 * Created by liuzhifang on 15/6/6.
 */
public class FaceppDetect {

    public interface CallBack {
        void success(JSONObject result);

        void error(FaceppParseException e);
    }

    public static void detect(final Bitmap bm, final CallBack callBack) {
        new Thread(() -> {
            try {
                HttpRequests request = new HttpRequests(ConstantValue.FACEPP_APIKEY, ConstantValue.FACEPP_APISECRET, true, true);

                Bitmap mBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight());
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                mBm.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] bytes = stream.toByteArray();

                PostParameters parameter = new PostParameters();
                parameter.setImg(bytes);


                JSONObject jsonObject = request.detectionDetect(parameter);

                L.i(jsonObject.toString());

                if (callBack != null) {
                    callBack.success(jsonObject);
                }
            } catch (FaceppParseException e) {
                e.printStackTrace();
                if (callBack != null) {
                    callBack.error(e);
                }
            }
        }).start();
    }

}
