package com.ly.cc.view.custom_controls.viewpager;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ly.cc.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class SmartTagLayoutActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_tag_layout);


        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("周日", DemoFragment.class)
                .add("周一", DemoFragment.class)
                .add("周二", DemoFragment.class)
                .add("周三", DemoFragment.class)
                .add("周四", DemoFragment.class)
                .add("周五", DemoFragment.class)
                .add("周六", DemoFragment.class)
                .create());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);
    }


}
