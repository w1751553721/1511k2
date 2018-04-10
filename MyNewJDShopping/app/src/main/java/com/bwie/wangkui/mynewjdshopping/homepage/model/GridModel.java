package com.bwie.wangkui.mynewjdshopping.homepage.model;

import com.bwie.wangkui.mynewjdshopping.API;
import com.bwie.wangkui.mynewjdshopping.ApiServer;
import com.bwie.wangkui.mynewjdshopping.homepage.model.bean.BannerBean;
import com.bwie.wangkui.mynewjdshopping.homepage.model.bean.GridBean;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ThinkPad on 2018/3/9.
 */

public class GridModel {
    //定义接口
    public interface CallBackData{
        void backData(GridBean bean);
    }
    CallBackData callBackData;
    //提供方法
    public void setCallBackData(CallBackData callBackData) {
        this.callBackData = callBackData;

    }

    public void getServerData() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(API.good1)
                .build();
        Observable<GridBean> observable = retrofit.create(ApiServer.class).getGridData();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GridBean>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onNext(GridBean bean) {
                        callBackData.backData(bean);
                    }
                });
    }
}
