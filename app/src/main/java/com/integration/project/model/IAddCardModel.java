package com.integration.project.model;

import com.integration.project.entity.BankBranchEntity;
import com.integration.project.entity.CardInfoEntity;
import com.integration.project.listener.OnRequestListener;

import java.util.List;

/**
 * Created by Wongerfeng on 2020/8/17.
 */
public interface IAddCardModel {

    void bindNewCard(String token, Integer  id, String name, String bank, String bankBranchName,
            String cnapsCode, String province, String city, String bankCardNo,
                     OnRequestListener<CardInfoEntity> listener);

    void showBranchNames(String token,String bankBranchName,OnRequestListener<List<BankBranchEntity.BranchBank>> listener);
}
