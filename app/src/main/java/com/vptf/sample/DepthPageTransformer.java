package com.vptf.sample;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * @author Andy.Ren
 * @date 2018/2/1
 */

public class DepthPageTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.70f;

    @Override
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        // 这个页面超出屏幕左边或者超出屏幕右侧
        if (position < -1 || position > 1) {
            view.setAlpha(0);
        } else if (position <= 0) {
            // 向左移动
            view.setAlpha(1);
            view.setTranslationX(0);
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (position <= 1) {
            // 淡出页面
            view.setAlpha(1 - position);
            // 抵消默认滑动过渡
            view.setTranslationX(pageWidth * -position);
            // 计算下一页 (MIN_SCALE and 1 之间)
            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
        }
    }
}
