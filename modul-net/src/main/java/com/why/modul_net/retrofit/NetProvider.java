package com.why.modul_net.retrofit;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * Created by why on 2019-02-19.
 */

public interface NetProvider {
    //添加所有拦截器
    Interceptor[] configInterceptors();

    //配置okhttp参数
    void configHttps(OkHttpClient.Builder builder);

    //设置cookie
    CookieJar configCookie();

    //参数拦截
    RequestHandler configHandler();

    //配置连接时间
    long configConnectTimeoutSecs();

    //配置读取时间
    long configReadTimeoutSecs();

    //配置写入时间
    long configWriteTimeoutSecs();

    //配置log
    boolean configLogEnable();
}
