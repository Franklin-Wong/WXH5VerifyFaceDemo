package com.integration.project.presenter;

import com.integration.project.entity.IncomeDetailEntity;
import com.integration.project.listener.OnRequestListener;
import com.integration.project.model.IIncomeModel;
import com.integration.project.model.IncomeModel;
import com.integration.project.view.IIncomeInterface;

/**
 * Created by Wongerfeng on 2020/8/17.
 */
public class IncomePresenter extends BasePresenter<IIncomeInterface> {

    private IIncomeInterface mIncomeInterface;
    private IIncomeModel mIncomeModel;

    public IncomePresenter(IIncomeInterface incomeInterface) {
        mIncomeInterface = incomeInterface;
        mIncomeModel = new IncomeModel();
    }

    public void getUserIncome(int startPage, int limit) {
        mIncomeModel.getIncomeDetail(mIncomeInterface.getUserToken(), mIncomeInterface.getUserId(),
                startPage, limit, new OnRequestListener<IncomeDetailEntity>() {
                    @Override
                    public void onRequestSuccess(IncomeDetailEntity data) {
                        mIncomeInterface.setUserData(data);
                    }

                    @Override
                    public void onRequestFailed(String message) {
                        mIncomeInterface.showRequestFailedView(message);
                    }
                });

    }
}
