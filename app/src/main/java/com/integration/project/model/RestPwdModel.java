package com.integration.project.model;

import com.integration.project.listener.OnRequestListener;
import com.integration.project.request.RequestMessageCode;
import com.integration.project.request.RequestResetPwd;
import com.integration.project.response.BaseResponse;
import com.integration.project.service.ServiceManager;

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
public class RestPwdModel implements IResetPwdModel {
    private Observable<BaseResponse> observable;

    @Override
    public void getMessageCode(String phoneNumber, final OnRequestListener<String> listener) {

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

    @Override
    public void resetPwd(String phoneNumber, String pwd, String pwd2, String code, final OnRequestListener<String> listener) {
        try {
            JSONObject object = new JSONObject();
            object.put("telephone", phoneNumber);
            object.put("password", pwd);
            object.put("cfgPassword", pwd2);
            object.put("messageCode", code);

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());

             observable = ServiceManager.getInstance()
                    .getServiceByClass(RequestResetPwd.class)
                    .resetPwdMethod(requestBody);
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

    @Override
    public void clearCache() {

    }
}
