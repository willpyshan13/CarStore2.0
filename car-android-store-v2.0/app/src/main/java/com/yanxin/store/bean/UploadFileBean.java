package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;

public class UploadFileBean extends BaseBean {

    /**
     * code : 0000
     * msg : success
     * data : https://car2021.oss-cn-beijing.aliyuncs.com/other/2021-07-13/f71b75317e864326a6fcbf795f7199cd.png
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
