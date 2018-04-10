package com.bwie.wangkui.mynewjdshopping.goods_details.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.bwie.wangkui.mynewjdshopping.R;

public class GoodsDatails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_datails);
        String pid = getIntent().getStringExtra("pid");
        Toast.makeText(this, "12"+pid, Toast.LENGTH_SHORT).show();

    }
}
