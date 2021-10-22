package com.integration.project.entity;

/**
 * Created by Wongerfeng on 2020/8/11.
 */
public class UploadPicEntity {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "UploadPicEntity{" +
                "url='" + url + '\'' +
                '}';
    }
}
