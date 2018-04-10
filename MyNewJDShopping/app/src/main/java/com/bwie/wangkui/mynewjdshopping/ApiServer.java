package com.bwie.wangkui.mynewjdshopping;



import com.bwie.wangkui.mynewjdshopping.GoodsSeek.SeekBean;
import com.bwie.wangkui.mynewjdshopping.find.model.VideoBean;
import com.bwie.wangkui.mynewjdshopping.homepage.model.bean.BannerBean;
import com.bwie.wangkui.mynewjdshopping.homepage.model.bean.GridBean;
import com.bwie.wangkui.mynewjdshopping.myinfo.model.bean.LoginBean;
import com.bwie.wangkui.mynewjdshopping.myinfo.model.bean.RegisterBean;
import com.bwie.wangkui.mynewjdshopping.myinfo.model.bean.UserInfoBean;
import com.bwie.wangkui.mynewjdshopping.shoppingcar.Bean.Bean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ThinkPad on 2018/4/1.
 */

public interface ApiServer {
  /*
  * 首页
  * */
   //banner
    @GET("ad/getAd")
    Observable<BannerBean> getBannerData();

    @GET("product/getCatagory")
    Observable<GridBean> getGridData();
  //登录
  @GET("user/login")
  Observable<LoginBean> getLoginInfo(@Query("mobile") String name, @Query("password") String pwd);

  //注册
  @GET("/user/reg")
  Observable<RegisterBean> getRegisterInfo(@Query("mobile") String name, @Query("password") String pwd);

  //用户信息
  @GET("user/getUserInfo")
  Observable<UserInfoBean> getUserInfo(@Query("uid") String uid, @Query("token") String token);

  //视频
  @GET("v1/?mpic=1&webp=1&essence=1&content_type=-101&message_cursor=-1&longitude=116.4121485&latitude=39.9365054&am_longitude=116.41828&am_latitude=39.937848&am_city=%E5%8C%97%E4%BA%AC%E5%B8%82&am_loc_time=1483686438786&count=30&min_time=1483929653&screen_width=1080&iid=7164180604&device_id=34822199408&ac=wifi&channel=baidu&aid=7&app_name=joke_essay&version_code=590&version_name=5.9.0&device_platform=android&ssmix=a&device_type=Nexus%2B5&device_brand=google&os_api=25&os_version=7.1&uuid=359250050588035&openudid=12645e537a2f0f25&manifest_version_code=590&resolution=1080*1776&dpi=480&update_version_code=5903")
  Observable<VideoBean> getVideoData();
  //查询购物车
  @GET("product/getCarts?source=android&uid=71")
  Observable<Bean> getAllData();
  //搜索
  //searchProducts?keywords=笔记本&page=1&source=android&sort=0
  @GET("searchProducts")
  Observable<SeekBean> getSeek(@Query("keywords") String words, @Query("page") int page, @Query("source") String source, @Query("sort") int sort);
}
