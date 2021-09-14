package com.yanxin.store.req;

import java.util.ArrayList;
import java.util.List;

public class PushCaseReq {

    /**
     * amt (number, optional): 金额 ,
     * attachSys (string, optional): 所属系统字典表attach_sys ,
     * brandUuid (string, optional): 品牌字典表repair_brand ,
     * caseImgList (Array[string], optional): 案例资源列表 图片/文件 ,
     * faultDesc (string, optional): 故障现象 ,
     * ideaProcess (string, optional): 诊断思路和过程 ,
     * madeTime (string, optional): 开始时间 yyyy-MM-dd ,
     * mileage (integer, optional): 行驶里程单位km ,
     * model (string, optional): 车型uuid,account模块车辆管理配置获取 ,
     * powerInfo (string, optional): 动力信息:发动机类型+变速器信息 手动输入 ,
     * summary (string, optional): 结论总结 ,
     * title (string, optional): 标题 ,
     * vin (string, optional): 车架号
     */

    private int amt;
    private String attachSys;
    private String brandUuid;
    private ArrayList<String> caseImgList;
    private String faultDesc;
    private String ideaProcess;
    private String madeTime;
    private String pafName;
    private int mileage;
    private String model;
    private String powerInfo;
    private String summary;
    private String title;
    private String vin;

    public String getPafName() {
        return pafName;
    }

    public void setPafName(String pafName) {
        this.pafName = pafName;
    }

    public int getAmt() {
        return amt;
    }

    public void setAmt(int amt) {
        this.amt = amt;
    }

    public String getAttachSys() {
        return attachSys;
    }

    public void setAttachSys(String attachSys) {
        this.attachSys = attachSys;
    }

    public String getBrandUuid() {
        return brandUuid;
    }

    public void setBrandUuid(String brandUuid) {
        this.brandUuid = brandUuid;
    }

    public ArrayList<String> getCaseImgList() {
        return caseImgList;
    }

    public void setCaseImgList(ArrayList<String> caseImgList) {
        this.caseImgList = caseImgList;
    }

    public String getFaultDesc() {
        return faultDesc;
    }

    public void setFaultDesc(String faultDesc) {
        this.faultDesc = faultDesc;
    }

    public String getIdeaProcess() {
        return ideaProcess;
    }

    public void setIdeaProcess(String ideaProcess) {
        this.ideaProcess = ideaProcess;
    }

    public String getMadeTime() {
        return madeTime;
    }

    public void setMadeTime(String madeTime) {
        this.madeTime = madeTime;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPowerInfo() {
        return powerInfo;
    }

    public void setPowerInfo(String powerInfo) {
        this.powerInfo = powerInfo;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
}
