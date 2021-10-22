package com.integration.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Wongerfeng on 2020/8/13.
 */
public class UserContractIncomeEntity implements Serializable{
    private String currentYearIncome;// : 20000.00, //本年累计收入
    private String monthIncome;// : 1800.00, //本月收入
    private int signed;// : 2, //未签约数量
    private Integer employeeId;// : 101, //当前查询用户id
    private int signStatus;// : 1, //是否签署验证0 不开启 (静默)1 开启(有感知)
    private List<ContractIncome> list;//:[//待签约列表

    public String getCurrentYearIncome() {
        return currentYearIncome;
    }

    public void setCurrentYearIncome(String currentYearIncome) {
        this.currentYearIncome = currentYearIncome;
    }

    public String getMonthIncome() {
        return monthIncome;
    }

    public void setMonthIncome(String monthIncome) {
        this.monthIncome = monthIncome;
    }

    public int getSigned() {
        return signed;
    }

    public void setSigned(int signed) {
        this.signed = signed;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public int getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(int signStatus) {
        this.signStatus = signStatus;
    }

    public List<ContractIncome> getList() {
        return list;
    }

    public void setList(List<ContractIncome> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "UserContractIncomeEntity{" +
                "currentYearIncome='" + currentYearIncome + '\'' +
                ", monthIncome='" + monthIncome + '\'' +
                ", signed=" + signed +
                ", employeeId='" + employeeId + '\'' +
                ", signStatus=" + signStatus +
                ", list=" + list +
                '}';
    }

    public class ContractIncome implements Serializable {
        private String downloadUrl;// : http://oss/1558943428041.jpg, // 待签署文件下载地址
        private String fileName;// : 星河雇员合同, //合同名称
        private Integer employeeId;// : 101, //当前签署用户id
        private Integer companyId;// : 101 //当前签署公司id

        public ContractIncome(String fileName) {
            this.fileName = fileName;
        }

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

        @Override
        public String toString() {
            return "ContractIncome{" +
                    "downloadUrl='" + downloadUrl + '\'' +
                    ", fileName='" + fileName + '\'' +
                    ", employeeId='" + employeeId + '\'' +
                    ", companyId='" + companyId + '\'' +
                    '}';
        }
    }
}
