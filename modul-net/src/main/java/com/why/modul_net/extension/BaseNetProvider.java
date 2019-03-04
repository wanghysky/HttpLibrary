package com.why.modul_net.extension;

import com.why.modul_net.BuildConfig;
import com.why.modul_net.retrofit.NetProvider;
import com.why.modul_net.retrofit.RequestHandler;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2019-02-24.
 */

public abstract class BaseNetProvider implements NetProvider {

    private static final long CONNECT_TIME_OUT = 30;
    private static final long READ_TIME_OUT = 180;
    private static final long WRITE_TIME_OUT = 30;

    private static final String TAG = "paramsInterceptor";


    @Override
    public Interceptor[] configInterceptors() {
        return null;
    }

    @Override
    public void configHttps(OkHttpClient.Builder builder) {

    }

    @Override
    public CookieJar configCookie() {
        return null;
    }

    @Override
    public abstract RequestHandler configHandler() ;

    @Override
    public long configConnectTimeoutSecs() {
        return CONNECT_TIME_OUT;
    }

    @Override
    public long configReadTimeoutSecs() {
        return READ_TIME_OUT;
    }

    @Override
    public long configWriteTimeoutSecs() {
        return WRITE_TIME_OUT;
    }

    @Override
    public boolean configLogEnable() {
        return BuildConfig.DEBUG;
    }



}
