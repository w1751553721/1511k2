package com.bwie.wangkui.mynewjdshopping.homepage.presenter;

import com.bwie.wangkui.mynewjdshopping.homepage.model.BannerModel;
import com.bwie.wangkui.mynewjdshopping.homepage.model.bean.BannerBean;
import com.bwie.wangkui.mynewjdshopping.homepage.view.myinterface.BannerView;

/**
 * Created by ThinkPad on 2018/4/1.
 */

public class BannerPresenter {
    private BannerModel bannerModel;
    private BannerView bannerView;

    public BannerPresenter(BannerView bannerView) {
        this.bannerView = bannerView;
        bannerModel = new BannerModel();
    }

    public void relevance(){
       bannerModel.getServerData();
       bannerModel.setCallBackData(new BannerModel.CallBackData() {
           @Override
           public void backData(BannerBean bean) {
               bannerView.CallBackBanner(bean);
           }
       });
    }
}
