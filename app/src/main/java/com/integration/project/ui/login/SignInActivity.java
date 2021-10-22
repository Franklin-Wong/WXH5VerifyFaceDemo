package com.integration.project.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.integration.project.BaseActivity;
import com.integration.project.R;
import com.integration.project.entity.SignInEntity;
import com.integration.project.presenter.SignInPresenter;
import com.integration.project.ui.MainActivity;
import com.integration.project.utils.MatchUtils;
import com.integration.project.utils.NetWorkUtils;
import com.integration.project.utils.SPUtil;
import com.integration.project.view.login.ISigninInterface;
import com.integration.project.widgets.TimeCounter;

public class SignInActivity extends BaseActivity<ISigninInterface, SignInPresenter> implements View.OnClickListener, ISigninInterface {
    private EditText mEtPhoneNumber,  mEtMessageCode;
    private Button mBtSignIn;
    private TextView mGetCode,mLoginPwd, mResetPwd, mSignUp;
    private String mPhoneNumber, mPwd, mMessageCode;
    private boolean isPwdType = true;
    private TimeCounter mTimeCounter;
    private String mAuthUrl;

    @Override
    public SignInPresenter createPresenter() {
        return mPresenter = new SignInPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        if (!NetWorkUtils.isNetworkAvailable(this)) {
            showToast("没有未来连接，请检查您的网络设置。");
        }
        initView();
        initEvent();
    }
    private void initView() {
        mEtPhoneNumber = findViewById(R.id.etPhoneNumber);
        mEtMessageCode = findViewById(R.id.etMessageCode);
        mGetCode = findViewById(R.id.tvGetCode);
        mBtSignIn = findViewById(R.id.btSignIn);
        mSignUp = findViewById(R.id.tvSignUp);

        mLoginPwd = findViewById(R.id.tvLoginPwd);
        mResetPwd = findViewById(R.id.tvReset);
    }

    private void initEvent() {
        mBtSignIn.setOnClickListener(this);
        mSignUp.setOnClickListener(this);
        mGetCode.setOnClickListener(this);

        mLoginPwd.setOnClickListener(this);
        mResetPwd.setOnClickListener(this);

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
                if (isPwdType) {
                    mPwd = mEtMessageCode.getText().toString().trim();

                } else {
                    mMessageCode = mEtMessageCode.getText().toString().trim();
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btSignIn:

                if (TextUtils.isEmpty(mPhoneNumber)) {
                    showToast("请您填写正确的手机号码");
                    return;
                } else if (!MatchUtils.isMobileRight(mPhoneNumber)) {
                    showToast("请您填写正确的手机号码");
                    return;
                }
                if (isPwdType) {
                    if (TextUtils.isEmpty(mPwd)) {
                        showToast("请您填写登录密码。");
                        return;
                    } else if (!MatchUtils.isPwdRight(mPwd)) {
                        showToast("请您填写正确的登录密码。");
                        return;
                    }
                }else {
                    if (TextUtils.isEmpty(mMessageCode)) {
                        showToast("请您填写短信验证码。");
                        return;
                    } else if (!MatchUtils.isVerifycationCodeRight(mMessageCode)) {
                        showToast("请您填写正确的短信验证码。");
                        return;
                    }
                }

                mPresenter.signIn();
                break;
            case R.id.tvGetCode:
                if (TextUtils.isEmpty(mPhoneNumber)) {
                    showToast("请您填写正确的手机号码");
                    return;
                } else if (!MatchUtils.isMobileRight(mPhoneNumber)) {
                    showToast("请您填写正确的手机号码");
                    return;
                }
                mPresenter.getCode();
                break;
            case R.id.tvLoginPwd:

                mEtMessageCode.setText("");
                isPwdType = !isPwdType;
                //密码登录
                if (isPwdType) {
                    mEtMessageCode.setHint("请您输入登录密码");
                    mEtMessageCode.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mEtMessageCode.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mEtMessageCode.setCompoundDrawablesRelativeWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_pwd, null), null,null,null);
                    mGetCode.setVisibility(View.GONE);
                    mLoginPwd.setText("短信验证码登录");

                } else {
                    //验证码登录
                    mEtMessageCode.setHint("请您输入短信验证码");
                    mEtMessageCode.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mEtMessageCode.setInputType(InputType.TYPE_CLASS_NUMBER);
                    mEtMessageCode.setCompoundDrawablesRelativeWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_message, null), null,null,null);

                    mGetCode.setVisibility(View.VISIBLE);
                    mLoginPwd.setText("密码登录");

                }

                break;
            case R.id.tvReset:
                startActivity(new Intent(this, ResetPwdActivity.class));
                break;
            case R.id.tvSignUp:
                startActivity(new Intent(this, SignUpActivity.class));
                break;
            default:
                break;
        }

    }

    long keyTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //首次按键， 相减值大于2， 提示用户
            if (System.currentTimeMillis() - keyTime > 2000) {
                //改变初始化值
                keyTime = System.currentTimeMillis();
                showToast("再按一次退出应用。");
                return false;
            } else {
                //2秒内二次按钮，相减如果值小于等于2， 退出
                finish();
                showToast("安全退出");
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    @Override
    public String getPwdOrCode() {
        if (isPwdType) {
            return mPwd;
        } else {
            return mMessageCode;
        }
    }

    @Override
    public int getSignType() {
        if (isPwdType) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public void saveUserData(SignInEntity data) {

        SPUtil.saveToken(this, data.getToken());
        SPUtil.saveEmployeeId(this, data.getEmployeeId());
        SPUtil.saveName(this, data.getName());
        mAuthUrl = data.getAuthUrl();
        if (!TextUtils.isEmpty(mAuthUrl)) {
            //登录成功，开始人脸识别
            Intent intent = new Intent(this, FaceActivity.class);
            intent.putExtra("url", mAuthUrl);
            intent.putExtra("telephone", mPhoneNumber);
            intent.putExtra("type", 2);
            startActivity(intent);
            finish();
        } else {
            //登录到首页
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void showRequestFailedView(String message) {
        showToast(message);
    }

    @Override
    public void showRequestSuccessView(String message) {
        showToast(message);
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
}
