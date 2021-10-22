package com.integration.project.model;

import com.integration.project.entity.SignUpEntity;
import com.integration.project.listener.OnRequestListener;
import com.integration.project.response.BaseResponse;

import java.io.File;

/**
 * Created by Wongerfeng on 2020/8/11.
 */
public interface ISignUpModel {

    void checkPhone(String phoneNumber,  OnRequestListener<BaseResponse> listener);

    void signUp(String phoneNumber, String code, String pwd, String pwd2, String name, String IdNumber,
                String urlAvatar, String urlMetal, String contextId,OnRequestListener<SignUpEntity> listener);

    void uploadAvatar(File myfile, Integer idCardType, String name, String idCardNO,
                      OnRequestListener<BaseResponse<String>> listener);

    void getCode(String phoneNumber,OnRequestListener<String> listener);

}
