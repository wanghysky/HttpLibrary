package com.why.modul_net.base;

import com.why.modul_net.exception.ApiException;
import com.why.modul_net.interfaces.IStringSubscriber;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2019-02-24.
 */

public abstract class BaseStringObserver implements Observer<String>, IStringSubscriber {
    public BaseStringObserver() {
    }

    public void onSubscribe(Disposable d) {
        this.doOnSubscribe(d);
    }

    public void onNext(String string) {
        this.doOnNext(string);
    }

    public void onError(Throwable e) {
        String error = ApiException.handleException(e).getMessage();
        this.doOnError(error);
    }

    public void onComplete() {
        this.doOnCompleted();
    }
}
