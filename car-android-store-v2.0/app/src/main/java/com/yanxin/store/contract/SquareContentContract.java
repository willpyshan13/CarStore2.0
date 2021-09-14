package com.yanxin.store.contract;

import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.base.IBaseFragment;
import com.yanxin.store.base.IBaseModel;
import com.yanxin.store.bean.RushAnswerBean;
import com.yanxin.store.req.DefaultReq;

import java.util.ArrayList;

import io.reactivex.Observable;

public interface SquareContentContract {
    abstract class SquareContentPresenter extends BasePresenter<SquareContentModel, SquareContentView> {
        //public abstract void getOrderSnapUp()

    }

    interface SquareContentModel extends IBaseModel {

    }

    interface SquareContentView extends IBaseFragment {

        void failed(String msg);
    }
}
