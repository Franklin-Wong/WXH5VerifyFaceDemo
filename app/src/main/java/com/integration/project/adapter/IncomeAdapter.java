package com.integration.project.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.integration.project.R;
import com.integration.project.entity.IncomeDetailEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.recyclerview.widget.RecyclerView.Adapter;

/**
 * Created by Wongerfeng on 2020/8/6.
 */
public class IncomeAdapter extends Adapter<IncomeAdapter.ItemHolder> {

    private List<IncomeDetailEntity.Income> mIncomeList;
    private Activity mActivity;
    private OnMyItemClickListener mItemClickListener;

    public IncomeAdapter(Activity activity, List<IncomeDetailEntity.Income> incomeList,
                         OnMyItemClickListener itemClickListener) {
        mIncomeList = incomeList;
        mActivity = activity;
        mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHolder holder;
        View view= LayoutInflater.from(mActivity).inflate(R.layout.item_income_layout, parent, false);
        holder = new ItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, final int position) {
        if (mIncomeList.get(position) != null) {
            IncomeDetailEntity.Income income = mIncomeList.get(position);
            itemHolder.companyName.setText(income.getCompanyName());
            itemHolder.incomeDate.setText(income.getSalaryTime());
            itemHolder.cardNumber.setText(income.getBankCardNo());
            itemHolder.money.setText(income.getAmount());

            itemHolder.rlItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onMyItemClickListener(v,position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mIncomeList.size();
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {

        RelativeLayout rlItem;
        TextView cardNumber,companyName,money,date, incomeDate;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            rlItem = itemView.findViewById(R.id.rlItem);
            cardNumber = itemView.findViewById(R.id.tvCardNumber);
            companyName = itemView.findViewById(R.id.tvCompanyName);
            money = itemView.findViewById(R.id.tvMoney);
            incomeDate = itemView.findViewById(R.id.tvIncomeDate);
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
