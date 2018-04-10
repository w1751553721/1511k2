package com.bwie.wangkui.mynewjdshopping.GoodsSeek;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.wangkui.mynewjdshopping.R;
import com.squareup.picasso.Picasso;

import java.util.List;



/**
 * Created by ThinkPad on 2018/3/17.
 */

public class ReAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<SeekBean.DataBean> data;
    //定义三种常量  表示三种条目类型
    public static final int TYPE_PULL_IMAGE = 0;
    public static final int TYPE_RIGHT_IMAGE = 1;
    public static final int TYPE_THREE_IMAGE = 2;
    private String[] split;

    public ReAdapter(Context context, List<SeekBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }
   //创建接口 用于条目点击事件
    public interface CallBackPosition{
        void callBackPosition(int pos);
   }
   //声明接口
    CallBackPosition callBackPosition;
  //传入接口对象
    public void setCallBackPosition(CallBackPosition callBackPosition) {
        this.callBackPosition = callBackPosition;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_PULL_IMAGE) {
            view =LayoutInflater.from(context).inflate(R.layout.item,parent,false);
            final MyViewholder viewholder = new MyViewholder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callBackPosition.callBackPosition(viewholder.getLayoutPosition());
                }
            });
            return viewholder;
        }else if (viewType == TYPE_RIGHT_IMAGE) {
            view =LayoutInflater.from(context).inflate(R.layout.item2,parent,false);
            final MyViewholder2 viewholder2 = new MyViewholder2(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callBackPosition.callBackPosition(viewholder2.getLayoutPosition());
                }
            });
            return viewholder2;
        } else {
            view =LayoutInflater.from(context).inflate(R.layout.item3,parent,false);
            final MyViewholder3 viewholder3 = new MyViewholder3(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callBackPosition.callBackPosition(viewholder3.getLayoutPosition());
                }
            });
            return viewholder3;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String images = data.get(position).getImages();
        if(images.contains("|")){
            split = images.split("\\|");
        }
        //加载第一页数据
        if(holder instanceof MyViewholder){
            Picasso.with(context).load(images).placeholder(R.mipmap.ic_launcher).into(((MyViewholder) holder).img);
            ((MyViewholder) holder).tit.setText(data.get(position).getTitle());
             ((MyViewholder) holder).price.setText("$ "+data.get(position).getPrice());
        }else if(holder instanceof MyViewholder2){
            Picasso.with(context).load(split[0]).placeholder(R.mipmap.ic_launcher).into(((MyViewholder2) holder).img2);
            Picasso.with(context).load(split[1]).placeholder(R.mipmap.ic_launcher).into(((MyViewholder2) holder).img3);
            ((MyViewholder2) holder).tit2.setText(data.get(position).getTitle());
            ((MyViewholder2) holder).price2.setText("$ "+data.get(position).getPrice());
        }else if(holder instanceof MyViewholder3){
            Picasso.with(context).load(split[0]).placeholder(R.mipmap.ic_launcher).into(((MyViewholder3) holder).img4);
            Picasso.with(context).load(split[1]).placeholder(R.mipmap.ic_launcher).into(((MyViewholder3) holder).img5);
            Picasso.with(context).load(split[2]).placeholder(R.mipmap.ic_launcher).into(((MyViewholder3) holder).img6);
            ((MyViewholder3) holder).tit3.setText(data.get(position).getTitle());
            ((MyViewholder3) holder).price3.setText("$ "+data.get(position).getPrice());
        }


    }





    @Override
    public int getItemCount() {
        return data.size();
    }
    //第一种布局
    public class MyViewholder extends RecyclerView.ViewHolder{
        private final ImageView img;
        private final TextView tit;
        private final TextView price;
        public MyViewholder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageView3);
            tit = itemView.findViewById(R.id.textView8);
            price = itemView.findViewById(R.id.textView9);
        }
    }
    //第二种布局
    public class MyViewholder2 extends RecyclerView.ViewHolder {
        private final ImageView img2;
        private final ImageView img3;
        private final TextView tit2;
        private final TextView price2;
        public MyViewholder2(View itemView) {
            super(itemView);
            img2 = itemView.findViewById(R.id.img2);
            img3 = itemView.findViewById(R.id.img3);
            tit2 = itemView.findViewById(R.id.tit2);
            price2 = itemView.findViewById(R.id.price2);
        }
    }
    //第三种布局
    public class MyViewholder3 extends RecyclerView.ViewHolder {
        private final ImageView img4;
        private final ImageView img5;
        private final ImageView img6;
        private final TextView tit3;
        private final TextView price3;
        public MyViewholder3(View itemView) {
            super(itemView);
            img4 = itemView.findViewById(R.id.img4);
            img5 = itemView.findViewById(R.id.img5);
            img6 = itemView.findViewById(R.id.img6);
            tit3 = itemView.findViewById(R.id.tit3);
            price3 = itemView.findViewById(R.id.price3);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int itemtype = data.get(position).getItemtype();
        //根据itemtype的数值判断加载布局
        if (itemtype == 0) {
            return TYPE_PULL_IMAGE;
        } else if (itemtype == 1) {
            return TYPE_RIGHT_IMAGE;
        } else {
            return TYPE_THREE_IMAGE;
        }
    }
}
