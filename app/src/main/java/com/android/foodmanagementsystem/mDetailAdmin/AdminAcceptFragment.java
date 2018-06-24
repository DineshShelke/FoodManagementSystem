package com.android.foodmanagementsystem.mDetailAdmin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.foodmanagementsystem.MainActivity;
import com.android.foodmanagementsystem.R;
import com.android.foodmanagementsystem.mData.Item;
import com.android.foodmanagementsystem.mDetail.Global;
import com.android.foodmanagementsystem.mRecycler.AdminRecyclerAdapter;
import com.android.foodmanagementsystem.mRecycler.RecyclerAdapter;
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
public class AdminAcceptFragment extends Fragment {

    private ArrayList<Item> list = new ArrayList<>();
    private String adminUrlPost = Global.url + "GetServlet";
    private String adminUrlAcceptPost = Global.url + "AcceptPostServlet";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AdminRecyclerAdapter recyclerAdapter;

    public AdminAcceptFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_adimin_accept, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.adminRecyclerview);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerAdapter = new AdminRecyclerAdapter(AdminAcceptFragment.this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerAdapter.notifyDataSetChanged();
        getPost();
        return view;
    }

    private void getPost() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, adminUrlPost,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
                        recyclerAdapter = new AdminRecyclerAdapter(AdminAcceptFragment.this);
                        recyclerView.setAdapter(recyclerAdapter);
                        recyclerView.setHasFixedSize(true);
                        recyclerAdapter.notifyDataSetChanged();
                        list.clear();
                        list = parseJson(response);
                        recyclerAdapter.setData(list);
                        recyclerAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
        };
        Singleton.getInstance(getContext()).addToRequestque(stringRequest);
    }

    private ArrayList<Item> parseJson(String response) {
        ArrayList<Item> arrayList = new ArrayList<>();
        arrayList.clear();
        try {

            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonarray = jsonObject.getJSONArray("Response");
            if (jsonarray != null) {
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jo = jsonarray.getJSONObject(i);
                    Item item = new Item();
                    item.setId(jo.getString("id"));
                    item.setName(jo.getString("name"));
                    item.setQty(jo.getString("qty"));
                    item.setPrice(jo.getString("price"));
                    item.setHiggintime(jo.getString("higgintime"));
                    item.setDate(jo.getString("date"));
                    item.setStatus(jo.getString("status"));
                    arrayList.add(item);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public void prepareSelection(View view, final String id, final String name, final String qty, final String price, final String higgintime, final String date) {
        Log.d("ValueFrom", id + " " + name + " " + qty + " " + price + " " + higgintime + " " + date);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, adminUrlAcceptPost,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("AcceptRespnse", response);
                        list.clear();
                        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
                        recyclerAdapter = new AdminRecyclerAdapter(AdminAcceptFragment.this);
                        recyclerView.setAdapter(recyclerAdapter);
                        recyclerView.setHasFixedSize(true);
                        recyclerAdapter.notifyDataSetChanged();

                        list = parseJson(response);
                        recyclerAdapter.setData(list);
                        recyclerAdapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("id", id);
                param.put("name", name);
                param.put("qty", qty);
                param.put("price", price);
                param.put("higgintime", higgintime);
                param.put("date", date);
                return param;
            }
        };
        Singleton.getInstance(getContext()).addToRequestque(stringRequest);
    }
}