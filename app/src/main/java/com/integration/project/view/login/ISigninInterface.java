package com.integration.project.view.login;

import com.integration.project.entity.SignInEntity;

/**
 * Created by Wongerfeng on 2020/8/10.
 */
public interface ISigninInterface {

    String getPhoneNumber();

    String getPwdOrCode();

    int getSignType();

    void saveUserData(SignInEntity data);

    void showRequestFailedView(String message);

    void showRequestSuccessView(String message);

    void showGetCodeSuccessView(String message);

    void showGetCodeFailedView(String message);
}
