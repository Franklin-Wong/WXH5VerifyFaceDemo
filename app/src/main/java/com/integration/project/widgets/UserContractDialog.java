package com.integration.project.widgets;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.integration.project.R;
import com.integration.project.adapter.UnsignContractAdapter;
import com.integration.project.entity.UserContractIncomeEntity;

import java.lang.ref.WeakReference;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Wongerfeng on 2019/12/20.
 */
public class UserContractDialog {

    private static Dialog mPayDialog;

    public static void showContractDialog(Activity activity, int contractNumber,
                                          List<UserContractIncomeEntity.ContractIncome> dataList,
                                          final OnPayDialogListener dialogListener) {
        //弱引用
        WeakReference<Context> reference = new WeakReference<Context>(activity);
        Context c = reference.get();
        if (c != null) {
            mPayDialog = new Dialog(c, R.style.custom_dialog);
            View layout = LayoutInflater.from(c).inflate(R.layout.user_contract_view, null, false);
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

            TextView contractCount = layout.findViewById(R.id.tvContractNumber);
            TextView confirm = layout.findViewById(R.id.tvConfirm);
            RecyclerView recyclerView = layout.findViewById(R.id.rvContract);

            recyclerView.setLayoutManager(new LinearLayoutManager(activity,
                    RecyclerView.VERTICAL,false));

            if (dataList != null) {
                UnsignContractAdapter adapter = new UnsignContractAdapter(activity, dataList);
                recyclerView.setAdapter(adapter);
            }

            if (contractNumber > 0) {
                contractCount.setText(String.format("%s%s", contractNumber,"份待签署" ));
            }

            final ImageView cancel = layout.findViewById(R.id.ivCancel);

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialogListener != null) {
                        dialogListener.onCancelClick(v);
                    }
                    dismissDialog();
                }
            });

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialogListener != null) {
                        dialogListener.onConfirmClick(v);
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

        void onCancelClick(View view);

        void onConfirmClick(View view);
    }


}
