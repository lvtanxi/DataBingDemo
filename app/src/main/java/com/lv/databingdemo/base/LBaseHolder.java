package com.lv.databingdemo.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * User: 吕勇
 * Date: 2016-03-01
 * Time: 8:39
 * Description:HeaderView的ViewHolder
 */
public class LBaseHolder extends ViewHolder {

    private ViewDataBinding dataBinding;

    public LBaseHolder(ViewGroup parent, int layoutResId) {
        this(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layoutResId, parent, false));
    }

    public LBaseHolder(View view) {
        super(view);
    }

    public LBaseHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        dataBinding = binding;
    }

    public <T extends ViewDataBinding> T getDataBinding() {
        return (T) dataBinding;
    }


    public LBaseHolder setOnItemChildClickListener(LBaseAdapter.OnItemChildClickListener listener,View...views) {
        listener.position = getAdapterPosition();
        for (View view : views) {
            view.setOnClickListener(listener);
        }
        return this;
    }
}
