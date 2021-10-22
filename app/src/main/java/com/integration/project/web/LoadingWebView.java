package com.integration.project.web;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by Wongerfeng on 2017/6/20.
 */

public class LoadingWebView extends WebView {

    private WebView mWebView;
    private OnWebViewLoadResultListener loadResultListener;
    public LoadingWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mScrollListener != null) {
            mScrollListener.onScroll(!(getScrollY() > 0));
        }
    }
    private OnWebViewScrollListener mScrollListener;

    public void setOnWebScrollListener(OnWebViewScrollListener listener) {
        this.mScrollListener = listener;
    }
    public interface OnWebViewScrollListener{
        void onScroll(boolean isTop);
    }

    public interface OnWebViewLoadResultListener {
        ///加载路径成功
        void onLoadSuccess();
        ///加载路径失败
        void onLoadError();
        ///加载中
        void onLoadingState();
    }

    public void setOnWebViewLoadListener(OnWebViewLoadResultListener listener) {
        this.loadResultListener = listener;
    }

}
