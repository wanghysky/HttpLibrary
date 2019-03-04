package com.why.modul_net.exception;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.io.IOException;
import java.io.NotSerializableException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import javax.net.ssl.SSLHandshakeException;

import retrofit2.HttpException;

/**
 * 描述：捕获异常
 * Created by why on 2019-02-24.
 */

public class ApiException extends IOException {
    public ApiException(String message) {
        super(message);
    }

    private int code;
    private String message;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
        this.message = throwable.getMessage();
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public static ApiException handleException(Throwable e) {
        ApiException ex;
        if(e instanceof HttpException) {
            HttpException httpException = (HttpException)e;
            ex = new ApiException(httpException, httpException.code());

            try {
                ex.message = httpException.response().errorBody().string();
            } catch (IOException var4) {
                var4.printStackTrace();
                ex.message = var4.getMessage();
            }

            return ex;
        } else if(e instanceof SocketTimeoutException) {
            ex = new ApiException(e, 1001);
            ex.message = "网络连接超时，请检查您的网络状态，稍后重试！";
            return ex;
        } else if(e instanceof ConnectException) {
            ex = new ApiException(e, 1001);
            ex.message = "网络连接异常，请检查您的网络状态，稍后重试！";
            return ex;
        } else if(e instanceof ConnectTimeoutException) {
            ex = new ApiException(e, 1001);
            ex.message = "网络连接超时，请检查您的网络状态，稍后重试！";
            return ex;
        } else if(e instanceof UnknownHostException) {
            ex = new ApiException(e, 1001);
            ex.message = "网络连接异常，请检查您的网络状态，稍后重试！";
            return ex;
        } else if(e instanceof NullPointerException) {
            ex = new ApiException(e, 1002);
            ex.message = "空指针异常";
            return ex;
        } else if(e instanceof SSLHandshakeException) {
            ex = new ApiException(e, 1003);
            ex.message = "证书验证失败";
            return ex;
        } else if(e instanceof ClassCastException) {
            ex = new ApiException(e, 1004);
            ex.message = "类型转换错误";
            return ex;
        } else if(!(e instanceof JsonParseException) && !(e instanceof JSONException) && !(e instanceof JsonSyntaxException) && !(e instanceof JsonSerializer) && !(e instanceof NotSerializableException) && !(e instanceof ParseException)) {
            if(e instanceof IllegalStateException) {
                ex = new ApiException(e, 1006);
                ex.message = e.getMessage();
                return ex;
            } else {
                ex = new ApiException(e, 1000);
                ex.message = "未知错误";
                return ex;
            }
        } else {
            ex = new ApiException(e, 1005);
            ex.message = "解析错误";
            return ex;
        }
    }

    public static class ERROR {
        public static final int UNKNOWN = 1000;
        public static final int TIMEOUT_ERROR = 1001;
        public static final int NULL_POINTER_EXCEPTION = 1002;
        public static final int SSL_ERROR = 1003;
        public static final int CAST_ERROR = 1004;
        public static final int PARSE_ERROR = 1005;
        public static final int ILLEGAL_STATE_ERROR = 1006;

        public ERROR() {
        }
    }
}
