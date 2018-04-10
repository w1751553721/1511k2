package com.bwie.wangkui.mynewjdshopping.myinfo.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bwie.wangkui.mynewjdshopping.R;
import com.bwie.wangkui.mynewjdshopping.base.BaseActivity;
import com.bwie.wangkui.mynewjdshopping.myinfo.model.bean.RegisterBean;
import com.bwie.wangkui.mynewjdshopping.myinfo.presenter.RegisterPresenter;
import com.bwie.wangkui.mynewjdshopping.myinfo.view.myinteface.RegisterIView;


public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterIView {

    private EditText mEditText;
    private EditText mEditText2;
    private Button mRegister;


    @Override
    protected int getLayoutID() {
        return R.layout.activity_register;
    }
    @Override
    protected void initView() {
        mEditText = (EditText) findViewById(R.id.editText);
        mEditText2 = (EditText) findViewById(R.id.editText2);
        mRegister = (Button) findViewById(R.id.register);
    }

    @Override
    protected RegisterPresenter getPovidPresenter() {
        return new RegisterPresenter(RegisterActivity.this);
    }

    @Override
    protected void initData() {
          mRegister.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  String name = mEditText.getText().toString();
                  String pwd = mEditText2.getText().toString();
                  present.relevance(name,pwd);
              }
          });
    }

    @Override
    public void showData(RegisterBean registerBean) {
        String msg = registerBean.getMsg();
        if(msg.contains("成功")){
            Toast.makeText(this, "注册成功，返回登录", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(this, "用户已经注册，请返回登录", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
