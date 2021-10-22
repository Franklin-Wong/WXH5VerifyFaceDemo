package com.integration.project.model;

import com.integration.project.entity.SignInEntity;
import com.integration.project.listener.OnRequestListener;

/**
 * Created by Wongerfeng on 2020/8/10.
 */
public interface ISigninModel {

    /**
     * 请求登录
     */
    void signin(String phoneNumber, String pwdOrCode, int type,String contextId, OnRequestListener<SignInEntity> listener);

    void getCode(String phoneNumber,OnRequestListener<String> listener);

}
