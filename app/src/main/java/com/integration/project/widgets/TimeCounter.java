package com.integration.project.widgets;

import android.os.CountDownTimer;
import android.widget.TextView;

import java.lang.ref.WeakReference;


/**
 * Created by Wongerfeng on 2020/8/14.
 */
public class TimeCounter extends CountDownTimer {

    private WeakReference<TextView> mWeakReference;
    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public TimeCounter(long millisInFuture, long countDownInterval, TextView textView) {
        super(millisInFuture, countDownInterval);
        mWeakReference = new WeakReference<>(textView);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        TextView textView = mWeakReference.get();
        if (textView != null) {
            textView.setClickable(false);
            textView.setText(millisUntilFinished / 1000 +"秒");
        }
    }

    @Override
    public void onFinish() {
        TextView textView = mWeakReference.get();
        if (textView != null) {
            textView.setClickable(true);
            textView.setText("获取");
        }
    }
}
