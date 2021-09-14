package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;

import java.util.ArrayList;
import java.util.List;

public class CaseOrderDetailBean extends BaseBean {


    /**
     * code : 0000
     * data : {"afterSaleInfoRes":{"afterSaleCause":"string","afterSaleImgList":["string"],"refundSts":0},"carOwnerInfoRes":{"completedNum":0,"paidNotCompleteNum":0,"refunded":0,"unpaidNum":0},"caseInfoListRes":[{"caseImgUrl":"string","caseName":"string","caseNum":0,"caseUuid":"string","materialsExpenses":0,"orderUuid":"string","uuid":"string"}],"evaluateSts":0,"orderDetailRes":{"orderAmount":"string","orderNum":"string","orderRemark":"string","orderSts":0,"payType":0,"receivableAmount":"string","uuid":"string"},"technicianInfoRes":{"technicianMobile":"string","technicianName":"string","technicianUuid":"string"},"technicianScore":0,"uuid":"string"}
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
         * afterSaleInfoRes : {"afterSaleCause":"string","afterSaleImgList":["string"],"refundSts":0}
         * carOwnerInfoRes : {"completedNum":0,"paidNotCompleteNum":0,"refunded":0,"unpaidNum":0}
         * caseInfoListRes : [{"caseImgUrl":"string","caseName":"string","caseNum":0,"caseUuid":"string","materialsExpenses":0,"orderUuid":"string","uuid":"string"}]
         * evaluateSts : 0
         * orderDetailRes : {"orderAmount":"string","orderNum":"string","orderRemark":"string","orderSts":0,"payType":0,"receivableAmount":"string","uuid":"string"}
         * technicianInfoRes : {"technicianMobile":"string","technicianName":"string","technicianUuid":"string"}
         * technicianScore : 0
         * uuid : string
         */

        private AfterSaleInfoResBean afterSaleInfoRes;
        private CarOwnerInfoResBean carOwnerInfoRes;
        private ArrayList<CaseInfoListResBean> caseInfoListRes;
        private int evaluateSts;
        private OrderDetailResBean orderDetailRes;
        private TechnicianInfoResBean technicianInfoRes;
        private int technicianScore;
        private String uuid;
        private String createTime;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public AfterSaleInfoResBean getAfterSaleInfoRes() {
            return afterSaleInfoRes;
        }

        public void setAfterSaleInfoRes(AfterSaleInfoResBean afterSaleInfoRes) {
            this.afterSaleInfoRes = afterSaleInfoRes;
        }

        public CarOwnerInfoResBean getCarOwnerInfoRes() {
            return carOwnerInfoRes;
        }

        public void setCarOwnerInfoRes(CarOwnerInfoResBean carOwnerInfoRes) {
            this.carOwnerInfoRes = carOwnerInfoRes;
        }

        public ArrayList<CaseInfoListResBean> getCaseInfoListRes() {
            return caseInfoListRes;
        }

        public void setCaseInfoListRes(ArrayList<CaseInfoListResBean> caseInfoListRes) {
            this.caseInfoListRes = caseInfoListRes;
        }

        public int getEvaluateSts() {
            return evaluateSts;
        }

        public void setEvaluateSts(int evaluateSts) {
            this.evaluateSts = evaluateSts;
        }

        public OrderDetailResBean getOrderDetailRes() {
            return orderDetailRes;
        }

        public void setOrderDetailRes(OrderDetailResBean orderDetailRes) {
            this.orderDetailRes = orderDetailRes;
        }

        public TechnicianInfoResBean getTechnicianInfoRes() {
            return technicianInfoRes;
        }

        public void setTechnicianInfoRes(TechnicianInfoResBean technicianInfoRes) {
            this.technicianInfoRes = technicianInfoRes;
        }

        public int getTechnicianScore() {
            return technicianScore;
        }

        public void setTechnicianScore(int technicianScore) {
            this.technicianScore = technicianScore;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public static class AfterSaleInfoResBean {
            /**
             * afterSaleCause : string
             * afterSaleImgList : ["string"]
             * refundSts : 0
             */

            private String afterSaleCause;
            private ArrayList<String> afterSaleImgList;
            private int refundSts;

            public String getAfterSaleCause() {
                return afterSaleCause;
            }

            public void setAfterSaleCause(String afterSaleCause) {
                this.afterSaleCause = afterSaleCause;
            }

            public ArrayList<String> getAfterSaleImgList() {
                return afterSaleImgList;
            }

            public void setAfterSaleImgList(ArrayList<String> afterSaleImgList) {
                this.afterSaleImgList = afterSaleImgList;
            }

            public int getRefundSts() {
                return refundSts;
            }

            public void setRefundSts(int refundSts) {
                this.refundSts = refundSts;
            }
        }

        public static class CarOwnerInfoResBean {
            /**
             * completedNum : 0
             * paidNotCompleteNum : 0
             * refunded : 0
             * unpaidNum : 0
             */

            private int completedNum;
            private int paidNotCompleteNum;
            private int refunded;
            private int unpaidNum;

            public int getCompletedNum() {
                return completedNum;
            }

            public void setCompletedNum(int completedNum) {
                this.completedNum = completedNum;
            }

            public int getPaidNotCompleteNum() {
                return paidNotCompleteNum;
            }

            public void setPaidNotCompleteNum(int paidNotCompleteNum) {
                this.paidNotCompleteNum = paidNotCompleteNum;
            }

            public int getRefunded() {
                return refunded;
            }

            public void setRefunded(int refunded) {
                this.refunded = refunded;
            }

            public int getUnpaidNum() {
                return unpaidNum;
            }

            public void setUnpaidNum(int unpaidNum) {
                this.unpaidNum = unpaidNum;
            }
        }

        public static class OrderDetailResBean {
            /**
             * orderAmount : string
             * orderNum : string
             * orderRemark : string
             * orderSts : 0
             * payType : 0
             * receivableAmount : string
             * uuid : string
             */

            private String orderAmount;
            private String orderNum;
            private String orderRemark;
            private int orderSts;
            private int payType;
            private String receivableAmount;
            private String uuid;

            public String getOrderAmount() {
                return orderAmount;
            }

            public void setOrderAmount(String orderAmount) {
                this.orderAmount = orderAmount;
            }

            public String getOrderNum() {
                return orderNum;
            }

            public void setOrderNum(String orderNum) {
                this.orderNum = orderNum;
            }

            public String getOrderRemark() {
                return orderRemark;
            }

            public void setOrderRemark(String orderRemark) {
                this.orderRemark = orderRemark;
            }

            public int getOrderSts() {
                return orderSts;
            }

            public void setOrderSts(int orderSts) {
                this.orderSts = orderSts;
            }

            public int getPayType() {
                return payType;
            }

            public void setPayType(int payType) {
                this.payType = payType;
            }

            public String getReceivableAmount() {
                return receivableAmount;
            }

            public void setReceivableAmount(String receivableAmount) {
                this.receivableAmount = receivableAmount;
            }

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }
        }

        public static class TechnicianInfoResBean {
            /**
             * technicianMobile : string
             * technicianName : string
             * technicianUuid : string
             */

            private String technicianMobile;
            private String technicianName;
            private String technicianUuid;

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
        }

        public static class CaseInfoListResBean {
            /**
             * caseImgUrl : string
             * caseName : string
             * caseNum : 0
             * caseUuid : string
             * materialsExpenses : 0
             * orderUuid : string
             * uuid : string
             */

            private String caseImgUrl;
            private String caseName;
            private int caseNum;
            private String caseUuid;
            private int materialsExpenses;
            private String orderUuid;
            private String uuid;

            public String getCaseImgUrl() {
                return caseImgUrl;
            }

            public void setCaseImgUrl(String caseImgUrl) {
                this.caseImgUrl = caseImgUrl;
            }

            public String getCaseName() {
                return caseName;
            }

            public void setCaseName(String caseName) {
                this.caseName = caseName;
            }

            public int getCaseNum() {
                return caseNum;
            }

            public void setCaseNum(int caseNum) {
                this.caseNum = caseNum;
            }

            public String getCaseUuid() {
                return caseUuid;
            }

            public void setCaseUuid(String caseUuid) {
                this.caseUuid = caseUuid;
            }

            public int getMaterialsExpenses() {
                return materialsExpenses;
            }

            public void setMaterialsExpenses(int materialsExpenses) {
                this.materialsExpenses = materialsExpenses;
            }

            public String getOrderUuid() {
                return orderUuid;
            }

            public void setOrderUuid(String orderUuid) {
                this.orderUuid = orderUuid;
            }

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }
        }
    }
}
