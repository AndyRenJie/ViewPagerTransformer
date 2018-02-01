package com.vptf.sample;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * @author Andy.Ren
 * @date 2018/2/1
 */
public class ScalePageTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.70f;
    private static final float MIN_ALPHA = 0.5f;

    @Override
    public void transformPage(View view, float position) {
        //超出屏幕左侧或者屏幕右侧
        if (position < -1 || position > 1) {
            view.setAlpha(MIN_ALPHA);
            view.setScaleX(MIN_SCALE);
            view.setScaleY(MIN_SCALE);
        }
        //向左移动或者向右移动
        else if (position <= 1) {
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            if (position < 0) {
                //前一页的XY缩放值
                view.setScaleX(1 + 0.3f * position);
                view.setScaleY(1 + 0.3f * position);
            } else {
                //后一页的XY缩放值
                view.setScaleX(1 - 0.3f * position);
                view.setScaleY(1 - 0.3f * position);
            }
            view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
        }
    }
}
