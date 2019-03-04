package com.why.modul_net.Bean;

/**
 * Created by Administrator on 2019-02-24.
 */

public class BaseData<T>  {
    private int code ;
    private String msg;
    private T data;

    public BaseData() {
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String toString() {
        return "BaseData{code=" + this.code + ", msg='" + this.msg + '\'' + ", data=" + this.data+'}';
    }
}
