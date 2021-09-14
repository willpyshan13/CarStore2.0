package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;

public class MyDtcNumberBean extends BaseBean {

    private String code;
    private String msg;
    private DataDTO data;
    private Boolean success;

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

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public static class DataDTO {
        private Integer kecha;
        private Integer guoqi;

        public Integer getKecha() {
            return kecha;
        }

        public void setKecha(Integer kecha) {
            this.kecha = kecha;
        }

        public Integer getGuoqi() {
            return guoqi;
        }

        public void setGuoqi(Integer guoqi) {
            this.guoqi = guoqi;
        }
    }
}
