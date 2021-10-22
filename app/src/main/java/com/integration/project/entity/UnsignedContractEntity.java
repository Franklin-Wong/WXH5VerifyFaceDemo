package com.integration.project.entity;

import java.util.Objects;

/**
 * Created by Wongerfeng on 2020/8/13.
 */
public class UnsignedContractEntity {

    private String downloadUrl;// : http://oss/1558943428041.jpg, // 文件下载地址
    private String fileName;// : 天和雇员合同, //合同名称
    private Integer employeeId;// : 101, //当前签署用户id
    private Integer companyId;// : 101, //当前签署公司id
    private int signStatus;// : 1 //是否签署验证0 不开启 (静默)1 开启(有感知)

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public int getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(int signStatus) {
        this.signStatus = signStatus;
    }

    @Override
    public String toString() {
        return "UnsignedContractEntity{" +
                "downloadUrl='" + downloadUrl + '\'' +
                ", fileName='" + fileName + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", companyId='" + companyId + '\'' +
                ", signStatus='" + signStatus + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnsignedContractEntity that = (UnsignedContractEntity) o;
        return signStatus == that.signStatus &&
                downloadUrl.equals(that.downloadUrl) &&
                fileName.equals(that.fileName) &&
                employeeId.equals(that.employeeId) &&
                companyId.equals(that.companyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(downloadUrl, fileName, employeeId, companyId, signStatus);
    }
}
