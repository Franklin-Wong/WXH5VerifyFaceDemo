package com.integration.project.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;

import com.integration.project.BaseActivity;
import com.integration.project.R;
import com.integration.project.presenter.FacePresenter;
import com.integration.project.sdk.WBH5FaceVerifySDK;
import com.integration.project.ui.MainActivity;
import com.integration.project.utils.SPUtil;
import com.integration.project.view.IBaseViewInterface;
import com.integration.project.web.AppChromeClient;
import com.integration.project.web.AppWebViewClient;
import com.integration.project.web.LoadingWebView;
import com.integration.project.web.WebViewClientManager;
import com.integration.project.widgets.WebLoadFailedView;

public class FaceActivity extends BaseActivity<IBaseViewInterface, FacePresenter> implements IBaseViewInterface {

    private LoadingWebView mWebView;
    private String mPhoneNumber, mAuthUrl;
    private WebLoadFailedView mLoadFailedView;
    private AppWebViewClient mClient;
    private AppChromeClient mChromClient;
    private WebViewClientManager mWebViewClientManager;
    private int mType = 0;

    @Override
    public FacePresenter createPresenter() {
        return mPresenter = new FacePresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face);
        initData();
        initView();
        initEvent();
        initWebSetting();
    }
    private void initData() {
        mAuthUrl = getIntent().getStringExtra("url");
        mPhoneNumber = getIntent().getStringExtra("telephone");
        mType = getIntent().getIntExtra("type", 0);

    }
    private void initView() {
        mLoadFailedView = findViewById(R.id.webViewFailed);
        mWebView = findViewById(R.id.webView);
        mTitle = findViewById(R.id.tvTitleName);
        mGoBack = findViewById(R.id.tvReturn);
        mTitle.setText(R.string.face_verify);

    }

    private void initEvent() {
        mGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //????????????
                if (mType == 1) {
                    startActivity(new Intent(FaceActivity.this, SignupSuccessActivity.class));
                    finish();
                } else if (mType == 2) {
                    //????????????
                    startActivity(new Intent(FaceActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
        mLoadFailedView.setOnRetryClickListener(new WebLoadFailedView.OnRetryClickListener() {
            @Override
            public void onRetryClickListener(View view) {
                initWebSetting();
                //??????????????????
                mClient.setLoadingFailed(false);
                String lastUrl = mClient.getUrlStack();
                mWebView.reload();
                mLoadFailedView.showLoadingStatus();

            }
        });

        mWebView.setOnWebScrollListener(new LoadingWebView.OnWebViewScrollListener() {
            @Override
            public void onScroll(boolean isTop) {
                if (isTop){
                    mWebView.scrollTo(0,0);
                }
            }
        });
    }


    private void initWebSetting() {
        mWebView.setBackgroundColor(0);

        mWebViewClientManager = new WebViewClientManager(mWebView, true);
        mWebViewClientManager.onCreate();

        mChromClient = new AppChromeClient(this);
        mClient = new AppWebViewClient();
        mWebView.setWebViewClient(mClient);
        mWebView.setWebChromeClient(mChromClient);

        mWebView.addJavascriptInterface(new JsToJava(), "jsToJava");


        /**
         * ??? WebSettings ????????????????????? ua ??????????????? h5 ???????????????
         * @param mWebView  ???????????? WebView ??????
         * @param context  ??????????????????
         */
        WBH5FaceVerifySDK.getInstance().setWebViewSettings(mWebView,getApplicationContext());
        mWebView.loadUrl(mAuthUrl);
//        mWebView.loadUrl("file:///android_asset/app.html");
    }


    private final class JsToJava {

        @JavascriptInterface
        public void verifyResult() {
            showToast("??????????????????");
            //????????????
            if (mType == 1) {
                startActivity(new Intent(FaceActivity.this, SignupSuccessActivity.class));
                finish();
            } else if (mType == 2) {
                //????????????
                startActivity(new Intent(FaceActivity.this, MainActivity.class));
                finish();
            } else {
                startActivity(new Intent(FaceActivity.this, MainActivity.class));
            }
        }

        @JavascriptInterface
        public String getMsgFromAndroid(String msg) {
            return "??????Android?????????????????????" + msg;
        }

    }
    /**
     *?????????WebView?????????Activity?????????
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (WBH5FaceVerifySDK.getInstance().receiveH5FaceVerifyResult(requestCode,resultCode,data)) {
            return;
        }
    }

    @Override
    public String getUserToken() {
        return null;
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
