package com.integration.project.request;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Wongerfeng on 2020/9/2.
 */
public interface RequestDownloadFile {
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(
            @Url String url
    );
}
