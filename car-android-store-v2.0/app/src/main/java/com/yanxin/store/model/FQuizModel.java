package com.yanxin.store.model;

import com.yanxin.store.base.BaseModel;
import com.yanxin.store.contract.FQuizContract;

public class FQuizModel extends BaseModel implements FQuizContract.FQuizModel {
    public static FQuizModel getInstance() {
        return new FQuizModel();
    }


}
