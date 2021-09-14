package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;

public class PersonBean extends BaseBean {

    /**
     * userName : 测试人员
     * idNum :
     * idType :
     * email :
     * mobile : 13585858585
     * personType : 504
     * renType : 零件
     */

    private String userName;
    private String uuid;
    private String idNum;
    private String idType;
    private String email;
    private String mobile;
    private String personType;
    private String renType;
    private boolean defaultPerson;

    public boolean isDefaultPerson() {
        return defaultPerson;
    }

    public void setDefaultPerson(boolean defaultPerson) {
        this.defaultPerson = defaultPerson;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public String getRenType() {
        return renType;
    }

    public void setRenType(String renType) {
        this.renType = renType;
    }
}
