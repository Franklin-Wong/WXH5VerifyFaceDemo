package com.integration.project.ui.center;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.integration.project.BaseActivity;
import com.integration.project.R;
import com.integration.project.entity.UserInfoEntity;
import com.integration.project.presenter.UserInfoPresenter;
import com.integration.project.utils.SPUtil;
import com.integration.project.view.center.IUserInfoInterface;

public class UserInfoActivity extends BaseActivity<IUserInfoInterface, UserInfoPresenter> implements IUserInfoInterface {

    private TextView mName, mIdNumber, mPhone;

    @Override
    public UserInfoPresenter createPresenter() {
        return mPresenter = new UserInfoPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();
        initEvent();
        mPresenter.getUserInfo();
    }

    private void initView() {
        mName = findViewById(R.id.tvName);
        mIdNumber = findViewById(R.id.tvIDNumber);
        mPhone = findViewById(R.id.tvPhone);

        mTitle = findViewById(R.id.tvTitleName);
        mGoBack = findViewById(R.id.tvReturn);
        mTitle.setText(R.string.user_info);
    }

    private void initEvent() {

        mGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
        showToast(message);
    }

    @Override
    public void showRequestFailedView(String message) {
        showToast(message);
    }

    @Override
    public void setUserInfo(UserInfoEntity entity) {
        mName.setText(entity.getName());
        String telephone = entity.getTelephone();
        mPhone.setText(telephone);
        mIdNumber.setText(entity.getIdCardNo());
    }

}
