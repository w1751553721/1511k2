package com.bwie.wangkui.shippingaddress;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by ThinkPad on 2018/4/12.
 * area.json文件的Bean类
 */

public class JsonBean2 implements IPickerViewData {

    /**
     * code : 110000
     * sheng : 11
     * di : 00
     * xian : 00
     * name : 北京市
     * level : 1
     */

    private String code;
    private String sheng;
    private String di;
    private String xian;
    private String name;
    private int level;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSheng() {
        return sheng;
    }

    public void setSheng(String sheng) {
        this.sheng = sheng;
    }

    public String getDi() {
        return di;
    }

    public void setDi(String di) {
        this.di = di;
    }

    public String getXian() {
        return xian;
    }

    public void setXian(String xian) {
        this.xian = xian;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String getPickerViewText() {
        return null;
    }
}
