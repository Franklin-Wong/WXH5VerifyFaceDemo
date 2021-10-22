package com.integration.project.model;

import android.util.Log;

import com.integration.project.entity.SignUpEntity;
import com.integration.project.listener.OnRequestListener;
import com.integration.project.request.RequestCheckPhone;
import com.integration.project.request.RequestMessageCode;
import com.integration.project.request.RequestSignUp;
import com.integration.project.request.RequestUploadPic;
import com.integration.project.response.BaseResponse;
import com.integration.project.service.ServiceManager;
import com.integration.project.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
/**
 * Created by Wongerfeng on 2020/8/11.
 */
public class SignUpModel implements ISignUpModel {

    private static final String TAG = "ghb";
    private Observable<BaseResponse<SignUpEntity>> observable;


    @Override
    public void checkPhone(String phoneNumber, final OnRequestListener<BaseResponse> listener) {
            try {
                JSONObject object = new JSONObject();
                object.put("telephone", phoneNumber);

                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());

                Observable<BaseResponse> observable = ServiceManager.getInstance()
                        .getServiceByClass(RequestCheckPhone.class)
                        .checkPhoneNumberMethod(requestBody);
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
                                        listener.onRequestSuccess(baseResponse);
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
    public void signUp(String phoneNumber, String code, String pwd, String pwd2, String name,
                       String idCardNO, String urlAvatar, String urlEmblem,String contextId,
                       final OnRequestListener<SignUpEntity> listener) {
        try {
            JSONObject object = new JSONObject();
            object.put("telephone", phoneNumber);
            object.put("password", pwd);
            object.put("cfgPassword", pwd2);
            object.put("messageCode", code);
            object.put("name", name);
            object.put("idCardNO", idCardNO);
            object.put("idCardFrontUrl", urlAvatar);
            object.put("idCardBackUrl", urlEmblem);

            object.put("contextId", contextId);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());

            observable = ServiceManager.getInstance()
                    .getServiceByClass(RequestSignUp.class)
                    .signUpMethod(requestBody);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponse<SignUpEntity>>() {
                        @Override
                        public void onCompleted() {
                        }
                        @Override
                        public void onError(Throwable e) {
                        }
                        @Override
                        public void onNext(BaseResponse<SignUpEntity> baseResponse) {
                            if (baseResponse != null){
                                LogUtils.i(TAG, "onNext: "+baseResponse.getData().toString());
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
    public void uploadAvatar(File myfile, Integer idCardType, String name, String idCardNO,
                             OnRequestListener<BaseResponse<String>> listener) {
        Log.i(TAG, "uploadAvatar: myfile="+ myfile +";idCardType="+idCardType+";name="+name
        +";idCardNO="+idCardNO);
            RequestBody requestBodyFile = RequestBody.create(MediaType.parse("multipart/form-data"), myfile);

            MultipartBody.Part partPic =
                    MultipartBody.Part.createFormData("myfile", getValueEncoded(myfile.getName()), requestBodyFile);

            Observable<BaseResponse<String>> observablePic = ServiceManager.getInstance()
                    .getServiceByClass(RequestUploadPic.class)
                    .uploadPicMethod(partPic, idCardType, name, idCardNO);

            observablePic.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponse<String>>() {
                        @Override
                        public void onCompleted() {
                        }
                        @Override
                        public void onError(Throwable e) {
                            LogUtils.i(TAG, e.getMessage());
                            listener.onRequestFailed(e.getMessage());
                        }
                        @Override
                        public void onNext(BaseResponse<String> baseResponse) {
                            if (baseResponse != null){
                                LogUtils.i(TAG, "onNext: "+baseResponse.toString());
                                if(baseResponse.getCode() == 0) {
                                    listener.onRequestSuccess(baseResponse);
                                    LogUtils.i(TAG, baseResponse.getData());
                                } else if (baseResponse.getCode() == 9999) {
                                    listener.onRequestFailed(baseResponse.getMessage());
                                    LogUtils.i(TAG, baseResponse.getMessage());
                                }
                            }
                        }
                    });
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
                            listener.onRequestFailed(e.getMessage());
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


    //由于okhttp header 中的 value 不支持 null, \n 和 中文这样的特殊字符,所以这里
    //会首先替换 \n ,然后使用 okhttp 的校验方式,校验不通过的话,就返回 encode 后的字符串
    private static String getValueEncoded(String value) {
        if (value == null) {
            return "null";
        }
        String newValue = value.replace("\n", "");
        for (int i = 0, length = newValue.length(); i < length; i++) {
            char c = newValue.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                try {
                    return URLEncoder.encode(newValue, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return newValue;
    }
}
