package com.integration.project.view;

import com.integration.project.entity.UnsignedContractEntity;
import com.integration.project.entity.UserContractIncomeEntity;

/**
 * Created by Wongerfeng on 2020/8/20.
 */
public interface IContractInterface extends IBaseViewInterface{

    Integer getCompanyId();

    /**
     * 获取待签约合同数
     * @param data
     */
    void getUnsignContractCount(UserContractIncomeEntity data);

    /**
     * 获取当前合同的下载地址
     * @param data
     */
    void getUnsignContractInfo(UnsignedContractEntity data);

    void requestContractInfoFailed(String message);

    void requestContractCountFailed(String message);
}
