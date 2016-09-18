package com.lv.databingdemo.base;

import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * User: 吕勇
 * Date: 2016-06-29
 * Time: 09:51
 * Description:
 */
public abstract class BaseActivity<DT extends ViewDataBinding> extends AppCompatActivity{

    protected DT mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mBinding= DataBindingUtil.setContentView(this, loadLayoutId());
        bindToolbar();
        initData();
        processLogic();
    }



    /**
     * 为Activity加载布局文件
     */
    protected abstract int loadLayoutId();

    /**
     * 设置Toolbar
     */
    protected void bindToolbar() {

    }
    /**
     * 初始化
     */
    protected void initData() {
    }
    /**
     * 为控件设置监听
     */
    protected void processLogic() {

    }

}
