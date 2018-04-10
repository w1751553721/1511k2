package com.bwie.wangkui.mynewjdshopping.find.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.wangkui.mynewjdshopping.R;
import com.bwie.wangkui.mynewjdshopping.find.model.VideoAdapter;
import com.bwie.wangkui.mynewjdshopping.find.model.VideoBean;
import com.bwie.wangkui.mynewjdshopping.find.presenter.VideoPresenter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ThinkPad on 2018/4/1.
 */

public class Fragment3 extends Fragment implements MyVideoView {
    @InjectView(R.id.rlv)
    RecyclerView rlv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment3, null);


        ButterKnife.inject(this, view);
        VideoPresenter presenter = new VideoPresenter(this);
        presenter.relevance();
        return view;
    }

    @Override
    public void ShowData(VideoBean videoBean) {
      rlv.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<VideoBean.DataBeanX.DataBean> data = videoBean.getData().getData();
        rlv.setAdapter(new VideoAdapter(getActivity(),data));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
