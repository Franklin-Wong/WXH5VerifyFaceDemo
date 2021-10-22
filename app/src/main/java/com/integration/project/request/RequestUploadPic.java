package com.integration.project.request;

import com.integration.project.response.BaseResponse;

import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by Wongerfeng on 2020/8/12.
 */
public interface RequestUploadPic {
    /**
     * 上传图片
     * @param myfile
     * @param idCardType
     * @param name
     * @param idCardNO
     * @return
     */
    @Multipart
    @POST("fileupload/upload")
    Observable<BaseResponse<String>> uploadPicMethod(
                @Part MultipartBody.Part myfile,
                @Part("idCardType") Integer idCardType,
                @Part("name") String name,
                @Part("idCardNO") String idCardNO
            );
}
