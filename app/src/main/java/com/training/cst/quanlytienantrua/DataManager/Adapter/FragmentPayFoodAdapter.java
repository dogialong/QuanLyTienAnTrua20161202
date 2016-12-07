package com.training.cst.quanlytienantrua.DataManager.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.training.cst.quanlytienantrua.DataManager.Object.Person;
import com.training.cst.quanlytienantrua.R;

import java.util.List;

/**
 * Created by longdg on 06/12/2016.
 */

public class FragmentPayFoodAdapter extends RecyclerView.Adapter<FragmentPayFoodAdapter.ViewHolder> {

    private Context mContext;
    private List<String> mListString;
    private List<Person> mListPerson;
    private ItemClickListener itemClickListener;
    public FragmentPayFoodAdapter(Context mContext, List<Person> mListPerson,List<String> mListString,
                                  ItemClickListener itemClickListener) {
        this.mContext = mContext;
        this.mListPerson = mListPerson;
        this.mListString = mListString;
        this.itemClickListener = itemClickListener;
    }
    public void loadNewString(List<String> mString){
        this.mListString.clear();
        this.mListString.addAll(mString);
        notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_pay_food_item,null,false);
        return new ViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvNamePerson.setText(mListPerson.get(position).getNamePerson());
        holder.chkFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.clickItemListtener(v,position);
                Log.d("TAG---","abc"+position);
            }
        });

//        holder.chkFood.setChecked(true);
        holder.tvNameFoodSelected.setText(mListString.get(position));
    }

    @Override
    public int getItemCount() {
        return mListPerson.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNamePerson;
        public TextView tvNameFoodSelected;
        public CheckBox chkFood;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNamePerson = (TextView) itemView.findViewById(R.id.fragment_pay_food_item_recycleview_tv_nameperson);
            tvNameFoodSelected = (TextView) itemView.findViewById(R.id.fragment_pay_food_item_rv_namefood);
            chkFood = (CheckBox) itemView.findViewById(R.id.fragment_pay_food_item_recycleview_ckb);
        }
    }
}
