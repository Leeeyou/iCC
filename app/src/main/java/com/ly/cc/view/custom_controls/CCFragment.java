package com.ly.cc.view.custom_controls;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.ly.cc.manager.UniversalAdapter;
import com.ly.cc.manager.ViewHolder;
import com.ly.cc.utils.Jump;
import com.ly.cc.view.custom_controls.android5p0.NewControlsActivity;
import com.ly.cc.view.custom_controls.flow_view.FlowActivity;
import com.ly.cc.view.custom_controls.imagePainting.ImagePaintingActivity;
import com.ly.cc.view.custom_controls.listview.ListViewListActivity;
import com.ly.cc.view.custom_controls.slidemenu.SlideMenuQQ5Activity;
import com.ly.cc.view.custom_controls.timeline.TimelineListActivity;
import com.ly.cc.view.custom_controls.toggleBtn.ToggleBtnMainActivity;
import com.ly.cc.view.custom_controls.viewpager.JazzyViewPagerTestActivity;
import com.ly.cc.view.custom_controls.viewpager.ViewPagerTestActivity;
import com.ly.cc.view.custom_controls.viewpager.ViewPagerWithTranformAnimActivity;

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
        Intent i = new Intent();
        switch (position) {
            case 0://ListView
                i.setClass(ctx, ListViewListActivity.class);


                startActivity(i);
                break;
            case 1://ToggleButton
                Jump.toActivity(getActivity(), ToggleBtnMainActivity.class);
                break;
            case 2://时间轴
                Jump.toActivity(getActivity(), TimelineListActivity.class);
                break;
            case 3://侧滑菜单
                Jump.toActivity(getActivity(), SlideMenuQQ5Activity.class);
                break;
            case 4://图像绘制
                Jump.toActivity(getActivity(), ImagePaintingActivity.class);
                break;
            case 5://Android 5.0新控件
                //TODO 判断当前系统的版本号再进行跳转
                i.setClass(ctx, NewControlsActivity.class);
                startActivity(i);
                break;
            case 6://流式布局
                i.setClass(ctx, FlowActivity.class);
                startActivity(i);
                break;
            case 7://ViewPager
                i.setClass(ctx, ViewPagerTestActivity.class);
                startActivity(i);
                break;
            default:
                break;
        }
    }
}
