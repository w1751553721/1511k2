package com.bwie.wangkui.mynewjdshopping.goods_details.model;

import android.util.Log;

import com.bwie.wangkui.mynewjdshopping.API;
import com.bwie.wangkui.mynewjdshopping.ApiServer;
import com.bwie.wangkui.mynewjdshopping.GoodsSeek.SeekModel;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by ThinkPad on 2018/4/10.
 */

public class GoodsDatailsModel {

    public interface CallBackData{
        void onSuccess(GoodsDatailsBean goodsDatails);
    }
    public CallBackData callBackData;

    public void setCallBackData(CallBackData callBackData) {
        this.callBackData = callBackData;
    }

    public void getData(String pid,String cource){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.good4)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        ApiServer apiServer = retrofit.create(ApiServer.class);
        Observable<GoodsDatailsBean> datails = apiServer.getGoodsDatails(pid, cource);
        datails
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GoodsDatailsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("++++++++++",e.getMessage());
                    }

                    @Override
                    public void onNext(GoodsDatailsBean seekBean) {
                        callBackData.onSuccess(seekBean);
                    }
                });
    }
}
