package com.ly.cc.fragment.custcollect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.ly.cc.fragment.custcollect.android5p0.NewControlsActivity;
import com.ly.cc.manager.UniversalAdapter;
import com.ly.cc.manager.ViewHolder;
import com.ly.cc.utils.T;

/**
 * Created by xzzz on 2015/1/9.
 */
public class CCFragment extends ListFragment {

    private UniversalAdapter<String> ccAdapter;
    private Context ctx;

    public static CCFragment newInstance(String[] ccList) {
        CCFragment fw = new CCFragment();
        if (ccList != null && ccList.length > 0) {
            Bundle b = new Bundle();
            b.putStringArray("ccList", ccList);
            fw.setArguments(b);
        }
        return fw;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ctx = getActivity();

        Bundle arg = getArguments();
        String[] ccLists = arg.getStringArray("ccList");
        if (ccLists == null || ccLists.length <= 0) return;

        ccAdapter = new UniversalAdapter<String>(getActivity(), ccLists, android.R.layout.simple_list_item_1) {
            @Override
            public void convert(ViewHolder vh, String item, int position) {
                if (item == null)
                    return;

                if (vh == null)
                    return;

                vh.setText(android.R.id.text1, item);
            }
        };

        setListAdapter(ccAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position) {
            case 0://ListView
                T.showShort(ctx, "ListView");
                break;
            case 1://ToggleButton
                T.showShort(ctx, "ToggleButton");
                break;
            case 2://时间轴
                T.showShort(ctx, "时间轴");
                break;
            case 3://侧滑菜单
                T.showShort(ctx, "侧滑菜单");
                break;
            case 4://图像绘制
                T.showShort(ctx, "图像绘制");
                break;
            case 5://Android 5.0新控件
                Intent i = new Intent(ctx, NewControlsActivity.class);
                startActivity(i);
                break;
            default:
                break;
        }
    }
}
