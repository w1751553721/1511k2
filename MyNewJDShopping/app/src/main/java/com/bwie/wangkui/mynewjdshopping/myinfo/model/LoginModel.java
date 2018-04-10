package com.bwie.wangkui.mynewjdshopping.myinfo.model;


import android.util.Log;

import com.bwie.wangkui.mynewjdshopping.API;
import com.bwie.wangkui.mynewjdshopping.ApiServer;
import com.bwie.wangkui.mynewjdshopping.myinfo.model.bean.LoginBean;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by ThinkPad on 2018/3/17.
 */

public class LoginModel {
      public interface CallBackData{
          void onSuccess(LoginBean loginBean);
      }
      public CallBackData callBackData;

    public void setCallBackData(CallBackData callBackData) {
        this.callBackData = callBackData;
    }

    public void getData(String name, String pwd){
        Log.e("======",""+name+"===="+pwd);
          Retrofit retrofit = new Retrofit.Builder()
                  .baseUrl(API.allinterface)
                  .addConverterFactory(GsonConverterFactory.create())
                  .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                  .build();
          ApiServer apiServer = retrofit.create(ApiServer.class);
          Observable<LoginBean> loginInfo = apiServer.getLoginInfo(name, pwd);
          loginInfo
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Observer<LoginBean>() {
                      @Override
                      public void onCompleted() {
                      }
                      @Override
                      public void onError(Throwable e) {
                          Log.d("======",""+e.getMessage());
                      }

                      @Override
                      public void onNext(LoginBean loginBean) {
                          Log.e("======",""+loginBean.getData().getToken());
                          callBackData.onSuccess(loginBean);
                      }
                  });
      }

}
