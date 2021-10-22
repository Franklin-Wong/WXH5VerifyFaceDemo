package com.integration.project.entity;

/**
 * Created by Wongerfeng on 2020/8/11.
 */
public class SignUpEntity {

    private String token;//: 73249h3s, //token令牌
    private Integer employeeId;//: 12 ,//员工id
    private String name;//:"张三",//员工姓名
    private String authUrl;//: "https://blog.csdn.net/weixin_42193830" //e签宝人脸识别地址  此字段有值则代表需要刷脸,无则不需要

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    @Override
    public String toString() {
        return "SignUpEntity{" +
                "token='" + token + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", name='" + name + '\'' +
                ", authUrl='" + authUrl + '\'' +
                '}';
    }
}
