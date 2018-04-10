package com.bwie.wangkui.mynewjdshopping.goods_details.view;

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
        Toast.makeText(this, pid +"", Toast.LENGTH_SHORT).show();
        GoodsDatailsPresenter presenter = new GoodsDatailsPresenter(this);
        presenter.guanlian(pid, source);
    }

    @Override
    public void showData(GoodsDatailsBean goodsDatailsBean) {
        String url = goodsDatailsBean.getData().getDetailUrl();
        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        web.loadUrl(url);
        //java回调js代码，不要忘了@JavascriptInterface这个注解，不然点击事件不起作用
        web.addJavascriptInterface(new JsCallJavaObj() {
            @JavascriptInterface
            @Override
            public void showBigImg(String url) {
                Toast.makeText(GoodsDatails.this, "你点击了加入购物车", Toast.LENGTH_SHORT).show();
                //添加购物车
                addCar("12575",pid);
            }
        },"jsCallJavaObj");

        web.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                setWebImageClick(view);
            }
        });
    }
    /**
     * Js調用Java接口
     */
    private interface JsCallJavaObj{
        void showBigImg(String url);
    }
    private  void setWebImageClick(WebView view) {
        String jsCode="javascript:(function(){" +
                "var btn=document.getElementById(\"addCartBtm\");" +
                "btn.onclick=function(){" +
                "window.jsCallJavaObj.showBigImg(this.src);" +
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
