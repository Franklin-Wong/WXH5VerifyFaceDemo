package com.integration.project.view;

/**
 * Created by Wongerfeng on 2020/8/13.
 */
public interface IBaseViewInterface{

    String getUserToken();

    Integer getUserId();


    void showRequestSuccessView(String message);

    void showRequestFailedView(String message);
}
