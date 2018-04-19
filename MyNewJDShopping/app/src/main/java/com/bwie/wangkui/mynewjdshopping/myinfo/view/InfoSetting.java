package com.bwie.wangkui.mynewjdshopping.myinfo.view;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bwie.wangkui.mynewjdshopping.MyApplication;

import com.bwie.wangkui.mynewjdshopping.R;
import com.bwie.wangkui.mynewjdshopping.myinfo.model.bean.JsonBean2;
import com.bwie.wangkui.mynewjdshopping.myinfo.model.bean.JsonFileReader;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class InfoSetting extends AppCompatActivity {

    @InjectView(R.id.button3)
    Button button3;
    @InjectView(R.id.imageView4)
    ImageView imageView4;
    @InjectView(R.id.editText3)//账号
            EditText editText3;
    @InjectView(R.id.editText4)//昵称
            EditText editText4;
    @InjectView(R.id.editText5)//收货地址
            EditText editText5;
    @InjectView(R.id.button8)
    Button button8;
    private TextView mTvAddress;//button控件
    private ArrayList<String> options1Items = new ArrayList<>();//一级列表数据
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();//二级列表数据
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();//三级列表数据
    private ArrayList<String> CityList;//城市集合
    private ArrayList<ArrayList<String>> province_areaList;//县外部集合
    private ArrayList<String> city_areaList;//县内部集合
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_setting);
        ButterKnife.inject(this);
        //得到数据库存入的值
        SharedPreferences preferences = MyApplication.getSharedPreferences();
        long uid = preferences.getLong("uid", -1);
        String username = preferences.getString("username", "13126990738");
        String icon = preferences.getString("icon", null);
        //说明有值
        if (uid != -1) {
            imageView4.setImageURI(Uri.parse(icon));
            editText3.setHint(username);
        }
        //设置按钮监听事件
        imageView4.setImageURI(Uri.parse("https://www.zhaoapi.cn//images//1522223095271img.png"));
        //解析数据
        initJsonData();
    }

    @OnClick({R.id.button3, R.id.imageView4,R.id.button8})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button3:
                break;
            case R.id.imageView4://头像上传
                break;
            case R.id.button8://头像上传
                showPickerView();
                break;
        }
    }
    private void showPickerView() {
        OptionsPickerView pvOptions=new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String text = options1Items.get(options1) +options2Items.get(options1).get(options2)+options3Items.get(options1).get(options2).get(options3);
                //把选中的位置设置给button
                mTvAddress.setText(text);
            }
        }).setTitleText("")
                .setDividerColor(Color.GRAY)
                .setTextColorCenter(Color.GRAY)
                .setContentTextSize(13)
                .setOutSideCancelable(false)
                .build();
        //   pvOptions.setPicker(options1Items);//一级选择器
        // pvOptions.setPicker(options1Items, options2Items);//二级选择器
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    private void initJsonData() {   //解析数据

        /**
         * 注意：assets 目录下的Json文件
         *
         * */
        //  获取json数据
        String JsonData = JsonFileReader.getJson(this, "area.json");
        //返回对象集合
        ArrayList<JsonBean2> jsonBean = parseData(JsonData);//用Gson 转成实体



        city_areaList= new ArrayList<>();
        for(int i=0 ; i<jsonBean.size()-1;i++){
            JsonBean2 bean2 = jsonBean.get(i);
            //获取省/市        判断如果等级为1 且地区 县城都为0
            if(bean2.getLevel()==1&&bean2.getDi().equals("00")&&bean2.getXian().equals("00")){
                //将省市名字添加到一级列表集合
                options1Items.add(bean2.getName());
                //清空城市和地区的集合
                CityList = new ArrayList<>();//该省的城市列表（第二级）
                province_areaList = new ArrayList<>();//该省的所有地区列表（第三极）

            }else if(bean2.getLevel()==2&&bean2.getXian().equals("00")){//判断等级为1 且县城编号为0
                //将城市名字添加二级列表集合
                CityList.add(bean2.getName());

            }else if(bean2.getLevel()==3){//判断等级为3的  添加到城市内部集合
                city_areaList.add(bean2.getName());
            }
              /*
              * 判断如果此对象的城市编号与下一个对象的城市编号不同
              * 则清空集合 并把县城内部集合放入外部集合中
              * */
            if(!bean2.getDi().equals(jsonBean.get(i+1).getDi())){
                city_areaList= new ArrayList<>();
                province_areaList.add(city_areaList);
            }
              /*
              * 判断省市编号不同时 添加城市集合与县城集合到列表集合中
              * */
            if(!bean2.getSheng().equals(jsonBean.get(i+1).getSheng())){
                options2Items.add(CityList);
                options3Items.add(province_areaList);
            }
        }
    }

    /**
     * Json文件解析 返回对象集合
     * @param result
     * @return
     */
    public ArrayList<JsonBean2> parseData(String result) {//Gson 解析
        ArrayList<JsonBean2> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean2 entity = gson.fromJson(data.getJSONObject(i).toString(), JsonBean2.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

}
