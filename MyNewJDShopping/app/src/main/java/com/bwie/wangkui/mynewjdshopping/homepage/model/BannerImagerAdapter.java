package com.bwie.wangkui.mynewjdshopping.homepage.model;
import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;



/**
 * Created by ThinkPad on 2018/3/9.
 */

public class BannerImagerAdapter extends ImageLoader {
    private Context context;


    public BannerImagerAdapter(Context context) {
        this.context = context;

    }

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).into(imageView);
    }

}
