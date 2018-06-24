package com.android.foodmanagementsystem.mDetailUser;


import android.content.SharedPreferences;
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
import com.android.foodmanagementsystem.mData.Item;
import com.android.foodmanagementsystem.mDetail.Global;
import com.android.foodmanagementsystem.mDetailAdmin.AdminAcceptFragment;
import com.android.foodmanagementsystem.mDetailAdmin.AdminStockFragment;
import com.android.foodmanagementsystem.mDetailAdmin.AdminTabFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserTabFragment extends Fragment {

    private ArrayList<Item> list = new ArrayList<>();
    public  static TabLayout tabLayout;
    public  static ViewPager viewPager = null;
    public  static int int_items= 2;
    public final static String MY_ACTION = "MY_ACTION";
    private String adminUrlPost = Global.url+"GetServlet";

    public UserTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_tab,null);

        tabLayout = (TabLayout)view.findViewById(R.id.userTabs);

        viewPager = (ViewPager)view.findViewById(R.id.userViewpager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //Toast.makeText(getContext(), position, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setAdapter(new MyAdapterUser( getChildFragmentManager()));

        tabLayout.post(new Runnable() {
            @Override
            public void run() {

                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return view;

    }

    public class MyAdapterUser extends FragmentPagerAdapter {

        public MyAdapterUser(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new AllPostFragment();
                case 1:
                    return new AcceptedPostFragment();
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
                    return "Post";
                case 1:
                    return "Place Order";
            }

            return null;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

    }
}
