package com.bwie.wangkui.mynewjdshopping.goods_details.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.bwie.wangkui.mynewjdshopping.MainActivity;
import com.bwie.wangkui.mynewjdshopping.R;
import com.bwie.wangkui.mynewjdshopping.goods_details.model.GoodsDatailsBean;
import com.bwie.wangkui.mynewjdshopping.goods_details.presenter.GoodsDatailsPresenter;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GoodsDatails extends AppCompatActivity implements DatailsIVew {
    @InjectView(R.id.web)
    WebView web;
    private String source = "android";
     private String addcarurl = "https://www.zhaoapi.cn/product/addCart";
    private String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_datails);
        ButterKnife.inject(this);
        pid = getIntent().getStringExtra("pid");

        GoodsDatailsPresenter presenter = new GoodsDatailsPresenter(this);
        presenter.guanlian(pid, source);
    }

    @Override
    public void showData(final GoodsDatailsBean goodsDatailsBean) {
        String url = goodsDatailsBean.getData().getDetailUrl();
        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);

        web.setWebViewClient(new WebViewClient(){
            //调用自身浏览器
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            //当页面加载完毕时  给H5的加入购物车按钮添加点击事件
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                setWebImageClick();
            }
        });
        web.loadUrl(url);
        //java回调js代码，不要忘了@JavascriptInterface这个注解，不然点击事件不起作用
        web.addJavascriptInterface(new JsCallJavaObj() {
            @JavascriptInterface
            @Override
            public void showBigImg() {
                //跳转到确认订单
                Intent intent = new Intent(GoodsDatails.this, ArrirmOrder.class);
                //商品名称
                intent.putExtra("title",goodsDatailsBean.getData().getTitle());
                //商品图片
                intent.putExtra("images",goodsDatailsBean.getData().getImages());
                //商品价格
                intent.putExtra("price",goodsDatailsBean.getData().getBargainPrice());
                startActivity(intent);
                finish();




                //添加购物车
             //   addCar("12575",pid);
            }
        },"jsCallJavaObj");


    }
    /**
     * Js調用Java接口
     */
    private interface JsCallJavaObj{
        void showBigImg();
    }
    private  void setWebImageClick() {
        String jsCode="javascript:(function(){" +
                "var btn=document.getElementById(\"directorderBtm\");" +
                "btn.onclick=function(){" +
                "window.jsCallJavaObj.showBigImg();" +
                "}})()";
        web.loadUrl(jsCode);
    }

    public void addCar(String uid,String pid){
        OkHttpClient build = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(addcarurl+"?uid="+uid+"&pid="+pid+"&source=android").build();
        build.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
            }
        });
    }
}
