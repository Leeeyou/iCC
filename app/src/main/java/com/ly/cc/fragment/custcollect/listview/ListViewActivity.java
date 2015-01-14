package com.ly.cc.fragment.custcollect.listview;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.ly.cc.bean.custcollect.listview.ListViewBean;
import com.ly.cc.fragment.custcollect.android5p0.recyclerView.RecyclerViewActivity;
import com.ly.cc.manager.UniversalAdapter;
import com.ly.cc.manager.ViewHolder;
import com.ly.cc.utils.T;

/**
 * Created by kongbei on 2015/1/14.
 */
public class ListViewActivity extends ListActivity {
    private UniversalAdapter<String> mListViewAdapter;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ctx = this;

        mListViewAdapter = new UniversalAdapter<String>(ctx, ListViewBean.items, android.R.layout.simple_list_item_1) {
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
                T.showShort(ctx, "常用的下拉刷新和加载更多");
                break;
            case 1:
                T.showShort(ctx, "带headView的下拉刷新");
                break;
            case 2:
                T.showShort(ctx, "水平ListView");
                break;
            default:
                break;
        }
    }
}
