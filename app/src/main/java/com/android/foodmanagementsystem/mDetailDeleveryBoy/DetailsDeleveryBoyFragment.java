package com.android.foodmanagementsystem.mDetailDeleveryBoy;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.foodmanagementsystem.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsDeleveryBoyFragment extends Fragment {

    private SharedPreferences preferences;
    private TextView tvItemName,tvHigginTime,tvItemPrice,tvUserName,tvUserEmail,tvUserContact,tvUserAddress;
    public DetailsDeleveryBoyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details_delevery_boy, container, false);

        tvItemName = (TextView)view.findViewById(R.id.itemName);
        tvHigginTime = (TextView)view.findViewById(R.id.higginTime);
        tvItemPrice = (TextView)view.findViewById(R.id.itemPrice);
        tvUserName = (TextView)view.findViewById(R.id.userName);
        tvUserEmail = (TextView)view.findViewById(R.id.userEmail);
        tvUserContact = (TextView)view.findViewById(R.id.userContact);
        tvUserAddress = (TextView)view.findViewById(R.id.userAddress);

        setData();
        return view;
    }

    private void setData() {


        String name = getArguments().getString("name");
        String higgintime = getArguments().getString("higgintime");
        String itemprice = getArguments().getString("itemprice");

        tvItemName.setText(getArguments().getString("name"));
        tvHigginTime.setText(getArguments().getString("higgintime"));
        tvItemPrice.setText(getArguments().getString("itemprice"));
        tvUserName.setText(getArguments().getString("User_name"));
        tvUserEmail.setText(getArguments().getString("User_email"));
        tvUserContact.setText(getArguments().getString("User_contact"));
        tvUserAddress.setText(getArguments().getString("User_address"));
    }

    private void getUserInformation() {
        preferences = getContext().getSharedPreferences("dataSend", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.commit();
        String response = preferences.getString("json","0");
        editor.commit();
        Log.d("DeleveryBoy",response);
    }
}
