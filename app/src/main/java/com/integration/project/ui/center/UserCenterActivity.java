package com.integration.project.ui.center;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.integration.project.R;
import com.integration.project.utils.SPUtil;

import androidx.appcompat.app.AppCompatActivity;

public class UserCenterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mName, mUserInfo, mBankCard, mIncomeDetails, mProjects, mGoMainPage;
    private ImageView mVerify;
    private int mStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);
        initData();
        initView();
        initEvent();

    }

    private void initData() {
//        mStatus = getIntent().getIntExtra("status", 0);
    }

    private void initView() {
        mName = findViewById(R.id.tvName);
        mUserInfo = findViewById(R.id.tvUserInfo);
        mBankCard = findViewById(R.id.tvBankCard);
        mIncomeDetails = findViewById(R.id.tvIncome);
        mProjects = findViewById(R.id.tvProject);
        mGoMainPage = findViewById(R.id.tvMainPage);
        mVerify = findViewById(R.id.ivVerify);

        mName.setText(SPUtil.shareUserName(this));
        mStatus = SPUtil.shareUserStatus(this);
        switch (mStatus) {
            case 0:
                mVerify.setBackgroundResource(R.mipmap.ic_verify_failed);
                mVerify.setVisibility(View.VISIBLE);
                break;
            case 1:
                mVerify.setBackgroundResource(R.mipmap.ic_verify_success);
                mVerify.setVisibility(View.VISIBLE);

                break;
            case 2:
                mVerify.setBackgroundResource(R.mipmap.ic_verify_failed);
                mVerify.setVisibility(View.VISIBLE);
                break;
            case 3:
                mVerify.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }
    private void initEvent() {
        mUserInfo.setOnClickListener(this);
        mBankCard.setOnClickListener(this);
        mIncomeDetails.setOnClickListener(this);
        mProjects.setOnClickListener(this);
        mGoMainPage.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvUserInfo:
                startActivity(new Intent(this, UserInfoActivity.class));
                break;
            case R.id.tvBankCard:
                startActivity(new Intent(this, BankCardActivity.class));
                break;
            case R.id.tvIncome:
                startActivity(new Intent(this, IncomeActivity.class));
                break;
            case R.id.tvProject:
                startActivity(new Intent(this, ProjectActivity.class));
                break;
            case R.id.tvMainPage:
                finish();
                break;
            default:
                break;
        }
    }

}
