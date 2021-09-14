package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;

import java.util.ArrayList;

public class RushOrderBean extends BaseBean {
    private ArrayList<String> consultImg;
    private String uuid;
    private int orderSts;
    private int answerSts;
    private int consultCheckSts;
    private String title;
    private String desc;
    private float amount;
    private String statusTitle;

    public int getAnswerSts() {
        return answerSts;
    }

    public void setAnswerSts(int answerSts) {
        this.answerSts = answerSts;
    }

    public int getConsultCheckSts() {
        return consultCheckSts;
    }

    public void setConsultCheckSts(int consultCheckSts) {
        this.consultCheckSts = consultCheckSts;
    }

    public float getAmount() {
        return amount;
    }

    public String getStatusTitle() {
        return statusTitle;
    }

    public void setStatusTitle(String statusTitle) {
        this.statusTitle = statusTitle;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public ArrayList<String> getConsultImg() {
        return consultImg;
    }

    public void setConsultImg(ArrayList<String> consultImg) {
        this.consultImg = consultImg;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getOrderSts() {
        return orderSts;
    }

    public void setOrderSts(int orderSts) {
        this.orderSts = orderSts;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
