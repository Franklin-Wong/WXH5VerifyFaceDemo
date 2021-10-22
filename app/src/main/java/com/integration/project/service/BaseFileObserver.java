package com.integration.project.service;

import java.io.File;

import io.reactivex.observers.DisposableObserver;

public abstract class BaseFileObserver<T> extends DisposableObserver<File> {
    @Override
    protected void onStart() {
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onNext(File file) {
        if (null != file && file.exists()) {
            onSuccess(file);
        } else {
            onError("file is null or a file does not exist");
        }
    }

    @Override
    public void onError(Throwable e) {
        onError(e.getMessage());
    }

    public abstract void onSuccess(File file);

    public abstract void onError(String msg);

}
