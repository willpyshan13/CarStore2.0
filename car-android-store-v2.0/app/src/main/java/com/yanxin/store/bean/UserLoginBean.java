package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;

public class UserLoginBean extends BaseBean {
    /**
     * code : 0000
     * msg : success
     * data : {"token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJXQUlDLVVTRVIgZm9yIG5vLXRlcm1pbmFsIiwiYXVkIjoibm8tdGVybWluYWwiLCJpc3MiOiJXQUlDLVVTRVIiLCJsb2dpblRva2VuIjoie1wiZXhwaXJlVGltZVwiOjIxNjAwLFwiZXh0cmFJbmZvXCI6e30sXCJsb2dpblRlcm1pbmFsXCI6XCJuby10ZXJtaW5hbFwiLFwibG9naW5UaW1lXCI6MTYyNjA3NDI3MjAyNyxcInVzZXJNb2JpbGVcIjpcIjEzMDc1ODAxOTMzXCIsXCJ1c2VyTmFtZVwiOlwi54mb5oqA5biIXCIsXCJ1c2VyVHlwZVwiOjIsXCJ1c2VyVXVpZFwiOlwiMTNjNTVjNzg1NjQ2NDFmZjk4MGVkMzYzMGFlYzY4ZjdcIixcInV1aWRcIjpcIjEzYzU1Yzc4NTY0NjQxZmY5ODBlZDM2MzBhZWM2OGY3XCJ9IiwiZXhwIjoxNjI3MzcwMjcyLCJpYXQiOjE2MjYwNzQyNzJ9.DlHXqmMeZJZ_rt1KnmTgKSNvcE_8guhtZfrQswxHtTo","expires_in":21600,"userType":2,"uuid":"13c55c78564641ff980ed3630aec68f7","checkSts":1}
     * success : true
     */

    private String code;
    private String msg;
    private DataBean data;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
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
         * expires_in (integer, optional): 有效期时间 ,
         * token (string, optional): 授权token ,
         * userType (string, optional): 用户类型 1:车主 2：技师 3：店铺 ,
         * uuid (string, optional): 数据uuid
         * isSubAccount
         * checkSts (integer, optional): 审核状态(0:待审核 1:审核通过 2:审核驳回) ,
         */

        private String token;
        private int expires_in;
        private int userType;
        private String uuid;
        private int isSubAccount;
        private int checkSts;

        public int getIsSubAccount() {
            return isSubAccount;
        }

        public void setIsSubAccount(int isSubAccount) {
            this.isSubAccount = isSubAccount;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(int expires_in) {
            this.expires_in = expires_in;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public int getCheckSts() {
            return checkSts;
        }

        public void setCheckSts(int checkSts) {
            this.checkSts = checkSts;
        }
    }
}
