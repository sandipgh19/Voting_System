package com.example.sandip.voting_system;

import android.app.ProgressDialog;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class Question_page extends AppCompatActivity {
    public static final String Question_URL = "http://democontact.esy.es/Voting_System/question.php";
    private ProgressDialog loading;
    private ListView listView;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences sharedPreferences = getSharedPreferences("myloginapp", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email","Not Available");
        listView=(ListView)findViewById(R.id.listView);
        sendRequest();
    }
    private void sendRequest(){

        StringRequest stringRequest = new StringRequest(Question_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Question_page.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String json){
        ParseJSON_question pj = new ParseJSON_question(json);
        pj.parseJSON_question();
        Custom_question cl = new Custom_question(this,ParseJSON_question.ids, ParseJSON_question.question);
        listView.setAdapter(cl);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent next= new Intent(getApplicationContext(), question_answer.class);
               // next.putExtra("value",Integer.toString(i));
               // startActivity(next);
            }
        });
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
                break;
            case R.id.follow:
                  Intent next1 = new Intent(this, Followers.class);
                  startActivity(next1);
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