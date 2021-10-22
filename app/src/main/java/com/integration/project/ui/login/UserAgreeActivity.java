package com.integration.project.ui.login;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.integration.project.R;
import com.joanzapata.pdfview.PDFView;

import androidx.appcompat.app.AppCompatActivity;

public class UserAgreeActivity extends AppCompatActivity {
//    private static final String TAG = "UserAgreeActivity";
    private PDFView mPDFView;
    private Uri mUri;
    private TextView mTitle;
    private ImageView  mGoBack;

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_agree);
        initView();
        initData();

    }

    private void initView() {
        mPDFView = findViewById(R.id.pdf);
        mGoBack = findViewById(R.id.tvReturn);
        mTitle = findViewById(R.id.tvTitleName);
        mTitle.setText(R.string.user_agreement);
        mGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void initData() {
        mPDFView.setSwipeVertical(true);
        mPDFView.fromAsset("user_agreement.pdf").load();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
