package com.integration.project.entity;

/**
 * Created by Wongerfeng on 2020/9/2.
 */
public class FaceResultEntity {
    private String employeeId;// : 101, //当前签署用户id
    private String status;// :2, //1人脸识别成功 2.人脸识别失败
    //刷脸失败 返回人脸识别地址
    private String authUrl;//https://blog.csdn.net/weixin_42193830" //e签宝人脸识别地址

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    @Override
    public String toString() {
        return "FaceResultEntity{" +
                "employeeId='" + employeeId + '\'' +
                ", status='" + status + '\'' +
                ", authUrl='" + authUrl + '\'' +
                '}';
    }
}
