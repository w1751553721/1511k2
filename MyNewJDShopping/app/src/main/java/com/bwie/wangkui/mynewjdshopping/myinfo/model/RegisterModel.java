package com.bwie.wangkui.mynewjdshopping.myinfo.model;




import com.bwie.wangkui.mynewjdshopping.API;
import com.bwie.wangkui.mynewjdshopping.ApiServer;
import com.bwie.wangkui.mynewjdshopping.myinfo.model.bean.RegisterBean;

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

public class RegisterModel {
      public interface CallBackData{
          void onSuccess(RegisterBean registerBean);
      }
      public CallBackData callBackData;

    public void setCallBackData(CallBackData callBackData) {
        this.callBackData = callBackData;
    }

    public void getData(String name, String pwd){
          Retrofit retrofit = new Retrofit.Builder()
                  .baseUrl(API.allinterface)
                  .addConverterFactory(GsonConverterFactory.create())
                  .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                  .build();
          ApiServer apiServer = retrofit.create(ApiServer.class);
        Observable<RegisterBean> registerInfo = apiServer.getRegisterInfo(name, pwd);
        registerInfo
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(new Observer<RegisterBean>() {
                       @Override
                       public void onCompleted() {

                       }

                       @Override
                       public void onError(Throwable e) {

                       }

                       @Override
                       public void onNext(RegisterBean registerBean) {
                           callBackData.onSuccess(registerBean);
                       }
                   });
      }

}
