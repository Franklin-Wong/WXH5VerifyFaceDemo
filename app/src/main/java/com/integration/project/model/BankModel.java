package com.integration.project.model;

import com.integration.project.entity.CardInfoEntity;
import com.integration.project.listener.OnRequestListener;
import com.integration.project.request.RequestCardInfo;
import com.integration.project.request.RequestUnbindCard;
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
 * Created by Wongerfeng on 2020/8/17.
 */
public class BankModel implements IBankModel {
    private static final String TAG = "ghb";

    @Override
    public void getBankInfo(String token, Integer id, final OnRequestListener<CardInfoEntity> listener) {
        try {
            JSONObject object = new JSONObject();
            object.put("id", id);
            RequestBody requestBody =
                    RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());

            Observable<BaseResponse<CardInfoEntity>> observable = ServiceManager.getInstance()
                    .getServiceByClass(RequestCardInfo.class)
                    .getCardInfoMethod(token, id, requestBody);
            observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<BaseResponse<CardInfoEntity>>() {
                @Override
                public void onCompleted() {
                }
                @Override
                public void onError(Throwable e) {

                }
                @Override
                public void onNext(BaseResponse<CardInfoEntity> baseResponse) {
                    if (baseResponse != null){
                        LogUtils.i(TAG, "onNext:CardInfoEntity "+baseResponse.toString());
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
    public void unbindCard(String token, Integer id, final OnRequestListener<String> listener) {
        try {
            JSONObject object = new JSONObject();
            object.put("id", id);
            RequestBody requestBody =
                    RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());

            Observable<BaseResponse> observable = ServiceManager.getInstance()
                    .getServiceByClass(RequestUnbindCard.class)
                    .unbindCardMethod(token, id, requestBody);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponse>() {
                        @Override
                        public void onCompleted() {
                        }
                        @Override
                        public void onError(Throwable e) {

                        }
                        @Override
                        public void onNext(BaseResponse baseResponse) {
                            if (baseResponse != null){
                                LogUtils.i(TAG, "onNext: "+baseResponse.toString());
                                if(baseResponse.getCode() == 0) {
                                    listener.onRequestSuccess(baseResponse.getMessage());
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
