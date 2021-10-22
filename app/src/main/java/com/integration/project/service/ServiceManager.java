package com.integration.project.service;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.integration.project.utils.Constants;
import com.integration.project.utils.LogUtils;

import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.android.BuildConfig;

/**
 * Created by Wongerfeng on 2020/8/10.
 */
public class ServiceManager implements IHttpService{
    private static final String TAG = "gonghui";
    public static final long CONNECTION_TIME_OUT = 60;
    private Retrofit mRetrofit;
    /**
     * 单一实例
     */
    private static ServiceManager mSInstance;

    public static ServiceManager getInstance() {
        return InstanceHolder.getInstance();
    }

    static class InstanceHolder {
        static ServiceManager getInstance() {
            return mSInstance = new ServiceManager();
        }
    }


    @Override
    public <T> T getServiceByClass(Class<T> clazz) {
        if (mRetrofit == null) {
            setupRetrofit();
        }

        T t = mRetrofit.create(clazz);
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new ProxyHander(t));
    }

    private void setupRetrofit() {
        //创建json对象和格式
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-DD hh:mm:ss").create();
        //获取OKhttp对象
        OkHttpClient okHttpClient = getOkHttpClient();
        //创建Retrofit对象，设置属性和参数
        mRetrofit = new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }

    private String getBaseUrl() {

        if (BuildConfig.DEBUG) {
            return Constants.TEST_URL;
        } else {
            return Constants.BASE_URL;
        }
    }

    private OkHttpClient getOkHttpClient() {
        //创建log拦截器

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtils.i(TAG, "log: "+message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //
        if (BuildConfig.DEBUG) {
            return new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .readTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                    .writeTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                    .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .addNetworkInterceptor(new StethoInterceptor())
                    .build();
        } else {
            return new OkHttpClient.Builder()
                    .readTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                    .writeTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                    .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build();

        }
    }
    private void setupRetrofit2() {
        //创建json解析格式
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-DD hh:mm:ss").create();
        //创建OKhttp客户端对象
        OkHttpClient okHttpClient = getOkHttpClient2();
        //创建Retrofit客户端对象，传入OKhttp对象,gson格式对象
        mRetrofit = new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();


    }
    private OkHttpClient getOkHttpClient2() {
        //设置log cookie session 拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        if (BuildConfig.DEBUG) {
            return new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .writeTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                    .build();

        } else {
            return new OkHttpClient.Builder()
                    .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .writeTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                    .build();
        }
    }
}
