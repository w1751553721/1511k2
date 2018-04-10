package com.bwie.wangkui.mynewjdshopping.homepage.presenter;
import com.bwie.wangkui.mynewjdshopping.homepage.model.GridModel;
import com.bwie.wangkui.mynewjdshopping.homepage.model.bean.GridBean;
import com.bwie.wangkui.mynewjdshopping.homepage.view.myinterface.GridInterface;

/**
 * Created by ThinkPad on 2018/3/9.
 */

public class GridPresenter {
    private GridInterface gridInterface;
    private GridModel gridModel;

    public GridPresenter(GridInterface gridInterface) {
        this.gridInterface = gridInterface;
        gridModel = new GridModel();
    }

    public void relevance(){
        gridModel.getServerData();
        gridModel.setCallBackData(new GridModel.CallBackData() {
            @Override
            public void backData(GridBean bean) {
                gridInterface.GridData(bean);
            }
        });

    }
}
