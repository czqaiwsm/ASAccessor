package com.accessories.city.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ScrollView;

/**
 * @desc 自定义scrollView(属性动画实现具有反弹效果的ScrollView)
 * @creator caozhiqing
 * @data 2016/2/19
 */
public class FlowScrollView extends ScrollView {

    private View contentView;
    private final float scalCount = 0.8f;//阻尼系数
    private float downY;//点击时的y点
    private ObjectAnimator objectAnimator;//动画
    private float distanceY;//移动距离
    private boolean isMoveing = false;//动画是正在进行
//    private int scrollHeight = 0;//当内容不足以全屏时，内容随手指可滑动距离



    public FlowScrollView(Context context) {
        super(context);
    }

    public FlowScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onFinishInflate() {//视图加载完成
        super.onFinishInflate();
        if (getChildCount() > 0) {
            contentView = getChildAt(0);
//            scrollHeight = contentView.getHeight();

        }
    }



    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (isMoveing) {
            return false;
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = ev.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                float deltY = ev.getY() - downY;
                if (Math.abs(deltY) > 10 &&(getScrollY() == 0 || (getScrollY()+getHeight())==contentView.getHeight())) {
                    contentView.setY(contentView.getY() + deltY * scalCount);
                    distanceY += deltY * scalCount;
                }
                downY = ev.getY();
                return  false;
            case MotionEvent.ACTION_UP:

                if (Math.abs(distanceY) > 0) {
                    objectAnimator = ObjectAnimator.ofFloat(contentView, "translationY", distanceY, -(float) contentView.getTop());
                    objectAnimator.setInterpolator(new DecelerateInterpolator());
                    objectAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            isMoveing = true;
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            distanceY = 0;
                            isMoveing = false;
                        }
                    });
                    objectAnimator.setDuration(300);
                    objectAnimator.start();
                }

                break;
            default:
                break;
        }

        return true;
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
