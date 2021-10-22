package com.integration.project.request;

import com.integration.project.entity.HomeEntity;
import com.integration.project.response.BaseResponse;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Wongerfeng on 2020/8/13.
 */
public interface RequestHomePage {
    @POST("employee/homePage")
    Observable<BaseResponse<HomeEntity>> getHomePageMethod(
            @Header("token") String token,
            @Body RequestBody body
    );
}
