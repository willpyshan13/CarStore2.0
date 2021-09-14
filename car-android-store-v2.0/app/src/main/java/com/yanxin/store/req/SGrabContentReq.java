package com.yanxin.store.req;

import java.util.List;

public class SGrabContentReq {

    /**
     * orderSts : [0]
     * pageNum : 1
     * pageSize : 10
     * storeSameCity : 0
     * storeUserUuid : string
     * uuid : string
     * vehicleUserUuid : string
     */

    private List<Integer> orderSts;
    private int pageNum = 1;
    private int pageSize = 50;
    private Integer storeSameCity;
    private String storeUserUuid;
    private String uuid;
    private String vehicleUserUuid;

    public List<Integer> getOrderSts() {
        return orderSts;
    }

    public void setOrderSts(List<Integer> orderSts) {
        this.orderSts = orderSts;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStoreSameCity() {
        return storeSameCity;
    }

    public void setStoreSameCity(Integer storeSameCity) {
        this.storeSameCity = storeSameCity;
    }

    public String getStoreUserUuid() {
        return storeUserUuid;
    }

    public void setStoreUserUuid(String storeUserUuid) {
        this.storeUserUuid = storeUserUuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getVehicleUserUuid() {
        return vehicleUserUuid;
    }

    public void setVehicleUserUuid(String vehicleUserUuid) {
        this.vehicleUserUuid = vehicleUserUuid;
    }
}
