package com.bwie.wangkui.mynewjdshopping.classifly.Model;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.bwie.wangkui.mynewjdshopping.R;

import java.util.List;

/**
 * Created by ThinkPad on 2018/3/14.
 */

public class Goods2Adapter extends RecyclerView.Adapter<Goods2Adapter.MyViewHolder> {
    private Context context;
    private List<Goods2Bean.DataBean> data;
    private Goods3Adapter goods3Adapter;
    private int g2pos;

    public Goods2Adapter(Context context, List<Goods2Bean.DataBean> data) {
        this.context = context;
        this.data = data;

    }
    //定义接口
    public interface CallGood2pos{
        void callg2pos(int g3pos, int g2pos);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.goods2,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        g2pos = myViewHolder.getLayoutPosition();

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.name.setText(data.get(position).getName());
            holder.rlv.setLayoutManager(new GridLayoutManager(context,3));
           List<Goods2Bean.DataBean.ListBean> list = data.get(position).getList();
           holder.rlv.setAdapter(new Goods3Adapter(context,list));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView name;
        private final RecyclerView rlv;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            rlv = itemView.findViewById(R.id.goods3rlv);
        }
    }
}
