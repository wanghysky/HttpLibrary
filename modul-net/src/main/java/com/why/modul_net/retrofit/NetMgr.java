package com.why.modul_net.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.why.modul_net.config.Constant;
import com.why.modul_net.interceptor.converter.FastJsonConverterFactory;
import com.why.modul_net.interceptor.converter.ToStringConverterFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 初始化retrofit，okhttp
 * Created by why on 2019-02-19.
 */

public class NetMgr {
    private NetProvider sProvider = null;
    private Map<String, NetProvider> providerMap = new HashMap<>();
    private Map<String, Retrofit> retrofitMap = new HashMap<>();
    private Map<String, OkHttpClient> clientMap = new HashMap<>();

    public static NetMgr netMgr = null;

    public static NetMgr getInstance(){
        if(netMgr == null){
            synchronized (NetMgr.class){
                if(netMgr == null){
                    netMgr = new NetMgr();
                }
            }
        }
        return netMgr;
    }

    public <S> S get(String baseUrl, Class<S> service) {
        return getInstance().getRetrofit(baseUrl).create(service);
    }

    public void registerProvider(NetProvider provider) {
        this.sProvider = provider;
    }

    public void registerProvider(String baseUrl, NetProvider provider) {
        getInstance().providerMap.put(baseUrl, provider);
    }

    public NetProvider getCommonProvider() {
        return sProvider;
    }

    public void clearCache() {
        getInstance().retrofitMap.clear();
        getInstance().clientMap.clear();
    }

    public Retrofit getRetrofit(String baseUrl) {
        return getRetrofit(baseUrl, null);
    }

    public Retrofit getRetrofit(String baseUrl, NetProvider provider) {
        if (checkBaseUrl(baseUrl)) {
            throw new IllegalStateException("baseUrl can not be null");
        }
//        if (retrofitMap.get(baseUrl) != null) {
//            return retrofitMap.get(baseUrl);
//        }
        if(retrofitMap != null && retrofitMap.containsKey(baseUrl)){
            return retrofitMap.get(baseUrl);
        }

        if (provider == null) {
            provider = providerMap.get(baseUrl);
            if (provider == null) {
                provider = sProvider;
            }
        }
        checkProvider(provider);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getClient(baseUrl, provider))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(new ToStringConverterFactory())
                .addConverterFactory(FastJsonConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        retrofitMap.put(baseUrl, retrofit);
        providerMap.put(baseUrl, provider);

        return retrofit;
    }


    private OkHttpClient getClient(String baseUrl, NetProvider provider) {
        if (checkBaseUrl(baseUrl)) {
            throw new IllegalStateException("baseUrl can not be null");
        }
        if (clientMap.get(baseUrl) != null) {
            return clientMap.get(baseUrl);
        }

        checkProvider(provider);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        setDefaultTimeout(provider,builder);

        CookieJar cookieJar = provider.configCookie();
        if (cookieJar != null) {
            builder.cookieJar(cookieJar);
        }
        provider.configHttps(builder);

        RequestHandler handler = provider.configHandler();
        if (handler != null) {
            builder.addInterceptor(new NetInterceptor(handler));
        }


        Interceptor[] interceptors = provider.configInterceptors();
        if (!checkInterceptor(interceptors)) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }

        if (provider.configLogEnable()) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }

        OkHttpClient client = builder.build();
        clientMap.put(baseUrl, client);
        providerMap.put(baseUrl, provider);

        return client;
    }

    /**
     * 设置超时和重试
     *
     * @param builder
     */
    private void setDefaultTimeout(NetProvider provider,OkHttpClient.Builder builder) {
        //设置超时
        builder.connectTimeout(provider.configConnectTimeoutSecs() != 0
                ? provider.configConnectTimeoutSecs()
                : Constant.default_connectTimeoutMills, TimeUnit.SECONDS);
        builder.readTimeout(provider.configReadTimeoutSecs() != 0
                ? provider.configReadTimeoutSecs() :  Constant.default_readTimeoutMills, TimeUnit.SECONDS);

        builder.writeTimeout(provider.configWriteTimeoutSecs() != 0
                ? provider.configReadTimeoutSecs() : Constant.default_readTimeoutMills, TimeUnit.SECONDS);

        //错误重连
        builder.retryOnConnectionFailure(true);
        //重定向
        builder.followRedirects(true);
        builder.followSslRedirects(true);
    }


    private boolean checkBaseUrl(String baseUrl) {
        return baseUrl == null || baseUrl.isEmpty();
    }

    private boolean checkInterceptor(Interceptor[] interceptors) {
        return interceptors == null || interceptors.length == 0;
    }

    private void checkProvider(NetProvider provider) {
        if (provider == null) {
            throw new IllegalStateException("must register provider first");
        }
    }

    public Map<String, Retrofit> getRetrofitMap() {
        return retrofitMap;
    }

    public Map<String, OkHttpClient> getClientMap() {
        return clientMap;
    }

}
