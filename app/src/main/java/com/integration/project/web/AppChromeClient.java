package com.integration.project.web;

import android.app.Activity;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.GeolocationPermissions;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.integration.project.sdk.WBH5FaceVerifySDK;
import com.integration.project.utils.LogUtils;

import java.lang.ref.WeakReference;

/**
 * Created by Wongerfeng on 2018/11/14.
 */

public class AppChromeClient extends WebChromeClient {
    private static final String TAG = "AppChromeClient";
    private WeakReference<Activity> mWeakReference;
    public String mTitle;

    public AppChromeClient(Activity activity) {
        mWeakReference = new WeakReference<Activity>(activity);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        if (!TextUtils.isEmpty(title)) {
            mTitle = title;
        }
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {

        return super.onJsAlert(view, url, message, result);
    }

//    @Override
//    public void onProgressChanged(WebView view, int newProgress) {
//        super.onProgressChanged(view, newProgress);
//        if (mWeakReference == null){
//            return;
//        }
//        mWeakReference.get().setTitle("加载中.....");
//        if (newProgress == 100){
//            mWeakReference.get().setImmersive(true);
//        }
//    }

    @Override
    public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
        callback.invoke(origin, true, false);
        LogUtils.i(TAG, "onGeolocationPermissionsShowPrompt: origin="+origin);
        super.onGeolocationPermissionsShowPrompt(origin, callback);
    }

    /**
     * android端接收H5端发来的请求
     For Android >= 3.0
     */
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        if(WBH5FaceVerifySDK.getInstance().recordVideoForApiBelow21(uploadMsg, acceptType,mWeakReference.get())) {
            return;
        }
        // TODO: 第三方有调用系统相机处理其他业务的话，将相关逻辑代码放在下面

    }
    /**
     * android端接收H5端发来的请求
     For Android >= 4.1
     */
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        if(WBH5FaceVerifySDK.getInstance().recordVideoForApiBelow21(uploadMsg, acceptType,mWeakReference.get())) {
            return;
        }
        // TODO: 第三方有调用系统相机处理其他业务的话，将相关逻辑代码放在下面

    }
    /**
     * android端接收H5端发来的请求
     For Lollipop 5.0+ Devices
     */
    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        if(WBH5FaceVerifySDK.getInstance().recordVideoForApi21(webView, filePathCallback,mWeakReference.get(), fileChooserParams)){

            return true;
        }
        // TODO: 第三方有调用系统相机处理其他业务的话，将相关逻辑代码放在下面

        return true;
    }
}
