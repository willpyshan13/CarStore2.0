package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;

import java.util.ArrayList;

public class DTCBean extends BaseBean {

    /**
     * code : 0000
     * msg : success
     * data : [{"uuid":"df29a78fb5034516a4e3c956bfa07d25","dtcCode":"哦明末","dtcType":"1603","dtcTypeName":"ACM  (音响前控制模块)","dtcDefinition":"摸摸","dtcBrandUuid":"22f72f047035447db228cd05acec439e","dtcAmount":0.5,"createdTime":"2021-05-28 15:50:28","createdBy":"小马哥哥","configName":"阿斯顿·马丁"},{"uuid":"874eba31d33c46fc96b6aec01c95fe1f","dtcCode":"测试描述","dtcType":"1605","dtcTypeName":"AWD  (全轮驱动)","dtcDefinition":"哈哈","dtcBrandUuid":"b144fcd919de4154a289717dfe20a1c0","dtcAmount":0.5,"createdTime":"2021-05-28 15:28:12","createdBy":"小马哥哥","configName":"奥迪"},{"uuid":"57cfdd694e9344bdb2f8ee277147bd18","dtcCode":"11","dtcType":"1604","dtcTypeName":"ATCM  (全地形控制模块)","dtcDefinition":"111","dtcBrandUuid":"22f72f047035447db228cd05acec439e","dtcAmount":0.5,"createdTime":"2021-03-30 10:20:21","createdBy":"J","configName":"阿斯顿·马丁"},{"uuid":"3ba1effe521f4dcb92a289226a402e8c","dtcCode":"D45HG","dtcType":"1605","dtcTypeName":"AWD  (全轮驱动)","dtcDefinition":"巴博斯全时四驱失效","dtcBrandUuid":"c5cd601591d14d438786447341e54bf2","dtcAmount":0.5,"createdTime":"2021-03-23 11:41:26","createdBy":"和明渠","configName":"巴博斯"},{"uuid":"dca6d6625f06493c81eda15117bc59ea","dtcCode":"C0001","dtcType":"1601","dtcTypeName":"ABS  (防抱死制动系统)","dtcDefinition":"TCS 控制信道\u201cA\u201d阀门 1：一般电气故障","dtcBrandUuid":"52208ea342404cab920703fee4ea5c84","dtcAmount":0.5,"createdTime":"2021-03-15 10:20:36","createdBy":"杜希柏","configName":"福特"},{"uuid":"302fb35f222f4654b3b8203f77d3a90a","dtcCode":"B10DF","dtcType":"1601","dtcTypeName":"ABS  (防抱死制动系统)","dtcDefinition":"主缸隔离阀：校准/参数内存故障","dtcBrandUuid":"52208ea342404cab920703fee4ea5c84","dtcAmount":0.5,"createdTime":"2021-03-15 10:16:36","createdBy":"杜希柏","configName":"福特"},{"uuid":"ef3623827dd041d5a8f763fc5537c51d","dtcCode":"P164E","dtcType":"1618","dtcTypeName":"GSM  (换挡模块)","dtcDefinition":"内部控制模块TR选择性能","dtcBrandUuid":"52208ea342404cab920703fee4ea5c84","dtcAmount":0.5,"createdTime":"2021-03-14 12:14:14","createdBy":"杜希柏","configName":"福特"},{"uuid":"91082e9e551447fba6179c061bfc97fd","dtcCode":"P166B","dtcType":"1618","dtcTypeName":"GSM  (换挡模块)","dtcDefinition":"驾驶座车门状态相互关系","dtcBrandUuid":"52208ea342404cab920703fee4ea5c84","dtcAmount":0.5,"createdTime":"2021-03-14 12:07:50","createdBy":"杜希柏","configName":"福特"},{"uuid":"35e95a47d0b445ac991587a1cf3ab567","dtcCode":"P1753","dtcType":"1618","dtcTypeName":"GSM  (换挡模块)","dtcDefinition":"换挡位置传感器校准","dtcBrandUuid":"52208ea342404cab920703fee4ea5c84","dtcAmount":0.5,"createdTime":"2021-03-13 16:54:52","createdBy":"杜希柏","configName":"福特"},{"uuid":"920ce3f01387475987db833c0b7c5d3d","dtcCode":"P07EC","dtcType":"1618","dtcTypeName":"GSM  (换挡模块)","dtcDefinition":"TR多功能选择电路","dtcBrandUuid":"52208ea342404cab920703fee4ea5c84","dtcAmount":0.5,"createdTime":"2021-03-13 16:45:14","createdBy":"杜希柏","configName":"福特"}]
     * size : 10
     * total : 1996
     * pages : 200
     * success : true
     */

    private String code;
    private String msg;
    private ArrayList<DataBean> data;
    private int size;
    private int total;
    private int pages;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * uuid : df29a78fb5034516a4e3c956bfa07d25
         * dtcCode : 哦明末
         * dtcType : 1603
         * dtcTypeName : ACM  (音响前控制模块)
         * dtcDefinition : 摸摸
         * dtcBrandUuid : 22f72f047035447db228cd05acec439e
         * dtcAmount : 0.5
         * createdTime : 2021-05-28 15:50:28
         * createdBy : 小马哥哥
         * configName : 阿斯顿·马丁
         */

        private String uuid;
        private String dtcCode;
        private String dtcCode2;
        private String dtcCode3;
        private String dtcType;
        private String dtcTypeName;
        private String dtcDefinition;
        private String dtcBrandUuid;
        private double dtcAmount;
        private String createdTime;
        private String createdBy;
        private String configName;
        private int needBuy;
        private boolean isWhite;

        public String getDtcCode2() {
            return dtcCode2 == null?"":dtcCode2;
        }

        public void setDtcCode2(String dtcCode2) {
            this.dtcCode2 = dtcCode2;
        }

        public String getDtcCode3() {
            return dtcCode3 == null?"":dtcCode3;
        }

        public void setDtcCode3(String dtcCode3) {
            this.dtcCode3 = dtcCode3;
        }

        public int getNeedBuy() {
            return needBuy;
        }

        public void setNeedBuy(int needBuy) {
            this.needBuy = needBuy;
        }

        public boolean isWhite() {
            return isWhite;
        }

        public void setWhite(boolean white) {
            isWhite = white;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getDtcCode() {
            return dtcCode;
        }

        public void setDtcCode(String dtcCode) {
            this.dtcCode = dtcCode;
        }

        public String getDtcType() {
            return dtcType;
        }

        public void setDtcType(String dtcType) {
            this.dtcType = dtcType;
        }

        public String getDtcTypeName() {
            return dtcTypeName;
        }

        public void setDtcTypeName(String dtcTypeName) {
            this.dtcTypeName = dtcTypeName;
        }

        public String getDtcDefinition() {
            return dtcDefinition;
        }

        public void setDtcDefinition(String dtcDefinition) {
            this.dtcDefinition = dtcDefinition;
        }

        public String getDtcBrandUuid() {
            return dtcBrandUuid;
        }

        public void setDtcBrandUuid(String dtcBrandUuid) {
            this.dtcBrandUuid = dtcBrandUuid;
        }

        public double getDtcAmount() {
            return dtcAmount;
        }

        public void setDtcAmount(double dtcAmount) {
            this.dtcAmount = dtcAmount;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getConfigName() {
            return configName;
        }

        public void setConfigName(String configName) {
            this.configName = configName;
        }
    }
}
