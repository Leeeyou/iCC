package com.ly.cc.view.activity;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ly.cc.R;
import com.ly.cc.bean.Cheeses;
import com.ly.cc.bean.custom_controls.CustCollect;
import com.ly.cc.bean.framework.FramewrokCollect;
import com.ly.cc.bean.function.FunctionCollect;
import com.ly.cc.bean.thridsdk.ThridSDKCollect;
import com.ly.cc.utils.T;
import com.ly.cc.view.activity.custom.CCFragment;
import com.ly.cc.view.activity.framework.FrameworkFragment;
import com.ly.cc.view.activity.function.FunctionFragment;
import com.ly.cc.view.activity.thirdsdk.ThirdSDKFragment;
import com.ly.cc.view.custom.ChangeColorIconWithTextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * 主界面
 */
public class MainActivity extends ActionBarActivity implements OnClickListener {
    static final int NUM_ITEMS = 4;

    MyAdapter mAdapter;

    ViewPager mViewPager;
    ChangeColorIconWithTextView mCC;
    ChangeColorIconWithTextView mFunction;
    ChangeColorIconWithTextView mFramework;
    ChangeColorIconWithTextView mSDK;
    LinearLayout ll_main_content;

    private List<ChangeColorIconWithTextView> mTabIndicator = new ArrayList<ChangeColorIconWithTextView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = findViewById(R.id.id_viewpager);
        mCC = findViewById(R.id.id_indicator_cc);
        mFunction = findViewById(R.id.id_indicator_function);
        mFramework = findViewById(R.id.id_indicator_framework);
        mSDK = findViewById(R.id.id_indicator_sdk);
        ll_main_content = findViewById(R.id.ll_main_content);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            setTranslucentStatus(true);
//        }
//
//        SystemBarTintManager tintManager = new SystemBarTintManager(this);
//        tintManager.setStatusBarTintEnabled(true);
//        tintManager.setStatusBarTintResource(R.color.logo_green);

        setOverflowShowingAlways();

        initDatas();

        //getSupportActionBar().setDisplayShowHomeEnabled(false);

//        throw new RuntimeException("Boom!");
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 初始化adapter和ViewPager
     */
    private void initDatas() {
        mAdapter = new MyAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset > 0) {
                    ChangeColorIconWithTextView left = mTabIndicator.get(position);
                    ChangeColorIconWithTextView right = mTabIndicator.get(position + 1);

                    left.setIconAlpha(1 - positionOffset);
                    right.setIconAlpha(positionOffset);
                }
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

        initTabIndicator();
    }

    /**
     * 初始化底部导航
     */
    private void initTabIndicator() {
        mCC = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_cc);
        mFunction = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_function);
        mFramework = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_framework);
        mSDK = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_sdk);

        mTabIndicator.add(mCC);
        mTabIndicator.add(mFunction);
        mTabIndicator.add(mFramework);
        mTabIndicator.add(mSDK);

        mCC.setOnClickListener(this);
        mFunction.setOnClickListener(this);
        mFramework.setOnClickListener(this);
        mSDK.setOnClickListener(this);

        mCC.setIconAlpha(1.0f);
    }

    @Override
    public void onClick(View v) {
        resetOtherTabs();
        switch (v.getId()) {
            case R.id.id_indicator_cc:
                mTabIndicator.get(0).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.id_indicator_function:
                mTabIndicator.get(1).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(1, false);
                break;
            case R.id.id_indicator_framework:
                mTabIndicator.get(2).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(2, false);
                break;
            case R.id.id_indicator_sdk:
                mTabIndicator.get(3).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(3, false);
                break;
        }
    }

    /**
     * 重置其他的Tab
     */
    private void resetOtherTabs() {
        for (int i = 0; i < mTabIndicator.size(); i++) {
            mTabIndicator.get(i).setIconAlpha(0);
        }
    }

    public static class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return CCFragment.newInstance(CustCollect.items);
                case 1:
                    return FunctionFragment.newInstance(FunctionCollect.items);
                case 2:
                    return FrameworkFragment.newInstance(FramewrokCollect.items);
                case 3:
                    return ThirdSDKFragment.newInstance(ThridSDKCollect.items);
                default:
                    return ArrayListFragment.newInstance(position);
            }
        }
    }

    public static class ArrayListFragment extends ListFragment {
        int mNum;

        /**
         * Create a new instance of CountingFragment,
         * providing "num" as an argument.
         */
        static ArrayListFragment newInstance(int num) {
            ArrayListFragment f = new ArrayListFragment();

            // Supply num input as an argument.
            Bundle args = new Bundle();
            args.putInt("num", num);
            f.setArguments(args);

            return f;
        }

        /**
         * When creating, retrieve this instance's number from its arguments.
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mNum = getArguments() != null ? getArguments().getInt("num") : 1;
        }

        /**
         * The Fragment's UI is just a simple text view showing its
         * instance number.
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_pager_list, container, false);
            View tv = v.findViewById(R.id.text);
            String s = "";
            switch (mNum) {
                case 0:
                    s = "微信";
                    break;
                case 1:
                    s = "通讯录";
                    break;
                case 2:
                    s = "发现";
                    break;
                case 3:
                    s = "我";
                    break;
            }
            ((TextView) tv).setText(s);
            return v;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            setListAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, Cheeses.sCheeseStrings));
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            Log.i("FragmentList", "Item clicked: " + id);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Search").setIcon(android.R.drawable.ic_menu_search).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        menu.addSubMenu("发起群聊");
        menu.addSubMenu("添加朋友");
        menu.addSubMenu("扫一扫");
        menu.addSubMenu("意见反馈");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        CharSequence name = item.getTitle();
        if (name.equals("Search")) {
//            Intent i = new Intent(this, ActivitySearch.class);
//            startActivity(i);

            T.showShort(this, "search ");
        }
        if (name.equals("发起群聊")) {
            T.showShort(this, "发起群聊");
        }
        if (name.equals("添加朋友")) {
            T.showShort(this, "添加朋友");
        }
        if (name.equals("扫一扫")) {
            T.showShort(this, "扫一扫");
        }
        if (name.equals("意见反馈")) {
            T.showShort(this, "意见反馈");
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 判断是否包含物理按键
     */
    private void setOverflowShowingAlways() {
        try {
            // true if a permanent menu key is present, false otherwise.
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
