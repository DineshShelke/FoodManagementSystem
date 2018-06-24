package com.android.foodmanagementsystem.mDetailAdmin;
import android.app.ActionBar;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.foodmanagementsystem.R;
import com.android.foodmanagementsystem.mData.Item;
import com.android.foodmanagementsystem.mDetail.Global;
import com.android.foodmanagementsystem.mRecycler.Singleton;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdminTabFragment extends Fragment  {

    private ArrayList<Item> list = new ArrayList<>();
    public  static TabLayout tabLayout;
    public  static ViewPager viewPager = null;
    public  static int int_items= 2;
    public final static String MY_ACTION = "MY_ACTION";
    private String adminUrlPost = Global.url+"GetServlet";
    private SharedPreferences preferences;

    public AdminTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_tab,null);

        tabLayout = (TabLayout)view.findViewById(R.id.adminTabs);

        viewPager = (ViewPager)view.findViewById(R.id.adminViewpager);

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

        viewPager.setAdapter(new MyAdapterAdmin( getChildFragmentManager()));

        tabLayout.post(new Runnable() {
            @Override
            public void run() {

                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return view;
    }

    public class MyAdapterAdmin extends FragmentPagerAdapter {

        public MyAdapterAdmin(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new AdminAcceptFragment();
                case 1:
                    return new AdminStockFragment();
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
                    return "Stocks";
            }

            return null;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

    }
}
