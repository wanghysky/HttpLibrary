package com.why.modul_net.interfaces;

import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2019-02-24.
 */

public interface IStringSubscriber {

    void doOnSubscribe(Disposable var1);

    void doOnError(String var1);

    void doOnNext(String var1);

    void doOnCompleted();
}
