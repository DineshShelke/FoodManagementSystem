package com.android.foodmanagementsystem.mDetail;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.foodmanagementsystem.MainActivity;
import com.android.foodmanagementsystem.R;
import com.android.foodmanagementsystem.mRecycler.Singleton;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText name,email,contact,password;
    private TextInputLayout inputLayoutName,inputLayoutEmail,inputLayoutContact,inputLayoutPassword;
    private TextView signUp;
    private Button regBtn;
    private String url = "http://192.168.1.111:8080/Web_Service/RegisterController";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

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
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            }
        });

    }

    private void initializeWidgets() {
        inputLayoutName = (TextInputLayout)findViewById(R.id.layoutName);
        inputLayoutEmail = (TextInputLayout)findViewById(R.id.layoutEmail);
        inputLayoutContact = (TextInputLayout)findViewById(R.id.layoutContact);
        inputLayoutPassword = (TextInputLayout)findViewById(R.id.layoutPassword);

        name = (EditText)findViewById(R.id.nameField);
        email = (EditText)findViewById(R.id.emailField);
        contact = (EditText)findViewById(R.id.contactField);
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
            inputLayoutContact.setError("Email is mandatory");
            isValid = false;
        }
        else{
            inputLayoutContact.setErrorEnabled(false);
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
            // Toast.makeText(this, "Sign Success", Toast.LENGTH_SHORT).show();
        }

    }

    private void Register() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("name",name.getText().toString());
                param.put("email",email.getText().toString());
                param.put("contact",contact.getText().toString());
                param.put("password",password.getText().toString());
                return param;
            }
        };
        Singleton.getInstance(RegisterActivity.this).addToRequestque(stringRequest);

    }

}
