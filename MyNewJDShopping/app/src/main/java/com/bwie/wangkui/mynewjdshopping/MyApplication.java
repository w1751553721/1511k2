package com.bwie.wangkui.mynewjdshopping;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;


/**
 * Created by ThinkPad on 2018/4/1.
 */

public class MyApplication extends Application {
    //全局对象
    public static Context context;
    private static SharedPreferences uesr;

    @Override
    public void onCreate() {
        super.onCreate();
        //Fresco的初始化
        Fresco.initialize(this);
        context = this;
        //初始化小型数据库
        uesr = getSharedPreferences("myapplicatin", MODE_PRIVATE);
        ZXingLibrary.initDisplayOpinion(this);

    }
    //获取Application全局对象
    public static Context getContext(){
        return  context;
    }
    //获取SharedPreferences
    public static SharedPreferences getSharedPreferences(){
        return uesr;
    }


}
