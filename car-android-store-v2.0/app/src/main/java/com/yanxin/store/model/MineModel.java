package com.yanxin.store.model;

import com.yanxin.store.base.BaseModel;
import com.yanxin.store.contract.MineContract;

public class MineModel extends BaseModel implements MineContract.MineModel {
    public static MineModel getInstance() {
        return new MineModel();
    }


}
