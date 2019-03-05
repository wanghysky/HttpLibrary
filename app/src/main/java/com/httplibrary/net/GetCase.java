package com.httplibrary.net;

import com.httplibrary.model.BookBean;
import com.httplibrary.net.api.APIService;
import com.why.modul_net.Bean.BaseData;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2019-02-24.
 */

public class GetCase extends UseCase<APIService> {

    public Observable<BaseData<List<BookBean>>> books() {
        return ApiClient().books("hot","玄幻",1);
    }

    public Observable<BaseData<String>> register() {
        return ApiClient().register("123","123456");
    }
}

