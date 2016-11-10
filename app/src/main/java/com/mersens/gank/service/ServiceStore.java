package com.mersens.gank.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by Mersens on 2016/11/8.
 */

public interface ServiceStore {
    @GET("{type}/{pageSize}/{pageIndex}")
    Call<ResponseBody> getInfo(@Path("type") String type,@Path("pageSize") String pageSize,@Path("pageIndex") String pageIndex);

    @GET
    Call<ResponseBody> download(@Url String fileUrl);

    @GET("https://api.github.com/users/{user}")
    Call<ResponseBody> getUserInfo(@Path("user") String user);
}
