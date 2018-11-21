package com.ly.cc.view.activity.custom.slidemenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ly.cc.R;
import com.ly.cc.view.custom.slidemenu.SlidingMenuQQ5;

public class SlideMenuQQ5Activity extends Activity {

    SlidingMenuQQ5 slidingMenu;
    Button btn_toggle_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_menu_qq5);
        slidingMenu = findViewById(R.id.slidingMenu);
        btn_toggle_menu = findViewById(R.id.btn_toggle_menu);

        findViewById(R.id.btn_toggle_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggleMenu();
            }
        });
    }

}
