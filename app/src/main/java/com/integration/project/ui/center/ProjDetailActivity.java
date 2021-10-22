package com.integration.project.ui.center;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;

import com.integration.project.BaseActivity;
import com.integration.project.R;
import com.integration.project.entity.UserContractEntity;
import com.integration.project.presenter.ProjDetailPresenter;
import com.integration.project.utils.Constants;
import com.integration.project.utils.SPUtil;
import com.integration.project.view.IBaseViewInterface;
import com.integration.project.view.IProjDetailInterface;
import com.joanzapata.pdfview.PDFView;

import java.io.File;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ProjDetailActivity extends BaseActivity<IProjDetailInterface, ProjDetailPresenter> implements IBaseViewInterface, IProjDetailInterface {
    private static final String TAG = "ghb";
    private PDFView mPdfContract;

    private String mDownloadUrl;
    private UserContractEntity.ContractList mProjectDetail;

    private DownLoadCompleteReceiver mCompleteReceiver;

    @Override
    public ProjDetailPresenter createPresenter() {
        return mPresenter = new ProjDetailPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proj_detail);
        mCompleteReceiver = new DownLoadCompleteReceiver();
        registerReceiver(mCompleteReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        initView();
        initEvent();
        initData();

    }

    public class DownLoadCompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
                //在广播中取出下载任务的id
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                DownloadManager.Query query = new DownloadManager.Query();
                DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                query.setFilterById(id);
                Cursor c = dm.query(query);
                if (c != null) {
                    try {
                        if (c.moveToFirst()) {
                            //获取文件下载路径
                            String filename = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                            int status = c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS));
                            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                                //启动更新
                                File file = new File(filename);
                                mPdfContract.recycle();
                                mPdfContract.fromFile(file).load();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    } finally {
                        c.close();
                    }

                }
            }
        }
    }
    private void initData() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Constants.REQUEST_WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    Constants.REQUEST_READ_EXTERNAL_STORAGE);
        }

        mProjectDetail = (UserContractEntity.ContractList) getIntent().getSerializableExtra("project");
        if (mProjectDetail != null) {
            mDownloadUrl = mProjectDetail.getUrl();
            if (!TextUtils.isEmpty(mDownloadUrl)) {
                showPdfContract(mDownloadUrl);
//                downloadPdfFromBrowser(mDownloadUrl);
            } else {
                showToast("获取待签约合同失败");
            }
        }
    }

    private void showPdfContract(String downloadUrl) {
        String path = Environment.DIRECTORY_DOWNLOADS;
        String fileName = "服务合同文本.pdf";
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(mDownloadUrl));
        request.setDestinationInExternalPublicDir(path,fileName );

        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        long enqueueId = downloadManager.enqueue(request);

    }

    private void downloadPdfFromBrowser(String downloadUrl) {
        Uri uri = Uri.parse(mDownloadUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    private void initView() {
        mPdfContract = findViewById(R.id.pdfContract);
        mTitle = findViewById(R.id.tvTitleName);
        mGoBack = findViewById(R.id.tvReturn);
        mTitle.setText(R.string.project_detail);
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
    public void showPDFFile(File file) {
        mPdfContract.fromFile(file).load();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mCompleteReceiver);
    }
}

