package com.example.widadwitiayasa.mytusk.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.widadwitiayasa.mytusk.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    public static final String REGISTER_URL = "http://128.199.93.60/mytusk/index.php/Sign/up";
    public static final String KEY_NAMADEPAN = "nama_depan";
    public static final String KEY_NAMABELAKANG = "nama_belakang";
    public static final String KEY_NOHP = "nohp";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_ACCESS = "access";

    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextNamaDepan;
    private EditText editTextNamaBelakang;
    private EditText editTextNoHp;
    private EditText editTextPassword;

    private TextView buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextNamaDepan = (EditText) findViewById(R.id.etNamadepan);
        editTextNamaBelakang = (EditText) findViewById(R.id.etNamaBelakang);
        editTextNoHp = (EditText) findViewById(R.id.etNohp);
        editTextUsername = (EditText) findViewById(R.id.etUsername);
        editTextPassword = (EditText) findViewById(R.id.etUsername);
        editTextEmail= (EditText) findViewById(R.id.etEmail);

//        String string = "user";
//        textAccess.setText(string);

        buttonRegister = (TextView) findViewById(R.id.tvRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                        registerUser();
                }
        });
    }

    private void registerUser() {
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String nohp = editTextNoHp.getText().toString().trim();
        final String nama_depan = editTextNamaDepan.getText().toString().trim();
        final String nama_belakang = editTextNamaBelakang.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                //Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_LONG).show();
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String code = jsonResponse.getString("code");
                    if (code.matches("200")){
                        String message = "Registrasi berhasil";
                        Intent a = new Intent(Register.this, Drawer.class);
                        a.putExtra("messageberhasil", message);
                        startActivityForResult(a, 1);
                    }
                    else {
                        Toast.makeText(Register.this, "Register gagal", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(Register.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put(KEY_USERNAME, username);
                params.put(KEY_PASSWORD, password);
                params.put(KEY_EMAIL, email);
                params.put(KEY_NOHP, nohp);
                params.put(KEY_NAMADEPAN, nama_depan);
                params.put(KEY_NAMABELAKANG, nama_belakang);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
