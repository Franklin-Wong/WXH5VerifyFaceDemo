package com.integration.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.integration.project.R;
import com.integration.project.entity.BankBranchEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wongerfeng on 2020/8/10.
 */
public class BranchListAdapter extends BaseAdapter {
    private Context mContext;
    private List<BankBranchEntity.BranchBank> mDataList = new ArrayList<>();

    public BranchListAdapter(Context context, List<BankBranchEntity.BranchBank> dataList) {
        mContext = context;
        mDataList = dataList;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return mDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ItemHolder holder;
        if (view == null) {
            holder = new ItemHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_branch_name, null);
            holder.branchName = view.findViewById(R.id.tvBranchName);
            holder.divider = view.findViewById(R.id.divider);
            view.setTag(holder);
        } else {
            holder = (ItemHolder) view.getTag();
        }
        BankBranchEntity.BranchBank item = (BankBranchEntity.BranchBank) getItem(i);
        if (item != null) {
            holder.branchName.setText(item.getBankName());
        }
        return view;
    }

    static class ItemHolder {

        TextView branchName;
        View divider;
    }
}
