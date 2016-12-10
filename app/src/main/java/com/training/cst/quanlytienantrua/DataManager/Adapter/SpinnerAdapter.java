package com.training.cst.quanlytienantrua.DataManager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.training.cst.quanlytienantrua.DataManager.Object.Food;
import com.training.cst.quanlytienantrua.R;

import java.util.List;

/**
 * Created by longdg on 05/12/2016.
 */

public class SpinnerAdapter extends BaseAdapter {
    Context context;
    List<Food> listFood;
    ItemClickListener itemClickListener;
    boolean checked = false;
    public SpinnerAdapter(Context context, List<Food> listFood,ItemClickListener itemClickListener) {
        this.context = context;
        this.listFood = listFood;
        this.itemClickListener = itemClickListener;
    }
    public void loadCheck(boolean checked){
        this.checked = checked;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        int count = listFood.size();
        return count > 0 ? count - 1 : count;
    }

    @Override
    public Object getItem(int position) {
        return listFood.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_spinner_item,parent,false);
        }
        Food food = listFood.get(position);
        CheckBox ckbFood = (CheckBox) convertView.findViewById(R.id.ckbfood);
        ckbFood.setChecked(checked);
        if(position == listFood.size()-1){
            ckbFood.setVisibility(View.GONE);
        }
        ckbFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.clickItemListtener(v,position);
            }
        });
        TextView tvNameFood = (TextView) convertView.findViewById(R.id.nameFood);
        tvNameFood.setText(food.getNameFood());
        return convertView;
    }
}
