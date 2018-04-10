package com.bwie.wangkui.mynewjdshopping.find.model;

import android.util.Log;

import com.bwie.wangkui.mynewjdshopping.API;
import com.bwie.wangkui.mynewjdshopping.ApiServer;
import com.bwie.wangkui.mynewjdshopping.homepage.model.BannerModel;
import com.bwie.wangkui.mynewjdshopping.homepage.model.bean.BannerBean;
import com.bwie.wangkui.mynewjdshopping.homepage.model.bean.GridBean;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ThinkPad on 2018/4/2.
 */

public class MyVideoModel {
    //定义接口
    public interface CallBackData{
        void backData(VideoBean videoBean);
    }
    CallBackData callBackData;
    //提供方法
    public void setCallBackData(CallBackData callBackData) {
        this.callBackData = callBackData;

    }
    public void getServerData(){
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(API.videourl)
                .build();
        Observable<VideoBean> observable = retrofit.create(ApiServer.class).getVideoData();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VideoBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(VideoBean videoBean) {
                        callBackData.backData(videoBean);
                    }
                });


    }
}
