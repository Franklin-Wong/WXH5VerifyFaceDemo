package com.integration.project.widgets;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.integration.project.R;

import java.lang.ref.WeakReference;

/**
 * Created by Wongerfeng on 2019/12/20.
 */
public class PhotoDialog {

    private static Dialog mPayDialog;

    public static void showPhotoDialog(Activity activity,
                                       final OnPayDialogListener dialogListener) {
        //弱引用
        WeakReference<Context> reference = new WeakReference<Context>(activity);
        Context c = reference.get();
        if (c != null) {
            mPayDialog = new Dialog(c, R.style.custom_dialog);
            View layout = LayoutInflater.from(c).inflate(R.layout.photo_view, null, false);
            ViewGroup.LayoutParams layoutParams =
                    new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);

            //添加布局和参数
            mPayDialog.addContentView(layout, layoutParams);
            //获取window
            Window window = mPayDialog.getWindow();
            WindowManager.LayoutParams attributes = null;
            if (window != null) {
                attributes = window.getAttributes();
                attributes.dimAmount = (float) 0.6;
                window.setAttributes(attributes);
                window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }

            TextView gallery = layout.findViewById(R.id.tvGallery);
            TextView camera = layout.findViewById(R.id.tvCamera);

            camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialogListener != null) {
                        dialogListener.onCameraClick(v);
                    }
                    dismissDialog();
                }
            });

            gallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialogListener != null) {
                        dialogListener.onGalleryClick(v);
                    }
                    dismissDialog();
                }
            });
        }
        //显示对话框
        if (mPayDialog != null && c != null && !mPayDialog.isShowing()) {
            mPayDialog.show();
        }
    }

    public static void dismissDialog() {
        if (mPayDialog != null) {
            mPayDialog.dismiss();
            mPayDialog = null;
        }

    }



    public interface OnPayDialogListener {

        void onCameraClick(View view);

        void onGalleryClick(View view);
    }


}
