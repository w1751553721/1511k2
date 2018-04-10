package com.bwie.wangkui.mynewjdshopping.shoppingcar.model;

import android.util.Log;
import com.bwie.wangkui.mynewjdshopping.API;
import com.bwie.wangkui.mynewjdshopping.ApiServer;
import com.bwie.wangkui.mynewjdshopping.shoppingcar.Bean.Bean;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ThinkPad on 2018/4/3.
 */

public class CarSeekModel {

    public interface CallBackData{
        void callBack(Bean bean);
    }
    CallBackData callBackData;

    public void setCallBackData(CallBackData callBackData) {
        this.callBackData = callBackData;
    }

    public void getData(){
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(API.seekurl)
                .build();
        Observable<Bean> observable = retrofit.create(ApiServer.class).getAllData();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("=========",e.getMessage());
                    }

                    @Override
                    public void onNext(Bean carBean) {
                        callBackData.callBack(carBean);
                    }
                });
    }
}
