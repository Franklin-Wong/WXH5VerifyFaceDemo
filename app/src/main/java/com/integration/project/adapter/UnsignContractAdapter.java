package com.integration.project.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.integration.project.R;
import com.integration.project.entity.UserContractIncomeEntity;

import java.lang.ref.WeakReference;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.recyclerview.widget.RecyclerView.Adapter;

/**
 * Created by Wongerfeng on 2020/8/6.
 */
public class UnsignContractAdapter extends Adapter<UnsignContractAdapter.ItemHolder> {

    private List<UserContractIncomeEntity.ContractIncome> mList;
    private Activity mActivity;

    public UnsignContractAdapter(Activity activity, List<UserContractIncomeEntity.ContractIncome> branchBankList) {
        WeakReference<Activity> reference = new WeakReference<>(activity);
        Activity a = reference.get();
        if (a != null) {
            mActivity = a;
        }
        mList = branchBankList;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHolder holder;
        View view= LayoutInflater.from(mActivity).inflate(R.layout.item_constract_name, parent, false);
        holder = new ItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, final int position) {
        if (mList.get(position) != null) {
            UserContractIncomeEntity.ContractIncome contractIncome = mList.get(position);
            itemHolder.contractName.setText(String.format("%s%s%s", "·《", contractIncome.getFileName(), "》"));

        }
    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return Math.max(mList.size(), 0);
        } else {
            return 0;
        }
    }

    static class ItemHolder extends RecyclerView.ViewHolder {

        TextView contractName;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            contractName = itemView.findViewById(R.id.tvContractName);
        }
    }
}
