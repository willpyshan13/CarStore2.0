package com.yanxin.store.req;

import java.util.ArrayList;

/**
 * 默认的请求参数，只包含页数和个数
 */
public class GoodsReq {
    private ArrayList<Integer> checkSts;
    private Integer sellSts;
    private int pageNum = 1;
    private int pageSize = 30;


    public ArrayList<Integer> getCheckSts() {
        return checkSts;
    }

    public void setCheckSts(ArrayList<Integer> checkSts) {
        this.checkSts = checkSts;
    }

    public Integer getSellSts() {
        return sellSts;
    }

    public void setSellSts(Integer sellSts) {
        this.sellSts = sellSts;
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
