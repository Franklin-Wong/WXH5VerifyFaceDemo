package com.integration.project.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.integration.project.R;
import com.integration.project.entity.UserContractEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.recyclerview.widget.RecyclerView.Adapter;

/**
 * Created by Wongerfeng on 2020/8/6.
 */
public class ProjectAdapter extends Adapter<ProjectAdapter.ViewHolder> {

    private List<UserContractEntity.ContractList> mProjectList;
    private Activity mActivity;
    private OnMyItemClickListener mItemClickListener;
    public static final int TYPE_HEADER=1;
    public static final int TYPE_FOOTER=2;
    private View headView;
    private View footView;

    public ProjectAdapter(Activity activity, List<UserContractEntity.ContractList> projectList, OnMyItemClickListener itemClickListener) {
        mProjectList = projectList;
        mActivity = activity;
        mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //判断位置
        if (viewType == TYPE_HEADER) {
            return new ViewHolder(headView);
        } else if ((viewType == TYPE_FOOTER)) {
            return new ViewHolder(footView);
        } else {
            View view= LayoutInflater.from(mActivity).inflate(R.layout.item_project_layout, parent, false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_FOOTER || getItemViewType(position) == TYPE_HEADER) {
            return;
        }
        if (mProjectList.get(position) != null) {
            UserContractEntity.ContractList project = mProjectList.get(position);
            holder.contractName.setText(project.getCompanyName());
            holder.contractDate.setText(project.getCreated());
            holder.rlItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onMyItemClickListener(v,position);
                }
            });
        }

    }

    private int getRealPosition(int position) {
        return headView == null ? position : position - 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && headView != null) {
            return TYPE_HEADER;
        } else if (position == mProjectList.size() - 1 && footView != null){
            return TYPE_FOOTER;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if (footView == null && headView == null) {
            return mProjectList.size();
        } else if ((footView == null && headView != null) || (footView != null && headView == null)) {
            return mProjectList.size() + 1;
        } else {
            return mProjectList.size() + 2;
        }
    }

    public void setHeadView(RecyclerView recyclerView, int resourceId) {
        headView = LayoutInflater.from(mActivity).inflate(resourceId, null, false);
        notifyItemInserted(0);
    }

    public void setFootView(RecyclerView recyclerView, View view) {
        footView = view;
        notifyItemInserted(headView == null? mProjectList.size() - 1 : mProjectList.size());
    }



    static class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rlItem;
        TextView contractName,date, contractDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlItem = itemView.findViewById(R.id.rlItem);
            contractName = itemView.findViewById(R.id.tvContractName);
            contractDate = itemView.findViewById(R.id.tvContractDate);
            date = itemView.findViewById(R.id.tvDate);
        }

    }

    public void setItemClickListener(OnMyItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public interface OnMyItemClickListener {
        void onMyItemClickListener(View view, int position);
    }
}
