package com.integration.project.view;

import com.integration.project.entity.BankBranchEntity;
import com.integration.project.entity.CardInfoEntity;

import java.util.List;

/**
 * Created by Wongerfeng on 2020/8/17.
 */
public interface IAddCardInterface extends IBaseViewInterface {

    void setUserData(CardInfoEntity entity);

    String getUserName();

    String getBankName();

    String getCardNumber();

    String getBranchName();

    String getCity();

    String getCnapsCode();

    String getProvince();

    void showBranchNames(List<BankBranchEntity.BranchBank> list);
}
