package com.yanxin.store.base;

import androidx.annotation.Nullable;

import com.yanxin.store.utils.RxUtils;


public abstract class BasePresenter<M,V> {
    protected M mIModel;
    protected V mIView;
    protected RxUtils rxUtils = new RxUtils();
    /**
     * 绑定IModel和IView的引用
     * @param v
     */
    public void attachMVP(@Nullable V v) {
        this.mIModel = getModel();
        this.mIView = v;
        this.onStart();
    }

    /**
     * 解绑IModel和IView的引用
     */
    public void detachMVP() {
        this.rxUtils.unSubscribe();
        this.mIModel = null;
        this.mIView = null;
    }



    /**
     * 返回presenter想持有的Model引用
     *
     * @return
     */
    protected abstract M getModel();

    /**
     * IView和IModel绑定完成立即执行
     * <p>
     * 实现类实现绑定完成后的逻辑,例如数据初始化等,界面初始化, 更新等
     */
    public abstract void onStart();
}
