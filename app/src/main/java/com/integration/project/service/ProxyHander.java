package com.integration.project.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Wongerfeng on 2020/8/10.
 */
public class ProxyHander implements InvocationHandler {

    /**
     * 声明相关的代理变量
     */
    private Object mProxyHandler;

    public ProxyHander(Object proxyHandler) {
        mProxyHandler = proxyHandler;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {

        return Observable.just(null).flatMap(new Func1<Object, Observable<?>>() {
            @Override
            public Observable<?> call(Object o) {
                try {
                    return (Observable<?>) method.invoke(mProxyHandler, args);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }).retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Throwable> observable) {
                return observable.flatMap(new Func1<Throwable, Observable<?>>() {
                    @Override
                    public Observable<?> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                });
            }
        });
    }
}
