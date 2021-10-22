package com.integration.project.entity;

/**
 * Created by Wongerfeng on 2020/8/13.
 */
public class UserInfoEntity {

    private String id;// : 101, // 员工id
    private String name;// : 张三, // 员工姓名
    private String idCardNo;// : 130323832377883, //身份证号
    private String telephone;// : 135000000030, //手机号
    private int status;// :0未实名 1 已实名 (蓝色字体提示) 2实名失败(红色字体提示) 3 无需要实名

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserInfoEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", idCardNo='" + idCardNo + '\'' +
                ", telephone='" + telephone + '\'' +
                ", status=" + status +
                '}';
    }
}
