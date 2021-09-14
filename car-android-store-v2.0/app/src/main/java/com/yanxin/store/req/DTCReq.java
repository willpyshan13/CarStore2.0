package com.yanxin.store.req;

public class DTCReq {

    /**
     * dtcCode : string   //dtc 故障代码
     * pageNum : 1
     * pageSize : 10
     */

    private String dtcCode;
    private String dtcBrandUuid;
    private int pageNum;
    private int dtcCheckSts = 1;
    private int pageSize;

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
