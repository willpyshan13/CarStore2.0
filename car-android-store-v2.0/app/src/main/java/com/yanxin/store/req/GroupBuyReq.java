package com.yanxin.store.req;

import java.util.ArrayList;

/**
 * 默认的请求参数，只包含页数和个数
 */
public class GroupBuyReq {
    private ArrayList<Integer> groupSts;
    private int pageNum = 1;
    private int pageSize = 30;


    public ArrayList<Integer> getGroupSts() {
        return groupSts;
    }

    public void setGroupSts(ArrayList<Integer> groupSts) {
        this.groupSts = groupSts;
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
