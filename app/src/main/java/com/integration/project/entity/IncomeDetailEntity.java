package com.integration.project.entity;

import java.util.List;

/**
 * Created by Wongerfeng on 2020/8/6.
 */
public class IncomeDetailEntity {
    private int totalPage;
    //: 2, //总页数
    private List<Income> mIncomeList;
    public int getTotalPage() {
        return totalPage;
    }
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<Income> getIncomeList() {
        return mIncomeList;
    }

    public void setIncomeList(List<Income> incomeList) {
        this.mIncomeList = incomeList;
    }

    @Override
    public String toString() {
        return "IncomeDetailEntity{" +
                "totalPage=" + totalPage +
                ", mIncomeList=" + mIncomeList +
                '}';
    }

    public class Income {
        private  String id;//" : 101, // 员工id
        private  String companyName;//" : 河北海升科技有限公司, // 公司全称
        private String amount;//" : 1800.00, //到账金额
        private  String salaryTime;//" : 2019-01-02, //到账时间
        private  String bankCardNo;//"

        public String getBankCardNo() {
            return bankCardNo;
        }

        public void setBankCardNo(String bankCardNo) {
            this.bankCardNo = bankCardNo;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getSalaryTime() {
            return salaryTime;
        }

        public void setSalaryTime(String salaryTime) {
            this.salaryTime = salaryTime;
        }

        @Override
        public String toString() {
            return "Income{" +
                    "id='" + id + '\'' +
                    ", companyName='" + companyName + '\'' +
                    ", amount=" + amount +
                    ", salaryTime='" + salaryTime + '\'' +
                    ", bankCardNo='" + bankCardNo + '\'' +
                    '}';
        }
    }


}





