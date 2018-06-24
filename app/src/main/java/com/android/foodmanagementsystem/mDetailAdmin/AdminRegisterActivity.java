package com.android.foodmanagementsystem.mDetailAdmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.foodmanagementsystem.MainActivity;
import com.android.foodmanagementsystem.R;
import com.android.foodmanagementsystem.mDetail.Global;
import com.android.foodmanagementsystem.mRecycler.Singleton;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AdminRegisterActivity extends AppCompatActivity {

    private EditText name,email,contact,address,password;
    private TextInputLayout inputLayoutName,inputLayoutEmail,inputLayoutContact,inputLayoutAddress,inputLayoutPassword;
    private TextView signUp;
    private Button regBtn;
    private Toolbar myToolbar;
    private Spinner mySpinnar;
    private String url = Global.url+"RegisterController";
    //private String url = "http://192.168.1.103:8080/Web_Service/RegisterController";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);

        myToolbar = (Toolbar)findViewById(R.id.toolbar_actionbar);
        myToolbar.setTitle(getResources().getString(R.string.admin));
        setSupportActionBar(myToolbar);

        //myToolbar.setNavigationIcon(R.drawable.ic_action_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminRegisterActivity.this,AdminLoginActivity.class));
                finish();
            }
        });
        mySpinnar = (Spinner)findViewById(R.id.spinnar);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AdminRegisterActivity.this,
                R.layout.custom_spinner_item,getResources().getStringArray(R.array.role));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinnar.setAdapter(myAdapter);

        mySpinnar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(AdminRegisterActivity.this, mySpinnar.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        initializeWidgets();

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BtnRegister();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminRegisterActivity.this,AdminLoginActivity.class));
                finish();
            }
        });

    }

    private void initializeWidgets() {
        inputLayoutName = (TextInputLayout)findViewById(R.id.layoutName);
        inputLayoutEmail = (TextInputLayout)findViewById(R.id.layoutEmail);
        inputLayoutContact = (TextInputLayout)findViewById(R.id.layoutContact);
        inputLayoutAddress = (TextInputLayout)findViewById(R.id.layoutAddress);
        inputLayoutPassword = (TextInputLayout)findViewById(R.id.layoutPassword);

        name = (EditText)findViewById(R.id.nameField);
        email = (EditText)findViewById(R.id.emailField);
        contact = (EditText)findViewById(R.id.contactField);
        address = (EditText)findViewById(R.id.addressField);
        password = (EditText)findViewById(R.id.passwordField);

        signUp = (TextView) findViewById(R.id.signUp);

        regBtn = (Button)findViewById(R.id.btnRegister);
    }

    private void BtnRegister() {


        boolean isValid = true;

        if(name.getText().toString().isEmpty()){
            inputLayoutName.setError("Name is mandatory");
            isValid = false;
        }
        else{
            inputLayoutName.setErrorEnabled(false);
        }

        if(email.getText().toString().isEmpty()){
            inputLayoutEmail.setError("Email is mandatory");
            isValid = false;
        }
        else{
            inputLayoutEmail.setErrorEnabled(false);
        }

        if(contact.getText().toString().isEmpty()){
            inputLayoutContact.setError("Contact is mandatory");
            isValid = false;
        }
        else{
            inputLayoutContact.setErrorEnabled(false);
        }

        if(address.getText().toString().isEmpty()){
            inputLayoutAddress.setError("Address is mandatory");
            isValid = false;
        }
        else {
            inputLayoutAddress.setErrorEnabled(false);
        }

        if(password.getText().toString().trim().length() < 8){
            inputLayoutPassword.setError("Minimum 8 charactor is required");
            isValid = false;
        }
        else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        if(isValid){

            Register();

        }

    }

    private void Register() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(AdminRegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AdminRegisterActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();

                param.put("name",name.getText().toString());
                param.put("email",email.getText().toString());
                param.put("contact",contact.getText().toString());
                param.put("address",address.getText().toString());
                param.put("role",mySpinnar.getSelectedItem().toString());
                param.put("password",password.getText().toString());
                return param;
            }
        };
        Singleton.getInstance(AdminRegisterActivity.this).addToRequestque(stringRequest);

    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() == 0){
            startActivity(new Intent(AdminRegisterActivity.this,AdminLoginActivity.class));
        } else {
            super.onBackPressed();
        }
    }
}
