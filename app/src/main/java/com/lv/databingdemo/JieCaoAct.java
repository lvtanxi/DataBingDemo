package com.lv.databingdemo;

import android.app.Activity;
import android.content.Intent;

import com.bumptech.glide.Glide;
import com.lv.databingdemo.base.BaseActivity;
import com.lv.databingdemo.databinding.ActJiecaoBinding;

/**
 * User: 吕勇
 * Date: 2016-06-29
 * Time: 14:21
 * Description:
 */
public class JieCaoAct extends BaseActivity<ActJiecaoBinding>{

    public static void startJieCaoAct(Activity activity) {
        activity.startActivity(new Intent(activity, JieCaoAct.class));
    }
    @Override
    protected int loadLayoutId() {
        return R.layout.act_jiecao;
    }

    @Override
    protected void bindToolbar() {
        setSupportActionBar(mBinding.jiecaoToolbar);
    }

    @Override
    protected void processLogic() {
        mBinding.customVideoplayerStandard.setUp("http://2449.vod.myqcloud.com/2449_bfbbfa3cea8f11e5aac3db03cda99974.f20.mp4"
                , "嫂子想我没");
        Glide
                .with(this)
                .load("\"http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640\"")
                .centerCrop()
                .placeholder(R.drawable.ic_arrow_back_black_24dp)
                .crossFade()
                .into(mBinding.customVideoplayerStandard.thumbImageView);
    }
}
