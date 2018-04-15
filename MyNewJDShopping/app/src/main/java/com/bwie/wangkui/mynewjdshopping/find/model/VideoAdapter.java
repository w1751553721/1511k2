package com.bwie.wangkui.mynewjdshopping.find.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bwie.wangkui.mynewjdshopping.R;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by ThinkPad on 2018/4/2.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {
    private Context context;
    private List<VideoBean.DataBeanX.DataBean> data;

    public VideoAdapter(Context context,List<VideoBean.DataBeanX.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.videoitem,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        boolean setUp1 = holder.video.setUp(data.get(position).getGroup().getMp4_url(), JCVideoPlayer.SCREEN_LAYOUT_LIST, data.get(position).getGroup().getContent());
//        if(setUp1){
//            Glide.with(context).load(data.get(position).getGroup().getLarge_cover().getUrl_list().get(0).getUrl()).into(holder.video.thumbImageView);
//        }
    }

    @Override
    public int getItemCount() {
     return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
       private JCVideoPlayerStandard video;
        public MyViewHolder(View itemView) {
            super(itemView);
          video = itemView.findViewById(R.id.player_list_video);
        }
    }
}
