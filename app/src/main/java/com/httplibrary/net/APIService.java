package com.httplibrary.net;

import com.httplibrary.model.City;
import com.why.modul_net.Bean.BaseData;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2019-02-24.
 */

public interface APIService {

    @Streaming
    @GET
    Observable<BaseData<List<City>>> getCitiesCase(@Url String url);

    @GET("api/china/")
    Observable<BaseData<List<City>>> getCitiesCase();
}
