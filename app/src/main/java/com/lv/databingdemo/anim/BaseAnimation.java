package com.lv.databingdemo.anim;

import android.animation.Animator;
import android.view.View;

/**
 * User: 吕勇
 * Date: 2016-05-18
 * Time: 9:39
 * Description:RecyclerView item动画基类
 */
public interface  BaseAnimation {

    Animator[] getAnimators(View view);

}
