package com.yanxin.store.contract;

import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.base.IBaseActivity;
import com.yanxin.store.base.IBaseModel;

public interface SharedSceneContract {
    abstract class SharedScenePresenter extends BasePresenter<SharedSceneModel, SharedSceneView> {

    }

    interface SharedSceneModel extends IBaseModel {

    }

    interface SharedSceneView extends IBaseActivity {

    }
}
