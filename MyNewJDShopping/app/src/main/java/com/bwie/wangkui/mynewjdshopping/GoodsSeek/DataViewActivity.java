package com.bwie.wangkui.mynewjdshopping.GoodsSeek;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.bawei.swiperefreshlayoutlibrary.SwipyRefreshLayout;
import com.bawei.swiperefreshlayoutlibrary.SwipyRefreshLayoutDirection;
import com.bwie.wangkui.mynewjdshopping.R;
import com.bwie.wangkui.mynewjdshopping.base.BaseActivity;
import com.bwie.wangkui.mynewjdshopping.goods_details.view.GoodsDatails;

import java.util.ArrayList;
import java.util.List;


public class DataViewActivity extends BaseActivity<SeekPresenter> implements SeekIView {
    /**
     * 请输入搜索信息
     */
    private EditText mSeek;
    private ImageView mImageView2;
    private RecyclerView mRlv;
    private SwipyRefreshLayout mSrl;
    private Button seek;
    private String source = "android";
    private int sort = 0;
    private int page = 1;
    private String words = "笔记本";
    private boolean falg = true;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private ImageView back;
    private List<SeekBean.DataBean> lists;
    private boolean booleanExtra;
    private ReAdapter adapter;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_data_view;
    }
    @Override
    protected void initView() {
        mSeek = (EditText) findViewById(R.id.seek);
        mImageView2 = (ImageView) findViewById(R.id.imageView2);
        mRlv = (RecyclerView) findViewById(R.id.rlv);
        mSrl = (SwipyRefreshLayout) findViewById(R.id.srl);
        seek = (Button) findViewById(R.id.button5);
        back = (ImageView) findViewById(R.id.back);
        //获取从首页搜索框穿过来的值进行数据展示
          words = getIntent().getStringExtra("seek");
        booleanExtra = getIntent().getBooleanExtra("flag",true);
        present.relevance(words, page, source, sort);

        //设置上拉加载下拉刷新
        mSrl.setDirection(SwipyRefreshLayoutDirection.BOTH);
        //列表布局
        linearLayoutManager = new LinearLayoutManager(this);
        //网格布局
        gridLayoutManager = new GridLayoutManager(this, 2);
         mRlv.setLayoutManager(linearLayoutManager);
        present.relevance(words,page,source,sort);
    }

    @Override
    protected SeekPresenter getPovidPresenter() {
        return new SeekPresenter(this);
    }

    @Override
    protected void initData() {
        //点击搜索
       seek.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               words = mSeek.getText().toString();
               if("".equals(words)) {
                   String s = mSeek.getHint().toString();
                   present.relevance(s, page, source, sort);
                   Toast.makeText(DataViewActivity.this, "搜索内容不能为空", Toast.LENGTH_SHORT).show();
               }else {
                   present.relevance(words, page, source, sort);
               }
           }
       });
        //点击切换视图
        mImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(falg){
                    mRlv.setLayoutManager(gridLayoutManager);
                    falg = false;
                }else {
                    mRlv.setLayoutManager(linearLayoutManager);
                    falg = true;
                }
            }
        });
        //上拉加载 下拉刷新
        mSrl.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(int index) {
                page = 1;
                present.relevance(words,page,source,sort);
                mSrl.setRefreshing(false);
            }

            @Override
            public void onLoad(int index) {
                page++;
                present.relevance(words,page,source,sort);
                mSrl.setRefreshing(false);
            }
        });

        //点击返回
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(booleanExtra){
                    finish();
                }
            }
        });
    }
    @Override
    public void showData(SeekBean seekBean) {
        final List<SeekBean.DataBean> data = seekBean.getData();
        //判断是否有数据
         if(data.size()>0){
             if(page==1){
                 lists = new ArrayList<>();
             }
             lists.addAll(data);
             //配置适配器
             adapter = new ReAdapter(this, lists);
            adapter.setCallBackPosition(new ReAdapter.CallBackPosition() {
                @Override
                public void callBackPosition(int pos) {
                   //根据点击下标获得pid 带值 跳转到商品详情界面 请求数据
                    Intent intent = new Intent(DataViewActivity.this, GoodsDatails.class);
                    intent.putExtra("pid",data.get(pos).getPid()+"");
                    startActivity(intent);
                }
            });
             mRlv.setAdapter(adapter);
         }else{
             Toast.makeText(this, "暂时无此商品数据，要不看看笔记本或者手机？", Toast.LENGTH_SHORT).show();
         }
    }
}
