package com.will.habit.base

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

/**
 * @author will
 *
 */
open class BaseModel<T> : BaseScopeModel() {
    private var mView: T? = null

    /**
     * Desc:管理BaseView层
     */
    fun setVm(v: T) {
        mView = v
    }

}