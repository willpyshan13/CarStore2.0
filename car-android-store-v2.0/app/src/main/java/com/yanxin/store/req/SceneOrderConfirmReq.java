package com.yanxin.store.req;

public class SceneOrderConfirmReq {

    /**
     * sceneOrderUuid : string
     * type : 0
     */

    private String sceneOrderUuid;
    private int type;

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
