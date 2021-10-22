package com.integration.project.model;

import com.integration.project.entity.UserInfoEntity;
import com.integration.project.listener.OnRequestListener;

/**
 * Created by Wongerfeng on 2020/8/14.
 */
public interface IUserInfoModel extends BaseModelInterface {

    void getUserInfo(String token, Integer id, OnRequestListener<UserInfoEntity> listener);
}
