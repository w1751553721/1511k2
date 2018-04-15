package com.bwie.wangkui.mynewjdshopping;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.bwie.wangkui.mynewjdshopping.base.BaseActivity;
import com.bwie.wangkui.mynewjdshopping.classifly.View.Fragment2;
import com.bwie.wangkui.mynewjdshopping.find.view.Fragment3;
import com.bwie.wangkui.mynewjdshopping.homepage.view.Fragment1;
import com.bwie.wangkui.mynewjdshopping.myinfo.view.Fragment5;
import com.bwie.wangkui.mynewjdshopping.myinfo.view.LoginPage;
import com.bwie.wangkui.mynewjdshopping.shoppingcar.Fragment4;

public class MainActivity extends BaseActivity {
    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;
    private Fragment4 fragment4;
    private Fragment5 fragment5;
    private FrameLayout mFragment;
    private RadioGroup mRadiogroup;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    //获取资源ID
    protected void initView() {

        //资源ID
        mFragment = (FrameLayout) findViewById(R.id.fragment);
        mRadiogroup = (RadioGroup) findViewById(R.id.radiogroup);
        //得到fragment对象
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        fragment4 = new Fragment4();
        fragment5 = new Fragment5();
        //开启事务
        FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
        fm.add(R.id.fragment,fragment1).add(R.id.fragment,fragment3).add(R.id.fragment,fragment2)
                .add(R.id.fragment,fragment4).add(R.id.fragment,fragment5)
                .hide(fragment2).hide(fragment3).hide(fragment4).hide(fragment5)
                .commit();
        //设置点击切换页面
        mRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.btn1:
                        getSupportFragmentManager().beginTransaction().show(fragment1).hide(fragment2).hide(fragment3).hide(fragment4).hide(fragment5).commit();
                        break;
                    case R.id.btn2:
                        getSupportFragmentManager().beginTransaction().show(fragment2).hide(fragment1).hide(fragment3).hide(fragment4).hide(fragment5).commit();
                        break;
                    case R.id.btn3:
                        getSupportFragmentManager().beginTransaction().show(fragment3).hide(fragment2).hide(fragment1).hide(fragment4).hide(fragment5).commit();
                        break;
                    case R.id.btn4:
                        getSupportFragmentManager().beginTransaction().show(fragment4).hide(fragment2).hide(fragment3).hide(fragment1).hide(fragment5).commit();
                        break;
                    case R.id.btn5:
                        getSupportFragmentManager().beginTransaction().show(fragment5).hide(fragment2).hide(fragment3).hide(fragment4).hide(fragment1).commit();
                        break;
                }
            }
        });
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = this.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            this.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    protected Object getPovidPresenter() {
        return null;
    }

    @Override
    protected void initData() {

    }














    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*
        * 在程序结束时清楚登录信息
          再次登录时依然显示跳转页面
        * */
        MyApplication.getSharedPreferences().edit().putBoolean("firstlogin",false).commit();
    }
}
