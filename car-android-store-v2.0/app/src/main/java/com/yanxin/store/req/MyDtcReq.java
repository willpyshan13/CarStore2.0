package com.yanxin.store.req;

public class MyDtcReq {

    /**
     * dtcCode : string   //dtc 故障代码
     * pageNum : 1
     * pageSize : 10
     */

    private int type;
    private int pageNum;
    private int pageSize;

    public int getDtcType() {
        return type;
    }

    public void setDtcType(int type) {
        this.type = type;
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
