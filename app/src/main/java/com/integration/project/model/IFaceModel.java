package com.integration.project.model;

import com.integration.project.listener.OnRequestListener;

/**
 * Created by Wongerfeng on 2020/9/2.
 */
public interface IFaceModel {


    void faceVerify(String employeeId, String telephone, String callbackUrl, String contextId,
                    OnRequestListener listener);
}
