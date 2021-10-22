package com.integration.project.ui.center;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.integration.project.BaseActivity;
import com.integration.project.R;
import com.integration.project.adapter.BranchAdapter;
import com.integration.project.entity.BankBranchEntity;
import com.integration.project.entity.CardInfoEntity;
import com.integration.project.presenter.AddCardPresenter;
import com.integration.project.utils.Constants;
import com.integration.project.utils.SPUtil;
import com.integration.project.view.IAddCardInterface;
import com.integration.project.widgets.BranchBankDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AddCardActivity extends BaseActivity<IAddCardInterface, AddCardPresenter>
        implements IAddCardInterface, View.OnClickListener, BranchAdapter.OnSelectedClickListener, BranchBankDialog.OnBranchBankDialogListener {

    private EditText mEtUserName, mEtCardNumber, mEtBankName;
    private TextView mMoreNames,mShowBranchName;
    private Button mBindCard;

    private String mUserName, mBankName, mCardNumber, mBranchName, mProvince, mCity, mCnapsCode;
    private BranchAdapter mBranchAdapter;
    private List<BankBranchEntity.BranchBank> mBranchList = new ArrayList<>();

    private RelativeLayout mRlList;
    private RecyclerView mRvBranchBanks;
    private LinearLayout mLlResult;
    private TextView mBranchName2,mBankCity;

    @Override
    public AddCardPresenter createPresenter() {
        return mPresenter = new AddCardPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        initView();
        initEvent();
        initData();

    }

    private void initData() {

    }

    private void initView() {
        mGoBack = findViewById(R.id.tvReturn);
        mTitle = findViewById(R.id.tvTitleName);
        mTitle.setText(R.string.add_card);

        mEtUserName = findViewById(R.id.tvUserName);
        mEtBankName = findViewById(R.id.tvBankName);
        mEtCardNumber = findViewById(R.id.tvCardNumber);

        mMoreNames = findViewById(R.id.tvMoreNames);
        mBindCard = findViewById(R.id.btBindCard);

        mRvBranchBanks = findViewById(R.id.rvBranchBank);

        mShowBranchName = findViewById(R.id.tvShowBranchName);
        mBranchName2 = findViewById(R.id.tvShowBranchName2);
        mBankCity = findViewById(R.id.tvShowBankCity);
        mLlResult = findViewById(R.id.llResult);

        mRlList = findViewById(R.id.rlList);

    }

    private void initEvent() {
        mBindCard.setOnClickListener(this);
        mMoreNames.setOnClickListener(this);
        mShowBranchName.setOnClickListener(this);
        mGoBack.setOnClickListener(this);

        mRvBranchBanks.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        mEtUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mUserName = mEtUserName.getText().toString().trim();
            }
        });
        mEtCardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mCardNumber = mEtCardNumber.getText().toString().trim();
            }
        });
        mEtBankName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //隐藏搜索结果
                mLlResult.setVisibility(View.GONE);
            }
            @Override
            public void afterTextChanged(Editable s) {
                mBankName = mEtBankName.getText().toString().trim();
            }
        });
    }

    @Override
    public void setUserData(CardInfoEntity entity) {
        //绑定成功
        Intent intent = new Intent();
        intent.putExtra("card", entity);
        setResult(Constants.RESULT_BIND_SUCCESS);
        finish();

    }

    @Override
    public String getUserName() {
        return mUserName;
    }

    @Override
    public String getBankName() {
        return mBankName;
    }

    @Override
    public String getCardNumber() {
        return mCardNumber;
    }

    @Override
    public String getBranchName() {
        return mBranchName;
    }

    @Override
    public String getCity() {
        return mCity;
    }

    @Override
    public String getCnapsCode() {
        return mCnapsCode;
    }

    @Override
    public String getProvince() {
        return mProvince;
    }

    @Override
    public void showBranchNames(List<BankBranchEntity.BranchBank> list) {
        //展示支行列表
        if (list != null && list.size() > 0) {
            mBranchList = list;
            if (mBranchList.size() > 0) {
                BankBranchEntity.BranchBank branchBank = mBranchList.get(0);
                if (branchBank != null) {
                    mLlResult.setVisibility(View.VISIBLE);

                    mBranchName = branchBank.getBankName();
                    mProvince = branchBank.getProvince();
                    mCity = branchBank.getCity();
                    mCnapsCode = branchBank.getCnapsCode();

                    mShowBranchName.setText(branchBank.getBankName());
                    mBranchName2.setText(branchBank.getBank());
                    mBankCity.setText(mProvince + " —— " + mCity);

                }
            }
        } else {
            showToast("没有查询到这个银行");
        }
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvMoreNames:
                mPresenter.showMoreNames();
                break;
            case R.id.tvShowBranchName:

                BranchBankDialog.showBranchBankDialog(this, mBranchList, this);
//                showBranchBankList(mBranchList);
                break;
            case R.id.btBindCard:
                mPresenter.bindCard();
                break;
            case R.id.rlList:

                break;
            case R.id.tvReturn:
                finish();
                break;
            default:
                break;
        }
    }

    private void showBranchBankList(List<BankBranchEntity.BranchBank> branchList) {
        if (branchList != null) {
            mRlList.setVisibility(View.VISIBLE);
            mBranchAdapter = new BranchAdapter(this, branchList, this);
            mRvBranchBanks.setAdapter(mBranchAdapter);
            mRvBranchBanks.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSelectedClickListener(View view, int position) {
        BankBranchEntity.BranchBank branchBank = mBranchList.get(position);
        if (branchBank != null) {
            mBranchName = branchBank.getBankName();
            mProvince = branchBank.getProvince();
            mCity = branchBank.getCity();
            mCnapsCode = branchBank.getCnapsCode();

            mShowBranchName.setText(mBranchName);
            mBranchName2.setText(branchBank.getBank());
            mBankCity.setText(mProvince +" —— "+mCity);
        } else {
            showToast("没有找到这个支行，请您重新选择。");
        }
    }

    @Override
    public void onCancelClick(View view) {

    }

    @Override
    public void onSelectedConfirmClick(View view, int position) {
        BankBranchEntity.BranchBank branchBank = mBranchList.get(position);
        if (branchBank != null) {
            mBranchName = branchBank.getBankName();
            mProvince = branchBank.getProvince();
            mCity = branchBank.getCity();
            mCnapsCode = branchBank.getCnapsCode();

            mShowBranchName.setText(mBranchName);
            mBranchName2.setText(branchBank.getBank());
            mBankCity.setText(mProvince +" —— "+mCity);
        } else {
            showToast("没有找到这个支行，请您重新选择。");
        }
    }
}
