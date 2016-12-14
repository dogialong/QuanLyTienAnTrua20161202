package com.training.cst.quanlytienantrua.DataManager.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.training.cst.quanlytienantrua.DataManager.Object.Food;
import com.training.cst.quanlytienantrua.Database.DatabaseUser;
import com.training.cst.quanlytienantrua.Helper.Contants;
import com.training.cst.quanlytienantrua.R;

import java.util.List;

/**
 * Created by longdg on 04/12/2016.
 */
public class FragmentFoodAdapter extends RecyclerView.Adapter<FragmentFoodAdapter.ViewHolder> {
    private List<Food> mListFood;
    private Context mContext;
    private DatabaseUser mDatabaseUser;
    ItemClickListener itemClickListener;
    private final ViewBinderHelper binderHelper = new ViewBinderHelper();
    public FragmentFoodAdapter(DatabaseUser mDatabaseUser, Context mContext,
                               List<Food> mListFood,ItemClickListener itemClickListener) {
        this.mDatabaseUser = mDatabaseUser;
        this.mContext = mContext;
        this.mListFood = mListFood;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_food_item,null,false);
        return new ViewHolder(myView);
    }
    public void setmListFood(List<Food> listFood) {
        mListFood.clear();
        mListFood.addAll(listFood);
        notifyDataSetChanged();
    }
    public void saveStates(Bundle outState) {
        binderHelper.saveStates(outState);
    }
    public void restoreStates(Bundle inState) {
        binderHelper.restoreStates(inState);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        binderHelper.bind(holder.swipeLayout,mListFood.get(position).getNameFood());
        holder.tvNameFood.setText(Contants.handlerTextToLong(mListFood.get(position).getNameFood()));
        holder.tvPriceFood.setText(String.valueOf(mListFood.get(position).getPriceFood()));
        holder.btnDeleteFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.clickItemListtener(v,position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return mListFood.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private SwipeRevealLayout swipeLayout;
        private TextView tvNameFood;
        private Button btnDeleteFood;
        private TextView tvPriceFood;
        private String current = "";
        private long money=0 ;

        public ViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeRevealLayout) itemView.findViewById(R.id.swipe_layout);
            btnDeleteFood = (Button) itemView.findViewById(R.id.fragment_food_item_rv_btn_delete);
            tvPriceFood = (TextView) itemView.findViewById(R.id.fragment_food_item_rv_tv_price);
            tvNameFood = (TextView) itemView.findViewById(R.id.fragment_food_item_rv_tv_name);
            tvPriceFood.addTextChangedListener(new TextWatcher() {
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
                            tvPriceFood.removeTextChangedListener(this);
                            tvPriceFood.setText(Contants.formatCurrency(s,current,money));
                            tvPriceFood.addTextChangedListener(this);
                        }
                    } catch (NumberFormatException n){
                        s.clear();
                    }
                }
            });
        }
    }
}
