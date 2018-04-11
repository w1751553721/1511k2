package com.bwie.wangkui.mynewjdshopping.myinfo.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bwie.wangkui.mynewjdshopping.MainActivity;
import com.bwie.wangkui.mynewjdshopping.MyApplication;
import com.bwie.wangkui.mynewjdshopping.R;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class InfoSetting extends AppCompatActivity {

    @InjectView(R.id.button3)
    Button button3;
    @InjectView(R.id.imageView4)
    ImageView imageView4;
    @InjectView(R.id.editText3)//账号
    EditText editText3;
    @InjectView(R.id.editText4)//昵称
    EditText editText4;
    @InjectView(R.id.editText5)//收货地址
    EditText editText5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_setting);
        ButterKnife.inject(this);
        //得到数据库存入的值
        SharedPreferences preferences = MyApplication.getSharedPreferences();
        long uid = preferences.getLong("uid", -1);
        String username = preferences.getString("username", "13126990738");
        String icon = preferences.getString("icon", null);
        //说明有值
        if (uid != -1) {
            imageView4.setImageURI(Uri.parse(icon));
            editText3.setHint(username);
        }

    }

    @OnClick({R.id.button3, R.id.imageView4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button3:
                break;
            case R.id.imageView4://头像上传
                break;
        }
    }


}
