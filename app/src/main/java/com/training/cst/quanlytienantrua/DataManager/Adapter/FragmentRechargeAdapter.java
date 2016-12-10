package com.training.cst.quanlytienantrua.DataManager.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.training.cst.quanlytienantrua.DataManager.Object.Person;
import com.training.cst.quanlytienantrua.Helper.Contants;
import com.training.cst.quanlytienantrua.R;

import java.util.List;

/**
 * Created by longdg123 on 11/24/2016.
 */

public class FragmentRechargeAdapter extends RecyclerView.Adapter<FragmentRechargeAdapter.ViewHolder> {
    private List<Person> mListPerson;
    private Context mContext;
    ItemClickListener itemClickListener;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.fragment_pay_item_recycleview,parent,false);
        return new ViewHolder(view);
    }

    public FragmentRechargeAdapter(Context mContext,List<Person> mListPerson,ItemClickListener itemClickListener) {
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
        holder.tvNamePersonRecharge.setText(mListPerson.get(position).getNamePerson());
        holder.tvMoneyPersonRechare.setText(String.valueOf(mListPerson.get(position).getParche()));
        holder.chkRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.clickItemListtener(v,position);
            }
        });
        holder.chkRecharge.setChecked(true);
    }


    @Override
    public int getItemCount() {
        return mListPerson.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final CheckBox chkRecharge;
        public final TextView tvNamePersonRecharge;
        public final EditText tvMoneyPersonRechare;
        private String current = "";
        private long money=0 ;
        public ViewHolder(View itemView) {
            super(itemView);
            chkRecharge = (CheckBox) itemView.findViewById(R.id.fragment_pay_item_recycleview_ckb);
            tvNamePersonRecharge = (TextView) itemView.findViewById(R.id.fragment_pay_item_recycleview_tv_nameperson);
            tvMoneyPersonRechare = (EditText)itemView.findViewById(R.id.fragment_pay_item_rv_money);
            tvMoneyPersonRechare.addTextChangedListener(new TextWatcher() {
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
                            tvMoneyPersonRechare.removeTextChangedListener(this);
                            tvMoneyPersonRechare.setText(Contants.formatCurrency(s,current,money));
                            tvMoneyPersonRechare.addTextChangedListener(this);
                        }
                    } catch (NumberFormatException n){
                        s.clear();
                    }
                }
            });
        }
    }
}
