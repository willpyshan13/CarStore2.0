package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;
import com.yanxin.store.utils.BasicUtils;

import java.util.ArrayList;
import java.util.List;

public class AskOrderDetailBean extends BaseBean {

    /**
     * code : 0000
     * data : {"answerCheckSts":"string","answerDesc":"string","answerSts":0,"answerTime":"2021-08-20T08:40:24.386Z","answerUrlList":["string"],"consultCheckSts":"string","consultDesc":"string","consultImgUrlList":["string"],"consultUuid":"string","createdTime":"string","evaluateSts":"string","orderAmount":0,"orderNum":"string","orderSts":"string","orderType":0,"payType":"string","technicianImgUrl":"string","technicianMobile":"string","technicianName":"string","technicianUuid":"string","title":"string","uuid":"string"}
     * msg : 操作成功
     * success : true
     */

    private String code;
    private DataBean data;
    private String msg;
    private boolean success;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * answerCheckSts : string
         * answerDesc : string
         * answerSts : 0
         * answerTime : 2021-08-20T08:40:24.386Z
         * answerUrlList : ["string"]
         * consultCheckSts : string
         * consultDesc : string
         * consultImgUrlList : ["string"]
         * consultUuid : string
         * createdTime : string
         * evaluateSts : string
         * orderAmount : 0
         * orderNum : string
         * orderSts : string
         * orderType : 0
         * payType : string
         * technicianImgUrl : string
         * technicianMobile : string
         * technicianName : string
         * technicianUuid : string
         * title : string
         * uuid : string
         */

        private int answerCheckSts;
        private String answerDesc;
        private int answerSts;
        private String answerTime;
        private ArrayList<String> answerUrlList;
        private int consultCheckSts;
        private String consultDesc;
        private ArrayList<String> consultImgUrlList;
        private String consultUuid;
        private String createdTime;
        private String evaluateSts;
        private float orderAmount;
        private String orderNum;
        private int orderSts;
        private int orderType;
        private int payType;
        private String technicianImgUrl;
        private String technicianMobile;
        private String technicianName;
        private String technicianUuid;
        private String title;
        private String uuid;

        public int getAnswerCheckSts() {
            return answerCheckSts;
        }

        public void setAnswerCheckSts(int answerCheckSts) {
            this.answerCheckSts = answerCheckSts;
        }

        public String getAnswerDesc() {
            return answerDesc;
        }

        public void setAnswerDesc(String answerDesc) {
            this.answerDesc = answerDesc;
        }

        public int getAnswerSts() {
            return answerSts;
        }

        public void setAnswerSts(int answerSts) {
            this.answerSts = answerSts;
        }

        public String getAnswerTime() {
            return answerTime;
        }

        public void setAnswerTime(String answerTime) {
            this.answerTime = answerTime;
        }

        public ArrayList<String> getAnswerUrlList() {
            return answerUrlList;
        }

        public void setAnswerUrlList(ArrayList<String> answerUrlList) {
            this.answerUrlList = answerUrlList;
        }

        public int getConsultCheckSts() {
            return consultCheckSts;
        }

        public void setConsultCheckSts(int consultCheckSts) {
            this.consultCheckSts = consultCheckSts;
        }

        public String getConsultDesc() {
            return consultDesc;
        }

        public void setConsultDesc(String consultDesc) {
            this.consultDesc = consultDesc;
        }

        public ArrayList<String> getConsultImgUrlList() {
            return consultImgUrlList;
        }

        public void setConsultImgUrlList(ArrayList<String> consultImgUrlList) {
            this.consultImgUrlList = consultImgUrlList;
        }

        public String getConsultUuid() {
            return consultUuid;
        }

        public void setConsultUuid(String consultUuid) {
            this.consultUuid = consultUuid;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public String getEvaluateSts() {
            return evaluateSts;
        }

        public void setEvaluateSts(String evaluateSts) {
            this.evaluateSts = evaluateSts;
        }

        public String getOrderAmount() {
            return BasicUtils.floatDecimalFormat(orderAmount);
        }

        public void setOrderAmount(float orderAmount) {
            this.orderAmount = orderAmount;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public int getOrderSts() {
            return orderSts;
        }

        public void setOrderSts(int orderSts) {
            this.orderSts = orderSts;
        }

        public int getOrderType() {
            return orderType;
        }

        public void setOrderType(int orderType) {
            this.orderType = orderType;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public String getTechnicianImgUrl() {
            return technicianImgUrl;
        }

        public void setTechnicianImgUrl(String technicianImgUrl) {
            this.technicianImgUrl = technicianImgUrl;
        }

        public String getTechnicianMobile() {
            return technicianMobile;
        }

        public void setTechnicianMobile(String technicianMobile) {
            this.technicianMobile = technicianMobile;
        }

        public String getTechnicianName() {
            return technicianName;
        }

        public void setTechnicianName(String technicianName) {
            this.technicianName = technicianName;
        }

        public String getTechnicianUuid() {
            return technicianUuid;
        }

        public void setTechnicianUuid(String technicianUuid) {
            this.technicianUuid = technicianUuid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }
    }
}
