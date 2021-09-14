package com.yanxin.store.base;

public interface IBaseActivity extends IBaseView {

    /**
     * 跳转页面
     */
    void startActivity();

    void failed(String msg);
}
