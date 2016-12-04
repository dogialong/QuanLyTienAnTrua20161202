package com.training.cst.quanlytienantrua.DataManager.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.training.cst.quanlytienantrua.DataManager.Object.Food;
import com.training.cst.quanlytienantrua.Database.DatabaseUser;
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
    public FragmentFoodAdapter(DatabaseUser mDatabaseUser, Context mContext, List<Food> mListFood) {
        this.mDatabaseUser = mDatabaseUser;
        this.mContext = mContext;
        this.mListFood = mListFood;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_food_item,null,false);
        return new ViewHolder(myView);
    }
    public void setmListPerson(List<Food> listFood) {
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
        holder.tvNameFood.setText(mListFood.get(position).getNameFood());
        holder.tvPriceFood.setText(String.valueOf(mListFood.get(position).getPriceFood()));
        holder.btnDeleteFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.clickItemListtener(v,position);
            }
        });
        holder.btnModifyFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.clickItemListtener(v,position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private SwipeRevealLayout swipeLayout;
        private TextView tvNameFood;
        private Button btnDeleteFood;
        private Button btnModifyFood;
        private TextView tvPriceFood;
        public ViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeRevealLayout) itemView.findViewById(R.id.swipe_layout);
            btnDeleteFood = (Button) itemView.findViewById(R.id.fragment_food_item_rv_btn_delete);
            btnModifyFood = (Button) itemView.findViewById(R.id.fragment_food_item_rv_btn_sua);
            tvPriceFood = (TextView) itemView.findViewById(R.id.fragment_food_item_rv_tv_price);
            tvNameFood = (TextView) itemView.findViewById(R.id.fragment_food_item_rv_tv_name);
        }
    }
}
