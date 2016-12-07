package com.training.cst.quanlytienantrua.UserInterface.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.training.cst.quanlytienantrua.DataManager.Adapter.ViewPagerAdapter;
import com.training.cst.quanlytienantrua.R;

/**
 * Created by longdg on 05/12/2016.
 */

public class FragmentHistory extends Fragment {
    ViewPager viewPager;
    ViewPagerAdapter mViewPagerAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_statistical, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        mViewPagerAdapter = new ViewPagerAdapter(getFragmentManager(),3);
        viewPager.setAdapter(mViewPagerAdapter);
        viewPager.setCurrentItem(2);
        mViewPagerAdapter.notifyDataSetChanged();
        return view;
    }
}
