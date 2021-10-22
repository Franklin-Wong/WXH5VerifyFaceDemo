package com.integration.project.presenter;

import com.integration.project.entity.UserInfoEntity;
import com.integration.project.listener.OnRequestListener;
import com.integration.project.model.UserInfoModel;
import com.integration.project.model.IUserInfoModel;
import com.integration.project.view.center.IUserInfoInterface;

/**
 * Created by Wongerfeng on 2020/8/14.
 */
public class UserInfoPresenter extends BasePresenter<IUserInfoInterface> {

    private IUserInfoInterface mUserInfoInterface;
    private IUserInfoModel mUserInfoModel;

    public UserInfoPresenter(IUserInfoInterface userInfoInterface) {
        mUserInfoInterface = userInfoInterface;
        mUserInfoModel = new UserInfoModel();
    }

    public void getUserInfo() {
        mUserInfoModel.getUserInfo(mUserInfoInterface.getUserToken(), mUserInfoInterface.getUserId(),
                new OnRequestListener<UserInfoEntity>() {
                    @Override
                    public void onRequestSuccess(UserInfoEntity data) {
                        mUserInfoInterface.setUserInfo(data);
                    }
                    @Override
                    public void onRequestFailed(String message) {
                        mUserInfoInterface.showRequestFailedView(message);
                    }
                });
    }
}
