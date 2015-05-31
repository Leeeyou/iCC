package com.ly.cc.view.function;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ly.cc.R;
import com.ly.cc.bean.function.MyContact;
import com.ly.cc.manager.UniversalAdapter;
import com.ly.cc.manager.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by liuzhifang on 15/5/13.
 */
public class GravitySensorActivity extends Activity {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private SensorEventListener mSensorListener = null;

    private int thresholdValue = 5;

    UniversalAdapter mContactAdapter;

    Context ctx;

    @InjectView(android.R.id.list)
    ListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        ButterKnife.inject(this);

        ctx = this;

        initGravitySensor();

        initListview(getMyContact());
    }

    private void initListview(final List<MyContact> mContactList) {
        mList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position < mContactList.size()) {
                    final MyContact myContact = mContactList.get(position);

                    if (myContact != null && !TextUtils.isEmpty(myContact.getPhone())) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + myContact.getPhone()));
                        startActivity(intent);
                    }
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener, mSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mSensorListener);
    }

    private void initGravitySensor() {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[SensorManager.DATA_X];
                float y = event.values[SensorManager.DATA_Y];
                float z = event.values[SensorManager.DATA_Z];

                //Log.e("", x + "  " + y + "  " + z);

                doSometing(x, y, z);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
    }

    private void doSometing(float x, float y, float z) {
        if (x > thresholdValue) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        if (x < -thresholdValue) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        }

        if (-thresholdValue < x && x < thresholdValue) {
            if (y > thresholdValue) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }
    }

    /**
     * 联系人列表
     */
    private List<MyContact> getMyContact() {
        final List<MyContact> mContactList = new ArrayList<>();

        ContentResolver resolver = getContentResolver();
        Uri uriRawContacts = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri uriData = Uri.parse("content://com.android.contacts/data");
        Cursor c = resolver.query(uriRawContacts, new String[]{"_id"}, null, null, null);//查询raw_contacts表(_id)
        if (c != null) {
            while (c.moveToNext()) {
                MyContact contact = new MyContact();

                int raw_contact_id = c.getInt(c.getColumnIndex("_id"));
                //查询data表(raw_contact_id,mimetype,data1)
                Cursor cData = resolver.query(uriData, new String[]{"mimetype", "data1"}, " raw_contact_id=? ", new String[]{String.valueOf(raw_contact_id)}, null);
                if (cData != null) {
                    while (cData.moveToNext()) {
                        String mimetype = cData.getString(0);
                        String data = cData.getString(1);

                        if (mimetype.equals("vnd.android.cursor.item/name")) {
                            contact.setName(data);
                        } else if (mimetype.equals("vnd.android.cursor.item/phone_v2")) {
                            contact.setPhone(data);
                        }
                    }

                    if (TextUtils.isEmpty(contact.getName())) {
                        contact = null;
                    } else {
                        mContactList.add(contact);
                    }
                }
            }
        }

        Log.e("联系人", mContactList.toString());

        if (mContactList.size() > 0) {
            mContactAdapter = new UniversalAdapter<MyContact>(ctx, mContactList, R.layout.item_lv_contact_layout) {
                @Override
                public void convert(ViewHolder vh, MyContact item, int position) {
                    if (item == null || vh == null)
                        return;

                    vh.setText(R.id.tv_contact_name, item.getName());
                    vh.setText(R.id.tv_phone, item.getPhone());

                }
            };

            mList.setAdapter(mContactAdapter);
        } else {

        }

        return mContactList;


    }
}
