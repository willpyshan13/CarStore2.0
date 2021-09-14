package com.yanxin.store.req;

public class MyRushReq {

    /**
     * pageNum : 1
     * pageSize : 10
     * quizzer : 0
     * state : 0
     */

private Integer answerCheckSts;
    private int pageNum;
    private int pageSize;
    private int quizzer;
    private Integer state;

    public Integer getAnswerCheckSts() {
        return answerCheckSts;
    }

    public void setAnswerCheckSts(Integer answerCheckSts) {
        this.answerCheckSts = answerCheckSts;
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

    public int getQuizzer() {
        return quizzer;
    }

    public void setQuizzer(int quizzer) {
        this.quizzer = quizzer;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
