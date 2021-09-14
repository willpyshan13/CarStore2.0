package com.yanxin.store.contract;

import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.base.IBaseActivity;
import com.yanxin.store.base.IBaseModel;

public interface FQuizContract {
    abstract class FQuizPresenter extends BasePresenter<FQuizModel, FQuizView> {

    }

    interface FQuizModel extends IBaseModel {

    }

    interface FQuizView extends IBaseActivity {

    }
}
