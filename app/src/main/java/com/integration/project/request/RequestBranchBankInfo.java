package com.integration.project.request;

import com.integration.project.entity.BankBranchEntity;
import com.integration.project.response.BaseResponse;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Wongerfeng on 2020/8/13.
 * 查询银行支行名称信息
 */
public interface RequestBranchBankInfo {
    @POST("send/getBankMsg")
    Observable<BaseResponse<List<BankBranchEntity.BranchBank>>> getBankCardMethod(
            @Body RequestBody body
            );

}
