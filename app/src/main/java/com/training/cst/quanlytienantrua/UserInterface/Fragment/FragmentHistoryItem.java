package com.training.cst.quanlytienantrua.UserInterface.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.training.cst.quanlytienantrua.DataManager.Adapter.FragmentHistoryItemAdapter;
import com.training.cst.quanlytienantrua.DataManager.Object.History;
import com.training.cst.quanlytienantrua.Database.DatabaseUser;
import com.training.cst.quanlytienantrua.R;

import java.util.List;

/**
 * Created by longdg on 06/12/2016.
 */

public class FragmentHistoryItem extends Fragment {
    String title;
    private FragmentHistoryItemAdapter fragmentHistoryItemAdapter;
    private List<History> mListHistory;
    private DatabaseUser mDatabseUser;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_history, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_history_rv);
        mDatabseUser = new DatabaseUser(getContext());
        mListHistory = mDatabseUser.getHistoryByDate(title);
        fragmentHistoryItemAdapter = new FragmentHistoryItemAdapter(getActivity(),mListHistory);
        setUpRecycleView(recyclerView);
        return view;
    }
    public FragmentHistoryItem (String title) {
        this.title = title;
    }

    public FragmentHistoryItem() {
    }
    // setup recycleview
    private void setUpRecycleView(RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        rv.setAdapter(fragmentHistoryItemAdapter);
    }
}
