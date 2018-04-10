package com.bwie.wangkui.mynewjdshopping.homepage.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.wangkui.mynewjdshopping.R;
import com.bwie.wangkui.mynewjdshopping.homepage.model.bean.GridBean;


import java.util.List;

/**
 * Created by ThinkPad on 2018/3/9.
 */

public class GridRecyclerAdapter extends RecyclerView.Adapter<GridRecyclerAdapter.MyViewHolder> {
     private Context context;
    private List<GridBean.DataBean> data;

    public GridRecyclerAdapter(Context context, List<GridBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.f1griditem,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Glide.with(context).load(data.get(position).getIcon()).into(holder.img);
        holder.tit.setText(data.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private final ImageView img;
        private final TextView tit;

        public MyViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            tit = (TextView) itemView.findViewById(R.id.tit);
        }
    }
}
