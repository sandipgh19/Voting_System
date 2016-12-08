package com.example.sandip.voting_system;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sandip on 8/31/2016.
 */

public class Add_follower extends AppCompatActivity {
    private static final String ADD_FOLLOW_URL = "http://democontact.esy.es/Voting_System/add_follow.php";
    public static final String KEY_EMAIL = "Email";
    public static final String KEY_FOLLOW = "Follow";
    private AutoCompleteTextView txt;
    private Button save, cancel;
    String email;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_follower);
        txt = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        save = (Button) findViewById(R.id.btnadd);
        cancel = (Button) findViewById(R.id.btnCancel);
        SharedPreferences sharedPreferences = getSharedPreferences("myloginapp", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email", "Not Available");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save_follower();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pre=new Intent(Add_follower.this,Followers.class);
                startActivity(pre);
            }
        });
    }
    public void save_follower(){
        final String follow=txt.getText().toString().trim().toLowerCase();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, ADD_FOLLOW_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.i("My App: ",s);
                int code = Integer.parseInt(s);
                if (code==3 || code==2){
                    Toast.makeText(Add_follower.this,"You have Added Successfully !",Toast.LENGTH_LONG).show();
                    //Use intent to go to a activity
                      Intent next=new Intent(Add_follower.this,Followers.class);
                    startActivity(next);
                }
                if (code==1){
                    Toast.makeText(Add_follower.this,"Please fill all the value !",Toast.LENGTH_LONG).show();
                }
                if(code==4){
                    Toast.makeText(Add_follower.this,"Please Try Again Later !",Toast.LENGTH_LONG).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Add_follower.this,error.toString(),Toast.LENGTH_LONG).show();

                    }
                }){
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<>();
                params.put(KEY_EMAIL,email);
                params.put(KEY_FOLLOW,follow);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    }
