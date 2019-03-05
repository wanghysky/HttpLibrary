package com.httplibrary.net;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.why.modul_net.exception.ApiException;
import com.why.modul_net.extension.BaseNetProvider;
import com.why.modul_net.retrofit.RequestHandler;
import com.why.modul_net.utils.HttpUtils;
import com.why.modul_net.utils.KLog;
import com.why.modul_net.utils.TimeUtils;

import java.io.IOException;
import java.net.URLDecoder;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetWrokProvider extends BaseNetProvider {

    private String TAG = "NetWrokProvider";

    @Override
    public RequestHandler configHandler() {
        return new HeaderHandler();
    }

    private class HeaderHandler implements RequestHandler {

        @Override
        public Request onBeforeRequest(Request request, Interceptor.Chain chain) throws IOException {
            Request originalRequest = chain.request();

            //获取参数
            String method = originalRequest.method();
            RequestBody body = originalRequest.body();
            RequestBody newBody = body;
            String type = null;
            if (body != null) {
                MediaType mediaType = body.contentType();
                if (mediaType != null) {
                    type = mediaType.toString();
                }
            }

            //取出参数并且在之后拼接公共参数
            KLog.d(TAG, "intercept type:" + type);
            if (method.equalsIgnoreCase("POST")) {
                String json;
                if ("application/x-www-form-urlencoded".equals(type)) {
                    //处理表单
                    json = processBody(body);
                } else if ("text/plain; charset=utf-8".equals(type)) {
                    //处理纯文本
                    json = processText(body);
                } else {
                    json = null;
                }
                String host = originalRequest.url().host();
                KLog.d(TAG, "intercept host: " + host);
                //在此配置自己的加密算法，android
//              json = HttpUtils.encodeBody(json);
                newBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), json);
            }
            return chain.request().newBuilder()
//                    .addHeader("X-Auth-Token", Constant.accessToken)
                    .addHeader("Authorization", "")
                    .addHeader("Platform", "android")
                    .addHeader("Accept", "text/plain; charset=utf-8")
                    .addHeader("access-token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoiMTIzIiwiaWF0IjoxNTUxNzUxMzEwLCJleHAiOjE1NTQzNDMzMTB9.bAIimJ8uMF3kuLlir8FGShK386EyVGRqwChCVDkStwA")
                    .addHeader("app-type", "Android")
                    .method(method, newBody)
                    .build();
        }

        @Override
        public Response onAfterRequest(Response response, Interceptor.Chain chain)
                throws IOException {
            ApiException e = null;
            if (401 == response.code()) {
                throw new ApiException("登录已过期,请重新登录!");
            } else if (403 == response.code()) {
                throw new ApiException("禁止访问!");
            } else if (404 == response.code()) {
                throw new ApiException("链接错误");
            } else if (503 == response.code()) {
                throw new ApiException("服务器升级中!");
            } else if (500 == response.code()) {
                throw new ApiException("服务器内部错误!");
            }
            return response;
        }
    }

    public static String processText(RequestBody body) throws IOException {
        String decodedBody;
        JSONObject jsonObj;
        okio.Buffer buffer = new okio.Buffer();
        body.writeTo(buffer);
        String bodyStr = buffer.readUtf8();
        decodedBody = URLDecoder.decode(bodyStr, "utf-8");
        if (TextUtils.isEmpty(decodedBody)) {
            jsonObj = new JSONObject();
        } else {
            jsonObj = JSONObject.parseObject(decodedBody);
        }
        jsonObj.put("expired_date", TimeUtils.getCurrentTime());
        jsonObj.put("platform","android");
        return jsonObj.toJSONString();
    }

    public static String processBody(RequestBody body) throws IOException {
        String decodedBody;
        okio.Buffer buffer = new okio.Buffer();
        body.writeTo(buffer);
        String bodyStr = buffer.readUtf8();
        decodedBody = URLDecoder.decode(bodyStr, "utf-8");
        //处理表单
        //补充版本号
        decodedBody += "&expired_date=" + TimeUtils.getCurrentTime();
        return HttpUtils.formToJson(decodedBody);
    }

}
