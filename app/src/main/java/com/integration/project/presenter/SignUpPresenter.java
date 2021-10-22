package com.integration.project.presenter;

import com.integration.project.entity.SignUpEntity;
import com.integration.project.listener.OnRequestListener;
import com.integration.project.model.ISignUpModel;
import com.integration.project.model.SignUpModel;
import com.integration.project.response.BaseResponse;
import com.integration.project.utils.Constants;
import com.integration.project.view.login.ISignUpInterface;

/**
 * Created by Wongerfeng on 2020/8/10.
 */
public class SignUpPresenter extends BasePresenter<ISignUpInterface> {

    private ISignUpInterface mSignUpInterface;
    private ISignUpModel mSignUpModel;

    public SignUpPresenter(ISignUpInterface signUpInterface) {
        mSignUpInterface = signUpInterface;
        mSignUpModel = new SignUpModel();
    }

    public void checkPhone() {
        mSignUpModel.checkPhone(mSignUpInterface.getPhoneNumber(), new OnRequestListener<BaseResponse>() {
            @Override
            public void onRequestSuccess(BaseResponse baseResponse) {
                mSignUpInterface.showRequestSuccessView(baseResponse.getMessage());
            }

            @Override
            public void onRequestFailed(String message) {
                mSignUpInterface.showRequestFailedView(message);
            }
        });

    }

    public void getMessageCode() {
        mSignUpModel.getCode(mSignUpInterface.getPhoneNumber(), new OnRequestListener<String>() {
            @Override
            public void onRequestSuccess(String data) {
                mSignUpInterface.showGetCodeSuccessView(data);
            }
            @Override
            public void onRequestFailed(String message) {
                mSignUpInterface.showGetCodeFailedView(message);
            }
        });

    }

    public void uploadHeadPic() {
        mSignUpModel.uploadAvatar(mSignUpInterface.getPhoto(), mSignUpInterface.getIdCardType(), mSignUpInterface.getRealName(),
                mSignUpInterface.getIdNumber(), new OnRequestListener<BaseResponse<String>>() {
                    @Override
                    public void onRequestSuccess(BaseResponse<String> data) {
                        if (data.getData() != null) {
                            mSignUpInterface.uploadHeadPicSuccess(data.getData());
                        }
                    }
                    @Override
                    public void onRequestFailed(String message) {

                        mSignUpInterface.showRequestFailedView(message);
                    }
                });

    }
    public void uploadEmblemPic() {
        mSignUpModel.uploadAvatar(mSignUpInterface.getPhoto(), mSignUpInterface.getIdCardType(), mSignUpInterface.getRealName(),
                mSignUpInterface.getIdNumber(), new OnRequestListener<BaseResponse<String>>() {
                    @Override
                    public void onRequestSuccess(BaseResponse<String> data) {
                        if (data.getData() != null) {
                            mSignUpInterface.uploadEmblemPicSuccess(data.getData());
                        }
                    }
                    @Override
                    public void onRequestFailed(String message) {

                        mSignUpInterface.showRequestFailedView(message);
                    }
                });

    }


    public void signUp() {
        mSignUpModel.signUp(mSignUpInterface.getPhoneNumber(), mSignUpInterface.getCode(),
                mSignUpInterface.getPwd(), mSignUpInterface.getPwd2(), mSignUpInterface.getRealName()
                , mSignUpInterface.getIdNumber(), mSignUpInterface.getUrlAvatar(),
                mSignUpInterface.getUrlMetal(), Constants.CLIENT_TYPE,
                new OnRequestListener<SignUpEntity>() {
                    @Override
                    public void onRequestSuccess(SignUpEntity data) {
                        mSignUpInterface.saveUserData(data);
                    }

                    @Override
                    public void onRequestFailed(String message) {
                        mSignUpInterface.showRequestFailedView(message);
                    }
                });
    }


}
