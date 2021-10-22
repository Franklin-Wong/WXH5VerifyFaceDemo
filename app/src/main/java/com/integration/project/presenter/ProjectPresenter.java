package com.integration.project.presenter;

import com.integration.project.entity.UserContractEntity;
import com.integration.project.listener.OnRequestListener;
import com.integration.project.model.IProjectModel;
import com.integration.project.model.ProjectModel;
import com.integration.project.view.IProjectInterface;

/**
 * Created by Wongerfeng on 2020/8/14.
 */
public class ProjectPresenter extends BasePresenter<IProjectInterface> {

    private IProjectInterface mProjectInterface;
    private IProjectModel mProjectModel;

    public ProjectPresenter(IProjectInterface projectInterface) {
        mProjectInterface = projectInterface;
        mProjectModel = new ProjectModel();
    }


    public void getUserProjects(int startPage, int limit) {
        mProjectModel.getUserProject(mProjectInterface.getUserToken(), mProjectInterface.getUserId(),
                startPage, limit, new OnRequestListener<UserContractEntity>() {
                    @Override
                    public void onRequestSuccess(UserContractEntity data) {
                        mProjectInterface.setProjectData(data);
                    }

                    @Override
                    public void onRequestFailed(String message) {
                        mProjectInterface.showRequestFailedView(message);

                    }
                });

    }
}
