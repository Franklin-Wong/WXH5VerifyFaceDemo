package com.integration.project.request;


import com.integration.project.entity.SignUpEntity;
import com.integration.project.entity.UploadPicEntity;
import com.integration.project.response.BaseResponse;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Wongerfeng on 2020/8/11.
 */
public interface RequestSignUp {

    @POST("employee/register")
    Observable<BaseResponse<SignUpEntity>> signUpMethod(
            @Body RequestBody requestBody
    );

    @POST("fileupload/upload")
    @Multipart
    Observable<BaseResponse<UploadPicEntity>> uploadPicMethod(
             @Body RequestBody requestBody
            );

}
