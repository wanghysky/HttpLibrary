package com.why.modul_net.base;

import com.why.modul_net.Bean.BaseData;
import com.why.modul_net.exception.ApiException;
import com.why.modul_net.interfaces.IDataSubscriber;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2019-02-24.
 */

public abstract class BaseDataObserver<T> implements Observer<BaseData<T>>, IDataSubscriber<T> {
    public BaseDataObserver() {
    }

    public void onSubscribe(Disposable d) {
        this.doOnSubscribe(d);
    }

    public void onNext(BaseData<T> baseData) {
        this.doOnNext(baseData);
    }

    public void onError(Throwable e) {
        String error = ApiException.handleException(e).getMessage();
        this.setError(error);
    }

    public void onComplete() {
        this.doOnCompleted();
    }

    private void setError(String errorMsg) {
        this.doOnError(errorMsg);
    }
}

