package com.example.sandip.voting_system;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {
    private TextView txt1,txt2,txt3,txt4,txt5,txt6;
    private String id;
    private ImageView img;
    private ProgressDialog loading;
    public static final String KEY_EMAIL="username";
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txt1= (TextView) findViewById(R.id.textView2);
        txt2= (TextView) findViewById(R.id.textView4);
        txt3= (TextView) findViewById(R.id.textView6);
        txt4= (TextView) findViewById(R.id.textView8);
        txt5= (TextView) findViewById(R.id.textView10);
        txt6= (TextView) findViewById(R.id.textView12);
        img= (ImageView) findViewById(R.id.imageView2);
        Intent intent=getIntent();
       // txt3.setText(intent.getStringExtra(Login.KEY_EMAIL));
       // id=intent.getStringExtra(Login.KEY_ID);
        SharedPreferences sharedPreferences = getSharedPreferences("myloginapp", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email","Not Available");

        //Showing the current logged in email to textview
        txt3.setText(email);
        getData();
        getImage();
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next=new Intent(Profile.this,Upload.class);
                next.putExtra("user",email);
                startActivity(next);
            }
        });

    }
    private void getData() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.DATA_URL,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Profile.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
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

    private void showJSON(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject Data = result.getJSONObject(0);
            txt2.setText(Data.getString(Config.KEY_Gender));
            txt1.setText(Data.getString(Config.KEY_Name));
            txt4.setText(Data.getString(Config.KEY_Phn));
            txt5.setText(Data.getString(Config.KEY_Pos));
            txt6.setText(Data.getString(Config.KEY_Org));
        } catch (JSONException e) {
            e.printStackTrace();
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
            break;
            case R.id.question:
                Intent next = new Intent(this, Question_page.class);
                startActivity(next);
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
    private void getImage() {

        class GetImage extends AsyncTask<String,Void,Bitmap> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Profile.this, "Uploading...", null,true,true);
            }

            @Override
            protected void onPostExecute(Bitmap b) {
                super.onPostExecute(b);
                loading.dismiss();
                img.setImageBitmap(b);
            }

            @Override
            protected Bitmap doInBackground(String... params) {
                String add = "http://democontact.esy.es/Voting_System/upload/"+email+".jpg";
                URL url = null;
                Bitmap image = null;
                try {
                    url = new URL(add);
                    image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return image;
            }
        }

        GetImage gi = new GetImage();
        gi.execute(email);
    }
}
