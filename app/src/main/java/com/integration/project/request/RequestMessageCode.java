package com.integration.project.request;

import com.integration.project.response.BaseResponse;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Wongerfeng on 2020/8/11.
 */
public interface RequestMessageCode {
    @POST("send/sendVerificationCode")
    Observable<BaseResponse> getMessageCodeMethod(
            @Body RequestBody requestBody
            );
}
