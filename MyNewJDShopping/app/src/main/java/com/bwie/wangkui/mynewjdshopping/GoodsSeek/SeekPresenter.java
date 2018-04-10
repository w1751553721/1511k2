package com.bwie.wangkui.mynewjdshopping.GoodsSeek;



/**
 * Created by ThinkPad on 2018/3/17.
 */

public class SeekPresenter {
     private SeekIView seekIView;
    private SeekModel seekModel;

    public SeekPresenter(SeekIView seekIView) {
        this.seekIView = seekIView;
        seekModel = new SeekModel();
    }


    public void relevance(String words,int page,String cource,int sort){
        //判断
        seekModel.getData(words,page,cource, sort);
        seekModel.setCallBackData(new SeekModel.CallBackData() {
            @Override
            public void onSuccess(SeekBean seekBean) {
                seekIView.showData(seekBean);
            }
        });
    }
}
