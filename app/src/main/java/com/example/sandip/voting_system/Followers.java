package com.example.sandip.voting_system;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Followers extends AppCompatActivity implements View.OnClickListener{

    Button flwPlus;
    String email;
    private ListView listView;
    private static final String FOLLOW_URL = "http://democontact.esy.es/Voting_System/follow.php";
    public static final String KEY_EMAIL="username";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.list_follow);
        SharedPreferences sharedPreferences = getSharedPreferences("myloginapp", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email","Not Available");

        flwPlus = (Button) findViewById(R.id.buttonFLWP);
        flwPlus.setOnClickListener(Followers.this);
        getData();
    }
    
    private void getData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,FOLLOW_URL ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("MY TEST",response);

                        showJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Followers.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                params.put(KEY_EMAIL,email);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String json){
        ParseJSON_follow pj = new ParseJSON_follow(json);
        pj.parseJSON_follow();
        Custom_follow cl = new Custom_follow(this, ParseJSON_follow.name,ParseJSON_follow.gender,ParseJSON_follow.phn);
        listView.setAdapter(cl);
    }

    @Override
    public void onClick(View view) {
        if (view==flwPlus){
            Intent i = new Intent(Followers.this,Add_follower.class);
            startActivity(i);
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity2, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.profile:
                Intent next = new Intent(this, Profile.class);
        startActivity(next);
                break;
            case R.id.question:
                Intent next1 = new Intent(this, Question_page.class);
                startActivity(next1);
                break;
            case R.id.follow:
                break;
            case R.id.logout:
                SharedPreferences sharedPreferences = getSharedPreferences("myloginapp", Context.MODE_PRIVATE);
                sharedPreferences.edit().clear().commit();
                Intent next2 = new Intent(this, MainActivity.class);
                next2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(next2);
                break;
            case R.id.response:
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
