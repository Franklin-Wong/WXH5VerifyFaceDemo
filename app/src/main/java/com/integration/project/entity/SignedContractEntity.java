package com.integration.project.entity;

/**
 * Created by Wongerfeng on 2020/8/13.
 */
public class SignedContractEntity {
    private String downloadUrl;// : http://oss/1558943428041.jpg, // 签署完成合同下载地址
    private Integer employeeId;// : 101, //当前签署用户id
    private Integer companyId;// : 1, //当前签署公司id
    private int state;// : 1 ,//签署成功
    private int signed;// : 2 //未签约数量

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getSigned() {
        return signed;
    }

    public void setSigned(int signed) {
        this.signed = signed;
    }

    @Override
    public String toString() {
        return "SignedContractEntity{" +
                "downloadUrl='" + downloadUrl + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", companyId='" + companyId + '\'' +
                ", state=" + state +
                ", signed=" + signed +
                '}';
    }
}
