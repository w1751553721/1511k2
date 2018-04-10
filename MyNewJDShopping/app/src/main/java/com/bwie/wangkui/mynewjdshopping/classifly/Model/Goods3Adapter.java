package com.bwie.wangkui.mynewjdshopping.classifly.Model;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.wangkui.mynewjdshopping.GoodsSeek.DataViewActivity;
import com.bwie.wangkui.mynewjdshopping.R;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by ThinkPad on 2018/3/14.
 *
 */

public class Goods3Adapter extends RecyclerView.Adapter<Goods3Adapter.MyViewHolder> {
    private Context context;
    private List<Goods2Bean.DataBean.ListBean> list;

    public Goods3Adapter(Context context, List<Goods2Bean.DataBean.ListBean> list) {

        this.context = context;
        this.list = list;

    }
    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.goods3,parent,false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = myViewHolder.getLayoutPosition();
                Intent intent = new Intent(context, DataViewActivity.class);
                intent.putExtra("seek",list.get(position).getName());
                context.startActivity(intent);
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Picasso.with(context).load(list.get(position).getIcon()).placeholder(R.mipmap.ic_launcher).into(holder.g3img);
          holder.tit.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private final ImageView g3img;
        private final TextView tit;

        public MyViewHolder(View itemView) {
            super(itemView);
            g3img = itemView.findViewById(R.id.g3img);
            tit = itemView.findViewById(R.id.tit);
        }
    }
}
