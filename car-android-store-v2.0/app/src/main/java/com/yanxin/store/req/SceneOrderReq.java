package com.yanxin.store.req;

public class SceneOrderReq {


    /**
     * pageNum : 1
     * pageSize : 10
     * queryType : 0
     */

    private int pageNum;
    private int pageSize;
    private int queryType;

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

    public int getQueryType() {
        return queryType;
    }

    public void setQueryType(int queryType) {
        this.queryType = queryType;
    }
}
