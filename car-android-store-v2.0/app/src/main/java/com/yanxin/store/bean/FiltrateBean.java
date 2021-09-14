package com.yanxin.store.bean;


import com.yanxin.store.base.BaseBean;

/**
 * @author zhouz
 * @date 2021/1/18
 */
public class FiltrateBean extends BaseBean {

    private String name;
    private String uuid;
    private int type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
