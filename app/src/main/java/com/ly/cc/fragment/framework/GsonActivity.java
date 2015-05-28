package com.ly.cc.fragment.framework;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.google.gson.Gson;
import com.ly.cc.R;
import com.ly.cc.utils.L;

import java.util.Arrays;

/**
 * https://sites.google.com/site/gson/gson-user-guide
 */
public class GsonActivity extends ActionBarActivity {

    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gson);

        gson = new Gson();

        SerializationPrimitives();
        deserializationPrimitives();


    }

    void SerializationPrimitives() {
        String s = gson.toJson(1);
        String abc = gson.toJson("abc");
        String s1 = gson.toJson(new Long(10));
        int[] value = {30, 100};
        String s2 = gson.toJson(value);

        L.i("int toJson --> " + s);
        L.i("String toJson --> " + abc);
        L.i("Long toJson --> " + s1);
        L.i("int array toJson --> " + s2);
    }

    void deserializationPrimitives() {
        int integer = gson.fromJson("10", int.class);
        Integer integer1 = gson.fromJson("20", Integer.class);
        Long aLong = gson.fromJson("30", Long.class);
        Boolean aFalse = gson.fromJson("false", Boolean.class);
        String s = gson.fromJson("\"abc\"", String.class);
        String[] s1 = gson.fromJson("[\"abc\",\"def\"]", String[].class);

        L.i("*********************");
        L.i("int fromJson --> " + integer);
        L.i("Integer fromJson --> " + integer1);
        L.i("Long fromJson --> " + aLong);
        L.i("Boolean fromJson --> " + aFalse);
        L.i("String fromJson --> " + s);
        L.i("String array fromJson --> " + Arrays.toString(s1));

    }
}
