package com.yanxin.store.model.mine;

import com.yanxin.store.base.BaseModel;
import com.yanxin.store.contract.AskEveryoneContract;
import com.yanxin.store.contract.mine.StoreOrderReportContract;

public class StoreOrderReportModel extends BaseModel implements StoreOrderReportContract.StoreOrderReportModel {
    public static StoreOrderReportModel getInstance() {
        return new StoreOrderReportModel();
    }


}
