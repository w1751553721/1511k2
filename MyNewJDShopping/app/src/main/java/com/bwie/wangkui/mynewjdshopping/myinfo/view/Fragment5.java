package com.bwie.wangkui.mynewjdshopping.myinfo.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwie.wangkui.mynewjdshopping.MyApplication;
import com.bwie.wangkui.mynewjdshopping.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ThinkPad on 2018/4/1.
 */

public class Fragment5 extends Fragment {
    @InjectView(R.id.textView3)
    TextView textView3;//登录注册

    @InjectView(R.id.textView5)
    TextView textView5;//设置
    @InjectView(R.id.img)
    SimpleDraweeView img;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment5, null);
        ButterKnife.inject(this, view);
        Log.e("=====", "onCreateView");
        return view;
    }

    @OnClick({R.id.img, R.id.textView5, R.id.textView3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img://头像

                break;
            case R.id.textView5://设置

                break;
            case R.id.textView3://登录注册
                Log.e("====", "点击了登录");
                startActivity(new Intent(getActivity(), LoginPage.class));
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("=====", "onDestroyView");
        ButterKnife.reset(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("=====", "onStart");
        //得到数据库存入的值
        SharedPreferences preferences = MyApplication.getSharedPreferences();
        long uid = preferences.getLong("uid", -1);
        String username = preferences.getString("username", "13126990738");
        String icon = preferences.getString("icon", null);
        //说明有值
        if (uid != -1) {
            img.setImageURI(Uri.parse(icon));
            textView3.setText(username);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("=====", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("=====", "onPause");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("=====", "onSaveInstanceState");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("=====", "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("=====", "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("=====", "onDetach");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("=====", "onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("=====", "onCreate");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("=====", "onActivityCreated");
    }
}
