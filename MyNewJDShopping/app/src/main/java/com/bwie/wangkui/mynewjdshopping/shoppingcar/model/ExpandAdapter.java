package com.bwie.wangkui.mynewjdshopping.shoppingcar.model;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.wangkui.mynewjdshopping.R;
import com.bwie.wangkui.mynewjdshopping.shoppingcar.Bean.AddDeleteView;
import com.bwie.wangkui.mynewjdshopping.shoppingcar.Bean.Bean;
import com.bwie.wangkui.mynewjdshopping.shoppingcar.Bean.MessageEvent;
import com.bwie.wangkui.mynewjdshopping.shoppingcar.Bean.PriceAndCountEvent;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by ThinkPad on 2018/4/3.
 */

public class ExpandAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Bean.DataBean> data;

    public ExpandAdapter(Context context, List<Bean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return data.get(i).getList().size();
    }

    @Override
    public Object getGroup(int i) {
        return data.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return data.get(i).getList().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }



    @Override
    public View getGroupView(final int i, boolean b, View view, ViewGroup viewGroup) {
        final MyViewholder1 myViewholder1;
        if(view==null){
            myViewholder1 = new MyViewholder1();
            view = View.inflate(context, R.layout.parentitem,null);
            myViewholder1.ghome = view.findViewById(R.id.goodsname);
            myViewholder1.parck = view.findViewById(R.id.parentcheckBox);
            view.setTag(myViewholder1);
        }
        else{
            myViewholder1 = (MyViewholder1) view.getTag();
        }
        if(i!=0){
           myViewholder1.ghome.setText(data.get(i).getSellerName());
           myViewholder1.parck.setChecked(data.get(i).isChecked());
           Log.e("++++++",i+""+data.get(i).isChecked());
           myViewholder1.parck.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   //一级列表改变  先改变一级列表的数据状态值
                   data.get(i).setChecked(myViewholder1.parck.isChecked());
                   Log.e("setOnCheckedCha",i+""+data.get(i).isChecked());
                   //再改变二级列表数据状态值
                   changeChildState(i,myViewholder1.parck.isChecked());
                   PriceAndCountEvent priceAndCountEvent = compute();
                   EventBus.getDefault().post(priceAndCountEvent);
                   notifyDataSetChanged();
               }
           });
        }
        return view;
    }

    @Override
    public View getChildView(final int i, final int i1, boolean b, View view, ViewGroup viewGroup) {
        final MyViewholder2 myViewholder2;
        if(view==null){
            myViewholder2 = new MyViewholder2();
            view = View.inflate(context, R.layout.childittem,null);
            myViewholder2.img = view.findViewById(R.id.imageView);
            myViewholder2.childck = view.findViewById(R.id.childcheckBox);
            myViewholder2.t1 = view.findViewById(R.id.textView2);
            myViewholder2.t2 = view.findViewById(R.id.textView4);
            myViewholder2.t3 = view.findViewById(R.id.textView5);
            myViewholder2.t4 = view.findViewById(R.id.textView6);
            myViewholder2.t5 = view.findViewById(R.id.textView8);
            view.setTag(myViewholder2);
        }else{
            myViewholder2 = (MyViewholder2) view.getTag();
        }
        final Bean.DataBean.ListBean listBean = data.get(i).getList().get(i1);
        String[] split = listBean.getImages().split("\\|");
        Picasso.with(context).load(split[0]).into(myViewholder2.img);
        myViewholder2.t1.setText(data.get(i).getSellerName()+"的第"+(i1+1)+"个商品");
        myViewholder2.t2.setText(listBean.getCreatetime());
        myViewholder2.t3.setText("￥"+listBean.getBargainPrice()+"");
        myViewholder2.t4.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        myViewholder2.t4.setText(listBean.getPrice()+"");
        myViewholder2.t5.setText(listBean.getNum()+"");
        myViewholder2.childck.setChecked(listBean.isChecked());
        //二级列表状态值改变
        myViewholder2.childck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //先改变二级列表的状态值
                data.get(i).getList().get(i1).setChecked(myViewholder2.childck.isChecked());
                //判断二级列表是不是全部选中
                if(childIsallselect(i)){
                    data.get(i).setChecked(true);
                    //判断一级列表是不是全部选中
                    changeAllCbState(parentIsAllSelect());
                }else{
                    data.get(i).setChecked(false);
                    changeAllCbState(parentIsAllSelect());
                }
                PriceAndCountEvent priceAndCountEvent = compute();
                EventBus.getDefault().post(priceAndCountEvent);
                notifyDataSetChanged();
            }
        });

        myViewholder2.t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AddDeleteView view1 = new AddDeleteView(context);
                new AlertDialog.Builder(context)
                          .setTitle("修改商品数量")
                          .setView(view1)
                          .setNegativeButton("确定",null)
                          .create().show();
                view1.setNumber(listBean.getNum());

                view1.setOnAddDelClickListener(new AddDeleteView.OnAddDelClickListener() {
                    @Override
                    public void onAddClick(View v) {
                        Log.i(TAG, "onAddClick: 执行");
                        int origin = view1.getNumber();
                        origin++;
                        view1.setNumber(origin);
                        listBean.setNum(origin);
                        if (myViewholder2.childck.isChecked()) {
                            EventBus.getDefault().post(compute());
                        }
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onDelClick(View v) {
                        int origin = view1.getNumber();
                        if (origin-- ==0) {
                            Toast.makeText(context,"最小数量为1",Toast.LENGTH_SHORT).show();
                            return ;
                        }
                        view1.setNumber(origin);
                        listBean.setNum(origin);
                        if (myViewholder2.childck.isChecked()) {
                            EventBus.getDefault().post(compute());
                        }
                        notifyDataSetChanged();
                    }
                });
            }
        });



        return view;
    }


    class MyViewholder1{
         CheckBox parck;
         TextView ghome;
    }
    class MyViewholder2{
         CheckBox childck;
         ImageView img;
         TextView t1,t2,t3,t4,t5;
    }
    /**
     * 改变一级列表选中状态
     * */
    public void changeParentState(int parid,boolean flag){
         data.get(parid).setChecked(flag);
    }
    /**
     * 改变二级列表选中状态
     * */
    public void changeChildState(int parid,boolean flag){
        List<Bean.DataBean.ListBean> list = data.get(parid).getList();
        for(int i=0;i<list.size();i++){
            list.get(i).setChecked(flag);
        }
        PriceAndCountEvent priceAndCountEvent = compute();
        EventBus.getDefault().post(priceAndCountEvent);
        notifyDataSetChanged();
    }
    /**
     * 全选全不选
     * */
    public void changeAllCkState(boolean flag){
         for(int i=0;i<data.size();i++){
             changeParentState(i,flag);
             changeChildState(i,flag);
         }
    }
    /**
     * 计算列表中，选中的钱和数量
     */
    private PriceAndCountEvent compute() {
        int count = 0;
        int price = 0;
        for(int i=0;i<data.size();i++){
            List<Bean.DataBean.ListBean> list = data.get(i).getList();
            for(int j=0;j<list.size();j++){
                     if(list.get(j).isChecked()){
                         price += list.get(j).getNum() *list.get(j).getBargainPrice();
                         count += list.get(j).getNum();
                     }
                }
        }
        PriceAndCountEvent priceAndCountEvent = new PriceAndCountEvent();
        priceAndCountEvent.setCount(count);
        priceAndCountEvent.setPrice(price);

        return priceAndCountEvent;
    }

    /**
     * 判断是不是二级列表是否全部选中
     */
    public boolean childIsallselect(int parid){
        List<Bean.DataBean.ListBean> list = data.get(parid).getList();
        for(int i=0;i<list.size();i++){
            if(!list.get(i).isChecked()){
                return false;
            }
        }
        return true;
    }

    /**
     * 判断一级列表是不是
     * */
    public boolean parentIsAllSelect(){
        for(int i=0;i<data.size();i++){
            if(!data.get(i).isChecked()){
                return false;
            }
        }
        return true;
    }
    /**
     * 改变全选的状态
     *
     * @param flag
     */
    private void changeAllCbState(boolean flag) {
        MessageEvent messageEvent = new MessageEvent();
        messageEvent.setChecked(flag);
        EventBus.getDefault().post(messageEvent);
    }


























    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
    @Override
    public boolean hasStableIds() {
        return true;
    }
}
