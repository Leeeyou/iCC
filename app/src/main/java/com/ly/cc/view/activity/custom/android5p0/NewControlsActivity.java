package com.ly.cc.view.activity.custom.android5p0;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.ly.cc.R;
import com.ly.cc.bean.custom_controls.android5p0.Android5p0Bean;
import com.ly.cc.view.activity.custom.android5p0.recyclerView.RecyclerViewActivity;
import com.ly.cc.manager.UniversalAdapter;
import com.ly.cc.manager.ViewHolder;
import com.ly.cc.view.activity.custom.android5p0.recyclerView2.HomeActivity;

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
            case 2:
                Intent i2 = new Intent(ctx, HomeActivity.class);
                startActivity(i2);
                break;
            case 3:
                Intent i3 = new Intent(ctx, ProgressBarActivity.class);
                startActivity(i3);
                break;
            case 4://MaterialDesign控件
                Intent i4 = new Intent(ctx, MaterialDesignActivity.class);
                startActivity(i4);
                break;
            default:
                break;
        }
    }
}
