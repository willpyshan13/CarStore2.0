package com.yanxin.store.contract;

import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.base.IBaseActivity;
import com.yanxin.store.base.IBaseModel;

public interface MineContract {
    abstract class MinePresenter extends BasePresenter<MineModel, MineView> {

    }

    interface MineModel extends IBaseModel {

    }

    interface MineView extends IBaseActivity {

    }
}
