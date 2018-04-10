package com.bwie.wangkui.mynewjdshopping.homepage.model;

import com.bwie.wangkui.mynewjdshopping.API;
import com.bwie.wangkui.mynewjdshopping.ApiServer;
import com.bwie.wangkui.mynewjdshopping.homepage.model.bean.BannerBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ThinkPad on 2018/4/1.
 */

public class BannerModel {
    //定义接口
    public interface CallBackData{
        void backData(BannerBean bean);
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
                .baseUrl(API.allinterface)
                .build();
        Observable<BannerBean> observable = retrofit.create(ApiServer.class).getBannerData();
        observable.subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Observer<BannerBean>() {
                      @Override
                      public void onCompleted() {
                      }
                      @Override
                      public void onError(Throwable e) {
                      }
                      @Override
                      public void onNext(BannerBean bannerBean) {
                          callBackData.backData(bannerBean);
                      }
                  });


    }
}
