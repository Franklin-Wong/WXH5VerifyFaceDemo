package com.integration.project.presenter;

import com.integration.project.entity.HomeEntity;
import com.integration.project.entity.UserContractIncomeEntity;
import com.integration.project.listener.OnRequestListener;
import com.integration.project.model.HomePageModel;
import com.integration.project.model.IHomePageModel;
import com.integration.project.view.IHomePageInterface;

/**
 * Created by Wongerfeng on 2020/8/13.
 */
public class HomPagePresenter extends BasePresenter<IHomePageInterface> {

    private IHomePageInterface mHomePageInterface;
    private IHomePageModel mHomePageModel;

    public HomPagePresenter(IHomePageInterface homePageInterface) {
        mHomePageInterface = homePageInterface;
        mHomePageModel = new HomePageModel();
    }

    public void getHomePageInfo() {
        mHomePageModel.getHomePageInfo(mHomePageInterface.getUserToken(),
                mHomePageInterface.getUserId(), new OnRequestListener<HomeEntity>() {
            @Override
            public void onRequestSuccess(HomeEntity data) {
                mHomePageInterface.setHomePageData(data);
            }

            @Override
            public void onRequestFailed(String message) {
                mHomePageInterface.showRequestFailedView(message);
            }
        });
    }


    public void getUnsignContractIncome() {

        mHomePageModel.getUnsignContractIncome(mHomePageInterface.getUserToken(),
                mHomePageInterface.getUserId(), new OnRequestListener<UserContractIncomeEntity>() {
                    @Override
                    public void onRequestSuccess(UserContractIncomeEntity data) {
                        mHomePageInterface.setUnsignContractIncome(data);
                    }

                    @Override
                    public void onRequestFailed(String message) {
                        mHomePageInterface.showRequestFailedView(message);
                    }
                });
    }
}
