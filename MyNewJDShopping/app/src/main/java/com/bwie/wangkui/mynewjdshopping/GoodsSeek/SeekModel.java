package com.bwie.wangkui.mynewjdshopping.GoodsSeek;



import android.util.Log;

import com.bwie.wangkui.mynewjdshopping.API;
import com.bwie.wangkui.mynewjdshopping.ApiServer;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ThinkPad on 2018/3/17.
 */

public class SeekModel {
      public interface CallBackData{
          void onSuccess(SeekBean seekBean);
      }
      public CallBackData callBackData;

    public void setCallBackData(CallBackData callBackData) {
        this.callBackData = callBackData;
    }

    public void getData(String words,int page,String cource,int sort){
          Retrofit retrofit = new Retrofit.Builder()
                  .baseUrl(API.goodsseek)
                  .addConverterFactory(GsonConverterFactory.create())
                  .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                  .build();
          ApiServer apiServer = retrofit.create(ApiServer.class);
        Observable<SeekBean> seek = apiServer.getSeek(words, page,cource,sort);
        seek
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(new Observer<SeekBean>() {
                       @Override
                       public void onCompleted() {

                       }

                       @Override
                       public void onError(Throwable e) {
                            Log.d("++++++++++",e.getMessage());
                       }

                       @Override
                       public void onNext(SeekBean seekBean) {
                        callBackData.onSuccess(seekBean);
                       }
                   });
      }

}
