package com.example.sandip.voting_system;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
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
 * Created by sandip on 8/30/2016.
 */

public class question_answer extends AppCompatActivity implements View.OnClickListener {
    private static final String question_answer = "http://democontact.esy.es/Voting_System/answer.php";
    public static final String KEY_EMAIL="username";
    public static final String KEY_ANS = "ans";
    public static final String KEY_ID="id";
    private RadioButton r1, r2;
    private Button btn;
    String ans1,email;
    String id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_view_layout);
        r1= (RadioButton) findViewById(R.id.radioButton3);
        r2= (RadioButton) findViewById(R.id.radioButton4);
        btn= (Button) findViewById(R.id.button2);
        Intent pre=getIntent();
       id = pre.getStringExtra("value");
        Integer pos= Integer.valueOf(id);
        id= String.valueOf(pos+1);
        Log.i("id", id);
        SharedPreferences sharedPreferences = getSharedPreferences("myloginapp", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email","Not Available");
        btn.setOnClickListener(this);
    }
    private void AnsUser(){
        if(r1.isChecked()) {
            ans1 = r1.getText().toString().trim();
        }
        else if(r2.isChecked()) {
            ans1 = r2.getText().toString().trim();
        }
        else{
            ans1=null;
        }
        Log.i("MY TEST",ans1);
        final String ans=ans1;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, question_answer,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(question_answer.this,response,Toast.LENGTH_LONG).show();
                        Intent pre=new Intent(question_answer.this,Question_page.class);
                        pre.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(pre);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(question_answer.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_EMAIL,email);
                params.put(KEY_ID,id);
                params.put(KEY_ANS,ans);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void onClick(View v){
        if(v==btn){
            AnsUser();
        }
    }
}
