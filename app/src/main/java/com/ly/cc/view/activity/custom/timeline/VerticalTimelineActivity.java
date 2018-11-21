package com.ly.cc.view.activity.custom.timeline;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.ly.cc.R;
import com.ly.cc.bean.custom_controls.VerticalTimelineBean;
import com.ly.cc.manager.UniversalAdapter;
import com.ly.cc.manager.ViewHolder;

import java.util.ArrayList;
import java.util.List;


public class VerticalTimelineActivity extends Activity {

    ListView lv_vertical_timeline;

    UniversalAdapter<VerticalTimelineBean> mAdapter;

    List<VerticalTimelineBean> list = new ArrayList<>(30);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_timeline);
        initData();

        initUI();
    }

    private void initData() {
        for (int i = 1; i <= 30; i++) {
            VerticalTimelineBean bean = new VerticalTimelineBean("2015年5月" + i + "号", String.valueOf(i));
            list.add(bean);
        }
    }

    private void initUI() {
        lv_vertical_timeline = findViewById(R.id.lv_vertical_timeline);

        lv_vertical_timeline.setDividerHeight(0);

        if (mAdapter == null) {
            mAdapter = new UniversalAdapter<VerticalTimelineBean>(VerticalTimelineActivity.this, list, R.layout.item_lv_vertical_timeline) {
                @Override
                public void convert(ViewHolder vh, VerticalTimelineBean item, int position) {
                    if (vh == null || item == null) {
                        return;
                    }

                    vh.setText(R.id.tv_time, item.getTime());

                    vh.setText(R.id.tv_content, item.getContent());
                }
            };
        }

        lv_vertical_timeline.setAdapter(mAdapter);
    }

}
