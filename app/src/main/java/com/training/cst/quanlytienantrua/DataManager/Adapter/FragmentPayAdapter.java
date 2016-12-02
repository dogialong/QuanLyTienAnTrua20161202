package com.training.cst.quanlytienantrua.DataManager.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.training.cst.quanlytienantrua.DataManager.Object.Person;
import com.training.cst.quanlytienantrua.Helper.Contants;
import com.training.cst.quanlytienantrua.R;

import java.util.List;

/**
 * Created by longdg123 on 11/24/2016.
 */

public class FragmentPayAdapter extends RecyclerView.Adapter<FragmentPayAdapter.ViewHolder> {
    private List<Person> mListPerson;
    private Context mContext;
    ItemClickListener itemClickListener;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.fragment_pay_item_recycleview,parent,false);
        return new ViewHolder(view);
    }

    public FragmentPayAdapter(Context mContext, List<Person> mListPerson, ItemClickListener itemClickListener) {
        this.mListPerson = mListPerson;
        this.mContext = mContext;
        this.itemClickListener = itemClickListener;
    }
    public void loadNewList(List<Person> mListPerson){
        this.mListPerson.clear();;
        this.mListPerson.addAll(mListPerson);
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvNamePersonPay.setText(mListPerson.get(position).getNamePerson());
        holder.tvMoneyPersonPay.setText(String.valueOf(mListPerson.get(position).getPay()));
        holder.chkPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.clickItemListtener(v,position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mListPerson.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final CheckBox chkPay;
        public final TextView tvNamePersonPay;
        public final TextView tvMoneyPersonPay;
        private String current = "";
        private long money=0 ;
        public ViewHolder(View itemView) {
            super(itemView);
            chkPay = (CheckBox) itemView.findViewById(R.id.fragment_pay_item_recycleview_ckb);
            tvNamePersonPay = (TextView) itemView.findViewById(R.id.fragment_pay_item_recycleview_tv_nameperson);
            tvMoneyPersonPay = (TextView)itemView.findViewById(R.id.fragment_pay_item_rv_money);
            tvMoneyPersonPay.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    try{
                        if(!s.toString().equals(current)){
                            tvMoneyPersonPay.removeTextChangedListener(this);
                            tvMoneyPersonPay.setText(Contants.formatCurrency(s,current,money));
                            tvMoneyPersonPay.addTextChangedListener(this);
                        }
                    } catch (NumberFormatException n){
                        s.clear();
                    }
                }
            });
        }

    }
}
