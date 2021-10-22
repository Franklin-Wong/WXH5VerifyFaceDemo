package com.integration.project.request;

import com.integration.project.entity.UnsignedContractEntity;
import com.integration.project.response.BaseResponse;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Wongerfeng on 2020/8/13.
 */
public interface RequestUnsignedContract {
    @POST("employee/createContract")
    Observable<BaseResponse<UnsignedContractEntity>> getUnsignedContractInfo(
            @Header("token") String token,
            @Body RequestBody body
            );
}
