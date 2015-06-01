package com.ly.cc.view.custom_controls.slidemenu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.ly.cc.R;
import com.ly.cc.custom_controls.slidemenu.SlidingMenuQQ5;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class SlideMenuQQ5Activity extends Activity {

    @InjectView(R.id.slidingMenu)
    SlidingMenuQQ5 slidingMenu;

    @InjectView(R.id.btn_toggle_menu)
    Button btn_toggle_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_menu_qq5);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.btn_toggle_menu)
    public void onClickToggleMenu() {
        slidingMenu.toggleMenu();
    }

}
