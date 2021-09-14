package com.yanxin.store.model;

import com.yanxin.store.base.BaseModel;
import com.yanxin.store.contract.MsgContract;

public class MsgModel extends BaseModel implements MsgContract.MsgModel {
    public static MsgModel getInstance() {
        return new MsgModel();
    }


}
