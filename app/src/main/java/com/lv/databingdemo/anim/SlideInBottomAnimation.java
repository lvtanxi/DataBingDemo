package com.lv.databingdemo.anim;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * User: 吕勇
 * Date: 2016-05-18
 * Time: 9:39
 * Description:RecyclerView 下先动画
 */

public class SlideInBottomAnimation implements BaseAnimation {

    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[]{
                ObjectAnimator.ofFloat(view, "translationY", view.getMeasuredHeight(), 0)
        };
    }
}
