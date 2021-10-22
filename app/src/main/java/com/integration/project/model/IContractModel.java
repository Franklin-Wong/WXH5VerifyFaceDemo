package com.integration.project.model;

import com.integration.project.entity.SignedContractEntity;
import com.integration.project.entity.UnsignedContractEntity;
import com.integration.project.entity.UserContractIncomeEntity;
import com.integration.project.listener.OnRequestListener;

/**
 * Created by Wongerfeng on 2020/8/20.
 */
public interface IContractModel {

    /**
     * 请求同意签约
     * @param token
     * @param id
     * @param companyId
     * @param listener
     */
    void signContract(String token, Integer id, Integer companyId, OnRequestListener<SignedContractEntity> listener);

    /**
     * 查询当前未签约的合同数
     * @param token
     * @param id
     * @param listener
     */
    void getUnsignContractIncome(String token, Integer id, OnRequestListener<UserContractIncomeEntity> listener);

    /**
     * 获取当前要签约的合同信息
     * @param token
     * @param id
     * @param listener
     */
    void getUnsignedContractInfo(String token, Integer id, OnRequestListener<UnsignedContractEntity> listener);

}
