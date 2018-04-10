package com.bwie.wangkui.mynewjdshopping.base;

/**
 * Created by hasee on 2018/2/2.
 */

public abstract class BasePresenter<V extends IBaseView> {
    protected V view;

    //在构造器中把和view层交互的接口传入
    public BasePresenter(V view) {
        this.view = view;
        initModle();
    }

    //规范子类初始化molde用的
    protected abstract void initModle();

    public void onDestroy() {
        view = null;
    }
//
//    protected Context context() {
//        if (view != null && view.context() != null) {
//            return context();
//        }
//        return MyApplication.getAppContext();
//    }


}
