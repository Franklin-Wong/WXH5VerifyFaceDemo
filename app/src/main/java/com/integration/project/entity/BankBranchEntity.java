package com.integration.project.entity;

import java.util.List;

/**
 * Created by Wongerfeng on 2020/8/13.
 */
public class BankBranchEntity {

    private List<BranchBank> list;

    public List<BranchBank> getList() {
        return list;
    }

    public void setList(List<BranchBank> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "BankCardEntity{" +
                "list=" + list +
                '}';
    }

    public class BranchBank {
        private String bank;// : 中国农业银行, // 银行总行名称
        private String bankName;//  : 上地支行, // 支行完整名称
        private String cnapsCode;//  : 123456, // 联行号信息
        private String province;//  : 北京, // 支行所在省份
        private String city;//  : 北京 //支行所在市/区

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getCnapsCode() {
            return cnapsCode;
        }

        public void setCnapsCode(String cnapsCode) {
            this.cnapsCode = cnapsCode;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        @Override
        public String toString() {
            return "BankCaardList{" +
                    "bank='" + bank + '\'' +
                    ", bankName='" + bankName + '\'' +
                    ", cnapsCode='" + cnapsCode + '\'' +
                    ", province='" + province + '\'' +
                    ", city='" + city + '\'' +
                    '}';
        }
    }
}
