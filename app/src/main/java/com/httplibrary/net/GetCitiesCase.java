package com.httplibrary.net;

import com.why.modul_net.Bean.BaseData;
import com.httplibrary.model.City;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2019-02-24.
 */

public class GetCitiesCase extends UseCase<APIService> {

    public Observable<BaseData<List<City>>> getCities(String url) {
        return ApiClient().getCitiesCase(url);
    }

    public Observable<BaseData<List<City>>> getCities() {
        return ApiClient().getCitiesCase();
    }
}

