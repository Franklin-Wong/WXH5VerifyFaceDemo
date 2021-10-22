package com.integration.project.model;

import com.integration.project.entity.HomeEntity;
import com.integration.project.entity.UserContractIncomeEntity;
import com.integration.project.listener.OnRequestListener;
import com.integration.project.request.RequestHomePage;
import com.integration.project.request.RequestUnsignContractIncome;
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
 * Created by Wongerfeng on 2020/8/13.
 */
public class HomePageModel implements IHomePageModel {
    private static final String TAG = "ghb";
    @Override
    public void getHomePageInfo(String token, Integer id, final OnRequestListener<HomeEntity> listener) {

        try {
            JSONObject object = new JSONObject();
            object.put("id", id);

            RequestBody requestBody =
                    RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());

            Observable<BaseResponse<HomeEntity>> observable = ServiceManager.getInstance()
                    .getServiceByClass(RequestHomePage.class)
                    .getHomePageMethod(token, requestBody);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponse<HomeEntity>>() {
                        @Override
                        public void onCompleted() {
                        }
                        @Override
                        public void onError(Throwable e) {
                            LogUtils.i(TAG, "onError: "+e.getMessage());
                        }
                        @Override
                        public void onNext(BaseResponse<HomeEntity> baseResponse) {
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
    public void getUnsignContractIncome(String token, Integer id,
                                        final OnRequestListener<UserContractIncomeEntity> listener) {
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
                                LogUtils.i(TAG, "onNext: "+baseResponse.toString());
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void clearCache() {

    }
}
