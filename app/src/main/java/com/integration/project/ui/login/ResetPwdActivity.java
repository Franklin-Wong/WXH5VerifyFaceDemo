package com.integration.project.ui.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.integration.project.BaseActivity;
import com.integration.project.R;
import com.integration.project.presenter.ResetPwdPresenter;
import com.integration.project.utils.MatchUtils;
import com.integration.project.view.login.IResetPwdInterface;
import com.integration.project.widgets.TimeCounter;

public class ResetPwdActivity extends BaseActivity<IResetPwdInterface, ResetPwdPresenter> implements View.OnClickListener, IResetPwdInterface {
    private EditText mEtPhoneNumber, mEtPwd, mEtPwd2, mEtMessageCode;
    private Button mBtRest;
    private TextView mGetCode, mLogin;
    private String mPhoneNumber, mPwd, mPwd2, mMessageCode;
    private TimeCounter mTimeCounter;


    @Override
    public ResetPwdPresenter createPresenter() {
        return mPresenter = new ResetPwdPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);
        initView();
        initEvent();

    }

    private void initView() {

        mEtPhoneNumber = findViewById(R.id.etPhoneNumber);
        mEtMessageCode = findViewById(R.id.etMessageCode);
        mEtPwd = findViewById(R.id.etPwd);
        mEtPwd2 = findViewById(R.id.etPwd2);
        mGetCode = findViewById(R.id.tvGetCode);
        mLogin = findViewById(R.id.tvLogin);

        mBtRest = findViewById(R.id.btReset);
    }

    private void initEvent() {
        mGetCode.setOnClickListener(this);
        mBtRest.setOnClickListener(this);
        mLogin.setOnClickListener(this);

        mEtPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mPhoneNumber = mEtPhoneNumber.getText().toString().trim();
            }
        });
        mEtMessageCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mMessageCode = mEtMessageCode.getText().toString().trim();
            }
        });
        mEtPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPwd = mEtPwd.getText().toString().trim();
            }
        });
        mEtPwd2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPwd2 = mEtPwd2.getText().toString().trim();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btReset:
                if (TextUtils.isEmpty(mPhoneNumber)) {
                    showToast("请您填写手机号码");
                    return;
                }
                if (!MatchUtils.isMobileRight(mPhoneNumber)) {
                    showToast("请您填写正确的手机号码");
                    return;
                }

                if (TextUtils.isEmpty(mPwd)) {
                    showToast("请您填写密码。");
                    return;
                } else if (!MatchUtils.isPwdRight(mPwd)) {
                    showToast("请您填写正确的密码。");
                    return;
                }
                if (TextUtils.isEmpty(mPwd2)) {
                    showToast("请您再次确认登录密码。");
                    return;
                } else if (!MatchUtils.isPwdRight(mPwd2)) {
                    showToast("请您再次确认正确的登录密码。");
                    return;
                }
                if (!mPwd.equals(mPwd2)) {
                    showToast("两次输入密码不一致。");
                    return;
                }
                if (TextUtils.isEmpty(mMessageCode)) {
                    showToast("请您填写验证码");
                    return;
                }
                if (!MatchUtils.isVerifycationCodeRight(mMessageCode)) {
                    showToast("请您填写正确的验证码");
                    return;
                }
                mPresenter.resetPwd();
                break;
            case R.id.tvGetCode:
                if (TextUtils.isEmpty(mPhoneNumber)) {
                    showToast("请您填写手机号码");
                    return;
                }
                else if (!MatchUtils.isMobileRight(mPhoneNumber)) {
                    showToast("请您填写正确的手机号码");
                    return;
                }
                mPresenter.getMessageCode();
                break;
            case R.id.tvLogin:
                finish();
                break;
            default:
                break;
        }
    }


    @Override
    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    @Override
    public String getCode() {
        return mMessageCode;
    }

    @Override
    public String getPwd() {
        return mPwd;
    }

    @Override
    public String getPwd2() {
        return mPwd2;
    }

    @Override
    public void showGetCodeSuccessView(String message) {
        mTimeCounter = new TimeCounter(60000, 1000, mGetCode);
        mTimeCounter.start();
        showToast(message);
    }

    @Override
    public void showGetCodeFailedView(String message) {
        mGetCode.setClickable(true);
        showToast(message);
    }

    @Override
    public void showRequestFailedView(String message) {
        showToast(message);
    }

    @Override
    public String getUserToken() {
        return null;
    }

    @Override
    public Integer getUserId() {
        return null;
    }


    @Override
    public void showRequestSuccessView(String message) {
        showToast(message);
        //修改成功，回到登录
        finish();
    }
}


