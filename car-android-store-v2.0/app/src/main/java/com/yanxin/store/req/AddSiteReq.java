package com.yanxin.store.req;

import com.yanxin.store.utils.BasicUtils;

import java.util.List;

public class AddSiteReq {

    /**
     * alreadyInspect (string, optional): 已检过程 ,
     * basicDoorAmountUuid (string, optional): 基本上门费用Uuid ,
     * boosterSystemUuid (string, optional): 增压系统uuid(对应字典uuid) ,
     * brandUuid (string, optional): 品牌uuid ,
     * carModelUuid (string, optional): 车型uuid ,
     * carStyle (string, optional): 车款 ,
     * detailedAddr (string, optional): 详细地址 ,
     * drivingModeUuid (string, optional): 驱动方式Uuid(对应字典uuid) ,
     * dtcCode (string, optional): DTC故障code ,
     * dtcImageList (Array[string], optional): dtc图片 ,
     * dtcUuid (string, optional): dtc故障uuid ,
     * engineDisplacementUuid (string, optional): 发动机排量uuid(对应字典uuid) ,
     * faultDesc (string, optional): 故障描述 ,
     * faultDescImageList (Array[string], optional): 故障描述图片 ,
     * orderServiceAmountUuid (string, optional): 平台订单服务费Uuid ,
     * otherSts (string, optional): 其他状态 ,
     * repairTypeUuid (string, optional): 维修类型uuid（对应字典表uuid） ,
     * serviceDate (string, optional): 服务时间 ,
     * totalAmount (number, optional): 总费用 ,
     * transmissionOneLevelUuid (string, optional): 变速器一级uuid(对应字典uuid) ,
     * transmissionTwoLevelUuid (string, optional): 变速器二级（对应字典uuid） ,
     * vinCode (string, optional): VIN码 ,
     * warrantySts (integer, optional): 保修状态0保修，1不保修
     */

    private String alreadyInspect;
    private String basicDoorAmountUuid;
    private String boosterSystemUuid;
    private String brandUuid;
    private String addressCity;
    private String addressCounty;
    private String addressProvince;
    private String carModelUuid;
    private String carStyle;
    private String detailedAddr;
    private String drivingModeUuid;
    private String dtcCode;
    private List<String> dtcImageList;
    private String dtcUuid;
    private String engineDisplacementUuid;
    private String faultDesc;
    private List<String> faultDescImageList;
    private String orderServiceAmountUuid;
    private String otherSts;
    private String repairTypeUuid;
    private String serviceDate;
    private float totalAmount;
    private double longitude;
    private double latitude;
    private String transmissionOneLevelUuid;
    private String transmissionTwoLevelUuid;
    private String vinCode;
    private int warrantySts;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressCounty() {
        return addressCounty;
    }

    public void setAddressCounty(String addressCounty) {
        this.addressCounty = addressCounty;
    }

    public String getAddressProvince() {
        return addressProvince;
    }

    public void setAddressProvince(String addressProvince) {
        this.addressProvince = addressProvince;
    }

    public String getAlreadyInspect() {
        return alreadyInspect;
    }

    public void setAlreadyInspect(String alreadyInspect) {
        this.alreadyInspect = alreadyInspect;
    }

    public String getBasicDoorAmountUuid() {
        return basicDoorAmountUuid;
    }

    public void setBasicDoorAmountUuid(String basicDoorAmountUuid) {
        this.basicDoorAmountUuid = basicDoorAmountUuid;
    }

    public String getBoosterSystemUuid() {
        return boosterSystemUuid;
    }

    public void setBoosterSystemUuid(String boosterSystemUuid) {
        this.boosterSystemUuid = boosterSystemUuid;
    }

    public String getBrandUuid() {
        return brandUuid;
    }

    public void setBrandUuid(String brandUuid) {
        this.brandUuid = brandUuid;
    }

    public String getCarModelUuid() {
        return carModelUuid;
    }

    public void setCarModelUuid(String carModelUuid) {
        this.carModelUuid = carModelUuid;
    }

    public String getCarStyle() {
        return carStyle;
    }

    public void setCarStyle(String carStyle) {
        this.carStyle = carStyle;
    }

    public String getDetailedAddr() {
        return detailedAddr;
    }

    public void setDetailedAddr(String detailedAddr) {
        this.detailedAddr = detailedAddr;
    }

    public String getDrivingModeUuid() {
        return drivingModeUuid;
    }

    public void setDrivingModeUuid(String drivingModeUuid) {
        this.drivingModeUuid = drivingModeUuid;
    }

    public String getDtcCode() {
        return dtcCode;
    }

    public void setDtcCode(String dtcCode) {
        this.dtcCode = dtcCode;
    }

    public List<String> getDtcImageList() {
        return dtcImageList;
    }

    public void setDtcImageList(List<String> dtcImageList) {
        this.dtcImageList = dtcImageList;
    }

    public String getDtcUuid() {
        return dtcUuid;
    }

    public void setDtcUuid(String dtcUuid) {
        this.dtcUuid = dtcUuid;
    }

    public String getEngineDisplacementUuid() {
        return engineDisplacementUuid;
    }

    public void setEngineDisplacementUuid(String engineDisplacementUuid) {
        this.engineDisplacementUuid = engineDisplacementUuid;
    }

    public String getFaultDesc() {
        return faultDesc;
    }

    public void setFaultDesc(String faultDesc) {
        this.faultDesc = faultDesc;
    }

    public List<String> getFaultDescImageList() {
        return faultDescImageList;
    }

    public void setFaultDescImageList(List<String> faultDescImageList) {
        this.faultDescImageList = faultDescImageList;
    }

    public String getOrderServiceAmountUuid() {
        return orderServiceAmountUuid;
    }

    public void setOrderServiceAmountUuid(String orderServiceAmountUuid) {
        this.orderServiceAmountUuid = orderServiceAmountUuid;
    }

    public String getOtherSts() {
        return otherSts;
    }

    public void setOtherSts(String otherSts) {
        this.otherSts = otherSts;
    }

    public String getRepairTypeUuid() {
        return repairTypeUuid;
    }

    public void setRepairTypeUuid(String repairTypeUuid) {
        this.repairTypeUuid = repairTypeUuid;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getTotalAmount() {
        return BasicUtils.floatDecimalFormat(totalAmount);
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTransmissionOneLevelUuid() {
        return transmissionOneLevelUuid;
    }

    public void setTransmissionOneLevelUuid(String transmissionOneLevelUuid) {
        this.transmissionOneLevelUuid = transmissionOneLevelUuid;
    }

    public String getTransmissionTwoLevelUuid() {
        return transmissionTwoLevelUuid;
    }

    public void setTransmissionTwoLevelUuid(String transmissionTwoLevelUuid) {
        this.transmissionTwoLevelUuid = transmissionTwoLevelUuid;
    }

    public String getVinCode() {
        return vinCode;
    }

    public void setVinCode(String vinCode) {
        this.vinCode = vinCode;
    }

    public int getWarrantySts() {
        return warrantySts;
    }

    public void setWarrantySts(int warrantySts) {
        this.warrantySts = warrantySts;
    }
}
