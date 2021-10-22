package com.integration.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Wongerfeng on 2020/8/13.
 */
public class UserContractEntity {
    private int totalPage;//: 2, //总页数
    private String employeeId;// : 101, // 员工id
    private List<ContractList> list;//

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public List<ContractList> getList() {
        return list;
    }

    public void setList(List<ContractList> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "UserContractEntity{" +
                "totalPage=" + totalPage +
                ", employeeId='" + employeeId + '\'' +
                ", list=" + list +
                '}';
    }

    public class ContractList implements Serializable {
        private String employeeId;// : 101, // 员工id
        private String companyName;// : 河北海升科技有限公司, // 公司全称
        private String contractName;//：自由职业者服务协议,// 合同名称，
        private String created;// : 2019-01-02, //签约时间
        private String url;// :  http://oss/1558943428041.jpg//公司与员工签署合同链接地址

        public String getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(String employeeId) {
            this.employeeId = employeeId;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getContractName() {
            return contractName;
        }

        public void setContractName(String contractName) {
            this.contractName = contractName;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "ContractList{" +
                    "employeeId='" + employeeId + '\'' +
                    ", companyName='" + companyName + '\'' +
                    ", contractName='" + contractName + '\'' +
                    ", created='" + created + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }
}
