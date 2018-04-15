package com.bwie.wangkui.mynewjdshopping.myinfo.model.bean;

import java.util.List;

/**
 * Created by ThinkPad on 2018/4/12.
 */

public class AddressBean {

    /**
     * msg : 地址列表请求成功
     * code : 0
     * data : [{"addr":"河南","addrid":1364,"mobile":13126990738,"name":"wangkui","status":0,"uid":12575},{"addr":"河南省","addrid":1365,"mobile":13126990738,"name":"wangkui","status":0,"uid":12575}]
     */

    private String msg;
    private String code;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * addr : 河南
         * addrid : 1364
         * mobile : 13126990738
         * name : wangkui
         * status : 0
         * uid : 12575
         */

        private String addr;
        private int addrid;
        private long mobile;
        private String name;
        private int status;
        private int uid;

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public int getAddrid() {
            return addrid;
        }

        public void setAddrid(int addrid) {
            this.addrid = addrid;
        }

        public long getMobile() {
            return mobile;
        }

        public void setMobile(long mobile) {
            this.mobile = mobile;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }
    }
}
