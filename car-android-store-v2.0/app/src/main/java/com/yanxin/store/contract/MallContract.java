package com.yanxin.store.contract;

import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.base.IBaseActivity;
import com.yanxin.store.base.IBaseFragment;
import com.yanxin.store.base.IBaseModel;

public interface MallContract {
    abstract class MallPresenter extends BasePresenter<MallModel, MallView> {

    }

    interface MallModel extends IBaseModel {

    }

    interface MallView extends IBaseFragment {

    }
}
