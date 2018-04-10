package com.bwie.wangkui.mynewjdshopping;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class FirstLogin extends AppCompatActivity implements View.OnClickListener {

    private CountDownTimer countDownTimer;
    /**
     * 3秒后跳转
     */
    private TextView mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_login);
        initView();
        //得到数据库对象
        SharedPreferences preferences = MyApplication.getSharedPreferences();
        //从数据库取出数据
        boolean firstlogin = preferences.getBoolean("firstlogin", false);
        /*
        * 如果值为true 表示第二次登录  不进行倒计时 直接跳转
        * 如果值为false 第一次登录  进行倒计时 后跳转
        * */
        if (false) {
            //跳转到主页
            startActivity(new Intent(FirstLogin.this, MainActivity.class));
            finish();
        } else {
            //存入数据库
            SharedPreferences.Editor edit = preferences.edit();
            edit.putBoolean("firstlogin", true);
            edit.commit();
            //倒计时
            countDownTimer = new CountDownTimer(5000, 1000) {
                @Override
                public void onTick(long l) {
                    mTimer.setText(l/1000 + "秒后跳转");
                }

                @Override
                public void onFinish() {
                    startActivity(new Intent(FirstLogin.this, MainActivity.class));
                    finish();

                }
            }.start();


        }


    }
    private void initView() {
        mTimer = (TextView) findViewById(R.id.timer);
        mTimer.setOnClickListener(this);
    }
    /*
       * 1、倒计时点击时  直接跳转
       * 2、结束倒计时
       * */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.timer:
                startActivity(new Intent(FirstLogin.this, MainActivity.class));
                countDownTimer.cancel();
                finish();
                break;
        }
    }
}
