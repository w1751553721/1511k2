package com.bwie.wangkui.mynewjdshopping.goods_details.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.wangkui.mynewjdshopping.MyApplication;
import com.bwie.wangkui.mynewjdshopping.R;
import com.bwie.wangkui.mynewjdshopping.myinfo.model.bean.AddressBean;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ArrirmOrder extends AppCompatActivity {

    @InjectView(R.id.name)
    TextView name;
    @InjectView(R.id.tel)
    TextView tel;
    @InjectView(R.id.address)
    TextView address;
    @InjectView(R.id.textView21)
    TextView textView21;
    @InjectView(R.id.goodsimg)
    ImageView goodsimg;
    @InjectView(R.id.goodstitle)
    TextView goodstitle;
    @InjectView(R.id.goodsprice)
    TextView goodsprice;
    @InjectView(R.id.button4)
    Button button4;
    @InjectView(R.id.textView22)
    TextView textView22;
    @InjectView(R.id.textView25)
    TextView textView25;
    @InjectView(R.id.textView27)
    TextView textView27;
    private String url = "https://www.zhaoapi.cn/user/getAddrs";

    //创建订单接口
    private String addorder = "https://www.zhaoapi.cn/product/createOrder?uid=12575";
    private int price;
    private int num = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrirm_order);
        ButterKnife.inject(this);
        long uid = MyApplication.getSharedPreferences().getLong("uid", -1);

        if (uid != -1) {//用户已经登录
            Intent intent = getIntent();
            String title = intent.getStringExtra("title");
            String images = intent.getStringExtra("images");
            price = intent.getIntExtra("price", 100);
            Picasso.with(this).load(images.split("\\|")[0]).into(goodsimg);

            goodsprice.setText("单价：" + price);
            goodstitle.setText(title);
            //网络请求 地址信息
            NetworkGetAddress((int) uid);
        } else {//用户未登录
            //跳转到登录界面
            Toast.makeText(this, "用户未登录", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessage(AddressBean addressBean) {
        name.setText("姓名：" + addressBean.getData().get(0).getName());
        tel.setText("手机号：" + addressBean.getData().get(0).getMobile() + "");
        address.setText("收货地址：" + addressBean.getData().get(0).getAddr());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessages(String str) {
        //跳转到选择支付界面
        Intent intent = new Intent(ArrirmOrder.this, ConfirmPment.class);

        startActivity(intent);

    }


    //网络请求收货地址
    private void NetworkGetAddress(int uid) {
        Request request = new Request.Builder().url(url + "?uid=" + uid + "&source=android").build();
        new OkHttpClient.Builder().build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                AddressBean addressBean = new Gson().fromJson(string, AddressBean.class);

                EventBus.getDefault().post(addressBean);
            }
        });
    }

    //确认订单按钮
    @OnClick(R.id.button4)
    public void onViewClicked() {
        //创建订单
        MyAddOrder(price);
    }

    private void MyAddOrder(int price) {
        Request request = new Request.Builder().url(url + "?uid=12575" + "&price=" + price).build();
        new OkHttpClient.Builder().build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    String msg = jsonObject.getString("msg");
                    if (msg.contains("成功")) {
                        EventBus.getDefault().post("成功 ");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("创建订单", "" + string);
            }
        });
    }

    @OnClick({R.id.button6, R.id.button7})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button6://减少
                if (num > 1) {
                    num--;
                } else {
                    Toast.makeText(this, "数量最小为1", Toast.LENGTH_SHORT).show();
                }

                textView22.setText(num + "");
              textView25.setText((num*98.80)+"元");
              textView27.setText((num*98.80)+"元");
                break;
            case R.id.button7://增加
                num++;
                textView22.setText(num + "");
                textView25.setText((num*98.80)+"元");
                textView27.setText((num*98.80)+"元");
                break;
        }
    }
}
