package com.ly.cc.recylerView;

import android.graphics.Outline;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewOutlineProvider;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.ly.cc.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 主界面
 */
public class MainActivity extends ActionBarActivity {
    @InjectView(R.id.deleteBar)
    FrameLayout deleteBar;
    @InjectView(R.id.fab_add)
    ImageButton fab_add;
    @InjectView(R.id.recycler_view)
    RecyclerView recycler_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.inject(this);

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
        //  linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler_view.setLayoutManager(linearLayoutManager);

        final RecyclerView.ItemDecoration itemDecoration = new SampleDivider(this);
        recycler_view.addItemDecoration(itemDecoration);

        final SampleRecyclerAdapter adapter = new SampleRecyclerAdapter();
        recycler_view.setAdapter(adapter);

        fab_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int firstCompletelyVisibleItemPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                adapter.addItem(firstCompletelyVisibleItemPosition);
            }
        });

        deleteBar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int firstCompletelyVisibleItemPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                adapter.removeData(firstCompletelyVisibleItemPosition);
            }
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
