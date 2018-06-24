package com.android.foodmanagementsystem.mDetailUser;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.foodmanagementsystem.R;
import com.android.foodmanagementsystem.mData.AcceptItem;
import com.android.foodmanagementsystem.mDetail.Global;
import com.android.foodmanagementsystem.mDetailAdmin.AdminStockFragment;
import com.android.foodmanagementsystem.mRecycler.OrderAdapter;
import com.android.foodmanagementsystem.mRecycler.PostAcceptAdapter;
import com.android.foodmanagementsystem.mRecycler.Singleton;
import com.android.foodmanagementsystem.mRecycler.UserPostAdapter;
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
public class AcceptedPostFragment extends Fragment {

    private ArrayList<AcceptItem> list = new ArrayList<>();
    private String orderUrl = Global.url + "OrderServletAccept";
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private SharedPreferences preferences;

    private boolean _hasLoadedOnce= true; // your boolean field

    public AcceptedPostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_accepted_post, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewAccept);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        orderAdapter = new OrderAdapter(getActivity());
        recyclerView.setAdapter(orderAdapter);
        recyclerView.setHasFixedSize(true);
        orderAdapter.notifyDataSetChanged();
        getPost();
        return view;
    }

    private void getPost() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, orderUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        orderAdapter = new OrderAdapter(getActivity());
                        recyclerView.setAdapter(orderAdapter);
                        recyclerView.setHasFixedSize(true);
                        orderAdapter.notifyDataSetChanged();
                        list.clear();
                        list = parseJson(response);
                        orderAdapter.setData(list);
                        orderAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("userId", Global.userId);
                return param;
            }
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
                    item.setOrderId(jo.getString("order_id"));
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
                    item.setUserAddress(jo.getString("User_address"));
                    /*
                    String id = jo.getString("id");
                    String orderid = jo.getString("order_id");
                    String barcodedata = jo.getString("name")+":"+jo.getString("price")+":"+jo.getString("totalitemprice")+":"+jo.getString("User_name")+":"+jo.getString("User_email")+":"+jo.getString("User_contact")+":"+jo.getString("User_address");

                    preferences = getActivity().getSharedPreferences("dataSend", 0);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.commit();
                    editor.clear();
                    editor.putString("itemid", id);
                    editor.putString("orderid", orderid);
                    editor.putString("barcode", barcodedata);
                    editor.commit();*/

                    arrayList.add(item);

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    @Override
    public void setUserVisibleHint(boolean isFragmentVisible_) {
        super.setUserVisibleHint(true);

        if (AcceptedPostFragment.this.isVisible()) {
            // we check that the fragment is becoming visible
            if (isFragmentVisible_ == _hasLoadedOnce) {
                //new NetCheck().execute();
                //callToast();
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                orderAdapter = new OrderAdapter(getActivity());
                recyclerView.setAdapter(orderAdapter);
                recyclerView.setHasFixedSize(true);
                orderAdapter.notifyDataSetChanged();
                getPost();
            }
        }
    }

}
