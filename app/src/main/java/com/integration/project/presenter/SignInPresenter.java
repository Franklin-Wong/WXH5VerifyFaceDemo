package com.integration.project.presenter;

import com.integration.project.entity.SignInEntity;
import com.integration.project.listener.OnRequestListener;
import com.integration.project.model.ISigninModel;
import com.integration.project.model.SigninModel;
import com.integration.project.utils.Constants;
import com.integration.project.view.login.ISigninInterface;

/**
 * Created by Wongerfeng on 2020/8/10.
 */
public class SignInPresenter extends BasePresenter<ISigninInterface> {

    private ISigninModel mSigninModel;
    private ISigninInterface mSigninInterface;

    public SignInPresenter(ISigninInterface signinInterface) {
        mSigninModel = new SigninModel();
        mSigninInterface = signinInterface;
    }

    public void signIn() {

        mSigninModel.signin(mSigninInterface.getPhoneNumber(), mSigninInterface.getPwdOrCode(),
                mSigninInterface.getSignType(), Constants.CLIENT_TYPE,new OnRequestListener<SignInEntity>() {
                    @Override
                    public void onRequestSuccess(SignInEntity data) {
                        mSigninInterface.saveUserData(data);
                        mSigninInterface.showRequestSuccessView("");
                    }
                    @Override
                    public void onRequestFailed(String message) {
                        mSigninInterface.showRequestFailedView(message);
                    }
                });
    }

    public void getCode() {
        mSigninModel.getCode(mSigninInterface.getPhoneNumber(), new OnRequestListener<String>() {
            @Override
            public void onRequestSuccess(String data) {
                mSigninInterface.showGetCodeSuccessView(data);
            }
            @Override
            public void onRequestFailed(String message) {
                mSigninInterface.showGetCodeFailedView(message);
            }
        });
    }
}
