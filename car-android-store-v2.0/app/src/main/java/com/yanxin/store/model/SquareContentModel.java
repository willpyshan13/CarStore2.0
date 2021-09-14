package com.yanxin.store.model;

import com.yanxin.store.base.BaseModel;
import com.yanxin.store.contract.SquareContentContract;

public class SquareContentModel extends BaseModel implements SquareContentContract.SquareContentModel {
    public static SquareContentModel getInstance() {
        return new SquareContentModel();
    }
}
