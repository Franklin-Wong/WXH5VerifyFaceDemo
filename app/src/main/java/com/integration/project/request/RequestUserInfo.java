package com.integration.project.request;

import com.integration.project.entity.UserInfoEntity;
import com.integration.project.response.BaseResponse;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Wongerfeng on 2020/8/13.
 */
public interface RequestUserInfo {
    @POST("employee/information")
    Observable<BaseResponse<UserInfoEntity>> getUserInfoMethod(
            @Header("token") String token,
            @Body RequestBody body
            );
}
