package com.ly.cc.view.activity.custom.android5p0.recyclerView;

import android.annotation.TargetApi;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.ly.cc.R;

/**
 * 主界面
 */
public class RecyclerViewActivity extends ActionBarActivity {
    FrameLayout deleteBar;
    ImageButton fab_add;
    RecyclerView recycler_view;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        deleteBar = findViewById(R.id.deleteBar);
        fab_add = findViewById(R.id.fab_add);
        recycler_view = findViewById(R.id.recycler_view);

        ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.fab_size);
                outline.setOval(-4, -4, dimensionPixelSize + 2, dimensionPixelSize + 2);
            }
        };
        fab_add.setOutlineProvider(viewOutlineProvider);

        //  创建线性布局管理器（默认是垂直方向）
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler_view.setLayoutManager(linearLayoutManager);

        final RecyclerView.ItemDecoration itemDecoration = new SampleDivider(this);
        recycler_view.addItemDecoration(itemDecoration);

        final SampleRecyclerAdapter adapter = new SampleRecyclerAdapter();
        recycler_view.setAdapter(adapter);

        fab_add.setOnClickListener(v -> {
            int firstCompletelyVisibleItemPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
            adapter.addItem(firstCompletelyVisibleItemPosition);
        });

        deleteBar.setOnClickListener(v -> {
            int firstCompletelyVisibleItemPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
            adapter.removeData(firstCompletelyVisibleItemPosition);
        });

        recycler_view.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //  dx：大于0，向右滚动    小于0，向左滚动
                //  dy：大于0，向上滚动    小于0，向下滚动

                if (dy > 0) {
                    if (deleteBar.getVisibility() == View.VISIBLE) {
                        hideDeleteBar();
                    }
                } else {
                    if (deleteBar.getVisibility() == View.GONE) {
                        showDeleteBar();
                    }
                }
            }
        });

    }

    private void showDeleteBar() {
        deleteBar.startAnimation(AnimationUtils.loadAnimation(this, R.anim.translate_up_on));
        deleteBar.setVisibility(View.VISIBLE);
    }

    private void hideDeleteBar() {
        deleteBar.startAnimation(AnimationUtils.loadAnimation(this, R.anim.translate_up_off));
        deleteBar.setVisibility(View.GONE);
    }

}
