package com.integration.project.presenter;

import com.integration.project.entity.SignedContractEntity;
import com.integration.project.entity.UnsignedContractEntity;
import com.integration.project.entity.UserContractIncomeEntity;
import com.integration.project.listener.OnRequestListener;
import com.integration.project.model.ContractModel;
import com.integration.project.model.IContractModel;
import com.integration.project.view.IContractInterface;

/**
 * Created by Wongerfeng on 2020/8/20.
 */
public class ContractPresenter extends BasePresenter<IContractInterface> {

    private IContractInterface mContractInterface;
    private IContractModel mContractModel;

    public ContractPresenter(IContractInterface contractInterface) {
        mContractInterface = contractInterface;
        mContractModel = new ContractModel();
    }

    public void getUsignedContractInfo() {
        mContractModel.getUnsignedContractInfo(mContractInterface.getUserToken(), mContractInterface.getUserId(),
                new OnRequestListener<UnsignedContractEntity>() {
                    @Override
                    public void onRequestSuccess(UnsignedContractEntity data) {
                        mContractInterface.getUnsignContractInfo(data);
                    }
                    @Override
                    public void onRequestFailed(String message) {
                        mContractInterface.requestContractInfoFailed(message);
                    }
                });

    }
    public void signContract() {
        mContractModel.signContract(mContractInterface.getUserToken(), mContractInterface.getUserId(),
                mContractInterface.getCompanyId(), new OnRequestListener<SignedContractEntity>() {
                    @Override
                    public void onRequestSuccess(SignedContractEntity data) {
                        mContractInterface.showRequestSuccessView("");

                    }
                    @Override
                    public void onRequestFailed(String message) {
                        mContractInterface.showRequestFailedView(message);
                    }
                });
    }

    public void getUnsignContractCount() {

        mContractModel.getUnsignContractIncome(mContractInterface.getUserToken(),
                mContractInterface.getUserId(), new OnRequestListener<UserContractIncomeEntity>() {
                    @Override
                    public void onRequestSuccess(UserContractIncomeEntity data) {
                        mContractInterface.getUnsignContractCount(data);
                    }
                    @Override
                    public void onRequestFailed(String message) {
                        mContractInterface.requestContractCountFailed(message);
                    }
                });
    }

}
