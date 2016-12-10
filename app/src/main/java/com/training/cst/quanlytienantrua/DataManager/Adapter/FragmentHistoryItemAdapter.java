package com.training.cst.quanlytienantrua.DataManager.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.training.cst.quanlytienantrua.DataManager.Object.History;
import com.training.cst.quanlytienantrua.R;

import java.util.List;

/**
 * Created by longdg on 07/12/2016.
 */

public class FragmentHistoryItemAdapter extends RecyclerView.Adapter<FragmentHistoryItemAdapter.ViewHolder> {
    private List<History> mListHistory;
    private Context mContext;

    public FragmentHistoryItemAdapter(FragmentActivity mContext, List<History> mListHistory) {
        this.mListHistory = mListHistory;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_history_item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvSumAmount.setText("Money left : " + String.valueOf(mListHistory.get(position).
                getmAmount()));
        holder.tvSumPay.setText("Money pay : " +String.valueOf(mListHistory.get(position).getmPay()));
        holder.tvNamePerson.setText("Name person : " +mListHistory.get(position).getNamePerson());
    }

    @Override
    public int getItemCount() {
        return mListHistory.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvNamePerson;
        public final TextView tvSumPay;
        public final TextView tvSumAmount;
        public ViewHolder(View itemView) {
            super(itemView);
            tvNamePerson = (TextView) itemView.findViewById(R.id.fragment_history_item_tvname);
            tvSumPay = (TextView) itemView.findViewById(R.id.fragment_history_item_tvpay);
            tvSumAmount = (TextView) itemView.findViewById(R.id.fragment_history_item_amount);

        }
    }
}

