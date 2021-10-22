package com.integration.project.presenter;

import com.integration.project.listener.OnRequestListener;
import com.integration.project.model.IResetPwdModel;
import com.integration.project.model.RestPwdModel;
import com.integration.project.view.login.IResetPwdInterface;

/**
 * Created by Wongerfeng on 2020/8/13.
 */
public class ResetPwdPresenter extends BasePresenter<IResetPwdInterface> {

    private IResetPwdInterface mResetPwdInterface;
    private IResetPwdModel mResetPwdModel;

    public ResetPwdPresenter(IResetPwdInterface resetPwdInterface) {
        mResetPwdInterface = resetPwdInterface;
        mResetPwdModel = new RestPwdModel();
    }


    public void getMessageCode() {
        mResetPwdModel.getMessageCode(mResetPwdInterface.getPhoneNumber(), new OnRequestListener<String>() {
            @Override
            public void onRequestSuccess(String data) {
                mResetPwdInterface.showGetCodeSuccessView(data);
            }
            @Override
            public void onRequestFailed(String message) {
                mResetPwdInterface.showRequestFailedView(message);
            }
        });

    }

    public void resetPwd() {
        mResetPwdModel.resetPwd(mResetPwdInterface.getPhoneNumber(), mResetPwdInterface.getPwd(),
                mResetPwdInterface.getPwd2(), mResetPwdInterface.getCode(), new OnRequestListener<String>() {
                    @Override
                    public void onRequestSuccess(String data) {
                        mResetPwdInterface.showRequestSuccessView(data);
                    }
                    @Override
                    public void onRequestFailed(String message) {

                        mResetPwdInterface.showRequestFailedView(message);
                    }
                });
    }
}
