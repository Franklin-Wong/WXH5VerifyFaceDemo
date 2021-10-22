package com.integration.project.request;

import com.integration.project.entity.IncomeDetailEntity;
import com.integration.project.response.BaseResponse;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Wongerfeng on 2020/8/13.
 */
public interface RequestIncomeDetail {
    @POST("employee/accountBalance")
    Observable<BaseResponse<IncomeDetailEntity>> getIncomeDetail(
            @Header("token") String token,
            @Body RequestBody body
            );
}
