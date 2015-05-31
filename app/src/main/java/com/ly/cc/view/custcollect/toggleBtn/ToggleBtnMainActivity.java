package com.ly.cc.view.custcollect.toggleBtn;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.ly.cc.R;
import com.ly.cc.custview.toggleButton.ToggleButton;
import com.ly.cc.utils.T;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ToggleBtnMainActivity extends ActionBarActivity {

    @InjectView(R.id.toggle_btn)
    ToggleButton toggleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_toggle_btn);
        ButterKnife.inject(this);

        //切换开关
        toggleBtn.toggle();

        //切换无动画
        toggleBtn.toggle(true);

        //开关切换事件
        toggleBtn.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                T.showShort(ToggleBtnMainActivity.this, String.valueOf(on));
            }
        });

//        toggleBtn.setToggleOn();
//        toggleBtn.setToggleOff();
//        //无动画切换
//        toggleBtn.setToggleOn(false);
//        toggleBtn.setToggleOff(false);
//
//        //禁用动画
//        toggleBtn.setAnimate(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
