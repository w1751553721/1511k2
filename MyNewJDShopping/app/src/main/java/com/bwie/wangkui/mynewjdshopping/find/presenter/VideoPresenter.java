package com.bwie.wangkui.mynewjdshopping.find.presenter;

import android.util.Log;

import com.bwie.wangkui.mynewjdshopping.find.model.VideoBean;
import com.bwie.wangkui.mynewjdshopping.find.model.MyVideoModel;
import com.bwie.wangkui.mynewjdshopping.find.view.MyVideoView;

/**
 * Created by ThinkPad on 2018/4/2.
 */

public class VideoPresenter {
    private MyVideoModel videoModel;
    private MyVideoView videoView;

    public VideoPresenter(MyVideoView videoView) {
        this.videoView = videoView;
        videoModel = new MyVideoModel();
    }

    public void relevance(){
        Log.e("444444","444444");
        videoModel.getServerData();
        videoModel.setCallBackData(new MyVideoModel.CallBackData() {
            @Override
            public void backData(VideoBean videoBean) {
                videoView.ShowData(videoBean);
            }
        });
    }
}
