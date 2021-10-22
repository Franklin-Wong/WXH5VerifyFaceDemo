package com.integration.project;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.integration.project.presenter.BasePresenter;

import androidx.appcompat.app.AppCompatActivity;


/**
 *
 * @param <V>
 * @param <T>
 */
public abstract class BaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity {
    protected TextView mTitle;
    protected ImageView mGoBack;
    protected T mPresenter;
    private Toast mToast;
    public abstract T createPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = createPresenter();
        mPresenter.attachView((V) this);

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.dettachView();
            mPresenter = null;
        }
    }

    protected void showToast(String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        showToast(this, message, Toast.LENGTH_SHORT);
    }

    public void showToast(Context context, CharSequence text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context.getApplicationContext(), text, duration);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast.setText(text);
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.setDuration(duration);
        }
        mToast.show();
    }
}
