package com.yanxin.store.model;

import com.yanxin.store.base.BaseModel;
import com.yanxin.store.contract.ServingContract;

public class ServingModel extends BaseModel implements ServingContract.ServingModel {
    public static ServingModel getInstance() {
        return new ServingModel();
    }


}
