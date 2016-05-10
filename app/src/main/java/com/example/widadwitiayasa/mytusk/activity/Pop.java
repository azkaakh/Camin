package com.example.widadwitiayasa.mytusk.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

import com.example.widadwitiayasa.mytusk.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by widadwitiayasa on 4/24/16.
 */
public class Pop extends AppCompatActivity {
    public static final String REGISTER_URL = "http://128.199.93.60/mytusk/index.php/Create/news";
    public static final String KEY_PLACE = "place";
    public static final String KEY_HOUR = "hour";
    public static final String KEY_MINUTE = "minute";
    public static final String KEY_YEAR = "year";
    public static final String KEY_MONTH = "month";
    public static final String KEY_DAY = "day";
    public static final String KEY_NOTES = "notes";
    public static final String KEY_ACCESS = "access";

    private EditText editTextPlace;
    private EditText editTextHour;
    private EditText editTextMinute;
    private EditText editTextYear;
    private EditText editTextMonth;
    private EditText editTextDay;
    private EditText editTextNotes;

    private Button buttonCreate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popwindow);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));


            editTextPlace = (EditText) findViewById(R.id.etPlace);
            editTextHour = (EditText) findViewById(R.id.etHour);
            editTextMinute = (EditText) findViewById(R.id.etMinute);
            editTextYear = (EditText) findViewById(R.id.etYear);
            editTextMonth = (EditText) findViewById(R.id.etMonth);
            editTextDay = (EditText) findViewById(R.id.etDay);
            editTextNotes = (EditText) findViewById(R.id.etNotes);

            buttonCreate = (Button) findViewById(R.id.ibCreate);
            buttonCreate.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    registerUser();
                }
            });
        }

    private void registerUser() {
        final String place = editTextPlace.getText().toString().trim();
        final String hour = editTextHour.getText().toString().trim();
        final String minute = editTextMinute.getText().toString().trim();
        final String year = editTextYear.getText().toString().trim();
        final String month = editTextMonth.getText().toString().trim();
        final String day = editTextDay.getText().toString().trim();
        final String notes = editTextNotes.getText().toString().trim();
        Toast.makeText(Pop.this, "response", Toast.LENGTH_LONG).show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                Toast.makeText(Pop.this, response, Toast.LENGTH_LONG).show();
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String code = jsonResponse.getString("code");
                    if (code.matches("200")){
                        String message = "Registrasi berhasil";
                        Intent a = new Intent(Pop.this, Drawer.class);
                        a.putExtra("messageberhasil", message);
                        startActivityForResult(a, 1);
                    }
                    else {
                        Toast.makeText(Pop.this, "Register gagal", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(Pop.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put(KEY_PLACE, place);
                params.put(KEY_HOUR, hour);
                params.put(KEY_MINUTE, minute);
                params.put(KEY_YEAR, year);
                params.put(KEY_MONTH, month);
                params.put(KEY_DAY, day);
                params.put(KEY_NOTES, notes);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
