package com.integration.project.request;

import com.integration.project.entity.SignInEntity;
import com.integration.project.response.BaseResponse;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Wongerfeng on 2020/8/11.
 */

public interface RequestSignin {

    @POST("employee/login")
    Observable<BaseResponse<SignInEntity>> signinMethod(
            @Body RequestBody requestBody
    );
}
