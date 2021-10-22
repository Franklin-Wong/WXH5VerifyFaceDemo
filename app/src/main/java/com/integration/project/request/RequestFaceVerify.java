package com.integration.project.request;

import com.integration.project.entity.FaceResultEntity;
import com.integration.project.response.BaseResponse;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Wongerfeng on 2020/9/2.
 */
public interface RequestFaceVerify {

    @POST("employee/accountFace")
    Observable<BaseResponse<FaceResultEntity>> getFaceResult(
            @Body RequestBody body
            );
}
