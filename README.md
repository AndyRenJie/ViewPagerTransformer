## ViewPagerTransformer ##

### ViewPager滑动动画 ###

Android从3.0开始，就添加了很多动画，ViewPager当然也不例外,相对于非常平庸的默认切换动画，Google给我们展示了两个动画例子,
DepthPageTransformer和ScalePageTransformer,实际上我们也可以通过实现ViewPager.PageTransformer来做出完全不同的切换动画效果。
关键是要理解transformPage(View view, float position)的参数。view理所当然就是滑动中的那个view，position这里是float类型，
不是平时理解的int位置，而是当前滑动状态的一个表示，比如当滑动到正全屏时，position是0，而向左滑动，使得右边刚好有一部被进入屏幕时，
position是1，如果前一页和下一页基本各在屏幕占一半时，前一页的position是-0.5，后一页的posiotn是0.5，所以根据position的值,
我们就可以自行设置需要的Alpha，ScaleX和ScaleY。下面，我们来看看Google官方提供的两种动画效果的代码，很简单，相信每个人看过之后，
都能自己实现一种动画效果。

DepthPageTransformer:
 
### 效果图 ###

![image](https://github.com/AndyRenJie/ViewPagerTransformer/blob/master/gif/20180201.gif)

```
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
```

ScalePageTransformer:

### 效果图 ###

![image](https://github.com/AndyRenJie/ViewPagerTransformer/blob/master/gif/20180202.gif)

```
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
```
