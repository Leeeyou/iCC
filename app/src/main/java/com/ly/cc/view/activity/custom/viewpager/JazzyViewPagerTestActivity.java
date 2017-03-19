package com.ly.cc.view.activity.custom.viewpager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ly.cc.R;
import com.ly.cc.view.custom.viewpager.JazzyViewPager;
import com.ly.cc.view.custom.viewpager.JazzyViewPager.TransitionEffect;
import com.ly.cc.view.custom.viewpager.OutlineContainer;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class JazzyViewPagerTestActivity extends Activity {

    @InjectView(R.id.jazzyViewPager)
    JazzyViewPager jazzyViewPager;

    private int[] mImgIds = new int[]{R.drawable.guide_image1, R.drawable.guide_image2, R.drawable.guide_image3, R.drawable.guide_image1, R.drawable.guide_image2, R.drawable.guide_image3};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jazzy_view_pager_test);
        ButterKnife.inject(this);
        setupJazziness(JazzyViewPager.TransitionEffect.Tablet);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Toggle Fade");
        String[] effects = this.getResources().getStringArray(R.array.jazzy_effects);
        for (String effect : effects)
            menu.add(effect);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().toString().equals("Toggle Fade")) {
            jazzyViewPager.setFadeEnabled(!jazzyViewPager.getFadeEnabled());
        } else {
            TransitionEffect effect =TransitionEffect.valueOf(item.getTitle().toString());
            setupJazziness(effect);
        }
        return true;
    }

    private void setupJazziness(TransitionEffect effect) {
        jazzyViewPager.setTransitionEffect(effect);
        jazzyViewPager.setAdapter(new MainAdapter());
        jazzyViewPager.setPageMargin(30);
    }

    private class MainAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mImgIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            if (view instanceof OutlineContainer) {
                return ((OutlineContainer) view).getChildAt(0) == obj;
            } else {
                return view == obj;
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView iv = new ImageView(JazzyViewPagerTestActivity.this);
            iv.setImageResource(mImgIds[position]);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            container.addView(iv);
            jazzyViewPager.setObjectForPosition(iv, position);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object obj) {
            container.removeView(jazzyViewPager.findViewFromObject(position));
        }
    }
}
