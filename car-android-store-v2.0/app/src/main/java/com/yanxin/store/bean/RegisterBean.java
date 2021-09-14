package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;

public class RegisterBean extends BaseBean {

    /**
     * code : 0000
     * msg : success
     * data : f85741bed3324d638541bbec1aacc5da
     * success : true
     */

    private String code;
    private String msg;
    private String data;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
