package com.lv.databingdemo.anim;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * User: 吕勇
 * Date: 2016-05-18
 * Time: 9:39
 * Description:RecyclerView 淡进动画
 */

public class AlphaInAnimation implements BaseAnimation {

    private static final float DEFAULT_ALPHA_FROM = 0f;
    private final float mFrom;

    public AlphaInAnimation() {
        this(DEFAULT_ALPHA_FROM);
    }

    public AlphaInAnimation(float from) {
        mFrom = from;
    }

    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[]{ObjectAnimator.ofFloat(view, "alpha", mFrom, 1f)};
    }
}
