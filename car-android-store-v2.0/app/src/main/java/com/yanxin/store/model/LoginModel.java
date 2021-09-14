package com.yanxin.store.model;

import com.yanxin.store.MyApplication;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseModel;
import com.yanxin.store.bean.UserLoginBean;
import com.yanxin.store.bean.DefaultBean;
import com.yanxin.store.contract.LoginContract;
import com.yanxin.store.req.UserLoginReq;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;

import io.reactivex.Observable;

public class LoginModel extends BaseModel implements LoginContract.LoginModel {
    public static LoginModel getInstance() {
        return new LoginModel();
    }


    @Override
    public Observable<UserLoginBean> login(UserLoginReq userLoginReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .userLogin(userLoginReq)
                .compose(RxHelper.rxSchedulerHelper())
                .map(bean -> bean);
    }

    @Override
    public Observable<DefaultBean> getVerificationCode(String mobile) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .getVerificationCode(mobile)
                .compose(RxHelper.rxSchedulerHelper())
                .map(bean -> bean);
    }
}
