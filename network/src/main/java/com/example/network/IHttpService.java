package com.example.network;

/**
 * Created by Wongerfeng on 2020/8/10.
 */
public interface IHttpService {

    /**
     *
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T getServiceByClass(Class<T> clazz);

}
