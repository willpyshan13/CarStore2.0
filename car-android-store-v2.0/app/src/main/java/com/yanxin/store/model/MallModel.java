package com.yanxin.store.model;

import com.yanxin.store.base.BaseModel;
import com.yanxin.store.contract.MallContract;

public class MallModel extends BaseModel implements MallContract.MallModel {
    public static MallModel getInstance() {
        return new MallModel();
    }


}
