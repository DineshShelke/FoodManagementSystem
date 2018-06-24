package com.android.foodmanagementsystem.mRecycler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBar;

import com.android.foodmanagementsystem.mDetailHotel.StockFragment;
import com.android.foodmanagementsystem.mDetailHotel.SummaryFragment;
import com.android.foodmanagementsystem.mDetailHotel.TabFragment;

import static com.android.foodmanagementsystem.mDetailHotel.TabFragment.int_items;

/**
 * Created by Admin on 3/1/2017.
 */

public class MyAdapter extends FragmentPagerAdapter {

    public MyAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
       switch (position){
            case 0:
                return new SummaryFragment();
            case 1:
                return new StockFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return int_items;
    }

    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return "Summary";
            case 1:
                return "Stocks";
        }

        return null;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}