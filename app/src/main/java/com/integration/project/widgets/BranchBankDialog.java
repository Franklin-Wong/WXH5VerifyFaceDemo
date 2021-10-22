package com.integration.project.widgets;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.integration.project.R;
import com.integration.project.adapter.BranchListAdapter;
import com.integration.project.entity.BankBranchEntity;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Wongerfeng on 2019/12/20.
 */
public class BranchBankDialog {

    private static Dialog mPayDialog;

    public static void showBranchBankDialog(Activity activity, List<BankBranchEntity.BranchBank> dataList,
                                          final OnBranchBankDialogListener dialogListener) {
        //弱引用
        WeakReference<Context> reference = new WeakReference<Context>(activity);
        Context c = reference.get();
        if (c != null) {
            mPayDialog = new Dialog(c, R.style.custom_dialog);
            View layout = LayoutInflater.from(c).inflate(R.layout.branch_bank_view, null, false);
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

            RelativeLayout empty = layout.findViewById(R.id.rlEmpty);
            ListView listView = layout.findViewById(R.id.rvBranchBank);


            if (dataList != null) {

                BranchListAdapter listAdapter = new BranchListAdapter(activity, dataList);
                listView.setAdapter(listAdapter);
            }

            empty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialogListener != null) {
                        dialogListener.onCancelClick(v);
                    }
                    dismissDialog();
                }
            });
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    if (dialogListener != null) {
                        dialogListener.onSelectedConfirmClick(view, position);
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



    public interface OnBranchBankDialogListener {

        void onCancelClick(View view);

        void onSelectedConfirmClick(View view, int position);
    }


}
