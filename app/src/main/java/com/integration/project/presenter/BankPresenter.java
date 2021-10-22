package com.integration.project.presenter;

import com.integration.project.entity.CardInfoEntity;
import com.integration.project.listener.OnRequestListener;
import com.integration.project.model.BankModel;
import com.integration.project.model.IBankModel;
import com.integration.project.view.IBankInterface;

/**
 * Created by Wongerfeng on 2020/8/17.
 */
public class BankPresenter extends BasePresenter<IBankInterface> {
    private IBankInterface mBankInterface;
    private IBankModel mBankModel;

    public BankPresenter(IBankInterface bankInterface) {
        mBankInterface = bankInterface;
        mBankModel = new BankModel();
    }


    public void getBankCardInfo() {
        mBankModel.getBankInfo(mBankInterface.getUserToken(), mBankInterface.getUserId(),
                new OnRequestListener<CardInfoEntity>() {
                    @Override
                    public void onRequestSuccess(CardInfoEntity data) {
                        mBankInterface.setUserData(data);
                    }

                    @Override
                    public void onRequestFailed(String message) {
                        mBankInterface.showRequestFailedView(message);
                    }
                });

    }

    public void unbindCard() {
        mBankModel.unbindCard(mBankInterface.getUserToken(), mBankInterface.getUserId(),
                new OnRequestListener<String>() {
                    @Override
                    public void onRequestSuccess(String data) {
                        mBankInterface.showRequestSuccessView(data);
                    }

                    @Override
                    public void onRequestFailed(String message) {
                        mBankInterface.showRequestFailedView(message);
                    }
                });

    }
}
