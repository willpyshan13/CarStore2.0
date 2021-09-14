package com.yanxin.store.req;

public class AddDTCReq {

    /**
     * dtcAmount (string, optional): dtc购买金额 ,
     * dtcBrandUuid (string, optional): dtc发布关联品牌(对应车辆品牌uuid) ,
     * dtcCheckSts (integer, optional): 审核状态:0 待审核 1 审核通过 2 审核驳回 ,
     * dtcCode (string, optional): dtc故障代码_前缀 ,
     * dtcCode2 (string, optional): dtc故障代码_中缀 ,
     * dtcCode3 (string, optional): dtc故障代码_后缀 ,
     * dtcDefinition (string, optional): dtc标题 ,
     * dtcDiagnose (string, optional): dtc故障诊断辅助(富文本图文) ,
     * dtcExplain (string, optional): dtc故障说明(富文本图文) ,
     * dtcModelUuid (string, optional): 车型 ,
     * dtcReasons (string, optional): dtc故障可能原因(富文本图文) ,
     * dtcRemarks (string, optional): 驳回详情 ,
     * dtcType (string, optional): dtc类型，对应字典
     */

    private String dtcAmount;
    private String dtcBrandUuid;
    private int dtcCheckSts;
    private String dtcCode;
    private String dtcCode2 = "";
    private String dtcCode3 = "";
    private String dtcDefinition;
    private String dtcDiagnose;
    private String dtcExplain;
    private String dtcModelUuid;
    private String dtcReasons;
    private String dtcRemarks;
    private String dtcType;

    public String getDtcAmount() {
        return dtcAmount;
    }

    public void setDtcAmount(String dtcAmount) {
        this.dtcAmount = dtcAmount;
    }

    public String getDtcBrandUuid() {
        return dtcBrandUuid;
    }

    public void setDtcBrandUuid(String dtcBrandUuid) {
        this.dtcBrandUuid = dtcBrandUuid;
    }

    public int getDtcCheckSts() {
        return dtcCheckSts;
    }

    public void setDtcCheckSts(int dtcCheckSts) {
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
}
