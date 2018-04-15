package com.bwie.wangkui.shippingaddress;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.lib.WheelView;
import com.bigkoo.pickerview.listener.OnItemSelectedListener;
import com.google.gson.Gson;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车三级列表
 * 注释部分为github源代码
 *       compile 'com.contrarywind:Android-PickerView:3.2.5'
         compile 'com.google.code.gson:gson:2.7'
         WheelView依赖
 */
public class MainActivity extends AppCompatActivity {
    private TextView mTvAddress;//button控件
    private ArrayList<String> options1Items = new ArrayList<>();//一级列表数据
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();//二级列表数据
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();//三级列表数据

    private ArrayList<String> CityList;//城市集合
    private ArrayList<ArrayList<String>> province_areaList;//县外部集合
    private ArrayList<String> city_areaList;//县内部集合

    private int options1=0;
    private int options2=0;
    private OptionsPickerView pvOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //资源ID
        initView();
        //设置按钮监听事件
        setListener();
        //解析数据
        initJsonData();
       //权限申请

        new WheelView(this).setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                Toast.makeText(MainActivity.this, "---"+index, Toast.LENGTH_SHORT).show();
            }
        });


    }





    /**
     * 按钮监听事件
     */
    private void setListener() {
        mTvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickerView();
            }
        });
    }

    /**
     * 弹出三级列表选项框
     */
    private void showPickerView() {
        //返回的分别是三个级别的选中位置
           //把选中的位置设置给button
        pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String text = options1Items.get(options1) +options2Items.get(options1).get(options2)+options3Items.get(options1).get(options2).get(options3);
                //把选中的位置设置给button

                mTvAddress.setText(text);
            }

        })
                .setTitleText("")
                .setDividerColor(Color.GRAY)
                .setTextColorCenter(Color.GRAY)
                .setContentTextSize(18)
                .setOutSideCancelable(false)
                .build();

       // pvOptions.setSelectOptions(0,0,0);
        //    pvOptions.setPicker(options1Items);//一级选择器
        //  pvOptions.setPicker(options1Items, options2Items);//二级选择器
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器

        pvOptions.returnData();
        pvOptions.show();
    }

    private void initView() {
        mTvAddress = (TextView) findViewById(R.id.addresbtn);
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



         for(int i=0 ; i<jsonBean.size();i++) {
             JsonBean2 bean2 = jsonBean.get(i);
             if(bean2.getLevel()==1){//等级等于1 省/市
                 CityList = new ArrayList<>();//该省的城市列表（第二级）
                 province_areaList = new ArrayList<>();
                 if(i>=jsonBean.size()-3){
                         CityList.add("");
                         city_areaList.add(" ");
                         city_areaList= new ArrayList<>();
                         province_areaList.add(city_areaList);

                 }
                 options1Items.add(bean2.getName());
                 options2Items.add(CityList);
                 options3Items.add(province_areaList);
             }else if(bean2.getLevel()==2){//等级等于2 地区
                 city_areaList = new ArrayList<>();
                 province_areaList.add(city_areaList);
                 CityList.add(bean2.getName());
             }else if(bean2.getLevel()==3){//等级等于3 城市
                 city_areaList.add(bean2.getName());
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
