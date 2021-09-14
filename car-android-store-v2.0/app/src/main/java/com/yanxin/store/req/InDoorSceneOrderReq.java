package com.yanxin.store.req;

import java.util.List;

public class InDoorSceneOrderReq {

    /**
     * describe : string
     * imageList : ["string"]
     * repairSummary : string
     * sceneOrderUuid : string
     * type : 0
     */

    private String describe;
    private List<String> imageList;
    private String repairSummary;
    private String sceneOrderUuid;
    private int type;

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public String getRepairSummary() {
        return repairSummary;
    }

    public void setRepairSummary(String repairSummary) {
        this.repairSummary = repairSummary;
    }

    public String getSceneOrderUuid() {
        return sceneOrderUuid;
    }

    public void setSceneOrderUuid(String sceneOrderUuid) {
        this.sceneOrderUuid = sceneOrderUuid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
