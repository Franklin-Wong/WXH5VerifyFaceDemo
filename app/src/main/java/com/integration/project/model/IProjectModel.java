package com.integration.project.model;

import com.integration.project.entity.UserContractEntity;
import com.integration.project.listener.OnRequestListener;

/**
 * Created by Wongerfeng on 2020/8/14.
 */
public interface IProjectModel extends BaseModelInterface {


    void getUserProject(String token, Integer id, int start, int limit, OnRequestListener<UserContractEntity> listener);
}
