package com.yanxin.store.req;

import java.util.ArrayList;
import java.util.List;

public class AskExpertReq {

    /**
     * .
     * onsultContent (string, optional): 咨询内容 ,
     * consultImgList (Array[string], optional): 咨询内容相关图片 ,
     * consultTitle (string, optional): 咨询标题 ,
     * technicianUuid (string, optional): 技师uuid ,
     * vehicleBrand (string, optional): 车辆品牌 ,
     * vehicleModel (string, optional): 车型类型
     */

    private String consultContent;
    private ArrayList<String> consultImgList;
    private String consultTitle;
    private String technicianUuid;
    private String vehicleBrand;
    private String vehicleModel;
    private String technicalTypeUuid;

    public String getTechnicalTypeUuid() {
        return technicalTypeUuid;
    }

    public void setTechnicalTypeUuid(String technicalTypeUuid) {
        this.technicalTypeUuid = technicalTypeUuid;
    }

    public String getConsultContent() {
        return consultContent;
    }

    public void setConsultContent(String consultContent) {
        this.consultContent = consultContent;
    }

    public ArrayList<String> getConsultImgList() {
        return consultImgList;
    }

    public void setConsultImgList(ArrayList<String> consultImgList) {
        this.consultImgList = consultImgList;
    }

    public String getConsultTitle() {
        return consultTitle;
    }

    public void setConsultTitle(String consultTitle) {
        this.consultTitle = consultTitle;
    }

    public String getTechnicianUuid() {
        return technicianUuid;
    }

    public void setTechnicianUuid(String technicianUuid) {
        this.technicianUuid = technicianUuid;
    }

    public String getVehicleBrand() {
        return vehicleBrand;
    }

    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }
}
