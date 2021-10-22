package com.integration.project.ui.contract;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.integration.project.BaseActivity;
import com.integration.project.R;
import com.integration.project.entity.UnsignedContractEntity;
import com.integration.project.entity.UserContractIncomeEntity;
import com.integration.project.presenter.ContractPresenter;
import com.integration.project.utils.Constants;
import com.integration.project.utils.SPUtil;
import com.integration.project.view.IContractInterface;
import com.integration.project.widgets.SignContractDialog;
import com.joanzapata.pdfview.PDFView;

import java.io.File;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ContractActivity extends BaseActivity<IContractInterface, ContractPresenter> implements IContractInterface {

    private PDFView mPdfContract;
    private TextView mConfirm;
    private TextView mDownloadContract;
    private LinearLayout mLLTips;
    private TextView mMessageOperate;

    private String mDownloadUrl;
    private UnsignedContractEntity mContract;
    private int mSignCount = 1;
    private int mSignStatus = 1;
    private int mCompanyId = 0;
    private DownLoadCompleteReceiver mCompleteReceiver;
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
                                if (file.exists()) {
                                    mPdfContract.fromFile(file).load();
                                } else {
                                    mPdfContract.fromAsset("user_agreement.pdf").load();
                                    Log.i("ghb", "onReceive: 文件不存在");
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        mPdfContract.fromAsset("user_agreement.pdf").load();
                        return;
                    } finally {
                        c.close();
                    }
                }
            }
        }
    }
    @Override
    public ContractPresenter createPresenter() {
        return mPresenter = new ContractPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract);
        mCompleteReceiver = new DownLoadCompleteReceiver();
        registerReceiver(mCompleteReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        initData();
        initView();
        initEvent();

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

        //请求合同信息
        mPresenter.getUsignedContractInfo();

    }

    private void initView() {
        mGoBack = findViewById(R.id.tvReturn);
        mTitle = findViewById(R.id.tvTitleName);
        mTitle.setText(R.string.sign_online);

        mConfirm = findViewById(R.id.tvConfirm);
        mPdfContract = findViewById(R.id.pdfContract);
        mDownloadContract = findViewById(R.id.tvDownloadContract);
        mLLTips = findViewById(R.id.llSuccessTips);
        mMessageOperate = findViewById(R.id.tvMessageOperateTip);

        mLLTips.setVisibility(View.GONE);
        mMessageOperate.setVisibility(View.GONE);
        mDownloadContract.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

        mPdfContract.fromAsset("user_agreement.pdf").load();
    }

    private void initEvent() {
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSignCount > 0) {
                    //请求签约
                    mPresenter.signContract();
                    //开启(有感知)签约
                    if(mSignStatus == 1) {
                        SignContractDialog.showSignDialog(ContractActivity.this,
                                new SignContractDialog.OnPayDialogListener() {
                            @Override
                            public void onCancelClick(View view) {
                            }
                            @Override
                            public void onConfirmClick(View view) {
                            }
                        });
                    }
                } else {
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
        mDownloadContract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
    public void getUnsignContractInfo(UnsignedContractEntity data) {
        if (data != null) {
            mContract = data;
            mCompanyId =  mContract.getCompanyId();
            mDownloadUrl = mContract.getDownloadUrl();
            mSignStatus = mContract.getSignStatus();
            //启动浏览器下载合同
            if (!TextUtils.isEmpty(mDownloadUrl)) {
                String path = Environment.DIRECTORY_DOWNLOADS;
                String fileName = "服务合同文本.pdf";
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(mDownloadUrl));
                request.setDestinationInExternalPublicDir(path,fileName );

                DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                long enqueueId = downloadManager.enqueue(request);

            }
        } else {
            showToast("获取待签约合同失败");
        }
    }

    @Override
    public void requestContractInfoFailed(String message) {
        showToast(message);
    }

    @Override
    public void requestContractCountFailed(String message) {
        showToast(message);
    }

    @Override
    public void showRequestSuccessView(String message) {
       mLLTips.setVisibility(View.VISIBLE);
        //短信签约成功后，请求未签约合同数量
        mPresenter.getUnsignContractCount();

    }

    @Override
    public void showRequestFailedView(String message) {
        //签约失败
        showToast(message);
       mLLTips.setVisibility(View.GONE);
       //提示用户
       mMessageOperate.setVisibility(View.VISIBLE);
    }

    @Override
    public Integer getCompanyId() {
        return mCompanyId;
    }

    @Override
    public void getUnsignContractCount(UserContractIncomeEntity data) {
        if (data != null) {
            mSignCount = data.getSigned();
            if (mSignCount > 0) {
                mConfirm.setText("短信签约完成,点击签署下一个");
            }else {
                mConfirm.setText("短信签约全部完成");
               mLLTips.setVisibility(View.GONE);
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mCompleteReceiver);
    }

}
