package com.bwie.wangkui.mynewjdshopping.shoppingcar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.bwie.wangkui.mynewjdshopping.R;
import com.bwie.wangkui.mynewjdshopping.shoppingcar.Bean.Bean;
import com.bwie.wangkui.mynewjdshopping.shoppingcar.Bean.MessageEvent;
import com.bwie.wangkui.mynewjdshopping.shoppingcar.Bean.PriceAndCountEvent;
import com.bwie.wangkui.mynewjdshopping.shoppingcar.model.ExpandAdapter;
import com.bwie.wangkui.mynewjdshopping.shoppingcar.presenter.CarSeekPresenter;
import com.bwie.wangkui.mynewjdshopping.shoppingcar.view.CarSeekinterface;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ThinkPad on 2018/4/1.
 */

public class Fragment4 extends Fragment implements CarSeekinterface {


    @InjectView(R.id.goodsnum)
    TextView goodsnum;
    @InjectView(R.id.exp)
    ExpandableListView exp;
    @InjectView(R.id.allcheckBox)
    CheckBox allcheckBox;
    @InjectView(R.id.textView)
    TextView textView;
    @InjectView(R.id.allprice)
    TextView allprice;
    @InjectView(R.id.textView3)
    TextView textView3;
    private String uid = "57";

    private CarSeekPresenter presenter;
    private ExpandAdapter adapter;
    private List<Bean.DataBean> data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment4, null);
        EventBus.getDefault().register(this);
        presenter = new CarSeekPresenter(this);
        ButterKnife.inject(this, view);
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        presenter.relevance();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void showData(Bean bean) {
        data = bean.getData();
        adapter = new ExpandAdapter(getActivity(), data);
        exp.setAdapter(adapter);
        //设置默认展开
        exp.setGroupIndicator(null);
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            exp.expandGroup(i);
        }
        //设置全选全不选
        allcheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < data.size(); i++) {
                    adapter.changeAllCkState(allcheckBox.isChecked());
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    //获取  数量 价格 设置给页面
    @Subscribe
    public void onMessageEvent(PriceAndCountEvent event) {
        textView3.setText("去支付(" + event.getCount() + ")");
        allprice.setText("￥" + event.getPrice());

    }

    //设置全选按钮的状态
    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        allcheckBox.setChecked(event.isChecked());
        adapter.notifyDataSetChanged();
    }

}
