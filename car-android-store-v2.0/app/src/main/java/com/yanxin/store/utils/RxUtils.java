package com.yanxin.store.utils;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class RxUtils {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public void register(Disposable dp){
        compositeDisposable.add(dp);
    }
    public void unSubscribe(){
        compositeDisposable.dispose();
    }
}
