package com.integration.project.view.center;

import com.integration.project.entity.UserInfoEntity;
import com.integration.project.view.IBaseViewInterface;

/**
 * Created by Wongerfeng on 2020/8/14.
 */
public interface IUserInfoInterface extends IBaseViewInterface {

    void setUserInfo(UserInfoEntity entity);
}
