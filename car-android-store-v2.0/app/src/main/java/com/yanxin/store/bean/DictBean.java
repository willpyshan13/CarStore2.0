package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;

import java.util.ArrayList;

public class DictBean extends BaseBean {

    /**
     * code : 0000
     * msg : success
     * data : [{"uuid":"1320","lableType":"attach_sys","lableTypeDesc":"所属系统","lableCode":"1","lableValue":"maintain","lableDesc":"养护信息","lableDescEn":null,"sortNum":0},{"uuid":"1321","lableType":"attach_sys","lableTypeDesc":"所属系统","lableCode":"2","lableValue":"engin","lableDesc":"发动机控制系统（燃油/机械/冷却等）","lableDescEn":null,"sortNum":0},{"uuid":"1322","lableType":"attach_sys","lableTypeDesc":"所属系统","lableCode":"3","lableValue":"gearbox","lableDesc":"变速器控制系统（手动/自动/差速器/分动箱等）","lableDescEn":null,"sortNum":0},{"uuid":"1323","lableType":"attach_sys","lableTypeDesc":"所属系统","lableCode":"4","lableValue":"temperature","lableDesc":"温度控制系统（ 手动/自动/双区域/座椅通风/座椅加热等）","lableDescEn":null,"sortNum":0},{"uuid":"1324","lableType":"attach_sys","lableTypeDesc":"所属系统","lableCode":"5","lableValue":"electrical","lableDesc":"电器系统（仪表/防盗/网络/线路控制等）","lableDescEn":null,"sortNum":0},{"uuid":"1325","lableType":"attach_sys","lableTypeDesc":"所属系统","lableCode":"6","lableValue":"baseplate","lableDesc":"底盘控制系统（悬架/制动/转向/传动等）","lableDescEn":null,"sortNum":0},{"uuid":"1326","lableType":"attach_sys","lableTypeDesc":"所属系统","lableCode":"7","lableValue":"car_body","lableDesc":"车身和油漆系统","lableDescEn":null,"sortNum":0}]
     * success : true
     */

    private String code;
    private String msg;
    private ArrayList<DataBean> data;
    private boolean success;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * uuid : 1320
         * lableType : attach_sys
         * lableTypeDesc : 所属系统
         * lableCode : 1
         * lableValue : maintain
         * lableDesc : 养护信息
         * lableDescEn : null
         * sortNum : 0
         */

        private String uuid;
        private String lableType;
        private String lableTypeDesc;
        private String lableCode;
        private String lableValue;
        private String lableDesc;
        private Object lableDescEn;
        private int sortNum;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getLableType() {
            return lableType;
        }

        public void setLableType(String lableType) {
            this.lableType = lableType;
        }

        public String getLableTypeDesc() {
            return lableTypeDesc;
        }

        public void setLableTypeDesc(String lableTypeDesc) {
            this.lableTypeDesc = lableTypeDesc;
        }

        public String getLableCode() {
            return lableCode;
        }

        public void setLableCode(String lableCode) {
            this.lableCode = lableCode;
        }

        public String getLableValue() {
            return lableValue;
        }

        public void setLableValue(String lableValue) {
            this.lableValue = lableValue;
        }

        public String getLableDesc() {
            return lableDesc;
        }

        public void setLableDesc(String lableDesc) {
            this.lableDesc = lableDesc;
        }

        public Object getLableDescEn() {
            return lableDescEn;
        }

        public void setLableDescEn(Object lableDescEn) {
            this.lableDescEn = lableDescEn;
        }

        public int getSortNum() {
            return sortNum;
        }

        public void setSortNum(int sortNum) {
            this.sortNum = sortNum;
        }
    }
}
