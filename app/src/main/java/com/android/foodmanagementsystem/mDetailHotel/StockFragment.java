package com.android.foodmanagementsystem.mDetailHotel;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.foodmanagementsystem.R;
import com.android.foodmanagementsystem.mData.Item;
import com.android.foodmanagementsystem.mDetail.Global;
import com.android.foodmanagementsystem.mRecycler.MyAdapter;
import com.android.foodmanagementsystem.mRecycler.RecyclerAdapter;
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
public class StockFragment extends Fragment {

    private ArrayList<Item> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private String urlGet = Global.url+"GetServlet";
    private SharedPreferences preferences;
    boolean _areLecturesLoaded = false;
    public StockFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stock, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerAdapter = new RecyclerAdapter(getActivity());
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerAdapter.notifyDataSetChanged();
        recyclerView.invalidate();

        getData();
        setData();

        return view;
    }

    private void setData(){

        preferences = getContext().getSharedPreferences("dataSend", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.commit();
        String response = preferences.getString("json","0");
        editor.commit();
        Log.d("Preference",response);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerAdapter = new RecyclerAdapter(getActivity());
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerAdapter.notifyDataSetChanged();
        recyclerView.invalidate();

        list = parseJSON(response);
        recyclerAdapter.setData(list);
        recyclerAdapter.notifyDataSetChanged();
    }

    private ArrayList<Item> parseJSON(String response) {
        ArrayList<Item> itemlist = new ArrayList<>();
        Log.d("Stock_Json_Response", response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonarray = jsonObject.getJSONArray("Response");
            if(jsonarray != null){
                for (int i = 0; i<jsonarray.length(); i++){
                    JSONObject jo = jsonarray.getJSONObject(i);
                    Item item = new Item();
                    item.setName(jo.getString("name"));
                    item.setPrice(jo.getString("price"));
                    item.setHiggintime(jo.getString("higgintime"));
                    itemlist.add(item);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return itemlist;
    }

    private void getData() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlGet,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerAdapter = new RecyclerAdapter(getActivity());
                        recyclerView.setAdapter(recyclerAdapter);
                        recyclerView.setHasFixedSize(true);
                        recyclerAdapter.notifyDataSetChanged();
                        recyclerView.invalidate();

                        list = parseJson(response);
                        recyclerAdapter.setData(list);
                        recyclerAdapter.notifyDataSetChanged();

                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.commit();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){};
        Singleton.getInstance(getContext()).addToRequestque(stringRequest);
    }

    private ArrayList<Item> parseJson(String response) {
        ArrayList<Item> itemlist = new ArrayList<>();
        Log.d("Stock_Json_Response", response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonarray = jsonObject.getJSONArray("Response");
                if(jsonarray != null){
                    for (int i = 0; i<jsonarray.length(); i++){
                        JSONObject jo = jsonarray.getJSONObject(i);
                        Item item = new Item();
                        item.setName(jo.getString("name"));
                        item.setPrice(jo.getString("price"));
                        item.setHiggintime(jo.getString("higgintime"));
                        itemlist.add(item);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return itemlist;
    }
}