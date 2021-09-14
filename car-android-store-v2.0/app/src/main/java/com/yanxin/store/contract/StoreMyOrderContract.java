package com.yanxin.store.contract;

import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.base.IBaseActivity;
import com.yanxin.store.base.IBaseModel;

public interface StoreMyOrderContract {
    abstract class StoreMyOrderPresenter extends BasePresenter<StoreMyOrderModel, StoreMyOrderView> {

    }

    interface StoreMyOrderModel extends IBaseModel {

    }

    interface StoreMyOrderView extends IBaseActivity {

    }
}
