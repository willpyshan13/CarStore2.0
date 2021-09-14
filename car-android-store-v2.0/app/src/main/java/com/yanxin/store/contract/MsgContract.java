package com.yanxin.store.contract;

import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.base.IBaseActivity;
import com.yanxin.store.base.IBaseModel;

public interface MsgContract {
    abstract class MsgPresenter extends BasePresenter<MsgModel, MsgView> {

    }

    interface MsgModel extends IBaseModel {

    }

    interface MsgView extends IBaseActivity {

    }
}
