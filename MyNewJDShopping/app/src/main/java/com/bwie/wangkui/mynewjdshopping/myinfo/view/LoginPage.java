package com.bwie.wangkui.mynewjdshopping.myinfo.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bwie.wangkui.mynewjdshopping.MyApplication;
import com.bwie.wangkui.mynewjdshopping.R;
import com.bwie.wangkui.mynewjdshopping.myinfo.model.bean.LoginBean;
import com.bwie.wangkui.mynewjdshopping.myinfo.presenter.LoginPresenter;
import com.bwie.wangkui.mynewjdshopping.myinfo.view.myinteface.LoginIView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginPage extends AppCompatActivity implements LoginIView {

    @InjectView(R.id.editText)
    EditText editText;//账号
    @InjectView(R.id.editText2)
    EditText editText2;//密码
    @InjectView(R.id.button2)
    Button button2;//注册
    @InjectView(R.id.button)
    Button button;//登录
    private LoginPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        ButterKnife.inject(this);
        presenter = new LoginPresenter(this);
    }

    @OnClick({R.id.button2, R.id.button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button2://注册
                startActivity(new Intent(LoginPage.this,RegisterActivity.class));
                break;
            case R.id.button://登录
                String numble = editText.getText().toString();
                String password = editText2.getText().toString();
                presenter.relevance(numble,password);
                break;
        }
    }
    @Override
    public void showData(LoginBean loginBean) {
        //判断是否登录成功
        String msg = loginBean.getMsg();

        final LoginBean.DataBean data = loginBean.getData();
        Log.e("======",msg+"===");
        if(msg.equals("登录成功")){
            Log.e("showData",msg+"====");
            SharedPreferences.Editor edit = MyApplication.getSharedPreferences().edit();
            //存入uid和token到数据库
            edit.putLong("uid",data.getUid());
            edit.putString("token",data.getToken());
            edit.putString("nickname",data.getNickname()+"");
            edit.putString("username",data.getUsername());
            edit.putString("icon",data.getIcon()+"");
            edit.commit();
            //页面销毁
            finish();

        }
    }
}
