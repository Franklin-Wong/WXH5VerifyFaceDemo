package com.integration.project.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.integration.project.BaseActivity;
import com.integration.project.R;
import com.integration.project.entity.HomeEntity;
import com.integration.project.entity.UserContractIncomeEntity;
import com.integration.project.presenter.HomPagePresenter;
import com.integration.project.ui.center.IncomeActivity;
import com.integration.project.ui.center.UserCenterActivity;
import com.integration.project.ui.contract.ContractActivity;
import com.integration.project.utils.SPUtil;
import com.integration.project.view.IHomePageInterface;
import com.integration.project.widgets.UserContractDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

public class MainActivity extends BaseActivity<IHomePageInterface, HomPagePresenter> implements View.OnClickListener, IHomePageInterface, UserContractDialog.OnPayDialogListener {

    private TextView mName,mVerifyResult, mUserCenter, mIncomeAnnual,
            mIncomeMonthly, mUserContract, mUserTip;
    private ImageView mIvVerify;
    private TextView mIncomeDetails;
    private CardView mSignContract;
    private boolean isLogout = false;

    private int mContractNumber = 0;
    private List<UserContractIncomeEntity.ContractIncome> mDataList = new ArrayList<>();
    private int mStatus = 0;
    private int REQUEST_SIGN = 1900;

    @Override
    public HomPagePresenter createPresenter() {
        return mPresenter = new HomPagePresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
        mPresenter.getHomePageInfo();
        mPresenter.getUnsignContractIncome();

    }

    private void initView() {
        mName = findViewById(R.id.tvName);
        mIncomeAnnual = findViewById(R.id.tvIncomeAnnual);
        mIncomeMonthly = findViewById(R.id.tvIncomeMonthly);
        mVerifyResult = findViewById(R.id.tvVerify);
        mUserCenter = findViewById(R.id.tvUserCenter);
        mUserContract = findViewById(R.id.tvUserContract);
        mUserTip = findViewById(R.id.tvUserTips);
        mSignContract = findViewById(R.id.signContract);

        mIvVerify = findViewById(R.id.ivVerify);
        mIncomeDetails = findViewById(R.id.tvIncomeDetails);
    }

    private void initEvent() {
        mUserCenter.setOnClickListener(this);
        mIncomeDetails.setOnClickListener(this);
        mSignContract.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvUserCenter:
                Intent intent = new Intent(this, UserCenterActivity.class);
                startActivity(intent);
                break;
            case R.id.tvIncomeDetails:
                startActivity(new Intent(this, IncomeActivity.class));
                break;
            case R.id.signContract:
                if (mContractNumber > 0) {
                    startActivity(new Intent(MainActivity.this, ContractActivity.class));
                }
                break;
            default:
                break;
        }
    }

    long keyTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - keyTime >= 2000) {
                keyTime = System.currentTimeMillis();
                Toast.makeText(this, "再按一次就退出应用", Toast.LENGTH_SHORT).show();
                return false;
                //
            } else if (System.currentTimeMillis() - keyTime < 2000) {
                Toast.makeText(this, "安全退出", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public String getUserToken() {
        return SPUtil.shareToken(this);
    }

    @Override
    public Integer getUserId() {
        return SPUtil.shareUserId(this);
    }
    @Override
    public void showRequestSuccessView(String message) {

    }

    @Override
    public void showRequestFailedView(String message) {
        showToast(message);
    }

    @Override
    public void setHomePageData(HomeEntity entity) {
        mStatus = entity.getStatus();
        SPUtil.saveUserStatus(this, mStatus);
        mName.setText(entity.getName());
        switch (mStatus) {
            case 0:
                mIvVerify.setBackgroundResource(R.mipmap.ic_verify_failed);
                mIvVerify.setVisibility(View.VISIBLE);

                mVerifyResult.setText("未实名");
                mVerifyResult.setTextColor(Color.BLUE);
                break;
            case 1:
                mIvVerify.setBackgroundResource(R.mipmap.ic_verify_success);
                mIvVerify.setVisibility(View.VISIBLE);

                mVerifyResult.setText("已实名");
                mVerifyResult.setTextColor(Color.WHITE);
                break;
            case 2:
                mIvVerify.setBackgroundResource(R.mipmap.ic_verify_failed);
                mIvVerify.setVisibility(View.VISIBLE);
                mVerifyResult.setText("实名失败");
                mVerifyResult.setTextColor(Color.RED);
                break;
            case 3:
                mIvVerify.setVisibility(View.GONE);
                mVerifyResult.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    @Override
    public void setUnsignContractIncome(UserContractIncomeEntity data) {
        if (data != null) {
            String yearIncome = data.getCurrentYearIncome();
            if (!TextUtils.isEmpty(yearIncome)) {
                mIncomeAnnual.setText(yearIncome);
            }
            String monthIncome = data.getMonthIncome();
            if (!TextUtils.isEmpty(monthIncome)) {
                mIncomeMonthly.setText(monthIncome);
            }

            mDataList = data.getList();
            mContractNumber = data.getSigned();

            if (mContractNumber > 0) {
                mSignContract.setVisibility(View.VISIBLE);
                mUserContract.setText(String.format("%s%s%s", "您有", mContractNumber, "份待签约的电子合同"));
                UserContractDialog.showContractDialog(this, mContractNumber, mDataList, this);
            } else {
                mSignContract.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public void onCancelClick(View view) {
    }

    @Override
    public void onConfirmClick(View view) {

        startActivity(new Intent(MainActivity.this, ContractActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SIGN && resultCode == RESULT_OK) {
            //隐藏合同数提示
            mSignContract.setVisibility(View.GONE);
        }
    }
}
