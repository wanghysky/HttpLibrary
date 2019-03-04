package com.why.modul_net.retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2019-02-19.
 */

public interface RequestHandler {

    Request onBeforeRequest(Request request, Interceptor.Chain chain) throws IOException;

    Response onAfterRequest(Response response, Interceptor.Chain chain) throws IOException;
}
