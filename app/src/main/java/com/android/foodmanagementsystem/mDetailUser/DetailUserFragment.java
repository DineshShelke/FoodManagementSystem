package com.android.foodmanagementsystem.mDetailUser;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.foodmanagementsystem.R;
import com.android.foodmanagementsystem.mDetail.Global;
import com.android.foodmanagementsystem.mRecycler.Singleton;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailUserFragment extends Fragment{

    private TextView tvUserName,tvUserEmail,tvUserContact,tvUserAddress;
    private TextView tvitemName,tvPrice,tvTotalPrice;

    FragmentManager manager;
    ImageView imageView;
    Button button;
    EditText editText;
    String EditTextValue ;
    Thread thread ;
    public final static int QRcodeWidth = 500 ;
    Bitmap bitmap ;

    public DetailUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_user, container, false);

        if (getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        }

        tvitemName = (TextView)view.findViewById(R.id.itemName);
        tvPrice = (TextView)view.findViewById(R.id.itemPrice);
        tvTotalPrice = (TextView)view.findViewById(R.id.totoalItemPrice);

        tvUserName = (TextView)view.findViewById(R.id.userName);
        tvUserEmail = (TextView)view.findViewById(R.id.userEmail);
        tvUserContact = (TextView)view.findViewById(R.id.userContact);
        tvUserAddress = (TextView)view.findViewById(R.id.userAddress);
        setData();
        return view;
    }

    private void setData() {

        tvitemName.setText("Item Name : "+getArguments().getString("Name"));
        tvPrice.setText("Item Price : "+getArguments().getString("Item_Price"));
        tvTotalPrice.setText("Total Price : "+getArguments().getString("total"));
        tvUserName.setText("User Name : "+getArguments().getString("User_name"));
        tvUserEmail.setText("User Email : "+getArguments().getString("User_email"));
        tvUserContact.setText("User Contact : "+getArguments().getString("User_contact"));
        tvUserAddress.setText("User Address : "+getArguments().getString("User_address"));

    }

}
