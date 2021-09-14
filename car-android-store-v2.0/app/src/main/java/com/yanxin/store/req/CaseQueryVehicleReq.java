package com.yanxin.store.req;

public class CaseQueryVehicleReq {

    /**
     * attachSys : string 所属系统 ,
     * brandUuid : string  品牌
     * model : string 型
     * pageNum : 1
     * pageSize : 10
     */

    private String attachSys;
    private String brandUuid;
    private String model;
    private int pageNum;
    private int pageSize;

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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
