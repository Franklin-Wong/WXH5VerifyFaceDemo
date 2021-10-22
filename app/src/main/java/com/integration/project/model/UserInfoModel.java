package com.integration.project.model;

import com.integration.project.entity.UserInfoEntity;
import com.integration.project.listener.OnRequestListener;
import com.integration.project.request.RequestUserInfo;
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
 * Created by Wongerfeng on 2020/8/14.
 */
public class UserInfoModel implements IUserInfoModel {
    private static final String TAG = "ghb";
    @Override
    public void clearCache() {

    }

    @Override
    public void getUserInfo(String token, Integer id, final OnRequestListener<UserInfoEntity> listener) {
        try {
            JSONObject object = new JSONObject();
            object.put("id", id);

            RequestBody requestBody =
                    RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());

            Observable<BaseResponse<UserInfoEntity>> observable = ServiceManager.getInstance()
                    .getServiceByClass(RequestUserInfo.class)
                    .getUserInfoMethod(token, requestBody);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponse<UserInfoEntity>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(BaseResponse<UserInfoEntity> baseResponse) {
                            if (baseResponse != null){
                                LogUtils.i(TAG, "onNext: UserInfoEntity "+baseResponse.getData().toString());
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
