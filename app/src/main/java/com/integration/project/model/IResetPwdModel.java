package com.integration.project.model;

import com.integration.project.listener.OnRequestListener;

/**
 * Created by Wongerfeng on 2020/8/13.
 */
public interface IResetPwdModel extends BaseModelInterface{

    void getMessageCode(String phoneNumber, OnRequestListener<String> listener);

    void resetPwd(String phoneNumber,String pwd, String pwd2, String code, OnRequestListener<String> listener );
}
