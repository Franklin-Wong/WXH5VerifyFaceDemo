package com.integration.project.widgets;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.integration.project.R;
import com.integration.project.utils.NetWorkUtils;


/**
 * Created by Wongerfeng on 2017/6/19.
 */

public class WebLoadFailedView extends RelativeLayout {

    private Context mContext;
    private View mProgressView;
    private LinearLayout mLinearLoading;
    private TextView mTextRetry;
    private LinearLayout ll_load_failed;
    private OnRetryClickListener mOnRetryClickListener;

    public WebLoadFailedView(Context context) {
        this(context, null);
    }

    public WebLoadFailedView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WebLoadFailedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
//        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.web_loading_failed,
//                this, true);
        LayoutInflater.from(context).inflate(R.layout.web_loading_failed, this, true);
        this.initViews();
    }

    private void initViews() {

        ViewGroup.LayoutParams p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(p);
        ll_load_failed = (LinearLayout) findViewById(R.id.ll_load_failed);
        this.mProgressView = findViewById(R.id.pb_preview_loading);

        this.mLinearLoading = (LinearLayout) findViewById(R.id.ll_preview_loading);
        mTextRetry = (TextView) findViewById(R.id.tv_preview_retry);
        mLinearLoading.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnRetryClickListener != null){
                    mOnRetryClickListener.onRetryClickListener(v);
                }
            }
        });
        this.showLoadingStatus();
    }


    public void showLoadingStatus() {
        this.setVisibility(View.VISIBLE);
        ll_load_failed.setVisibility(View.GONE);
        mTextRetry.setVisibility(View.GONE);
        mLinearLoading.setClickable(false);
        this.mProgressView.setVisibility(View.VISIBLE);
    }

    public void showLoadFailedStatus(String text) {
        ll_load_failed.setVisibility(View.VISIBLE);
        this.setVisibility(View.VISIBLE);
        mLinearLoading.setClickable(true);
        mTextRetry.setVisibility(View.VISIBLE);
        this.mProgressView.setVisibility(View.GONE);

    }

    public void showLoadFailedStatus() {
        ll_load_failed.setVisibility(View.VISIBLE);
        if (!NetWorkUtils.isNetworkAvailable(mContext)) {
            this.setVisibility(View.VISIBLE);
            mTextRetry.setVisibility(View.VISIBLE);
            mLinearLoading.setClickable(true);
            mTextRetry.setText("重新加载");
            this.mProgressView.setVisibility(View.GONE);
        } else {
            showLoadFailedStatus("加载失败");
        }
    }

    public void showViewEmptyStatus(String emptyText) {
        this.setVisibility(View.VISIBLE);
        ll_load_failed.setVisibility(View.VISIBLE);
        mProgressView.setVisibility(GONE);
        mTextRetry.setVisibility(View.VISIBLE);
        mLinearLoading.setClickable(false);
        mTextRetry.setText(TextUtils.isEmpty(emptyText) ? "没有内容~" : emptyText);
    }
    public void hiddenView() {
        this.setVisibility(View.GONE);
    }

    public interface OnRetryClickListener{
        public void onRetryClickListener(View view);
    }

    public void setOnRetryClickListener(OnRetryClickListener mOnRetryClickListener) {
        this.mOnRetryClickListener = mOnRetryClickListener;
    }
}
