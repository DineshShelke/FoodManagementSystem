package com.android.foodmanagementsystem.mDetailDeleveryBoy;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.foodmanagementsystem.R;
import com.android.foodmanagementsystem.mDetailUser.AcceptedPostFragment;
import com.android.foodmanagementsystem.mDetailUser.AllPostFragment;
import com.android.foodmanagementsystem.mRecycler.MyAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoyTabFragment extends Fragment {

    public  static TabLayout tabLayout;
    public  static ViewPager viewPager;
    public  static int int_items= 2;
    public final static String MY_ACTION = "MY_ACTION";

    public BoyTabFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab2,null);

        tabLayout=(TabLayout)v.findViewById(R.id.tabs);

        viewPager=(ViewPager)v.findViewById(R.id.viewpager);

        viewPager.setAdapter(new MyAdapterBoy( getChildFragmentManager()));

        tabLayout.post(new Runnable() {
            @Override
            public void run() {

                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return v;
    }

    public class MyAdapterBoy extends FragmentPagerAdapter {

        public MyAdapterBoy(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new BoyDraftskFragment();
                case 1:
                    return new BarcodeFragment();
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
                    return "Place Order";
                case 1:
                    return "Barcode Scan";
            }

            return null;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

    }

}
