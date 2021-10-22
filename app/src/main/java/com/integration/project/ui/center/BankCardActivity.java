package com.integration.project.ui.center;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.integration.project.BaseActivity;
import com.integration.project.R;
import com.integration.project.adapter.IncomeAdapter;
import com.integration.project.entity.BankBranchEntity;
import com.integration.project.entity.CardInfoEntity;
import com.integration.project.presenter.BankPresenter;
import com.integration.project.ui.MainActivity;
import com.integration.project.utils.Constants;
import com.integration.project.utils.SPUtil;
import com.integration.project.view.IBankInterface;
import com.integration.project.widgets.ComfirmDialog;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class BankCardActivity extends BaseActivity<IBankInterface, BankPresenter> implements IBankInterface {

    private RecyclerView mIncomes;
    private IncomeAdapter mAdapter;
    private List<BankBranchEntity.BranchBank> mIncomeList;

    private TextView mAddCard, mUserName, mCardNumber, mBankName;
    private LinearLayout mLLCard;
    private TextView mUnbindCard;
    private RelativeLayout mRlAddCard;

    private int mRouteType = 0;


    @Override
    public BankPresenter createPresenter() {
        return mPresenter = new BankPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_card);
        initData();
        initView();
        initEvent();
        mPresenter.getBankCardInfo();

    }

    private void initData() {
        mRouteType = getIntent().getIntExtra("route", 0);
    }

    private void initEvent() {
        mRlAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(BankCardActivity.this,
                        AddCardActivity.class), Constants.REQUEST_SUCCESS);
            }
        });
        mUnbindCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ComfirmDialog.showComfirmDialog(BankCardActivity.this,
                        new ComfirmDialog.OnUnbindDialogListener() {
                    @Override
                    public void onCancelClick(View view) {

                    }
                    @Override
                    public void onConfirmClick(View view) {
                        mPresenter.unbindCard();
//                        showToast("解绑了");
                    }
                });

            }
        });
        mGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRouteType == 0) {
                    finish();
                } else if (mRouteType == 1) {
                    //登录到首页
                    Intent intent = new Intent(BankCardActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_SUCCESS && resultCode == Constants.RESULT_BIND_SUCCESS) {
            CardInfoEntity entity = null;
            if (data != null) {
                entity = (CardInfoEntity) data.getSerializableExtra("card");
                if (entity != null) {
                    mRlAddCard.setVisibility(View.GONE);
                    mUnbindCard.setVisibility(View.VISIBLE);
                    mLLCard.setVisibility(View.VISIBLE);

                    mUserName.setText(entity.getName());
                    mCardNumber.setText(entity.getBankCardNo());
                    mBankName.setText(entity.getBankBranchName());
                } else {
                    mRlAddCard.setVisibility(View.VISIBLE);
                    mLLCard.setVisibility(View.GONE);
                    mUnbindCard.setVisibility(View.GONE);
                }
            }
        }
    }

    private void initView() {
        mGoBack = findViewById(R.id.tvReturn);
        mTitle = findViewById(R.id.tvTitleName);

        mTitle.setText(R.string.my_bank_card);
        mAddCard = findViewById(R.id.tvAddNewCard);
        mLLCard = findViewById(R.id.llCard);
        mRlAddCard = findViewById(R.id.rlAddCard);

        mUserName = findViewById(R.id.tvUserName);
        mCardNumber = findViewById(R.id.tvCardNumber);
        mBankName = findViewById(R.id.tvBankName);
        mUnbindCard = findViewById(R.id.tvUnbind);

        mLLCard.setVisibility(View.VISIBLE);
        mUnbindCard.setVisibility(View.GONE);
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
        //解绑成功
        showToast(message);
        mRlAddCard.setVisibility(View.VISIBLE);
        mLLCard.setVisibility(View.GONE);
        mUnbindCard.setVisibility(View.GONE);
    }

    @Override
    public void showRequestFailedView(String message) {
        showToast(message);
    }

    @Override
    public void setUserData(CardInfoEntity entity) {
        if (entity == null) {
            mRlAddCard.setVisibility(View.VISIBLE);
            mLLCard.setVisibility(View.GONE);
            mUnbindCard.setVisibility(View.GONE);
        } else {
            mRlAddCard.setVisibility(View.GONE);
            mLLCard.setVisibility(View.VISIBLE);
            mUnbindCard.setVisibility(View.VISIBLE);

            mUserName.setText(entity.getName());
            mCardNumber.setText(entity.getBankCardNo());
            mBankName.setText(entity.getBankBranchName());

        }
    }
}
