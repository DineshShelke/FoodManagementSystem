package com.android.foodmanagementsystem.mDetailHotel;


import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.android.foodmanagementsystem.R;
import com.android.foodmanagementsystem.mDetail.Global;
import com.android.foodmanagementsystem.mRecycler.Singleton;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * A simple {@link Fragment} subclass.
 */
public class SummaryFragment extends Fragment {

    private String urlPost = Global.url+"PostServlet";
    private TimePicker timePicker;
    private EditText nameEditTxt,qtyEditTxt,priceEditTxt,higgintimeEditTxt;
    private Button saveBtn,postItem;
    private String date;
    private SharedPreferences preferences;

    public SummaryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_summary,container,false);

        postItem = (Button) view.findViewById(R.id.postItem);
        //getting the timepicker object
        timePicker = (TimePicker)view.findViewById(R.id.timePicker);

        view.findViewById(R.id.buttonAlarm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //We need a calendar object to get the specified time in millis
                //as the alarm manager method takes time in millis to setup the alarm

                Calendar calendar = Calendar.getInstance();
                if(Build.VERSION.SDK_INT >= 23){
                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                            timePicker.getHour(), timePicker.getMinute(), 0);
                }
                else {
                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                            timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                }
                setAlarm(calendar.getTimeInMillis());
            }
        });

        //Toast.makeText(getActivity(), "onCreateView SummaryFragment", Toast.LENGTH_SHORT).show();

        postItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDialog();
            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        date = sdf.format(new Date());

        Log.d("date",date);

        return view;
    }

    private void setAlarm(long time) {
        //getting the alarm manager

        AlarmManager am = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);

        //creating a new intent specifying the broadcast receiver
        Intent i = new Intent(getContext(), MyAlarm.class);

        //creating a pending intent using the intent
        PendingIntent pi = PendingIntent.getBroadcast(getContext(), 0, i, 0);

        //setting the repeating alarm that will be fired every day
        am.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pi);
        Toast.makeText(getContext(), "Alarm is set", Toast.LENGTH_SHORT).show();
    }

    private void displayDialog(){

        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.dialog_layout, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder.setCancelable(true);
        nameEditTxt = (EditText) promptsView.findViewById(R.id.nameEditTxt);
        qtyEditTxt = (EditText) promptsView.findViewById(R.id.qtyEditTxt);
        priceEditTxt = (EditText) promptsView.findViewById(R.id.priceEditTxt);
        higgintimeEditTxt = (EditText) promptsView.findViewById(R.id.higgintimeEditTxt);
        saveBtn = (Button) promptsView.findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = true;
                if(nameEditTxt.getText().toString().isEmpty()){
                    nameEditTxt.setError("Item is manditory");
                    isValid = false;

                }else if(qtyEditTxt.getText().toString().isEmpty()){
                    qtyEditTxt.setError("Qantity is manditory");
                    isValid = false;

                }else if(priceEditTxt.getText().toString().isEmpty()){
                    priceEditTxt.setError("Price is manditory");
                    isValid = false;

                }else if(higgintimeEditTxt.getText().toString().isEmpty()){
                    higgintimeEditTxt.setError("Higgin time is manditory");
                    isValid = false;

                }else {
                    setData();
                }
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    private void setData() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlPost,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        preferences = getActivity().getSharedPreferences("dataSend", 0);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.commit();
                        editor.putString("json", response);
                        editor.commit();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("name", nameEditTxt.getText().toString());
                param.put("qty", qtyEditTxt.getText().toString());
                param.put("price", priceEditTxt.getText().toString());
                param.put("higgintime", higgintimeEditTxt.getText().toString());
                param.put("date", date.toString());
                return param;
            }
        };
        Singleton.getInstance(getContext()).addToRequestque(stringRequest);

    }
}