package com.yanxin.store.contract;

import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.base.IBaseActivity;
import com.yanxin.store.base.IBaseModel;
import com.yanxin.store.bean.UserLoginBean;
import com.yanxin.store.bean.DefaultBean;
import com.yanxin.store.req.UserLoginReq;

import io.reactivex.Observable;

public interface LoginContract {
    abstract class LoginPresenter extends BasePresenter<LoginModel, LoginView> {
        public abstract void login(UserLoginReq userLoginReq);

        public abstract void getVerificationCode(String mobile);

    }

    interface LoginModel extends IBaseModel {
        Observable<UserLoginBean> login(UserLoginReq userLoginReq);

        Observable<DefaultBean> getVerificationCode(String mobile);

    }

    interface LoginView extends IBaseActivity {
        void verificationSuccess(String msg);

        void loginSuccess(UserLoginBean.DataBean dataBean);

        void failed(String errorMsg);
    }
}
