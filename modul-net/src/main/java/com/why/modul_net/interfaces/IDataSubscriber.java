package com.why.modul_net.interfaces;

import com.why.modul_net.Bean.BaseData;

import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2019-02-24.
 */

public interface IDataSubscriber<T> {
    void doOnSubscribe(Disposable var1);

    void doOnError(String var1);

    void doOnNext(BaseData<T> var1);

    void doOnCompleted();
}
