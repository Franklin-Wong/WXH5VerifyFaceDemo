package com.integration.project.listener;

/**
 * Created by Wongerfeng on 2020/8/11.
 */
public interface OnRequestListener<T> {

//    <T> T onRequestListener();

    void onRequestSuccess(T data);

    void onRequestFailed(String message);

}
