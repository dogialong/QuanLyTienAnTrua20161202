package com.training.cst.quanlytienantrua.DataManager.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.training.cst.quanlytienantrua.DataManager.Object.Person;
import com.training.cst.quanlytienantrua.Helper.Contants;
import com.training.cst.quanlytienantrua.R;

import java.util.List;

/**
 * Created by longdg123 on 11/24/2016.
 */

public class FragmentOverviewAdapter extends RecyclerView.Adapter<FragmentOverviewAdapter.ViewHolder> {
    private List<Person> mListperson;
    private Context mContext;

    public FragmentOverviewAdapter(FragmentActivity mContext, List<Person> mListperson) {
        this.mListperson = mListperson;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_overview_list_item_person, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvMoneyLeft.setText(String.valueOf(mListperson.get(position).
                getmAmount()));
        holder.tvNamePerson.setText(mListperson.get(position).getNamePerson());
    }

    @Override
    public int getItemCount() {
        return mListperson.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvNamePerson;
        public final TextView tvMoneyLeft;
        private String current = "";
        private double money=0.0 ;
        public ViewHolder(View itemView) {
            super(itemView);
            tvNamePerson = (TextView) itemView.findViewById(R.id.fragment_overview_item_tv_name);
            tvMoneyLeft = (TextView) itemView.findViewById(R.id.fragment_overview_item_tv_money);
            tvMoneyLeft.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    try{
                        if(!s.toString().equals(current) && s.toString().contains("-")){
                            tvMoneyLeft.removeTextChangedListener(this);
                            Contants.formatCurrency(s,current,money);
                            tvMoneyLeft.setText("-"+Contants.formatCurrency(s,current,money));
                            tvMoneyLeft.addTextChangedListener(this);
                        } else {
                            tvMoneyLeft.removeTextChangedListener(this);
                            Contants.formatCurrency(s,current,money);
                            tvMoneyLeft.setText(Contants.formatCurrency(s,current,money));
                            tvMoneyLeft.addTextChangedListener(this);
                        }
                    } catch (NumberFormatException n){
                        s.clear();
                    }
                }
            });
        }
    }
}
