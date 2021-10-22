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
 * 获取已绑定的银行卡信息
 */
public interface RequestCardInfo {
    @POST("employee/bindBankCardMsg")
    Observable<BaseResponse<CardInfoEntity>> getCardInfoMethod(
            @Header("token") String token,
            @Header("id") Integer id,
            @Body RequestBody body
            );
}
