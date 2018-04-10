package com.bwie.wangkui.mynewjdshopping.homepage.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bwie.wangkui.mynewjdshopping.R;
import com.bwie.wangkui.mynewjdshopping.homepage.model.bean.BannerBean;
import com.squareup.picasso.Picasso;
import java.util.List;


/**
 * Created by ThinkPad on 2018/3/12.
 */

public class MiaoshaAdapter extends RecyclerView.Adapter<MiaoshaAdapter.MyViewHoler> {
    private Context context;
    private  List<BannerBean.MiaoshaBean.ListBean> data;

    public MiaoshaAdapter(Context context, List<BannerBean.MiaoshaBean.ListBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.miaoshaitem,parent,false);
        MyViewHoler myViewHoler = new MyViewHoler(view);
        return myViewHoler;
    }

    @Override
    public void onBindViewHolder(MyViewHoler holder, int position) {
        String images = data.get(position).getImages();
        String[] split = images.split("\\|");
        Picasso.with(context).load(split[0]).into(holder.img);
        holder.price.setText("$"+data.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHoler extends RecyclerView.ViewHolder{

        private final ImageView img;
        private final TextView price;
        public MyViewHoler(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            price = itemView.findViewById(R.id.textView7);
        }
    }
}
