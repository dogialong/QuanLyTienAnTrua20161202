package com.training.cst.quanlytienantrua.UserInterface.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.training.cst.quanlytienantrua.DataManager.Adapter.FragmentOverviewAdapter;
import com.training.cst.quanlytienantrua.DataManager.Object.Person;
import com.training.cst.quanlytienantrua.Database.DatabaseUser;
import com.training.cst.quanlytienantrua.Helper.Contants;
import com.training.cst.quanlytienantrua.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FragmentOverview extends Fragment {
    private DatabaseUser mDatabaseUser;
    private List<Person> mListPerson = new ArrayList<>();
    private TextView tvTotalMoney;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = (View) inflater.inflate(R.layout.fragment_overview, container, false);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_overview_lv);
        mDatabaseUser = new DatabaseUser(getContext());
        mListPerson = mDatabaseUser.getPerson();
        tvTotalMoney = (TextView) view.findViewById(R.id.fragment_overview_tv_moneytotal);
        try {
            if(mDatabaseUser.getTotalMoney(mListPerson) < 0){
                tvTotalMoney.setText("-" + Contants.formatCurrencyForTextView
                        (String.valueOf(mDatabaseUser.getTotalMoney(mListPerson)),"",0.0));
            } else {
                tvTotalMoney.setText(Contants.formatCurrencyForTextView
                        (String.valueOf(mDatabaseUser.getTotalMoney(mListPerson)),"",0.0));
            }
        } catch (NumberFormatException n){
            tvTotalMoney.setText("0.0");
        }
        setUpRecycleView(mRecyclerView);
        setHasOptionsMenu(true);
        return view;
    }

    // setup recycleview
    private void setUpRecycleView(RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        rv.setAdapter(new FragmentOverviewAdapter(getActivity(), mListPerson));
    }
}
