package com.training.cst.quanlytienantrua.UserInterface.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.training.cst.quanlytienantrua.DataManager.Adapter.FragmentPayFoodAdapter;
import com.training.cst.quanlytienantrua.DataManager.Adapter.ItemClickListener;
import com.training.cst.quanlytienantrua.DataManager.Adapter.SpinnerAdapter;
import com.training.cst.quanlytienantrua.DataManager.Object.Food;
import com.training.cst.quanlytienantrua.DataManager.Object.Person;
import com.training.cst.quanlytienantrua.Database.DatabaseUser;
import com.training.cst.quanlytienantrua.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longdg on 05/12/2016.
 */

public class FragmentPayBasedOnFood extends Fragment {
    private DatabaseUser mDatabaseUser;
    private List<Food> mListFood;
    private List<Person> mListPerson;
    private SpinnerAdapter mSpinnerAdapter;
    private FragmentPayFoodAdapter mFragmentPayFoodAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = (View)inflater.inflate(R.layout.fragment_pay_baseonfood,null,false);
        RecyclerView mRecyclerView = (RecyclerView) myView.findViewById(R.id.fragment_food_rv);
        initSpinner();
        mListPerson = new ArrayList<>();
        mListPerson = mDatabaseUser.getPerson();
        mFragmentPayFoodAdapter = new FragmentPayFoodAdapter(getContext(), mListPerson, new ItemClickListener() {
            @Override
            public void clickItemListtener(View view, int possition) {

            }
        });
        Spinner spinner = (Spinner) myView.findViewById(R.id.myspinner);
        spinner.setAdapter(mSpinnerAdapter);
        spinner.setDropDownVerticalOffset(50);
        spinner.setDropDownHorizontalOffset(40);
        spinner.setSelection(mSpinnerAdapter.getCount());
        setUpRecycleView(mRecyclerView);
        return myView;
    }
    // init multiSelection spinner
    private void initSpinner() {
        mDatabaseUser = new DatabaseUser(getContext());
        mListFood = mDatabaseUser.getFood();
        mListFood.add(new Food("Select food to pay",0));
        mSpinnerAdapter = new SpinnerAdapter(getContext(), mListFood, new ItemClickListener() {
            @Override
            public void clickItemListtener(View view, int possition) {
                CheckBox chkFood = (CheckBox) view.findViewById(R.id.ckbfood);
                if(chkFood.isChecked()){
                    Toast.makeText(getContext(), "hic" + possition, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    // setup recycleview
    private void setUpRecycleView(RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        rv.setAdapter(mFragmentPayFoodAdapter);

    }
}
