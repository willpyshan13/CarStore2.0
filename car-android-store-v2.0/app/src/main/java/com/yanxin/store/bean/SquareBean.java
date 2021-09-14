package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;

import java.util.ArrayList;

public class SquareBean extends BaseBean {

    /**
     * code : 0000
     * msg : success
     * data : [{"uuid":"1","title":"测试标题","technicianName":"刘师傅","technicianImgUrl":"1","carOwnerName":"1","consultDesc":"车胎没气了，怎么办","answerDesc":"...","createdTime":"2021-07-16","consultAmt":2,"orderUuid":"11"}]
     * size : 10
     * total : 1
     * pages : 1
     * success : true
     */

    private String code;
    private String msg;
    private ArrayList<DataBean> data;
    private int size;
    private int total;
    private int pages;
    private boolean success;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * uuid : 1
         * title : 测试标题
         * technicianName : 刘师傅
         * technicianImgUrl : 1
         * carOwnerName : 1
         * consultDesc : 车胎没气了，怎么办
         * answerDesc : ...
         * createdTime : 2021-07-16
         * consultAmt : 2
         * orderUuid : 11
         */

        private String uuid;
        private String title;
        private String technicianName;
        private String technicianImgUrl;
        private String carOwnerName;
        private String consultDesc;
        private String answerDesc;
        private String createdTime;
        private int consultAmt;
        private String orderUuid;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTechnicianName() {
            return technicianName;
        }

        public void setTechnicianName(String technicianName) {
            this.technicianName = technicianName;
        }

        public String getTechnicianImgUrl() {
            return technicianImgUrl;
        }

        public void setTechnicianImgUrl(String technicianImgUrl) {
            this.technicianImgUrl = technicianImgUrl;
        }

        public String getCarOwnerName() {
            return carOwnerName;
        }

        public void setCarOwnerName(String carOwnerName) {
            this.carOwnerName = carOwnerName;
        }

        public String getConsultDesc() {
            return consultDesc;
        }

        public void setConsultDesc(String consultDesc) {
            this.consultDesc = consultDesc;
        }

        public String getAnswerDesc() {
            return answerDesc;
        }

        public void setAnswerDesc(String answerDesc) {
            this.answerDesc = answerDesc;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public int getConsultAmt() {
            return consultAmt;
        }

        public void setConsultAmt(int consultAmt) {
            this.consultAmt = consultAmt;
        }

        public String getOrderUuid() {
            return orderUuid;
        }

        public void setOrderUuid(String orderUuid) {
            this.orderUuid = orderUuid;
        }
    }
}
