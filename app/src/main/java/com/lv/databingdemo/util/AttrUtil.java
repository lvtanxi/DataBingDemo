package com.lv.databingdemo.util;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * User: 吕勇
 * Date: 2016-09-18
 * Time: 11:36
 * Description:代理属性
 */
public class AttrUtil {

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String url) {
        Log.d("BindingAdapter", "loadImage(ImageView view, String url)");
        Log.d("BindingAdapter", url + "");
        Glide.with(view.getContext()).load(url).into(view);
    }

    @BindingConversion
    public static ColorDrawable colorDrawable(int color) {
        Log.d("BindingConversion", color + "");
        return new ColorDrawable(color);
    }

    //解决部分死循环
    @BindingAdapter("android:text")
    public static void setText(TextView textView, CharSequence text) {
        Log.d("BindingAdapter", text + "");
        final CharSequence oldText = textView.getText();
        if (TextUtils.equals(oldText, text)){
            Log.d("BindingAdapter", "执行了return");
            return;
        }
        textView.setText(text);
    }
}
