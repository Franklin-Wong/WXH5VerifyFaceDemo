package com.integration.project.view.login;

import com.integration.project.view.IBaseViewInterface;

/**
 * Created by Wongerfeng on 2020/8/13.
 */
public interface IResetPwdInterface extends IBaseViewInterface {

    String getPhoneNumber();

    String getCode();

    String getPwd();

    String getPwd2();

    void showGetCodeSuccessView(String message);

    void showGetCodeFailedView(String message);

}
