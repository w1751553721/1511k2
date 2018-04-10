package com.bwie.wangkui.mynewjdshopping.myinfo.presenter;


import android.util.Log;

import com.bwie.wangkui.mynewjdshopping.MyApplication;
import com.bwie.wangkui.mynewjdshopping.myinfo.model.Aerifly;
import com.bwie.wangkui.mynewjdshopping.myinfo.model.LoginModel;
import com.bwie.wangkui.mynewjdshopping.myinfo.model.bean.LoginBean;
import com.bwie.wangkui.mynewjdshopping.myinfo.view.myinteface.LoginIView;

/**
 * Created by ThinkPad on 2018/3/17.
 */

public class LoginPresenter {
     private LoginIView loginIView;
     private LoginModel loginModel;

    public LoginPresenter(LoginIView loginIView) {
        this.loginIView = loginIView;
        loginModel = new LoginModel();
    }


    public void relevance(String num,String pwd) {
        //判断
        boolean b = Aerifly.numbleRex(MyApplication.getContext(), num);
        boolean b1 = Aerifly.pwdRex(MyApplication.getContext(), pwd);
        if (b && b1) {
            loginModel.getData(num, pwd);
            loginModel.setCallBackData(new LoginModel.CallBackData() {
                @Override
                public void onSuccess(LoginBean loginBean) {
                    loginIView.showData(loginBean);
                }
            });
        }
    }
}
