package com.bwie.wangkui.mynewjdshopping.goods_details.presenter;

import com.bwie.wangkui.mynewjdshopping.goods_details.model.GoodsDatailsBean;
import com.bwie.wangkui.mynewjdshopping.goods_details.model.GoodsDatailsModel;
import com.bwie.wangkui.mynewjdshopping.goods_details.view.DatailsIVew;

/**
 * Created by ThinkPad on 2018/4/10.
 */

public class GoodsDatailsPresenter {
    private DatailsIVew datailsIVew;
    private GoodsDatailsModel goodsDatailsModel;

    public GoodsDatailsPresenter(DatailsIVew datailsIVew) {
        this.datailsIVew = datailsIVew;
        goodsDatailsModel = new GoodsDatailsModel();
    }

    public void guanlian(String pid, String source){
         goodsDatailsModel.getData(pid,source);
         goodsDatailsModel.setCallBackData(new GoodsDatailsModel.CallBackData() {
             @Override
             public void onSuccess(GoodsDatailsBean goodsDatails) {
                 datailsIVew.showData(goodsDatails);
             }
         });
    }
}
