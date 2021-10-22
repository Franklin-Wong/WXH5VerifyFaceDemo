package com.integration.project.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.integration.project.R;
import com.integration.project.ui.center.BankCardActivity;
import com.integration.project.widgets.TimeCounter;

import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;

public class SignupSuccessActivity extends AppCompatActivity {
    private TextView mTitle;
    private ImageView mGoBack;
    private TextView mSignIn, mTimeCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_success);
        initView();
        initEvent();
        initData();
    }

    private void initEvent() {
        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBindCard();
            }
        });
    }

    private void startBindCard() {
        Intent intent = new Intent(SignupSuccessActivity.this, BankCardActivity.class);
        intent.putExtra("route", 1);
        startActivity(intent);
        finish();
    }

    private void initView() {
        mGoBack = findViewById(R.id.tvReturn);
        mTitle = findViewById(R.id.tvTitleName);
        mTitle.setText(R.string.signup_success);
        mGoBack.setVisibility(View.GONE);

        mTimeCount = findViewById(R.id.tvCountTimer);
        mSignIn = findViewById(R.id.tvSignIn);
    }

    private void initData() {
        new TimeCounter(5000, 1000, mTimeCount).start();
        //创建计时线程

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startBindCard();
            }
        }, 4000);

    }
    long keyTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - keyTime > 2000) {
                keyTime = System.currentTimeMillis();
                Toast.makeText(this, "请您立即登录", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                startBindCard();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
