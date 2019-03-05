package com.httplibrary.net.api;

import com.httplibrary.model.BookBean;
import com.why.modul_net.Bean.BaseData;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2019-02-24.
 */

public interface APIService {


    @GET("/api/books")
    Observable<BaseData<List<BookBean>>> books( @Query("type") String type,
                                                @Query("major") String major, @Query("page") int page);

    /**
     * 用户注册
     *
     * @return
     */
    @POST("/api/user")
    @FormUrlEncoded
    Observable<BaseData<String>> register(@Field("name") String username, @Field("password") String password);
}
