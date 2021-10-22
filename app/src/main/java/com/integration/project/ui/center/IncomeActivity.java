package com.integration.project.ui.center;

import android.os.Bundle;
import android.view.View;

import com.integration.project.BaseActivity;
import com.integration.project.R;
import com.integration.project.adapter.IncomeAdapter;
import com.integration.project.entity.IncomeDetailEntity;
import com.integration.project.presenter.IncomePresenter;
import com.integration.project.utils.SPUtil;
import com.integration.project.view.IIncomeInterface;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class IncomeActivity extends BaseActivity<IIncomeInterface, IncomePresenter> implements IncomeAdapter.OnMyItemClickListener, IIncomeInterface {
    private RecyclerView mIncomes;
    private IncomeAdapter mAdapter;
    private List<IncomeDetailEntity.Income> mIncomeList = new ArrayList<>();


    private int mTotalPage = 0;
    private int mStartPage = 0;
    private int limit = 10;
    @Override
    public IncomePresenter createPresenter() {
        return mPresenter = new IncomePresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        initView();
        initEvent();
        mPresenter.getUserIncome(mStartPage, limit);

    }

    private void initView() {
        mGoBack = findViewById(R.id.tvReturn);
        mTitle = findViewById(R.id.tvTitleName);
        mTitle.setText(R.string.income);
        mIncomes = findViewById(R.id.rvIncomes);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        mIncomes.setLayoutManager(layoutManager);
        mAdapter = new IncomeAdapter(this, mIncomeList, this);
        mIncomes.setAdapter(mAdapter);
    }

    private void initEvent() {
        mAdapter.setItemClickListener(this);
        mGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public void onMyItemClickListener(View view, int position) {

    }

    @Override
    public void setUserData(IncomeDetailEntity entity) {
        mIncomeList = entity.getIncomeList();
        if (mIncomeList != null) {
            mAdapter = new IncomeAdapter(this, mIncomeList, this);
            mIncomes.setAdapter(mAdapter);
            mTotalPage = entity.getTotalPage();
        } else {
            showToast("暂时没有收入明细");
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

    }

    @Override
    public void showRequestFailedView(String message) {
        mIncomes.setVisibility(View.GONE);
        showToast(message);

    }
}
