package com.integration.project.ui.center;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.integration.project.BaseActivity;
import com.integration.project.R;
import com.integration.project.adapter.ProjectAdapter;
import com.integration.project.adapter.ProjectAdapter.OnMyItemClickListener;
import com.integration.project.entity.UserContractEntity;
import com.integration.project.presenter.ProjectPresenter;
import com.integration.project.utils.LogUtils;
import com.integration.project.utils.SPUtil;
import com.integration.project.view.IProjectInterface;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class ProjectActivity extends BaseActivity<IProjectInterface, ProjectPresenter> implements OnMyItemClickListener, IProjectInterface {
    private static final String TAG = "ghb";

    private RecyclerView mProjects;
    private ProjectAdapter mAdapter;
    private List<UserContractEntity.ContractList> mProjectList = new ArrayList<>();
    private int mTotalPage = 0;
    private int mStartPage = 0;
    private int mLimit = 15;

    private ProgressBar mProgressBar;
    private TextView mLoadMore;
    private String mDownloadUrl;

    private SwipeRefreshLayout mRefreshLayout;
    private View mFootView;

    @Override
    public ProjectPresenter createPresenter() {
        return mPresenter = new ProjectPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        initView();
        initEvent();
        mPresenter.getUserProjects(mStartPage, mLimit);
    }

    private void initView() {
        mRefreshLayout = findViewById(R.id.refresh);
        mGoBack = findViewById(R.id.tvReturn);
        mTitle = findViewById(R.id.tvTitleName);
        mTitle.setText(R.string.my_project_name);
        mFootView = LayoutInflater.from(this).inflate(R.layout.item_foot_view, null, false);
        mLoadMore = mFootView.findViewById(R.id.tvMore);
        mProgressBar = mFootView.findViewById(R.id.progressMore);

        mProjects = findViewById(R.id.rvProjects);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mProjects.setLayoutManager(layoutManager);
        mAdapter = new ProjectAdapter(this, mProjectList, this);
        mProjects.setAdapter(mAdapter);

        mRefreshLayout.setColorSchemeResources(R.color.text_red, R.color.gray_color, R.color.green);
        mRefreshLayout.setRefreshing(true);
    }

    private void initEvent() {
        mAdapter.setItemClickListener(this);
        mGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getUserProjects(mStartPage, mLimit);
            }
        });
        mProjects.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    int itemCount = layoutManager.getItemCount();
                    if (mProgressBar.getVisibility() == View.GONE && lastVisibleItemPosition > itemCount - 1 && dy > 0) {
                        mProgressBar.setVisibility(View.VISIBLE);
                        loadMoreData();
                    }
                }
            }
        });
    }

    private void loadMoreData() {
        mStartPage++;
        if (mStartPage < mTotalPage) {
            mPresenter.getUserProjects(mStartPage , mLimit);
        }
    }

    @Override
    public void onMyItemClickListener(View view, int position) {
        UserContractEntity.ContractList project = mProjectList.get(position);
        if (project != null) {
            mDownloadUrl = project.getUrl();
            if (!TextUtils.isEmpty(mDownloadUrl)) {
                downloadPdfFromBrowser(mDownloadUrl);
//                showPdfContract(project);

            } else {
                showToast("没有找到合同下载地址");
            }
        }
    }

    private void showPdfContract(UserContractEntity.ContractList project) {
        Intent intent = new Intent(this, ProjDetailActivity.class);
        intent.putExtra("project", project);
        startActivity(intent);

    }

    private void downloadPdfFromBrowser(String downloadUrl) {
        Uri uri = Uri.parse(mDownloadUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    public void setProjectData(UserContractEntity data) {
        mRefreshLayout.setRefreshing(false);
        mProgressBar.setVisibility(View.GONE);
        if (data != null) {
            mTotalPage = data.getTotalPage();
            mProjectList.addAll(data.getList());
//            mProjectList = data.getList();
            if (mAdapter == null) {
                mAdapter = new ProjectAdapter(this, mProjectList, this);
                mAdapter.setFootView(mProjects, mFootView);
                mProjects.setAdapter(mAdapter);
            } else {
                mAdapter.notifyDataSetChanged();
            }
            LogUtils.i(TAG, "setProjectData: " + mProjectList.toString());
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
        showToast(message);
    }
}
