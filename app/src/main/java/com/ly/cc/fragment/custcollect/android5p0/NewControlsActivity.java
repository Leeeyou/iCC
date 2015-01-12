package com.ly.cc.fragment.custcollect.android5p0;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.ly.cc.R;
import com.ly.cc.bean.custcollect.android5p0.Android5p0Bean;
import com.ly.cc.fragment.custcollect.android5p0.recyclerView.RecyclerViewActivity;
import com.ly.cc.manager.UniversalAdapter;
import com.ly.cc.manager.ViewHolder;

/**
 * Created by xzzz on 2015/1/12.
 */
public class NewControlsActivity extends ListActivity {

    private UniversalAdapter<String> newControlsAdapter;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        ctx = this;

        newControlsAdapter = new UniversalAdapter<String>(ctx, Android5p0Bean.items, android.R.layout.simple_list_item_1) {
            @Override
            public void convert(ViewHolder vh, String item, int position) {
                if (item == null)
                    return;

                if (vh == null)
                    return;

                vh.setText(android.R.id.text1, item);
            }
        };

        setListAdapter(newControlsAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position) {
            case 0:
                Intent i = new Intent(ctx, RecyclerViewActivity.class);
                startActivity(i);
                break;
            default:
                break;
        }
    }
}
