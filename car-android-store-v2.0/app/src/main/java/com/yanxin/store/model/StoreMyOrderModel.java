package com.yanxin.store.model;

import com.yanxin.store.base.BaseModel;
import com.yanxin.store.contract.StoreMyOrderContract;

public class StoreMyOrderModel extends BaseModel implements StoreMyOrderContract.StoreMyOrderModel {
    public static StoreMyOrderModel getInstance() {
        return new StoreMyOrderModel();
    }


}
