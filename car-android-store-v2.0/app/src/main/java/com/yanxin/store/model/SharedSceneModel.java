package com.yanxin.store.model;

import com.yanxin.store.base.BaseModel;
import com.yanxin.store.contract.SharedSceneContract;

public class SharedSceneModel extends BaseModel implements SharedSceneContract.SharedSceneModel {
    public static SharedSceneModel getInstance() {
        return new SharedSceneModel();
    }


}
