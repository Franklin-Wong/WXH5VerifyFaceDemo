package com.integration.project.web;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.integration.project.utils.NetWorkUtils;

import java.lang.ref.SoftReference;

import androidx.annotation.RequiresApi;

/**
 * Created by Wongerfeng on 2018/12/25.
 */

public class WebViewClientManager {
    private static final String TAG = "WebViewClientManager";

    private WebView mWebView;
    private AppWebViewClient mWebViewClient;
    private SoftReference<WebView> mSoftReference;
    private boolean mCacheEnable = true;

    public WebViewClientManager(WebView webView, boolean cacheEnable) {
        mSoftReference = new SoftReference<WebView>(webView);
        mWebViewClient = new AppWebViewClient();
        mCacheEnable = cacheEnable;
    }

    @RequiresApi(api = Build.VERSION_CODES.ECLAIR_MR1)
    @SuppressLint("SetJavaScriptEnabled")
    public void onCreate(){
        mWebView = mSoftReference.get();
        if (mWebView != null){
            mWebView.setWebViewClient(mWebViewClient);
            WebSettings settings = mWebView.getSettings();
            settings.setAppCacheEnabled(true);
            settings.setJavaScriptEnabled(true);

            //视图设置
            settings.setSupportMultipleWindows(false);
            settings.setDisplayZoomControls(true);
            settings.setUseWideViewPort(true);
            settings.setLoadsImagesAutomatically(true);
            //可以缩放
            settings.setSupportZoom(false);
            settings.setBuiltInZoomControls(true);//支持缩放
            settings.setDisplayZoomControls(true);
            //网页自适应屏幕
            settings.setUseWideViewPort(true);//最大视野完整显示网页
            settings.setLoadWithOverviewMode(true);

            settings.setAllowContentAccess(true);
            settings.setAllowFileAccess(true);
            settings.setAllowUniversalAccessFromFileURLs(true);
            settings.setAllowFileAccessFromFileURLs(true);
            //地理定位
            settings.setDomStorageEnabled(true);
            //支持同时加载http和https的混合模式
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }

            if (mCacheEnable){
                if (NetWorkUtils.isNetworkAvailable(mWebView.getContext())){
                    settings.setCacheMode(WebSettings.LOAD_DEFAULT);
                    Log.i(TAG, "mCacheEnable: " + "LOAD_DEFAULT");
                }else {
                    //优先使用缓存
                    settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
                    Log.i(TAG, "mCacheEnable: " + "LOAD_CACHE_ELSE_NETWORK");
                }
            }else {
                Log.i(TAG, "mCacheEnable: " + "LOAD_DEFAULT");
                //不使用缓存
                settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            }
        }
    }
}
