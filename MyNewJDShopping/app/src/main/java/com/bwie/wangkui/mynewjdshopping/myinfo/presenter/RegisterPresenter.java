package com.bwie.wangkui.mynewjdshopping.myinfo.presenter;


import com.bwie.wangkui.mynewjdshopping.myinfo.model.RegisterModel;
import com.bwie.wangkui.mynewjdshopping.myinfo.model.bean.RegisterBean;
import com.bwie.wangkui.mynewjdshopping.myinfo.view.myinteface.RegisterIView;

/**
 * Created by ThinkPad on 2018/3/17.
 */

public class RegisterPresenter {
     private RegisterIView registerIView;
    private RegisterModel registerModel;

    public RegisterPresenter(RegisterIView registerIView) {
        this.registerIView = registerIView;
        registerModel = new RegisterModel();
    }


    public void relevance(String num,String pwd){
        //判断
        registerModel.getData(num,pwd);
        registerModel.setCallBackData(new RegisterModel.CallBackData() {
             @Override
             public void onSuccess(RegisterBean registerBean) {
                 registerIView.showData(registerBean);
             }
         });
    }
}
