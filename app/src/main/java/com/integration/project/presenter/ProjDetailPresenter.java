package com.integration.project.presenter;

import com.integration.project.listener.OnRequestListener;
import com.integration.project.model.IProjDetailModel;
import com.integration.project.model.ProjDetailModel;
import com.integration.project.view.IProjDetailInterface;

import java.io.File;

/**
 * Created by Wongerfeng on 2020/8/28.
 */
public class ProjDetailPresenter extends BasePresenter<IProjDetailInterface> {
    private IProjDetailInterface mViewInterface;
    private IProjDetailModel mDetailModel;

    public ProjDetailPresenter(IProjDetailInterface viewInterface) {
        mViewInterface = viewInterface;
        mDetailModel = new ProjDetailModel();
    }

    public void downloadFile(String url,String destDir, String fileName) {
        mDetailModel.downloadContract(url,destDir, fileName, new OnRequestListener<File>() {
            @Override
            public void onRequestSuccess(File data) {
                mViewInterface.showPDFFile(data);
            }
            @Override
            public void onRequestFailed(String message) {
                mViewInterface.showRequestFailedView(message);
            }
        });

    }
}
