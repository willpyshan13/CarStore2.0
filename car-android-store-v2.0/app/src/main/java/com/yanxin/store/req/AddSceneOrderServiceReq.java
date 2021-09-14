package com.yanxin.store.req;

import com.yanxin.store.utils.BasicUtils;

import java.util.List;

public class AddSceneOrderServiceReq {

    /**
     * basicInspectAmount : 0
     * checkData : string
     * faultDesc : string
     * imageList : ["string"]
     * orderServiceAmountUuid : string
     * otherAmount : 0
     * repairAmount : 0
     * sceneOrderUuid : string
     * solution : string
     * totalAmount : 0
     */

    private float basicInspectAmount;
    private String checkData;
    private String faultDesc;
    private List<String> imageList;
    private String orderServiceAmountUuid;
    private float otherAmount;
    private float repairAmount;
    private String sceneOrderUuid;
    private String solution;
    private float totalAmount;

    public String getBasicInspectAmount() {
        return BasicUtils.floatDecimalFormat(basicInspectAmount);
    }

    public void setBasicInspectAmount(float basicInspectAmount) {
        this.basicInspectAmount = basicInspectAmount;
    }

    public String getCheckData() {
        return checkData;
    }

    public void setCheckData(String checkData) {
        this.checkData = checkData;
    }

    public String getFaultDesc() {
        return faultDesc;
    }

    public void setFaultDesc(String faultDesc) {
        this.faultDesc = faultDesc;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public String getOrderServiceAmountUuid() {
        return orderServiceAmountUuid;
    }

    public void setOrderServiceAmountUuid(String orderServiceAmountUuid) {
        this.orderServiceAmountUuid = orderServiceAmountUuid;
    }

    public float getOtherAmount() {
        return otherAmount;
    }

    public void setOtherAmount(float otherAmount) {
        this.otherAmount = otherAmount;
    }

    public float getRepairAmount() {
        return repairAmount;
    }

    public void setRepairAmount(float repairAmount) {
        this.repairAmount = repairAmount;
    }

    public String getSceneOrderUuid() {
        return sceneOrderUuid;
    }

    public void setSceneOrderUuid(String sceneOrderUuid) {
        this.sceneOrderUuid = sceneOrderUuid;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getTotalAmount() {
        return BasicUtils.floatDecimalFormat(totalAmount);
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }
}
