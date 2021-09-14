package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;

import java.util.ArrayList;

public class AuditBean extends BaseBean {

    /**
     * code : 0000
     * data : [{"answerSts":0,"orderSts":0,"technicianImgUrl":"string","technicianName":"string","title":"string","uuid":"string","vehicleBrand":"string","vehicleModel":"string"}]
     * msg : 操作成功
     * pages : 10
     * size : 10
     * success : true
     * total : 100
     */

    private String code;
    private ArrayList<DataBean> data;
    private String msg;
    private int pages;
    private int size;
    private boolean success;
    private int total;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public static class DataBean {
        /**
         * answerSts (integer, optional): 答复状态 0 未答复 1 已答复 ,
         * orderSts (integer, optional): 订单状态 0 待支付 1 已支付 2: 已取消 3:退款中 4:退款成功 5:退款失败 ,
         * technicianImgUrl (string, optional): 技师头像地址 ,
         * technicianName (string, optional): 技师姓名 ,
         * title (string, optional): 咨询标题 ,
         * uuid (string, optional): 订单uuid ,
         * vehicleBrand (string, optional): 车辆品牌 ,
         * vehicleModel (string, optional): 车型类型
         */

        private int answerSts;
        private int orderSts;
        private String technicianImgUrl;
        private String technicianName;
        private String title;
        private String uuid;
        private String vehicleBrand;
        private String vehicleModel;
        private String vehicleBrandName;
        private String vehicleModelName;
        private String consultDesc;
        private ArrayList<String> imgs;
        private int yesOrNo;

        public String getVehicleBrandName() {
            return vehicleBrandName;
        }

        public void setVehicleBrandName(String vehicleBrandName) {
            this.vehicleBrandName = vehicleBrandName;
        }

        public String getVehicleModelName() {
            return vehicleModelName;
        }

        public void setVehicleModelName(String vehicleModelName) {
            this.vehicleModelName = vehicleModelName;
        }

        public String getConsultDesc() {
            return consultDesc;
        }

        public void setConsultDesc(String consultDesc) {
            this.consultDesc = consultDesc;
        }

        public ArrayList<String> getImgs() {
            return imgs;
        }

        public void setImgs(ArrayList<String> imgs) {
            this.imgs = imgs;
        }

        public int getYesOrNo() {
            return yesOrNo;
        }

        public void setYesOrNo(int yesOrNo) {
            this.yesOrNo = yesOrNo;
        }

        public int getAnswerSts() {
            return answerSts;
        }

        public void setAnswerSts(int answerSts) {
            this.answerSts = answerSts;
        }

        public int getOrderSts() {
            return orderSts;
        }

        public void setOrderSts(int orderSts) {
            this.orderSts = orderSts;
        }

        public String getTechnicianImgUrl() {
            return technicianImgUrl;
        }

        public void setTechnicianImgUrl(String technicianImgUrl) {
            this.technicianImgUrl = technicianImgUrl;
        }

        public String getTechnicianName() {
            return technicianName;
        }

        public void setTechnicianName(String technicianName) {
            this.technicianName = technicianName;
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

        public String getVehicleBrand() {
            return vehicleBrand;
        }

        public void setVehicleBrand(String vehicleBrand) {
            this.vehicleBrand = vehicleBrand;
        }

        public String getVehicleModel() {
            return vehicleModel;
        }

        public void setVehicleModel(String vehicleModel) {
            this.vehicleModel = vehicleModel;
        }
    }
}
