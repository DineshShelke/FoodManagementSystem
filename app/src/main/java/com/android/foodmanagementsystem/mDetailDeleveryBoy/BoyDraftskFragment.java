package com.android.foodmanagementsystem.mDetailDeleveryBoy;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.foodmanagementsystem.R;
import com.android.foodmanagementsystem.mData.AcceptItem;
import com.android.foodmanagementsystem.mDetail.Global;
import com.android.foodmanagementsystem.mDetailHotel.TabFragment;
import com.android.foodmanagementsystem.mRecycler.DeleveryBoyAdapter;
import com.android.foodmanagementsystem.mRecycler.OrderAdapter;
import com.android.foodmanagementsystem.mRecycler.Singleton;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoyDraftskFragment extends Fragment {

    private ArrayList<AcceptItem> list = new ArrayList<>();
    private String getUserUrl = Global.url + "GetUserDetailServlet";
    private RecyclerView recyclerView;
    private DeleveryBoyAdapter deleveryBoyAdapter;

    public BoyDraftskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_draftsk, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewBoy);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        deleveryBoyAdapter = new DeleveryBoyAdapter(getActivity());
        recyclerView.setAdapter(deleveryBoyAdapter);
        recyclerView.setHasFixedSize(true);
        deleveryBoyAdapter.notifyDataSetChanged();
        getPost();
        return view;
    }

    private void getPost() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, getUserUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        deleveryBoyAdapter = new DeleveryBoyAdapter(getActivity());
                        recyclerView.setAdapter(deleveryBoyAdapter);
                        recyclerView.setHasFixedSize(true);
                        deleveryBoyAdapter.notifyDataSetChanged();
                        list.clear();
                        list = parseJson(response);
                        deleveryBoyAdapter.setData(list);
                        deleveryBoyAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
        };
        Singleton.getInstance(getContext()).addToRequestque(stringRequest);
    }

    private ArrayList<AcceptItem> parseJson(String response) {
        Log.d("StringStock",response);
        ArrayList<AcceptItem> arrayList = new ArrayList<>();
        arrayList.clear();
        try {

            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonarray = jsonObject.getJSONArray("Response");
            if (jsonarray != null) {
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jo = jsonarray.getJSONObject(i);
                    AcceptItem item = new AcceptItem();
                    item.setId(jo.getString("id"));
                    item.setName(jo.getString("name"));
                    item.setQty(jo.getString("qty"));
                    item.setPrice(jo.getString("price"));
                    item.setHiggintime(jo.getString("higgintime"));
                    item.setDate(jo.getString("date"));
                    item.setItemquantity(jo.getString("itemquantity"));
                    item.setTotoalitemprice(jo.getString("totalitemprice"));

                    item.setUserName(jo.getString("User_name"));
                    item.setUserEmail(jo.getString("User_email"));
                    item.setUserContact(jo.getString("User_contact"));
                    item.setUserRole(jo.getString("User_role"));
                    item.setUserAddress(jo.getString("User_address"));
                    item.setUserPassword(jo.getString("User_password"));

                    arrayList.add(item);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public void CallFragment(View v){

        FragmentManager FM;
        FragmentTransaction FT;
        FM = getActivity().getSupportFragmentManager();
        FT = FM.beginTransaction();
        FT.replace(R.id.containerDeleveryBoy, new DetailsDeleveryBoyFragment()).commit();

    }

}
