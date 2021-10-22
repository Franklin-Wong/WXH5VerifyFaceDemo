package com.integration.project.view.login;

import com.integration.project.entity.SignUpEntity;

import java.io.File;

/**
 * Created by Wongerfeng on 2020/8/10.
 */
public interface ISignUpInterface {

    String getPhoneNumber();

    String getPwd();

    String getPwd2();

    String getCode();

    String getRealName();

    String getIdNumber();

    String getUrlAvatar();

    String getUrlMetal();

    File getPhoto();

    void saveUserData(SignUpEntity data);

    void showRequestFailedView(String message);

    void showRequestSuccessView(String message);

    void showGetCodeSuccessView(String message);

    void showGetCodeFailedView(String message);

    Integer getIdCardType();

    void uploadHeadPicSuccess(String picUrl);

    void uploadEmblemPicSuccess(String picUrl);
}
