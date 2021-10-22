package com.integration.project.model;

import com.integration.project.listener.OnRequestListener;
import com.integration.project.request.RequestDownloadFile;
import com.integration.project.service.BaseFileObserver;
import com.integration.project.service.ServiceManager;
import com.integration.project.utils.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * Created by Wongerfeng on 2020/9/2.
 */
public class ProjDetailModel implements IProjDetailModel {
    private static final String TAG = "ProjDetailModel";

    @Override
    public void downloadContract(String url, String destDir, String fileName, OnRequestListener<File> listener){
        //创建文件路径
//        String path = Environment.getExternalStorageDirectory() + "/DOWNLOAD/" + "我的合同文本.pdf";

        //創建請求
       ServiceManager.getInstance()
                .getServiceByClass(RequestDownloadFile.class).downloadFile(url).map(new Function<ResponseBody, File>() {
           @Override
           public File apply(ResponseBody responseBody) throws Exception {
//                        if (downModel.getDownSize() > 0) {
//                            //断点下载
//                            return FileUtils.saveFile(responseBody.byteStream(), downModel.getDownSize(), downModel.getDestDir(), downModel.getName());
//                        }
               return FileUtil.saveFile(responseBody.byteStream(), destDir, fileName);
           }
       })
               .subscribeOn(io.reactivex.schedulers.Schedulers.io())
               .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
               .subscribeWith(new BaseFileObserver() {
                   @Override
                   public void onSuccess(File file) {
                       listener.onRequestSuccess(file);
//                        downModel.setState(DownModel.DOWN_FINISH);
//                        downModel.setFileSize(FileUtil.getFormatSize(file.length()));
//
//                        if (null != mDownloadListeners && mDownloadListeners.size() > 0) {
//                            for (DownloadListener listener : mDownloadListeners) {
//                                listener.onFinishDownload(downModel.getUrl(), file);
//                            }
//                        }
                   }

                   @Override
                   public void onError(String msg) {
                       listener.onRequestFailed(msg);
//                        downModel.setState(DownModel.DOWN_FAIL);
//                        if (null != mDownloadListeners && mDownloadListeners.size() > 0) {
//                            for (DownloadListener listener : mDownloadListeners) {
//                                listener.onFail(downModel.getUrl(), msg);
//                            }
//                        }
                   }
               });


    }


    private boolean writeToFile(ResponseBody baseResponse, File file, OnRequestListener listener) {
        try {
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {

                byte[] fileReader = new byte[2048];
                inputStream = baseResponse.byteStream();
                outputStream = new FileOutputStream(file);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                }
                outputStream.flush();
                return true;

            } catch (IOException e) {
                listener.onRequestFailed(e.getMessage());
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            listener.onRequestFailed(e.getMessage());
            return false;
        }
    }
}
