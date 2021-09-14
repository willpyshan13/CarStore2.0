package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;
import com.yanxin.store.utils.BasicUtils;

public class DtcOrderDetailBean extends BaseBean {

    /**
     * code : 0000
     * data : {"buyerMobile":"string","buyerName":"string","buyerUuid":"string","createdTime":"2021-08-09T01:35:29.922Z","dtcBrandName":"string","dtcBrandUuid":"string","dtcCode":"string","dtcCode2":"string","dtcCode3":"string","dtcDefinition":"string","dtcDiagnose":"string","dtcExplain":"string","dtcIssuerType":0,"dtcReasons":"string","dtcType":"string","dtcUuid":"string","isPay":false,"issuerMobile":"string","issuerName":"string","issuerUuid":"string","orderAmount":0,"orderNum":"string","orderSts":0,"payType":0,"readCount":0,"uuid":"string"}
     * msg : 操作成功
     * success : true
     */

    private String code;
    private DataBean data;
    private String msg;
    private boolean success;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * buyerMobile (string, optional): 购买者手机号码 ,
         * buyerName (string, optional): 购买者姓名 ,
         * buyerUuid (string, optional): 购买者uuid ,
         * createdTime (string, optional): 创建时间 ,
         * dtcBrandName (string, optional): dtc品牌名称 ,
         * dtcBrandUuid (string, optional): dtc发布关联品牌(对应车辆品牌uuid) ,
         * dtcCode (string, optional): dtc故障代码_前缀 ,
         * dtcCode2 (string, optional): dtc故障代码_中缀 ,
         * dtcCode3 (string, optional): dtc故障代码_后缀 ,
         * dtcDefinition (string, optional): dtc定义(标题) ,
         * dtcDiagnose (string, optional): dtc故障诊断辅助(富文本图文) ,
         * dtcExplain (string, optional): dtc故障说明(富文本图文) ,
         * dtcIssuerType (integer, optional): 发布人类型：0：后台发布，1：技师 ，2：店铺 ,
         * dtcReasons (string, optional): dtc故障可能原因(富文本图文) ,
         * dtcType (string, optional): DTC类型 ,
         * dtcUuid (string, optional): dtc故障uuid ,
         * isPay (boolean, optional): 是否支付，true已支付，false未支付 ,
         * issuerMobile (string, optional): 发布者手机号码 ,
         * issuerName (string, optional): 发布者姓名 ,
         * issuerUuid (string, optional): 发布者uuid ,
         * orderAmount (number, optional): 订单金额 ,
         * orderNum (string, optional): 订单编号 ,
         * orderSts (integer, optional): 订单状态 0 待支付 1 已支付 2: 已取消 3:退款中 4:退款成功 5:退款失败 ,
         * payType (integer, optional): 支付方式 0 微信支付 1 支付宝支付 ,
         * readCount (integer, optional): 查看次数,最多可看3次,超过3次重新购买 ,
         * uuid (string, optional): 主键ID
         */

        private String buyerMobile;
        private String buyerName;
        private String buyerUuid;
        private String createdTime;
        private String dtcBrandName;
        private String dtcModelUuidName;
        private String dtcBrandUuid;
        private String dtcCode;
        private String dtcCode2;
        private String dtcCode3;
        private String dtcDefinition;
        private String dtcDiagnose;
        private String dtcExplain;
        private int dtcIssuerType;
        private String dtcReasons;
        private String dtcType;
        private String dtcUuid;
        private boolean isPay;
        private String issuerMobile;
        private String issuerName;
        private String issuerUuid;
        private float orderAmount;
        private String orderNum;
        private int orderSts;
        private int payType;
        private int readCount;
        private String uuid;

        public String getDtcModelUuidName() {
            return dtcModelUuidName;
        }

        public void setDtcModelUuidName(String dtcModelUuidName) {
            this.dtcModelUuidName = dtcModelUuidName;
        }

        public boolean isPay() {
            return isPay;
        }

        public void setPay(boolean pay) {
            isPay = pay;
        }

        public String getBuyerMobile() {
            return buyerMobile;
        }

        public void setBuyerMobile(String buyerMobile) {
            this.buyerMobile = buyerMobile;
        }

        public String getBuyerName() {
            return buyerName;
        }

        public void setBuyerName(String buyerName) {
            this.buyerName = buyerName;
        }

        public String getBuyerUuid() {
            return buyerUuid;
        }

        public void setBuyerUuid(String buyerUuid) {
            this.buyerUuid = buyerUuid;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public String getDtcBrandName() {
            return dtcBrandName;
        }

        public void setDtcBrandName(String dtcBrandName) {
            this.dtcBrandName = dtcBrandName;
        }

        public String getDtcBrandUuid() {
            return dtcBrandUuid;
        }

        public void setDtcBrandUuid(String dtcBrandUuid) {
            this.dtcBrandUuid = dtcBrandUuid;
        }

        public String getDtcCode() {
            return dtcCode;
        }

        public void setDtcCode(String dtcCode) {
            this.dtcCode = dtcCode;
        }

        public String getDtcCode2() {
            return dtcCode2 == null?"":dtcCode2;
        }

        public void setDtcCode2(String dtcCode2) {
            this.dtcCode2 = dtcCode2;
        }

        public String getDtcCode3() {
            return dtcCode3;
        }

        public void setDtcCode3(String dtcCode3) {
            this.dtcCode3 = dtcCode3;
        }

        public String getDtcDefinition() {
            return dtcDefinition;
        }

        public void setDtcDefinition(String dtcDefinition) {
            this.dtcDefinition = dtcDefinition;
        }

        public String getDtcDiagnose() {
            return dtcDiagnose;
        }

        public void setDtcDiagnose(String dtcDiagnose) {
            this.dtcDiagnose = dtcDiagnose;
        }

        public String getDtcExplain() {
            return dtcExplain;
        }

        public void setDtcExplain(String dtcExplain) {
            this.dtcExplain = dtcExplain;
        }

        public int getDtcIssuerType() {
            return dtcIssuerType;
        }

        public void setDtcIssuerType(int dtcIssuerType) {
            this.dtcIssuerType = dtcIssuerType;
        }

        public String getDtcReasons() {
            return dtcReasons;
        }

        public void setDtcReasons(String dtcReasons) {
            this.dtcReasons = dtcReasons;
        }

        public String getDtcType() {
            return dtcType;
        }

        public void setDtcType(String dtcType) {
            this.dtcType = dtcType;
        }

        public String getDtcUuid() {
            return dtcUuid;
        }

        public void setDtcUuid(String dtcUuid) {
            this.dtcUuid = dtcUuid;
        }

        public boolean isIsPay() {
            return isPay;
        }

        public void setIsPay(boolean isPay) {
            this.isPay = isPay;
        }

        public String getIssuerMobile() {
            return issuerMobile;
        }

        public void setIssuerMobile(String issuerMobile) {
            this.issuerMobile = issuerMobile;
        }

        public String getIssuerName() {
            return issuerName;
        }

        public void setIssuerName(String issuerName) {
            this.issuerName = issuerName;
        }

        public String getIssuerUuid() {
            return issuerUuid;
        }

        public void setIssuerUuid(String issuerUuid) {
            this.issuerUuid = issuerUuid;
        }

        public String getOrderAmount() {
            return BasicUtils.floatDecimalFormat(orderAmount);
        }

        public void setOrderAmount(float orderAmount) {
            this.orderAmount = orderAmount;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public int getOrderSts() {
            return orderSts;
        }

        public void setOrderSts(int orderSts) {
            this.orderSts = orderSts;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public int getReadCount() {
            return readCount;
        }

        public void setReadCount(int readCount) {
            this.readCount = readCount;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }
    }
}
