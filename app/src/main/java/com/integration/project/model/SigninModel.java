package com.integration.project.model;

import com.integration.project.entity.SignInEntity;
import com.integration.project.listener.OnRequestListener;
import com.integration.project.request.RequestMessageCode;
import com.integration.project.request.RequestSignin;
import com.integration.project.response.BaseResponse;
import com.integration.project.service.ServiceManager;
import com.integration.project.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wongerfeng on 2020/8/11.
 */
public class SigninModel implements ISigninModel {
    private static final String TAG = "ghb";

    @Override
    public void signin(String phoneNumber, String pwdOrCode, int type,String contextId, final OnRequestListener<SignInEntity> listener) {
        //创建json对象
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userName", phoneNumber);
            jsonObject.put("type", type);
            if (type == 1) {
                jsonObject.put("password", pwdOrCode);
            } else if (type == 2) {
                jsonObject.put("messageCode", pwdOrCode);
            }
            jsonObject.put("contextId", contextId);
            //解析json ，生成请求body
            final RequestBody requestBody =
                    RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
            //发送请求
            Observable<BaseResponse<SignInEntity>> observable = ServiceManager.getInstance()
                    .getServiceByClass(RequestSignin.class).signinMethod(requestBody);
            Subscription subscribe = observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponse<SignInEntity>>() {
                        @Override
                        public void onCompleted() {
                        }
                        @Override
                        public void onError(Throwable e) {
                            LogUtils.e(TAG, e.getMessage(), e);
                        }

                        @Override
                        public void onNext(BaseResponse<SignInEntity> response) {
                            if (response != null){
                                if(response.getCode() == 0) {
                                    SignInEntity entity = response.getData();
                                    if (entity != null) {
                                        listener.onRequestSuccess(entity);
                                        LogUtils.i(TAG, "onNext: "+response.getData().toString());
                                    } else {
                                        listener.onRequestFailed(response.getMessage());
                                    }
                                } else if (response.getCode() == 9999) {
                                    listener.onRequestFailed(response.getMessage());
                                }
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getCode(String phoneNumber, final OnRequestListener<String> listener) {

        try {
            JSONObject object = new JSONObject();
            object.put("telephone", phoneNumber);

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());

            Observable<BaseResponse> observable = ServiceManager.getInstance()
                    .getServiceByClass(RequestMessageCode.class)
                    .getMessageCodeMethod(requestBody);
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
