package com.bwie.wangkui.mynewjdshopping.classifly.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.bwie.wangkui.mynewjdshopping.API;
import com.bwie.wangkui.mynewjdshopping.R;
import com.bwie.wangkui.mynewjdshopping.classifly.Model.Goods2Adapter;
import com.bwie.wangkui.mynewjdshopping.classifly.Model.Goods2Bean;
import com.bwie.wangkui.mynewjdshopping.classifly.Model.Goods3Adapter;
import com.bwie.wangkui.mynewjdshopping.classifly.Presenter.Goods2Presenter;
import com.bwie.wangkui.mynewjdshopping.homepage.model.bean.GridBean;
import com.bwie.wangkui.mynewjdshopping.homepage.presenter.GridPresenter;
import com.bwie.wangkui.mynewjdshopping.homepage.view.myinterface.GridInterface;
import com.orhanobut.logger.Logger;


import java.util.List;

/**
 * 分类
 * Created by ThinkPad on 2018/2/6.
 */

public class Fragment2 extends Fragment implements GridInterface {
    private View view;
    private ListView mList;
    private RecyclerView mRlv;
    private int currentItem = 0;
    private List<GridBean.DataBean> data;
    private int cid = 1;
    private List<Goods2Bean.DataBean> data2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment2, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mList = (ListView) view.findViewById(R.id.list);
        //list集合去除右侧滚动条
        mList.setVerticalScrollBarEnabled(false);
        mList.setScrollbarFadingEnabled(false);

        mRlv = (RecyclerView) view.findViewById(R.id.rlv);
        mRlv.setLayoutManager(new LinearLayoutManager(getActivity()));
        //获取点击位置

       //获取一级商品分类数据
        GridPresenter gridPresenter = new GridPresenter(this);
        gridPresenter.relevance();
        //获取耳机商品列表信息
        getGoodsInfo();

    }

    private void getGoodsInfo() {
        new Goods2Presenter(new Goods2IView() {
            @Override
            public void goods2data(Goods2Bean goods2Bean) {
                data2 = goods2Bean.getData();
                mRlv.setAdapter(new Goods2Adapter(getActivity(), data2));
            }
        }).guanlian(API.good2+"?cid="+(currentItem+1));
    }

    @Override
    public void GridData(GridBean gridBean) {
        data = gridBean.getData();
        final ListAdapter listAdapter = new ListAdapter();
        mList.setAdapter(listAdapter);
        //点击切换背景颜色 以及传入分类ID
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                  currentItem = i;
                getGoodsInfo();
                listAdapter.notifyDataSetChanged();
            }
        });
    }
    //listView适配器
    public class ListAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = View.inflate(getActivity(),R.layout.list_item,null);
            TextView name = view.findViewById(R.id.litem);
            name.setText(data.get(i).getName());
            if(i == currentItem){
                view.setBackgroundResource(R.color.huise);
            }else{
                view.setBackgroundResource(R.color.white);
            }
            return view;
        }
    }
}
