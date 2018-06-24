package com.android.foodmanagementsystem.mDetailAdmin;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.foodmanagementsystem.R;
import com.android.foodmanagementsystem.mData.AcceptItem;
import com.android.foodmanagementsystem.mData.Item;
import com.android.foodmanagementsystem.mDetail.Global;
import com.android.foodmanagementsystem.mRecycler.AdminRecyclerAdapter;
import com.android.foodmanagementsystem.mRecycler.PostAcceptAdapter;
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
public class AdminStockFragment extends Fragment {

    private ArrayList<AcceptItem> list = new ArrayList<>();
    private String acceptUrlPost = Global.url + "GetDataAcceptServlet";
    private RecyclerView recyclerView;
    private PostAcceptAdapter postAcceptAdapter;

    private boolean _hasLoadedOnce= true; // your boolean field

    public AdminStockFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_stock,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.adminRecyclerAccept);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        postAcceptAdapter = new PostAcceptAdapter(getActivity());
        recyclerView.setAdapter(postAcceptAdapter);
        recyclerView.setHasFixedSize(true);
        postAcceptAdapter.notifyDataSetChanged();
        getPost();
        return view;
    }

    private void getPost() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, acceptUrlPost,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        
                        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
                        postAcceptAdapter = new PostAcceptAdapter(getActivity());
                        recyclerView.setAdapter(postAcceptAdapter);
                        recyclerView.setHasFixedSize(true);
                        postAcceptAdapter.notifyDataSetChanged();
                        list.clear();
                        
                        list = parseJson(response);
                        postAcceptAdapter.setData(list);
                        postAcceptAdapter.notifyDataSetChanged();
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
                   // item.setStatus(jo.getString("status"));
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

        if (AdminStockFragment.this.isVisible()) {
            // we check that the fragment is becoming visible
            if (isFragmentVisible_ == _hasLoadedOnce) {
                //new NetCheck().execute();
                //callToast();
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
                postAcceptAdapter = new PostAcceptAdapter(getActivity());
                recyclerView.setAdapter(postAcceptAdapter);
                recyclerView.setHasFixedSize(true);
                postAcceptAdapter.notifyDataSetChanged();
                getPost();
            }
        }
    }
}