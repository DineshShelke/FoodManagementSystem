package com.android.foodmanagementsystem.mDetailUser;


import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.foodmanagementsystem.R;
import com.android.foodmanagementsystem.mData.AcceptItem;
import com.android.foodmanagementsystem.mDetail.Global;
import com.android.foodmanagementsystem.mDetailAdmin.AdminAcceptFragment;
import com.android.foodmanagementsystem.mRecycler.AdminRecyclerAdapter;
import com.android.foodmanagementsystem.mRecycler.OrderAdapter;
import com.android.foodmanagementsystem.mRecycler.PostAcceptAdapter;
import com.android.foodmanagementsystem.mRecycler.Singleton;
import com.android.foodmanagementsystem.mRecycler.UserPostAdapter;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllPostFragment extends Fragment{

    private ArrayList<AcceptItem> list = new ArrayList<>();
    private String acceptUrlPost = Global.url + "GetDataAcceptServlet";
    private String userOrder = Global.url + "OrderServlet";
    private String orderUrl = Global.url + "SendBarcodeDataServlet";
    private String barcodeUrl = Global.url+"BarcodeServlet";
    private SharedPreferences preferences;
    private RecyclerView recyclerView;
    private UserPostAdapter userPostAdapter;
    private double totalPriceFinal = 0;
    private String itemid,orderid,data;

    ImageView imageView;
    Button button;
    EditText editText;
    String EditTextValue ;
    Thread thread ;
    public final static int QRcodeWidth = 500 ;
    Bitmap bitmap ;

    private boolean _hasLoadedOnce= true; // your boolean field

    public AllPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_post, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.userRecyclerAccept);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        userPostAdapter = new UserPostAdapter(AllPostFragment.this);
        recyclerView.setAdapter(userPostAdapter);
        recyclerView.setHasFixedSize(true);
        userPostAdapter.notifyDataSetChanged();
        getPost();

      /*  preferences = getContext().getSharedPreferences("dataSend", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.commit();
        itemid = preferences.getString("itemid","0");
        orderid = preferences.getString("orderid","0");
        data = preferences.getString("barcode","0");
        editor.commit();
        editor.commit();

        QRGenerator(data,itemid,orderid);

        Log.d("databarcode", itemid);
        Log.d("databarcode", orderid);
        Log.d("databarcode", data);*/

        return view;
    }

    private void getPost() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, acceptUrlPost,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
                        userPostAdapter = new UserPostAdapter(AllPostFragment.this);
                        recyclerView.setAdapter(userPostAdapter);
                        recyclerView.setHasFixedSize(true);
                        userPostAdapter.notifyDataSetChanged();
                        list.clear();
                        list = parseJson(response);
                        userPostAdapter.setData(list);
                        userPostAdapter.notifyDataSetChanged();
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
        Log.d("StringStockStock",response);
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
                    //item.setStatus(jo.getString("status"));

                    Global.itemId = jo.getString("id");

                   /* item.setUserName("User_name");
                    item.setUserEmail("User_email");
                    item.setUserContact("User_contact");
                    item.setUserAddress("User_address");
                    item.setUserRole("User_role");
                    item.setUserPassword("User_password");*/

                    arrayList.add(item);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public void prepareSelection(View view, final String id, final String name, final String qty, final String price, final String higgintime, final String date,final int counter) {
        Log.d("ValueFrom", id + " " + name + " " + qty + " " + price + " " + higgintime + " " + date);

        double d = Double.parseDouble(price);
        double d2 = (double) counter;

        totalPriceFinal = d*d2;
        itemid = id;
        final String totalPrice = String.valueOf(totalPriceFinal);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, userOrder,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("AcceptRespnse", response);
                        QRGeneratorr();
                        list.clear();
                        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
                        userPostAdapter = new UserPostAdapter(AllPostFragment.this);
                        recyclerView.setAdapter(userPostAdapter);
                        recyclerView.setHasFixedSize(true);
                        userPostAdapter.notifyDataSetChanged();

                        list = parseJson(response);
                        userPostAdapter.setData(list);
                        userPostAdapter.notifyDataSetChanged();

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
                param.put("userId", Global.userId);
                param.put("counter", String.valueOf(counter));
                param.put("totalprice", totalPrice);
                return param;
            }
        };
        Singleton.getInstance(getContext()).addToRequestque(stringRequest);
    }

    private void QRGeneratorr() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, orderUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            Log.d("DataBarcode", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("Response");
                            for (int i = 0;i<jsonArray.length();i++){
                                JSONObject jo = jsonArray.getJSONObject(i);
                                orderid = jo.getString("order_id");
                                Toast.makeText(getActivity(), "Order Id"+orderid, Toast.LENGTH_SHORT).show();
                                
                                QRGenerator();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("userId", Global.userId);
                return param;
            }
        };
        Singleton.getInstance(getActivity()).addToRequestque(stringRequest);

    }

    private void QRGenerator() {
       // Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, barcodeUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                            Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();

                              /*  preferences = getActivity().getSharedPreferences("dataSend", 0);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.commit();
                                editor.putString("barcode", String.valueOf(bitmap));
                                editor.commit();*/
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("itemId", itemid);
                param.put("order_id",orderid);
                param.put("userId", Global.userId);
                return param;
            }
        };
        Singleton.getInstance(getActivity()).addToRequestque(stringRequest);
    }


}

