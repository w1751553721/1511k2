package com.bwie.wangkui.mynewjdshopping.classifly.Presenter;

import com.bwie.wangkui.mynewjdshopping.classifly.Model.Goods2Bean;
import com.bwie.wangkui.mynewjdshopping.classifly.Model.Goods2Model;
import com.bwie.wangkui.mynewjdshopping.classifly.View.Goods2IView;

/**
 * Created by ThinkPad on 2018/3/14.
 */

public class Goods2Presenter {
    public Goods2Model goods2Model;
    public Goods2IView goods2IView;

    public Goods2Presenter(Goods2IView goods2IView) {
        this.goods2IView = goods2IView;
        goods2Model = new Goods2Model();
    }


    public void guanlian(String url){
        goods2Model.getData(url);
        goods2Model.setCallBackData(new Goods2Model.CallBackData() {
            @Override
            public void callBack(Goods2Bean goods2Bean) {
                goods2IView.goods2data(goods2Bean);
            }
        });
    }
}
