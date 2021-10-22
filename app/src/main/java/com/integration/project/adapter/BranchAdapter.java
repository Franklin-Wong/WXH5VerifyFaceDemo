package com.integration.project.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.integration.project.R;
import com.integration.project.entity.BankBranchEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.recyclerview.widget.RecyclerView.Adapter;

/**
 * Created by Wongerfeng on 2020/8/6.
 */
public class BranchAdapter extends Adapter<BranchAdapter.ItemHolder> {

    private List<BankBranchEntity.BranchBank> mList;
    private Activity mActivity;
    private OnSelectedClickListener mItemClickListener;

    public BranchAdapter(Activity activity, List<BankBranchEntity.BranchBank> branchBankList,
                         OnSelectedClickListener itemClickListener) {
        mList = branchBankList;
        mActivity = activity;
        mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHolder holder;
        View view= LayoutInflater.from(mActivity).inflate(R.layout.item_branch_name, parent, false);
        holder = new ItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, final int position) {
        if (mList.get(position) != null) {
            BankBranchEntity.BranchBank branchName = mList.get(position);
            itemHolder.branchName.setText(branchName.getBankName());

            itemHolder.branchName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onSelectedClickListener(v,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder {

        TextView branchName;
        View divider;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            branchName = itemView.findViewById(R.id.tvBranchName);
            divider = itemView.findViewById(R.id.divider);
        }
    }

    public void setItemClickListener(OnSelectedClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public interface OnSelectedClickListener {
        void onSelectedClickListener(View view, int position);
    }
}
