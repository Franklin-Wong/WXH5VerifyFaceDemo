package com.integration.project.model;

import com.integration.project.entity.SignedContractEntity;
import com.integration.project.entity.UnsignedContractEntity;
import com.integration.project.entity.UserContractIncomeEntity;
import com.integration.project.listener.OnRequestListener;
import com.integration.project.request.RequestSignContract;
import com.integration.project.request.RequestUnsignContractIncome;
import com.integration.project.request.RequestUnsignedContract;
import com.integration.project.response.BaseResponse;
import com.integration.project.service.ServiceManager;
import com.integration.project.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wongerfeng on 2020/8/20.
 */
public class ContractModel implements IContractModel {
    private static final String TAG = "ghb";
    @Override
    public void signContract(String token, Integer id, Integer companyId, final OnRequestListener<SignedContractEntity> listener) {
        try {
            JSONObject object = new JSONObject();
            object.put("id", id);

            RequestBody requestBody =
                    RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());

            Observable<BaseResponse<SignedContractEntity>> observable = ServiceManager.getInstance()
                    .getServiceByClass(RequestSignContract.class)
                    .signingContract(token, requestBody);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponse<SignedContractEntity>>() {
                        @Override
                        public void onCompleted() {
                        }
                        @Override
                        public void onError(Throwable e) {
                            LogUtils.i(TAG, "onError: "+e.getMessage());
                        }
                        @Override
                        public void onNext(BaseResponse<SignedContractEntity> baseResponse) {
                            if (baseResponse != null){
                                if(baseResponse.getCode() == 0) {
                                    listener.onRequestSuccess(baseResponse.getData());
                                } else if (baseResponse.getCode() == 9999) {
                                    listener.onRequestFailed(baseResponse.getMessage());
                                }
                                LogUtils.i(TAG, "onNext:SignedContractEntity "+baseResponse.toString());
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getUnsignContractIncome(String token, Integer id, final OnRequestListener<UserContractIncomeEntity> listener) {
        try {
            JSONObject object = new JSONObject();
            object.put("id", id);

            RequestBody requestBody =
                    RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());

            Observable<BaseResponse<UserContractIncomeEntity>> observable = ServiceManager.getInstance()
                    .getServiceByClass(RequestUnsignContractIncome.class)
                    .getUnsignContractIncome(token, requestBody);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponse<UserContractIncomeEntity>>() {
                        @Override
                        public void onCompleted() {
                        }
                        @Override
                        public void onError(Throwable e) {
                            LogUtils.i(TAG, "onError: "+e.getMessage());
                        }
                        @Override
                        public void onNext(BaseResponse<UserContractIncomeEntity> baseResponse) {
                            if (baseResponse != null){
                                if(baseResponse.getCode() == 0) {
                                    listener.onRequestSuccess(baseResponse.getData());
                                } else if (baseResponse.getCode() == 9999) {
                                    listener.onRequestFailed(baseResponse.getMessage());
                                }
                                LogUtils.i(TAG, "onNext:UserContractIncomeEntity "+baseResponse.toString());
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getUnsignedContractInfo(String token, Integer id, final OnRequestListener<UnsignedContractEntity> listener) {
        try {
            JSONObject object = new JSONObject();
            object.put("id", id);

            RequestBody requestBody =
                    RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());

            Observable<BaseResponse<UnsignedContractEntity>> observable = ServiceManager.getInstance()
                    .getServiceByClass(RequestUnsignedContract.class)
                    .getUnsignedContractInfo(token, requestBody);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponse<UnsignedContractEntity>>() {
                        @Override
                        public void onCompleted() {
                        }
                        @Override
                        public void onError(Throwable e) {
                        }
                        @Override
                        public void onNext(BaseResponse<UnsignedContractEntity> baseResponse) {
                            if (baseResponse != null){
                                if(baseResponse.getCode() == 0) {
                                    listener.onRequestSuccess(baseResponse.getData());
                                } else if (baseResponse.getCode() == 9999) {
                                    listener.onRequestFailed(baseResponse.getMessage());
                                }
                                LogUtils.i(TAG, "onNext:UnsignedContractEntity "+baseResponse.getData().toString());
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
