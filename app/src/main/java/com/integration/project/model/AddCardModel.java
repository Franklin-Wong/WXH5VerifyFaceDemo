package com.integration.project.model;

import com.integration.project.entity.BankBranchEntity;
import com.integration.project.entity.CardInfoEntity;
import com.integration.project.listener.OnRequestListener;
import com.integration.project.request.RequestBindCard;
import com.integration.project.request.RequestBranchBankInfo;
import com.integration.project.response.BaseResponse;
import com.integration.project.service.ServiceManager;
import com.integration.project.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wongerfeng on 2020/8/17.
 */
public class AddCardModel implements IAddCardModel {
    private static final String TAG = "ghb";
    @Override
    public void bindNewCard(String token, Integer id, String name, String bank, String bankBranchName,
                            String cnapsCode, String province, String city, String bankCardNo,
                            final OnRequestListener<CardInfoEntity> listener) {
        try {
            JSONObject object = new JSONObject();
            object.put("id", id);
            object.put("name", name);
            object.put("bank", bank);
            object.put("bankBranchName", bankBranchName);
            object.put("cnapsCode", cnapsCode);
            object.put("province", province);
            object.put("city", city);
            object.put("bankCardNo", bankCardNo);
            RequestBody requestBody =
                    RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());

            Observable<BaseResponse<CardInfoEntity>> observable = ServiceManager.getInstance()
                    .getServiceByClass(RequestBindCard.class)
                    .bindCardMethod(token, id, requestBody);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponse<CardInfoEntity>>() {
                        @Override
                        public void onCompleted() {
                        }
                        @Override
                        public void onError(Throwable e) {
                            LogUtils.i(TAG, "onError: "+e.getMessage());
                        }
                        @Override
                        public void onNext(BaseResponse<CardInfoEntity> baseResponse) {
                            if (baseResponse != null){
                                LogUtils.i(TAG, "onNext: "+baseResponse.toString());
                                if(baseResponse.getCode() == 0) {
                                    listener.onRequestSuccess(baseResponse.getData());
                                } else if (baseResponse.getCode() == 9999) {
                                    listener.onRequestFailed(baseResponse.getMessage());
                                }
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showBranchNames(String token, String bankBranchName,
                                final OnRequestListener<List<BankBranchEntity.BranchBank>> listener) {
        LogUtils.i(TAG, "showBranchNames: token=" + token+"; bank="+bankBranchName);
        try {
            JSONObject object = new JSONObject();
            object.put("subbranch", bankBranchName);
            object.put("token", token);
            RequestBody requestBody =
                    RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());

            Observable<BaseResponse<List<BankBranchEntity.BranchBank>>> observable = ServiceManager.getInstance()
                    .getServiceByClass(RequestBranchBankInfo.class)
                    .getBankCardMethod(requestBody);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponse<List<BankBranchEntity.BranchBank>>>() {
                        @Override
                        public void onCompleted() {
                        }
                        @Override
                        public void onError(Throwable e) {
                            LogUtils.i(TAG, "onError: "+ e.getMessage());
                        }
                        @Override
                        public void onNext(BaseResponse<List<BankBranchEntity.BranchBank>> baseResponse) {
                            if (baseResponse != null){
                                LogUtils.i(TAG, "onNext: "+baseResponse.toString());
                                if(baseResponse.getCode() == 0) {
                                    listener.onRequestSuccess(baseResponse.getData());
                                } else if (baseResponse.getCode() == 9999) {
                                    listener.onRequestFailed(baseResponse.getMessage());
                                }
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
