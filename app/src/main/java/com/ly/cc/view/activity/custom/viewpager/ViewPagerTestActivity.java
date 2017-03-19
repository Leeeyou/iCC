package com.ly.cc.view.activity.custom.viewpager;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.ly.cc.bean.custom_controls.ViewpagerBean;
import com.ly.cc.manager.UniversalAdapter;
import com.ly.cc.manager.ViewHolder;
import com.ly.cc.utils.Jump;

public class ViewPagerTestActivity extends ListActivity {

    private UniversalAdapter<String> mListViewAdapter;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ctx = this;

        mListViewAdapter = new UniversalAdapter<String>(ctx, ViewpagerBean.items, android.R.layout.simple_list_item_1) {
            @Override
            public void convert(ViewHolder vh, String item, int position) {
                if (item == null)
                    return;

                if (vh == null)
                    return;

                vh.setText(android.R.id.text1, item);
            }
        };

        setListAdapter(mListViewAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position) {
            case 0:
                Jump.toActivity(ViewPagerTestActivity.this, ViewPagerWithTranformAnimActivity.class);
                break;
            case 1:
                Jump.toActivity(ViewPagerTestActivity.this,JazzyViewPagerTestActivity.class);
                break;
            case 2:
                Jump.toActivity(ViewPagerTestActivity.this,SmartTagLayoutActivity.class);
                break;
            default:
                break;
        }
    }

}
