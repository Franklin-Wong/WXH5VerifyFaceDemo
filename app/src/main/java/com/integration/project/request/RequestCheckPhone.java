package com.integration.project.request;

import com.integration.project.response.BaseResponse;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Wongerfeng on 2020/9/9.
 */
public interface RequestCheckPhone {

    @POST("send/checkPhone")
    Observable<BaseResponse> checkPhoneNumberMethod(
      @Body RequestBody body
    );
}
