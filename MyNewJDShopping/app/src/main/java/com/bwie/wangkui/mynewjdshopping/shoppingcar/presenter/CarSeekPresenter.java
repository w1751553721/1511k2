package com.bwie.wangkui.mynewjdshopping.shoppingcar.presenter;


import com.bwie.wangkui.mynewjdshopping.shoppingcar.Bean.Bean;
import com.bwie.wangkui.mynewjdshopping.shoppingcar.model.CarSeekModel;
import com.bwie.wangkui.mynewjdshopping.shoppingcar.view.CarSeekinterface;

/**
 * Created by ThinkPad on 2018/4/3.
 */

public class CarSeekPresenter {
      private CarSeekModel carSeekModel;
      private CarSeekinterface seekinterface;

    public CarSeekPresenter(CarSeekinterface seekinterface) {
        this.seekinterface = seekinterface;
        carSeekModel = new CarSeekModel();
    }

    public void relevance(){
         carSeekModel.getData();
         carSeekModel.setCallBackData(new CarSeekModel.CallBackData() {
             @Override
             public void callBack(Bean o) {
                 seekinterface.showData(o);
             }
         });
    }
}
