package com.training.cst.quanlytienantrua.DataManager.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.training.cst.quanlytienantrua.UserInterface.Fragment.FragmentHistoryItem;

import java.util.List;

/**
 * Created by longdg on 06/12/2016.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    List<String> listNgay;
    public ViewPagerAdapter(FragmentManager fm,int mNumOfTabs,List<String> listNgay) {
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
        this.listNgay = listNgay;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new FragmentHistoryItem(listNgay.get(position));
        return fragment;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
    //Get Title
    @Override
    public CharSequence getPageTitle(int position) {
        return listNgay.get(position);
    }
}
