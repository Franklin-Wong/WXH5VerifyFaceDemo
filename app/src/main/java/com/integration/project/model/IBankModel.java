package com.integration.project.model;

import com.integration.project.entity.CardInfoEntity;
import com.integration.project.listener.OnRequestListener;

/**
 * Created by Wongerfeng on 2020/8/17.
 */
public interface IBankModel {

    void getBankInfo(String token, Integer id, OnRequestListener<CardInfoEntity> listener);

    void unbindCard(String token, Integer id, OnRequestListener<String> listener);
}
