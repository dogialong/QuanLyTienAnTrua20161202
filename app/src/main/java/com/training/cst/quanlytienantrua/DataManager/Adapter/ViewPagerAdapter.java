package com.training.cst.quanlytienantrua.DataManager.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.training.cst.quanlytienantrua.UserInterface.Fragment.FragmentFood;

/**
 * Created by longdg on 06/12/2016.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public ViewPagerAdapter(FragmentManager fm,int mNumOfTabs) {
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new FragmentFood();
        return fragment;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
    //Get Title
    @Override
    public CharSequence getPageTitle(int position) {
        return "a";
    }
}
