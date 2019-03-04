package com.why.modul_net.base;

import com.why.modul_net.exception.ApiException;
import com.why.modul_net.interfaces.ISubscriber;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by why on 2019-02-24.
 */

public abstract class BaseObserver<T> implements Observer<T>, ISubscriber<T> {
    public BaseObserver() {
    }

    public void onSubscribe(@NonNull Disposable d) {
        this.doOnSubscribe(d);
    }

    public void onNext(@NonNull T t) {
        this.doOnNext(t);
    }

    public void onError(@NonNull Throwable e) {
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
