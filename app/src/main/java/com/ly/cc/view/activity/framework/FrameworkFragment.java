package com.ly.cc.view.activity.framework;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.ly.cc.manager.UniversalAdapter;
import com.ly.cc.manager.ViewHolder;
import com.ly.cc.utils.AppUtil;

/**
 * Created by xzzz on 2015/1/9.
 */
public class FrameworkFragment extends ListFragment {

    private UniversalAdapter<String> ccAdapter;
    private Context ctx;

    public static FrameworkFragment newInstance(String[] ccList) {
        FrameworkFragment fw = new FrameworkFragment();
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
            case 0://OKHttp
                AppUtil.goToWebsite(getActivity(), "http://square.github.io/okhttp/");
                break;
            case 1://ButterKinfe
                AppUtil.goToWebsite(getActivity(), "http://jakewharton.github.io/butterknife/");
                break;
            case 2://EventBus
                AppUtil.goToWebsite(getActivity(), "http://greenrobot.org/eventbus/");
                break;
            case 3://Gson
                AppUtil.goToWebsite(getActivity(), "https://github.com/google/gson");
//                Jump.toActivity(getActivity(), GsonActivity.class);
                break;
            case 4://Volley
                AppUtil.goToWebsite(getActivity(), "http://blog.csdn.net/guolin_blog/article/details/17482095");
                break;
            case 5://RxAndroid
                AppUtil.goToWebsite(getActivity(), "https://github.com/ReactiveX/RxAndroid");
//                Jump.toActivity(getActivity(), RxAndroidActivity.class);
                break;
            default:
                break;
        }
    }

}
