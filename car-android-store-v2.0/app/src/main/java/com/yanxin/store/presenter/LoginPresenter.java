package com.yanxin.store.presenter;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.LoginContract;
import com.yanxin.store.model.LoginModel;
import com.yanxin.store.req.UserLoginReq;

public class LoginPresenter extends LoginContract.LoginPresenter {

    public static BasePresenter newInstance() {
        return new LoginPresenter();
    }


    @Override
    protected LoginContract.LoginModel getModel() {
        return LoginModel.getInstance();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void login(UserLoginReq userLoginReq) {
        rxUtils.register(mIModel.login(userLoginReq)
                .subscribe(userLoginBean -> {
                    if (userLoginBean.isSuccess()) {
                        mIView.loginSuccess(userLoginBean.getData());
                    }else{
                        mIView.failed(userLoginBean.getMsg());
                    }
                }, throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void getVerificationCode(String mobile) {
        rxUtils.register(mIModel.getVerificationCode(mobile)
                .subscribe(verificationCodeBean -> {
                            if (verificationCodeBean.isSuccess()) {
                                mIView.verificationSuccess("验证码发送成功");
                            }else{
                                mIView.failed(verificationCodeBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }
}
