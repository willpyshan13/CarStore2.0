package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;

import java.util.ArrayList;
import java.util.List;

public class MyOrderBean extends BaseBean {

    /**
     * code : 0000
     * msg : success
     * data : [{"uuid":"2a67bb038d71446d9fb8e02342adf815","evaluateSts":null,"technicianScore":null,"afterSaleInfoRes":null,"carOwnerInfoRes":null,"orderDetailRes":null,"technicianInfoRes":null,"caseInfoListRes":null,"createdTime":"2021-08-05 10:29:05","title":"安利","orderAmount":"10.00","technicianName":"迪嘉","carOwnerName":"6bbe5eec96bd4ac19e48d3de19419035"},{"uuid":"793cea204d75463e92ce0333f3483a61","evaluateSts":null,"technicianScore":null,"afterSaleInfoRes":null,"carOwnerInfoRes":null,"orderDetailRes":null,"technicianInfoRes":null,"caseInfoListRes":null,"createdTime":"2021-08-05 10:03:59","title":"安利","orderAmount":"10.00","technicianName":"迪嘉","carOwnerName":"d7a8ed9e08b542bc8552e0d67fb35514"},{"uuid":"ab1fbc5dec1f4207a426052a09f9cfae","evaluateSts":null,"technicianScore":null,"afterSaleInfoRes":null,"carOwnerInfoRes":null,"orderDetailRes":null,"technicianInfoRes":null,"caseInfoListRes":null,"createdTime":"2021-08-05 21:27:03","title":"股骨头","orderAmount":"10.00","technicianName":"大奔汽修店","carOwnerName":"4a74eb50dc334d06ae3fba52b89e4a55"},{"uuid":"da64924190fc46abadbfe6607ea2fdd7","evaluateSts":null,"technicianScore":null,"afterSaleInfoRes":null,"carOwnerInfoRes":null,"orderDetailRes":null,"technicianInfoRes":null,"caseInfoListRes":null,"createdTime":"2021-08-05 16:37:11","title":"安利","orderAmount":"10.00","technicianName":"www","carOwnerName":"f891d2e42887427bab285a933581d26f"}]
     * success : true
     */

    private String code;
    private String msg;
    private ArrayList<DataBean> data;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * uuid : 2a67bb038d71446d9fb8e02342adf815
         * evaluateSts : null
         * technicianScore : null
         * afterSaleInfoRes : null
         * carOwnerInfoRes : null
         * orderDetailRes : null
         * technicianInfoRes : null
         * caseInfoListRes : null
         * createdTime : 2021-08-05 10:29:05
         * title : 安利
         * orderAmount : 10.00
         * technicianName : 迪嘉
         * carOwnerName : 6bbe5eec96bd4ac19e48d3de19419035
         */

        private String uuid;
        private Object evaluateSts;
        private Object technicianScore;
        private Object afterSaleInfoRes;
        private Object carOwnerInfoRes;
        private Object orderDetailRes;
        private Object technicianInfoRes;
        private Object caseInfoListRes;
        private String createdTime;
        private String title;
        private String orderAmount;
        private String technicianName;
        private String carOwnerName;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public Object getEvaluateSts() {
            return evaluateSts;
        }

        public void setEvaluateSts(Object evaluateSts) {
            this.evaluateSts = evaluateSts;
        }

        public Object getTechnicianScore() {
            return technicianScore;
        }

        public void setTechnicianScore(Object technicianScore) {
            this.technicianScore = technicianScore;
        }

        public Object getAfterSaleInfoRes() {
            return afterSaleInfoRes;
        }

        public void setAfterSaleInfoRes(Object afterSaleInfoRes) {
            this.afterSaleInfoRes = afterSaleInfoRes;
        }

        public Object getCarOwnerInfoRes() {
            return carOwnerInfoRes;
        }

        public void setCarOwnerInfoRes(Object carOwnerInfoRes) {
            this.carOwnerInfoRes = carOwnerInfoRes;
        }

        public Object getOrderDetailRes() {
            return orderDetailRes;
        }

        public void setOrderDetailRes(Object orderDetailRes) {
            this.orderDetailRes = orderDetailRes;
        }

        public Object getTechnicianInfoRes() {
            return technicianInfoRes;
        }

        public void setTechnicianInfoRes(Object technicianInfoRes) {
            this.technicianInfoRes = technicianInfoRes;
        }

        public Object getCaseInfoListRes() {
            return caseInfoListRes;
        }

        public void setCaseInfoListRes(Object caseInfoListRes) {
            this.caseInfoListRes = caseInfoListRes;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(String orderAmount) {
            this.orderAmount = orderAmount;
        }

        public String getTechnicianName() {
            return technicianName;
        }

        public void setTechnicianName(String technicianName) {
            this.technicianName = technicianName;
        }

        public String getCarOwnerName() {
            return carOwnerName;
        }

        public void setCarOwnerName(String carOwnerName) {
            this.carOwnerName = carOwnerName;
        }
    }
}
