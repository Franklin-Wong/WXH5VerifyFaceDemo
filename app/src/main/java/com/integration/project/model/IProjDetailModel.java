package com.integration.project.model;

import com.integration.project.listener.OnRequestListener;

import java.io.File;

/**
 * Created by Wongerfeng on 2020/9/2.
 */
public interface IProjDetailModel {

    void downloadContract(String url, String destDir, String fileName, OnRequestListener<File> listener);
}
