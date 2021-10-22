package com.integration.project.presenter;

import com.integration.project.model.FaceModel;
import com.integration.project.model.IFaceModel;
import com.integration.project.view.IBaseViewInterface;

/**
 * Created by Wongerfeng on 2020/9/2.
 */
public class FacePresenter extends BasePresenter<IBaseViewInterface> {

    private IBaseViewInterface mViewInterface;
    private IFaceModel mFaceModel;

    public FacePresenter(IBaseViewInterface viewInterface) {
        mViewInterface = viewInterface;
        mFaceModel = new FaceModel();

    }

    public void faceVerify() {


    }
}
