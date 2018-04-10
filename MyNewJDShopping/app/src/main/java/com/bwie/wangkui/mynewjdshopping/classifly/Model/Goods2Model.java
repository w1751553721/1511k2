package com.bwie.wangkui.mynewjdshopping.classifly.Model;

import android.util.Log;

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

/**
 * Created by ThinkPad on 2018/3/14.
 */

public class Goods2Model {
    public interface CallBackData{
        void callBack(Goods2Bean goods2Bean);
    }
    public CallBackData callBackData;

    public void setCallBackData(CallBackData callBackData) {
        this.callBackData = callBackData;
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessage(Goods2Bean goods2Bean){
        callBackData.callBack(goods2Bean);
        EventBus.getDefault().unregister(this);
    }
    
    public void getData(String url){
        Request request = new Request.Builder().url(url).build();
        new OkHttpClient.Builder().build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Goods2Bean goods2Bean = new Gson().fromJson(string, Goods2Bean.class);
                Log.d("=======",goods2Bean.getData().size()+"");
                EventBus.getDefault().post(goods2Bean);
            }
        });
    }
}
