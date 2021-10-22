package com.integration.project.model;

import com.integration.project.entity.IncomeDetailEntity;
import com.integration.project.listener.OnRequestListener;

/**
 * Created by Wongerfeng on 2020/8/17.
 */
public interface IIncomeModel extends BaseModelInterface{

    void getIncomeDetail(String token, int id, int start, int limit, OnRequestListener<IncomeDetailEntity> listener);
}
