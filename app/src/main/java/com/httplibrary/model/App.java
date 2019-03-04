package com.httplibrary.model;

import android.app.Application;

import com.httplibrary.net.NetWrokProvider;
import com.why.modul_net.retrofit.NetMgr;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NetMgr.getInstance().registerProvider(new NetWrokProvider());
    }
}
