package com.integration.project.web;

import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.HttpAuthHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.integration.project.utils.LogUtils;

import java.util.Stack;

/**
 * Created by Wongerfeng on 2018/11/13.
 */

public class AppWebViewClient extends WebViewClient {
    private static final String TAG = "AppWebViewClient";

    private boolean isLoading = false;
    private boolean isLoadingFailed = false;
    private LoadingWebView.OnWebViewLoadResultListener mLoadResultListener;
    private Stack<String> mUrlStack = new Stack<>();

    private String mBeforeRedirectUrl;

    public AppWebViewClient() {
    }

    public AppWebViewClient(LoadingWebView.OnWebViewLoadResultListener loadResultListener) {
        mLoadResultListener = loadResultListener;
    }


    public void setLoadingFailed(boolean loadingFailed) {
        isLoadingFailed = loadingFailed;
    }

    public String getUrlStack() {
        if (mUrlStack.size() == 0){
            LogUtils.i(TAG, "getUrlStack: size= "+ mUrlStack.size());
            return null;
        }
        LogUtils.i(TAG, "getUrlStack: "+mUrlStack.lastElement());
        return mUrlStack.lastElement();
    }
    /**
     * 存储URL，避免刷新页面造成重复入栈
     * @param url
     */
    private void setUrlStack(String url) {
        if (!TextUtils.isEmpty(url) && !url.equalsIgnoreCase(getLastUrl())){
            mUrlStack.push(url);
            LogUtils.i(TAG, "setUrlStack:1 ="+url);

        }else if (!TextUtils.isEmpty(url)){
            LogUtils.i(TAG, "setUrlStack:2= "+mBeforeRedirectUrl);
            mUrlStack.push(mBeforeRedirectUrl);
            mBeforeRedirectUrl = null;
        }
    }

    private synchronized String getLastUrl(){
        return mUrlStack.size() > 0 ? mUrlStack.peek() : null;
    }

    /**
     * 弹出最近的URL地址
     * @return
     */
    private String popLastUrl(){
        if (mUrlStack.size() > 2){
            mUrlStack.pop();
            return mUrlStack.pop();
        }
        return null;
    }


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        LogUtils.i(TAG, "shouldOverrideUrlLoading: url="+request.getUrl().toString());
        view.loadUrl(request.getUrl().toString());
        return false;
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        LogUtils.i(TAG, "onReceivedError21: " + error.toString());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            LogUtils.i(TAG, "onReceivedError23: " + error.getErrorCode()+"----"+error.getDescription());
        }
        showLoadingFailedView();
    }

    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        super.onReceivedHttpError(view, request, errorResponse);
        showLoadingFailedView();
        LogUtils.i(TAG, "onReceivedHttpError: "+request.toString()+"----request="+request.getUrl());
        LogUtils.i(TAG, "onReceivedHttpError: " + errorResponse.getStatusCode()+"----"+errorResponse.getData());
    }

    @Override
    public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
        super.onReceivedLoginRequest(view, realm, account, args);
        showLoadingFailedView();
        LogUtils.i(TAG, "onReceivedLoginRequest: ");
    }


    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        super.onReceivedHttpAuthRequest(view, handler, host, realm);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        isLoading = true;
        if (mLoadResultListener != null){
            mLoadResultListener.onLoadingState();
        }
        ///
        if (isLoading && mUrlStack.size() > 0){
            mBeforeRedirectUrl = mUrlStack.pop();
        }

    }


    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        LogUtils.i(TAG, "onPageFinished: "+url);
        isLoading = false;
        if (mLoadResultListener != null && !isLoadingFailed){
            mLoadResultListener.onLoadSuccess();
        }
        setUrlStack(url);
    }

    public void showLoadingFailedView(){
        isLoadingFailed = true;
        if (mLoadResultListener != null){
            mLoadResultListener.onLoadError();
        }

    }

}
