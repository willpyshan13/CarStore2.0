package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;
import com.yanxin.store.utils.BasicUtils;

public class DtcDetailBean extends BaseBean {

    /**
     * code : 0000
     * data : {"configName":"string","createdBy":"string","createdTime":"2021-08-09T01:35:29.965Z","dtcAmount":0,"dtcBrandUuid":"string","dtcCheckSts":"string","dtcCode":"string","dtcCode2":"string","dtcCode3":"string","dtcContentUuid":"string","dtcDefinition":"string","dtcDiagnose":"string","dtcExplain":"string","dtcIssuerUuid":"string","dtcModelUuid":"string","dtcReasons":"string","dtcRemarks":"string","dtcType":"string","dtcTypeName":"string","isOneself":false,"orderSts":0,"orderUuid":"string","uuid":"string"}
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
         * configName (string, optional): 品牌名称 ,
         * createdBy (string, optional): 创建人 ,
         * createdTime (string, optional): 创建时间 ,
         * dtcAmount (number, optional): dtc购买金额 ,
         * dtcBrandUuid (string, optional): dtc发布关联品牌(对应车辆品牌uuid) ,
         * dtcCheckSts (string, optional): 审核状态 ,
         * dtcCode (string, optional): dtc故障代码_前缀 ,
         * dtcCode2 (string, optional): dtc故障代码_中缀 ,
         * dtcCode3 (string, optional): dtc故障代码_后缀 ,
         * dtcContentUuid (string, optional): 内容详情uuid ,
         * dtcDefinition (string, optional): dtc标题 ,
         * dtcDiagnose (string, optional): dtc故障诊断辅助(富文本图文) ,
         * dtcExplain (string, optional): dtc故障说明(富文本图文) ,
         * dtcIssuerUuid (string, optional): 发布人uuid ,
         * dtcModelUuid (string, optional): 车型 ,
         * dtcReasons (string, optional): dtc故障可能原因(富文本图文) ,
         * dtcRemarks (string, optional): 审核原因 ,
         * dtcType (string, optional): dtc类型，对应字典 ,
         * dtcTypeName (string, optional): dtc类型名称 ,
         * isOneself (boolean, optional): 是否是本人发布信息，true是，false否 ,
         * orderSts (integer, optional): 订单状态 0 待支付 1 已支付 2: 已取消 3:退款中 4:退款成功 5:退款失败 ,
         * orderUuid (string, optional): 订单状态uuid ,
         * uuid (string, optional): uuid
         */

        private String configName;
        private String createdBy;
        private String createdTime;
        private float dtcAmount;
        private String dtcBrandUuid;
        private String dtcBrandUuidName;
        private String dtcModelUuidName;
        private String dtcCheckSts;
        private String dtcCode;
        private String dtcCode2;
        private String dtcCode3;
        private String dtcContentUuid;
        private String dtcDefinition;
        private String dtcDiagnose;
        private String dtcExplain;
        private String dtcIssuerUuid;
        private String dtcModelUuid;
        private String dtcReasons;
        private String dtcRemarks;
        private String dtcType;
        private String dtcTypeName;
        private boolean isOneself;
        private int orderSts;
        private String orderUuid;
        private String uuid;

        public String getDtcBrandUuidName() {
            return dtcBrandUuidName;
        }

        public void setDtcBrandUuidName(String dtcBrandUuidName) {
            this.dtcBrandUuidName = dtcBrandUuidName;
        }

        public String getDtcModelUuidName() {
            return dtcModelUuidName;
        }

        public void setDtcModelUuidName(String dtcModelUuidName) {
            this.dtcModelUuidName = dtcModelUuidName;
        }

        public boolean isOneself() {
            return isOneself;
        }

        public void setOneself(boolean oneself) {
            isOneself = oneself;
        }

        public String getConfigName() {
            return configName;
        }

        public void setConfigName(String configName) {
            this.configName = configName;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public String getDtcAmount() {
            return BasicUtils.floatDecimalFormat(dtcAmount);
        }

        public void setDtcAmount(float dtcAmount) {
            this.dtcAmount = dtcAmount;
        }

        public String getDtcBrandUuid() {
            return dtcBrandUuid;
        }

        public void setDtcBrandUuid(String dtcBrandUuid) {
            this.dtcBrandUuid = dtcBrandUuid;
        }

        public String getDtcCheckSts() {
            return dtcCheckSts;
        }

        public void setDtcCheckSts(String dtcCheckSts) {
            this.dtcCheckSts = dtcCheckSts;
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
            return dtcCode3 == null?"":dtcCode3;
        }

        public void setDtcCode3(String dtcCode3) {
            this.dtcCode3 = dtcCode3;
        }

        public String getDtcContentUuid() {
            return dtcContentUuid;
        }

        public void setDtcContentUuid(String dtcContentUuid) {
            this.dtcContentUuid = dtcContentUuid;
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

        public String getDtcIssuerUuid() {
            return dtcIssuerUuid;
        }

        public void setDtcIssuerUuid(String dtcIssuerUuid) {
            this.dtcIssuerUuid = dtcIssuerUuid;
        }

        public String getDtcModelUuid() {
            return dtcModelUuid;
        }

        public void setDtcModelUuid(String dtcModelUuid) {
            this.dtcModelUuid = dtcModelUuid;
        }

        public String getDtcReasons() {
            return dtcReasons;
        }

        public void setDtcReasons(String dtcReasons) {
            this.dtcReasons = dtcReasons;
        }

        public String getDtcRemarks() {
            return dtcRemarks;
        }

        public void setDtcRemarks(String dtcRemarks) {
            this.dtcRemarks = dtcRemarks;
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

        public boolean isIsOneself() {
            return isOneself;
        }

        public void setIsOneself(boolean isOneself) {
            this.isOneself = isOneself;
        }

        public int getOrderSts() {
            return orderSts;
        }

        public void setOrderSts(int orderSts) {
            this.orderSts = orderSts;
        }

        public String getOrderUuid() {
            return orderUuid;
        }

        public void setOrderUuid(String orderUuid) {
            this.orderUuid = orderUuid;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }
    }
}
