package com.bwie.wangkui.mynewjdshopping;

/**
 * Created by ThinkPad on 2018/4/1.
 */

public class API {
    /*
    * 首页
    * */
    //Gif图片
    public static String gifimgurl = "http://img.zcool.cn/community/019ffe56dfafe26ac72531cb9a87c8.gif";
    //总地址
    public static String allinterface = "https://www.zhaoapi.cn/";

    //手机号正则
    public static String numblerex="[1][34578]\\d{9}";
    //密码不能包含特殊字符
    public static String pwdrex = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";


    //商品分类接口
    public static String good1 = "https://www.zhaoapi.cn/";
    //商品子分类接口   cid	   string
    public static String good2 = "https://www.zhaoapi.cn/product/getProductCatagory";
    //当前子分类下的商品列表  pscid	string
    public static String good3 = "https://www.zhaoapi.cn/product/getProducts";
    //商品详情   pid	string
    public static String good4 = "https://www.zhaoapi.cn/product/getProductDetail";


    /*
    * 发现————视频
    * */
    public static String videourl="http://is.snssdk.com/neihan/stream/mix/";
    //查询购物车
    public static String seekurl="http://120.27.23.105/";
    //商品详情查询
    public static String goodsseek = "https://www.zhaoapi.cn/product/";
}
