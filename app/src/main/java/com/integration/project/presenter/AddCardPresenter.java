package com.integration.project.presenter;

import com.integration.project.entity.BankBranchEntity;
import com.integration.project.entity.CardInfoEntity;
import com.integration.project.listener.OnRequestListener;
import com.integration.project.model.AddCardModel;
import com.integration.project.model.IAddCardModel;
import com.integration.project.view.IAddCardInterface;

import java.util.List;

/**
 * Created by Wongerfeng on 2020/8/17.
 */
public class AddCardPresenter extends BasePresenter<IAddCardInterface> {

    private IAddCardInterface mAddCardInterface;
    private IAddCardModel mAddCardModel;

    public AddCardPresenter(IAddCardInterface addCardInterface) {
        mAddCardInterface = addCardInterface;
        mAddCardModel = new AddCardModel();
    }

    public void bindCard() {
        mAddCardModel.bindNewCard(mAddCardInterface.getUserToken(), mAddCardInterface.getUserId(),
                mAddCardInterface.getUserName(), mAddCardInterface.getBankName(),
                mAddCardInterface.getBranchName(), mAddCardInterface.getCnapsCode(),
                mAddCardInterface.getProvince(), mAddCardInterface.getCity(),
                mAddCardInterface.getCardNumber(), new OnRequestListener<CardInfoEntity>() {
                    @Override
                    public void onRequestSuccess(CardInfoEntity data) {
                        mAddCardInterface.setUserData(data);
                    }

                    @Override
                    public void onRequestFailed(String message) {
                        mAddCardInterface.showRequestFailedView(message);
                    }
                });

    }

    public void showMoreNames() {
        mAddCardModel.showBranchNames(mAddCardInterface.getUserToken(), mAddCardInterface.getBankName(),
                new OnRequestListener<List<BankBranchEntity.BranchBank>>() {
                    @Override
                    public void onRequestSuccess(List<BankBranchEntity.BranchBank> data) {
                        mAddCardInterface.showBranchNames(data);
                    }

                    @Override
                    public void onRequestFailed(String message) {
                        mAddCardInterface.showRequestFailedView(message);
                    }
                });


    }
}
