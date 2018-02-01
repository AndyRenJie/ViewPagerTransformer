package com.vptf.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by Andy.Ren on 2018/2/1.
 */

public class ScalePageActivity extends AppCompatActivity{

    private ViewPager viewPager;
    private RelativeLayout ryContainer;
    private Integer [] mImages = {R.mipmap.ic_picture_1,R.mipmap.ic_picture_2,R.mipmap.ic_picture_3,
            R.mipmap.ic_picture_4,R.mipmap.ic_picture_5,R.mipmap.ic_picture_6,
            R.mipmap.ic_picture_7,R.mipmap.ic_picture_8,R.mipmap.ic_picture_9,
            R.mipmap.ic_picture_10};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_page);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        /**
         * 将RelativeLayout的事件分发交给ViewPager,这样就可以在外层滑动
         * 如果要设置滑动速度可以自定义ViewPager
         */
        ryContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return viewPager.dispatchTouchEvent(event);
            }
        });
    }

    private void initData() {
        viewPager.setAdapter(new ViewpagerAdapter());
        //设置缓存数为展示的数目
        viewPager.setOffscreenPageLimit(mImages.length);
        //设置Item间距
        viewPager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.viewpager_margin));
        //设置切换动画
        viewPager.setPageTransformer(true, new ScalePageTransformer());
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        ryContainer = (RelativeLayout) findViewById(R.id.ry_container);
    }

    class ViewpagerAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = LayoutInflater.from(ScalePageActivity.this).inflate(R.layout.view_pager_item, null);
            ImageView itemIV = (ImageView) view.findViewById(R.id.item_iv);
            itemIV.setImageResource(mImages[position]);
            itemIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ScalePageActivity.this, position+"", Toast.LENGTH_SHORT).show();
                }
            });
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return mImages.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
