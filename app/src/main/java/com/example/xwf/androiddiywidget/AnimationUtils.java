package com.example.xwf.androiddiywidget;

import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

/**
 * Created by Hsia on 16/5/13.
 * E-mail: xiaweifeng@live.cn
 * //TODO:动画工具类
 */
public class AnimationUtils {
    //定义动画执行过程
    public static int runingAnimation = 0;
    /**
     * 退出动画
     * @param rl
     */
    public static void rotateOutAnimation(RelativeLayout rl,long offset){
        RotateAnimation rotateAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setStartOffset(offset);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setAnimationListener(new MyAnimationListener());
        rl.startAnimation(rotateAnimation);
    }

    /**
     * 进入动画
     * @param rl
     */
    public static void rotateInAnimation(RelativeLayout rl,long offset){
        RotateAnimation rotateAnimation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setStartOffset(offset);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setAnimationListener(new MyAnimationListener());
        rl.startAnimation(rotateAnimation);
    }

    //设置一个动画监听
    static class MyAnimationListener implements Animation.AnimationListener{

        private static final String TAG = "Hsia";


        @Override
        public void onAnimationStart(Animation animation) {
            runingAnimation++;
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            runingAnimation--;
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
