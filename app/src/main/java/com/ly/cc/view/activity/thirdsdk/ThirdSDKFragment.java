package com.ly.cc.view.activity.thirdsdk;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.ly.cc.manager.UniversalAdapter;
import com.ly.cc.manager.ViewHolder;
import com.ly.cc.utils.Jump;
import com.ly.cc.view.activity.thirdsdk.amap.AMapLocationActivity;

/**
 * Created by xzzz on 2015/1/9.
 */
public class ThirdSDKFragment extends ListFragment {

    private UniversalAdapter<String> ccAdapter;
    private Context ctx;

    public static ThirdSDKFragment newInstance(String[] ccList) {
        ThirdSDKFragment fw = new ThirdSDKFragment();
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
            case 0://微信分享
                break;
            case 1://腾讯接入
                break;
            case 2://讯飞语音
                break;
            case 3://ShareSDK
                break;
            case 4://JPush
                break;
            case 5://环信
                break;
            case 6://个推
                break;
            case 7://二维码扫描
                break;
            case 8://Vitamio
                break;
            case 9://高德地图
                Jump.toActivity(getActivity(),AMapLocationActivity.class);
                break;
            case 10://刷脸神器
                Jump.toActivity(getActivity(),FaceppTestYourFaceActivity.class);
                break;
            default:
                break;
        }
    }

}
