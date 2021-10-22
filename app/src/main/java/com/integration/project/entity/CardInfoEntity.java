package com.integration.project.entity;

import java.io.Serializable;

/**
 * Created by Wongerfeng on 2020/8/13.
 */
public class CardInfoEntity implements Serializable {
     private String id;//:232,//员工id
             private String name;//:张三,//姓名
            private String bankBranchName;//: 北京银行上地支行,//支行完整名称
            private String bankCardNo;//: 622848063241818288//银行卡号

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

    public String getBankBranchName() {
        return bankBranchName;
    }

    public void setBankBranchName(String bankBranchName) {
        this.bankBranchName = bankBranchName;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    @Override
    public String toString() {
        return "CardInfoEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", bankBranchName='" + bankBranchName + '\'' +
                ", bankCardNo='" + bankCardNo + '\'' +
                '}';
    }
}
