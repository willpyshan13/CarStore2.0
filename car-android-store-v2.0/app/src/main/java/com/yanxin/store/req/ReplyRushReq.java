package com.yanxin.store.req;

import java.util.ArrayList;
import java.util.List;

public class ReplyRushReq {

    /**
     * answerContent : string
     * answerImgList : ["string"]
     * orderUuid : string
     */

    private String answerContent;
    private ArrayList<String> answerImgList;
    private String orderUuid;

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public ArrayList<String> getAnswerImgList() {
        return answerImgList;
    }

    public void setAnswerImgList(ArrayList<String> answerImgList) {
        this.answerImgList = answerImgList;
    }

    public String getOrderUuid() {
        return orderUuid;
    }

    public void setOrderUuid(String orderUuid) {
        this.orderUuid = orderUuid;
    }
}
