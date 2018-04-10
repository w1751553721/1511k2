package com.bwie.wangkui.mynewjdshopping.homepage.view;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bwie.wangkui.mynewjdshopping.API;
import com.bwie.wangkui.mynewjdshopping.MainActivity;
import com.bwie.wangkui.mynewjdshopping.R;
import com.bwie.wangkui.mynewjdshopping.homepage.model.BannerImagerAdapter;
import com.bwie.wangkui.mynewjdshopping.homepage.model.GridRecyclerAdapter;
import com.bwie.wangkui.mynewjdshopping.homepage.model.MiaoshaAdapter;
import com.bwie.wangkui.mynewjdshopping.homepage.model.TuijianAdapter;
import com.bwie.wangkui.mynewjdshopping.homepage.model.bean.BannerBean;
import com.bwie.wangkui.mynewjdshopping.homepage.model.bean.GridBean;
import com.bwie.wangkui.mynewjdshopping.homepage.presenter.BannerPresenter;
import com.bwie.wangkui.mynewjdshopping.homepage.presenter.GridPresenter;
import com.bwie.wangkui.mynewjdshopping.homepage.view.myinterface.BannerView;
import com.bwie.wangkui.mynewjdshopping.homepage.view.myinterface.GridInterface;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.yzq.zxinglibrary.android.CaptureActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by ThinkPad on 2018/4/1.
 */

public class Fragment1 extends Fragment implements BannerView {
    @InjectView(R.id.banner)
    Banner banner;
    @InjectView(R.id.gifimg)
    ImageView gifimg;
    @InjectView(R.id.rlv)
    RecyclerView rlv;
    @InjectView(R.id.miaotime)
    TextView miaotime;
    @InjectView(R.id.miaosha)
    RecyclerView miaosha;
    @InjectView(R.id.tuijian)
    RecyclerView tuijian;
    @InjectView(R.id.img1)
    ImageButton img1;
    @InjectView(R.id.edit)
    EditText edit;
    private SimpleDateFormat myformat;
    private int REQUEST_CODE =999;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment1, null);
        ButterKnife.inject(this, view);
        //请求数据
        getData();
        //加载Gif图
        gifImageViewload();
        //加载横向滚动图标
        scollerhrizontal();
        return view;
    }

    //获取数据
    private void getData() {
        BannerPresenter bannerPresenter = new BannerPresenter(this);
        bannerPresenter.relevance();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void CallBackBanner(BannerBean bannerBean) {
        //设置banner
        setBanner(bannerBean);
        //设置京东秒杀
        setjdMiaosha(bannerBean);
        //设置推荐
        settuiJian(bannerBean);
    }

    //设置推荐
    private void settuiJian(BannerBean bannerBean) {
        List<BannerBean.TuijianBean.ListBeanX> tui = bannerBean.getTuijian().getList();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        tuijian.setLayoutManager(gridLayoutManager);
        tuijian.setAdapter(new TuijianAdapter(getActivity(), tui));
    }


    //设置秒杀
    private void setjdMiaosha(BannerBean bannerBean) {
        //时间转换格式
        myformat = new SimpleDateFormat("HH:mm:ss");
        //获取事件
        final int time = bannerBean.getMiaosha().getTime();
        //开始倒计时
        new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long l) {
                String format = myformat.format(new Date(l));
                miaotime.setText("京东秒杀：" + format);
            }

            @Override
            public void onFinish() {
            }
        }.start();
        //设置秒杀显示
        List<BannerBean.MiaoshaBean.ListBean> miao = bannerBean.getMiaosha().getList();
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        miaosha.setLayoutManager(linearLayoutManager1);
        MiaoshaAdapter miaoshaAdapter = new MiaoshaAdapter(getActivity(), miao);
        miaosha.setAdapter(miaoshaAdapter);
    }

    //加载Gif图
    public void gifImageViewload() {
        Glide.with(getActivity()).load(API.gifimgurl).into(gifimg);
    }

    //轮播图 banner
    public void setBanner(BannerBean bannerBean) {
        List<BannerBean.DataBean> data = bannerBean.getData();
        List<String> imglist = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            imglist.add(data.get(i).getIcon());
        }
        banner.setImageLoader(new BannerImagerAdapter(getActivity()));
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置图片
        banner.setImages(imglist);
        //设置轮播时间
        banner.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

    }

    //横向滚动图标
    public void scollerhrizontal() {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
        rlv.setLayoutManager(staggeredGridLayoutManager);
        rlv.setItemAnimator(new DefaultItemAnimator());
        new GridPresenter(new GridInterface() {
            @Override
            public void GridData(GridBean gridBean) {
                List<GridBean.DataBean> data = gridBean.getData();
                rlv.setAdapter(new GridRecyclerAdapter(getActivity(), data));
            }
        }).relevance();
    }

    @OnClick({R.id.img1, R.id.edit})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.img1:
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                //常量，请求码，可以任意定义
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.edit://点击搜索框 跳转到搜索页面
                   getActivity().startActivity(new Intent(getActivity(),F1SeekActivity.class));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE){
            if(null!=data){
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                    String result = bundle.getString("result_string");
                    Toast.makeText(getActivity(), "解析结果:" + result, Toast.LENGTH_LONG).show();


            }

        }
    }
}
