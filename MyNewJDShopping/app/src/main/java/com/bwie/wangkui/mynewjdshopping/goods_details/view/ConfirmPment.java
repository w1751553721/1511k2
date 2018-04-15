package com.bwie.wangkui.mynewjdshopping.goods_details.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bwie.wangkui.mynewjdshopping.R;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 选择支付方式
 */
public class ConfirmPment extends AppCompatActivity {


    @InjectView(R.id.button6)
    Button button6;
    @InjectView(R.id.rediogroup)
    RadioGroup rediogroup;
    @InjectView(R.id.radioButton)
    RadioButton radioButton;
    @InjectView(R.id.radioButton2)
    RadioButton radioButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_pment);
        ButterKnife.inject(this);
    }


    /**
     * 点击确认支付  拿预订单信息与后台交互  在成功的回调方法里
     * 后台调用微信同意下单接口
     */
    @OnClick(R.id.button6)
    public void onViewClicked() {


        new OkHttpClient.Builder().build().newCall(new Request.Builder().url("").build()).enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {

      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {

      }
  });


        if (radioButton.isChecked()){
            Toast.makeText(this, "您选择了微信支付", Toast.LENGTH_SHORT).show();
        }else if(radioButton2.isChecked()){
            Toast.makeText(this, "您选择了支付宝支付", Toast.LENGTH_SHORT).show();
        }




    }


}
