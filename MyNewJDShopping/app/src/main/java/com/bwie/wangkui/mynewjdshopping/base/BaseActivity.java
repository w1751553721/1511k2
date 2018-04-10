package com.bwie.wangkui.mynewjdshopping.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ThinkPad on 2018/3/17.
 */

public abstract class BaseActivity<T> extends AppCompatActivity {
    public T present;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获得布局
        setContentView(getLayoutID());
        //获得presenter
        present = getPovidPresenter();
        //初始化视图
        initView();
        //数据操作
        initData();

    }

    protected abstract int getLayoutID();
    protected abstract void initView();
    protected abstract T getPovidPresenter();
    protected abstract void initData();






}
