package com.integration.project.request;

import com.integration.project.entity.CardInfoEntity;
import com.integration.project.response.BaseResponse;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Wongerfeng on 2020/8/13.
 */
public interface RequestBindCard {
    @POST("employee/bindBankCard")
    Observable<BaseResponse<CardInfoEntity>> bindCardMethod(
            @Header("token") String token,
            @Header("employeeId") Integer employeeId,
            @Body RequestBody body
            );
}
