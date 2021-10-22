package com.integration.project.model;

import com.integration.project.entity.HomeEntity;
import com.integration.project.entity.UserContractIncomeEntity;
import com.integration.project.listener.OnRequestListener;

/**
 * Created by Wongerfeng on 2020/8/13.
 */
public interface IHomePageModel extends BaseModelInterface {

    void getHomePageInfo(String token, Integer id, OnRequestListener<HomeEntity> listener);

    void getUnsignContractIncome(String token, Integer id, OnRequestListener<UserContractIncomeEntity> listener);

}
