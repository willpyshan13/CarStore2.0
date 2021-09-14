package com.yanxin.store.req;

public class AuditReq {

    /**
     * carOwnerUuid : 13c55c78564641ff980ed3630aec68f7
     * consultType : 0
     * pageNum : 1
     * pageSize : 10
     */

    private String carOwnerUuid;
    private int consultType;
    private int pageNum;
    private int pageSize;

    public String getCarOwnerUuid() {
        return carOwnerUuid;
    }

    public void setCarOwnerUuid(String carOwnerUuid) {
        this.carOwnerUuid = carOwnerUuid;
    }

    public int getConsultType() {
        return consultType;
    }

    public void setConsultType(int consultType) {
        this.consultType = consultType;
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
