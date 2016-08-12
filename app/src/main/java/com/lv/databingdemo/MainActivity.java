package com.lv.databingdemo;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.lv.databingdemo.base.BaseActivity;
import com.lv.databingdemo.base.LBaseAdapter;
import com.lv.databingdemo.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity<ActivityMainBinding> {
    private LBaseAdapter<String> mBaseAdapter;
    @Override
    protected int loadLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void bindToolbar() {
        mBinding.mainToolbar.setTitle("测试");
        setSupportActionBar(mBinding.mainToolbar);
    }

    @Override
    protected void processLogic() {
        mBaseAdapter=new LBaseAdapter<String>(R.layout.item_main, com.lv.databingdemo.BR.mainItem){
            @Override
            protected void onItemClick(String s) {
                JieCaoAct.startJieCaoAct(MainActivity.this);
            }
        };
        mBinding.mainRecyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mBinding.mainRecyclerview.setAdapter(mBaseAdapter);
        mBaseAdapter.notifyDataSetChanged();
        addDatas();
    }

    private void addDatas() {
        Observable.timer(5, TimeUnit.SECONDS)
                .map(new Func1<Long, List<String>>() {
                    @Override
                    public List<String> call(Long aLong) {
                        Log.d("MainActivity", Thread.currentThread().getName());
                        List<String> array = new ArrayList<>();
                        array.add("asdfasdf");
                        array.add("asdfasdf");
                        array.add("asdfasdf");
                        array.add("asdfasdf");
                        array.add("asdfasdf");
                        array.add("asdfasdf");
                        array.add("asdfasdf");
                        array.add("asdfasdf");
                        array.add("asdfasdf");
                        array.add("asdfasdf");
                        array.add("asdfasdf");
                        array.add("asdfasdf");
                        array.add("asdfasdf");
                        array.add("asdfasdf");
                        array.add("asdfasdf");
                        array.add("asdfasdf");
                        return array;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> strings) {
                        mBaseAdapter.addItems(strings);
                    }
                });
    }

}
