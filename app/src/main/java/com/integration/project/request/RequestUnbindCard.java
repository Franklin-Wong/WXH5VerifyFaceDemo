package com.integration.project.request;

import com.integration.project.response.BaseResponse;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Wongerfeng on 2020/8/13.
 *
 * 解除绑定银行卡
 */
public interface RequestUnbindCard {
    @POST("employee/unbindBankCard")
    Observable<BaseResponse> unbindCardMethod(
            @Header("token") String token,
            @Header("id") Integer id,
            @Body RequestBody body
    );
}
