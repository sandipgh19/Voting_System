package com.example.sandip.voting_system;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class registration extends AppCompatActivity implements View.OnClickListener {
    private static final String REGISTER_URL = "http://democontact.esy.es/Voting_System/register.php";
    public static final String KEY_USERNAME = "Name";
    public static final String KEY_GENDER= "Gender";
    public static final String KEY_EMAIL= "Email";
    public static final String KEY_PASSWORD = "Password";
    public static final String KEY_PHN = "Phn";
    public static final String KEY_ORGANIZATION = "Organization";
    public static final String KEY_POSITION = "Position";
    private EditText name,pass,phn,org,email1;
    private RadioButton rb1,rb2;
    private Spinner sp1;
    String sex,item;
    private Button buttonRegister;
    ArrayAdapter<String> adapter;
    String numbers[] = { "EMPLOYEE", "STUDENT" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        name= (EditText) findViewById(R.id.editText);
        rb1= (RadioButton) findViewById(R.id.radioButton);
        rb2= (RadioButton) findViewById(R.id.radioButton2);
        email1= (EditText) findViewById(R.id.editText1);
        pass= (EditText) findViewById(R.id.editText2);
        phn= (EditText) findViewById(R.id.editText3);
        org= (EditText) findViewById(R.id.editText4);
        sp1= (Spinner) findViewById(R.id.spinner);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, numbers);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adapter);

        buttonRegister = (Button) findViewById(R.id.button);

        buttonRegister.setOnClickListener(this);
    }
    private void registerUser() {
        final String Name = name.getText().toString().trim().toLowerCase();
        final String email = email1.getText().toString().trim().toLowerCase();
        final String password = pass.getText().toString().trim().toLowerCase();
        final String phone = phn.getText().toString().trim().toLowerCase();
        final String org1 =org.getText().toString().trim().toLowerCase();
        if(rb1.isChecked()) {
            sex = rb1.getText().toString().trim().toLowerCase();
        }
        if(rb2.isChecked()) {
            sex = rb2.getText().toString().trim().toLowerCase();
        }
        final String gender=sex;
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                item=numbers[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
            final String pos=numbers[sp1.getSelectedItemPosition()];


        Log.i("My TEST", Name+" "+email+" "+password+" "+phone+" "+org1+" "+gender+" "+pos);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        int code = Integer.parseInt(response);
                        if (code==3){
                            Toast.makeText(registration.this,"You have Registered Successfully !",Toast.LENGTH_LONG).show();
                            //Use intent to go to a activity
                            Intent next=new Intent(registration.this,MainActivity.class);
                            startActivity(next);
                        }
                        if (code==1){
                            Toast.makeText(registration.this,"Please fill all the value !",Toast.LENGTH_LONG).show();
                        }
                        if(code==2){
                            Toast.makeText(registration.this,"Email already exists !",Toast.LENGTH_LONG).show();
                        }
                        if(code==4){
                            Toast.makeText(registration.this,"Please Try Again Later !",Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(registration.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_USERNAME,Name);
                params.put(KEY_GENDER,gender);
                params.put(KEY_EMAIL, email);
                params.put(KEY_PASSWORD,password);
                params.put(KEY_PHN,phone);
                params.put(KEY_ORGANIZATION,org1);
                params.put(KEY_POSITION,pos);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonRegister){
            registerUser();
        }
    }

}

