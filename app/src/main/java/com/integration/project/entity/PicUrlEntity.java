package com.integration.project.entity;

/**
 * Created by Wongerfeng on 2020/8/21.
 */
public class PicUrlEntity {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "PicUrlEntity{" +
                "url='" + url +
                '}';
    }
}
