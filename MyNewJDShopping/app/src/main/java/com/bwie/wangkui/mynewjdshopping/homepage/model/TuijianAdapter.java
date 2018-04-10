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

public class TuijianAdapter extends RecyclerView.Adapter<TuijianAdapter.MyViewHoler>{
    private Context context;
    private List<BannerBean.TuijianBean.ListBeanX> list;

    public TuijianAdapter(Context context, List<BannerBean.TuijianBean.ListBeanX> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public MyViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tuijian,parent,false);
        MyViewHoler myViewHoler = new MyViewHoler(view);
        return myViewHoler;
    }

    @Override
    public void onBindViewHolder(MyViewHoler holder, int position) {
        String images = list.get(position).getImages();
        if(images.contains("|")){
            String[] split = images.split("\\|");
//            Uri parse = Uri.parse(split[0]);
//            holder.img.setImageURI(parse);
            Picasso.with(context).load(split[0]).into(holder.img);
        }else{
//            Uri parse = Uri.parse(images);
//           holder.img.setImageURI(parse);
            Picasso.with(context).load(images).into(holder.img);
        }
        holder.tit.setText(list.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHoler extends RecyclerView.ViewHolder{
        private final ImageView img;
        private final TextView tit;
        public MyViewHoler(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.sdv);
            tit = itemView.findViewById(R.id.tit);
        }
    }
}
