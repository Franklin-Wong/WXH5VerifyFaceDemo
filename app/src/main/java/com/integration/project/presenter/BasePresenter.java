package com.integration.project.presenter;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by Wongerfeng on 2020/8/7.
 */
public abstract class BasePresenter<T> {

    /**
     * 声明引用的接口类型
     */
    public Reference<T> mReference;

    /**
     * 绑定引用的接口
     */
    public void attachView(T t) {
        mReference = new WeakReference<>(t);
    }

    /**
     * 获取引用的接口对象
     */
    public T getView() {
        return mReference.get();
    }

    /**
     * 判断是否引用成功
     */
    public boolean isAttachView() {
        return mReference != null && mReference.get() != null;
    }

    /**
     * 解除绑定
     */
    public void dettachView() {
        if (mReference != null) {
            mReference.clear();
        }
    }
}
