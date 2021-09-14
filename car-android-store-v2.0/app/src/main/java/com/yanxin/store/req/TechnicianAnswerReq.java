package com.yanxin.store.req;

/**
 * 默认的请求参数，只包含页数和个数
 */
public class TechnicianAnswerReq {

    private int pageNum = 1;
    private int pageSize = 50;
    private String technologyTypeUuid;

    public String getTechnologyTypeUuid() {
        return technologyTypeUuid;
    }

    public void setTechnologyTypeUuid(String technologyTypeUuid) {
        this.technologyTypeUuid = technologyTypeUuid;
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
