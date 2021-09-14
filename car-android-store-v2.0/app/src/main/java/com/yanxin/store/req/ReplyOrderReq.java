package com.yanxin.store.req;

public class ReplyOrderReq {

    /**
     * answerSts : 1
     * pageNum : 1
     * pageSize : 10
     */

    private int answerSts = 1;
    private int pageNum = 1;
    private int pageSize = 50;

    public int getAnswerSts() {
        return answerSts;
    }

    public void setAnswerSts(int answerSts) {
        this.answerSts = answerSts;
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
