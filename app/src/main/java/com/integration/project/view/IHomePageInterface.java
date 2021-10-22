package com.integration.project.view;

import com.integration.project.entity.HomeEntity;
import com.integration.project.entity.UserContractIncomeEntity;

/**
 * Created by Wongerfeng on 2020/8/14.
 */
public interface IHomePageInterface extends IBaseViewInterface {

    void setHomePageData(HomeEntity data);

    void setUnsignContractIncome(UserContractIncomeEntity data);

}
