package com.httplibrary.net;

import com.why.modul_net.retrofit.NetMgr;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * Created by Administrator on 2019-02-24.
 */

public abstract class UseCase<T> {
    //用于分页请求


    protected T ApiClient() {
        return NetMgr.getInstance().getRetrofit(Api.BASE_URL).create(getType());
    }

    private Class<T> getType() {
        Class<T> entityClass = null;
        Type t = getClass().getGenericSuperclass();
        Type[] p = ((ParameterizedType) t).getActualTypeArguments();
        entityClass = (Class<T>) p[0];
        return entityClass;
    }
}

