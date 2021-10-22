package com.integration.project.model;

import com.integration.project.entity.IncomeDetailEntity;
import com.integration.project.listener.OnRequestListener;
import com.integration.project.request.RequestIncomeDetail;
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
public class IncomeModel implements IIncomeModel {
    private static final String TAG = "ghb";

    @Override
    public void getIncomeDetail(String token, int id, int start, int limit,
                                final OnRequestListener<IncomeDetailEntity> listener) {
        try {
            JSONObject object = new JSONObject();
            object.put("id", id);
            object.put("start", start);
            object.put("limit", limit);

            RequestBody requestBody =
                    RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());

            Observable<BaseResponse<IncomeDetailEntity>> observable = ServiceManager.getInstance()
                    .getServiceByClass(RequestIncomeDetail.class)
                    .getIncomeDetail(token, requestBody);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponse<IncomeDetailEntity>>() {
                        @Override
                        public void onCompleted() {
                        }
                        @Override
                        public void onError(Throwable e) {
                            LogUtils.i(TAG, e.getMessage());
                        }

                        @Override
                        public void onNext(BaseResponse<IncomeDetailEntity> baseResponse) {
                            if (baseResponse != null) {
                                LogUtils.i(TAG, "onNext:IncomeDetailEntity= " + baseResponse.toString() + baseResponse.getMessage());
                                if (baseResponse.getCode() == 0) {
                                    listener.onRequestSuccess(baseResponse.getData());
                                } else if (baseResponse.getCode() == 9999) {
                                    listener.onRequestFailed(baseResponse.getMessage());
                                }
                            } else {
                                listener.onRequestFailed("请求不成功");
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
