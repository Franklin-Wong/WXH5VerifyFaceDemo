package com.integration.project.view;

import com.integration.project.entity.CardInfoEntity;

/**
 * Created by Wongerfeng on 2020/8/17.
 */
public interface IBankInterface extends IBaseViewInterface {

    void setUserData(CardInfoEntity entity);

}
