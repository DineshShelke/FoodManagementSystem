package com.android.foodmanagementsystem.mDetailDeleveryBoy;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.foodmanagementsystem.MainActivity;
import com.android.foodmanagementsystem.R;
import com.android.foodmanagementsystem.mDetail.Global;
import com.android.foodmanagementsystem.mDetailAdmin.AdminLoginActivity;
import com.android.foodmanagementsystem.mRecycler.Singleton;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DeleveryBoyLoginActivity extends AppCompatActivity {

    private EditText email, password;
    private TextInputLayout inputLayoutEmail,inputLayoutPassword;
    private TextView signUp;
    private Button signupBtn;
    private Toolbar myToolbar;
    private AlertDialog.Builder builder;
    private String url = Global.url+"LoginServlet";
    private String Role = "Delevery Boy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delevery_boy_login);

        builder  = new AlertDialog.Builder(DeleveryBoyLoginActivity.this);

        myToolbar = (Toolbar)findViewById(R.id.toolbar_actionbar);
        myToolbar.setTitle(getResources().getString(R.string.delevery_Boy));
        setSupportActionBar(myToolbar);

        //myToolbar.setNavigationIcon(R.drawable.ic_action_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DeleveryBoyLoginActivity.this, MainActivity.class));
                finish();
            }
        });
        initializeWidgets();

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DeleveryBoyLoginActivity.this, DeleveryBoyRegisterActivity.class));
                finish();
            }
        });

    }

    private void initializeWidgets() {
        inputLayoutEmail = (TextInputLayout)findViewById(R.id.layoutEmail);
        inputLayoutPassword = (TextInputLayout)findViewById(R.id.layoutPassword);

        email = (EditText)findViewById(R.id.emailField);
        password = (EditText)findViewById(R.id.passwordField);

        signUp = (TextView)findViewById(R.id.signUp);

        signupBtn = (Button)findViewById(R.id.btnSubmit);
    }

    private void signUp() {

        boolean isValid = true;
        if(email.getText().toString().isEmpty()){
            inputLayoutEmail.setError("Email is mandatory");
            isValid = false;
        }
        else{
            inputLayoutEmail.setErrorEnabled(false);
        }

        if(password.getText().toString().trim().length() < 8){
            inputLayoutPassword.setError("Minimum 8 charactor is required");
            isValid = false;
        }
        else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        if(isValid){
            Login();
           // Toast.makeText(this, "Sign Success", Toast.LENGTH_SHORT).show();
        }

    }

    public void Login(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(DeleveryBoyLoginActivity.this, response, Toast.LENGTH_SHORT).show();
                        //Log.d("JSON",response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("Response");
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jo = jsonArray.getJSONObject(i);
                                Global.userId = jo.getString("id");
                                if (jo.getString("success").equals("Login Successfully")){
                                    builder.setTitle("Login");
                                    builder.setMessage(jo.getString("success"));
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(new Intent(DeleveryBoyLoginActivity.this,HomeActivityDelevery.class));
                                            finish();
                                        }
                                    });
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                }
                                else
                                {
                                    builder.setTitle("Login");
                                    displayAlert(jo.getString("success"));
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DeleveryBoyLoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();

                param.put("email",email.getText().toString());
                param.put("password",password.getText().toString());
                param.put("role",Role);
                return param;
            }
        };
        Singleton.getInstance(DeleveryBoyLoginActivity.this).addToRequestque(stringRequest);
    }

    public void displayAlert(String message){
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                email.setText("");
                password.setText("");
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(DeleveryBoyLoginActivity.this,MainActivity.class));
    }
}
