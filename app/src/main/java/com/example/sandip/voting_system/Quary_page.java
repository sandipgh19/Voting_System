package com.example.sandip.voting_system;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by sandip on 8/30/2016.
 */

public class Quary_page extends AppCompatActivity{
    private EditText txt;
    private Button btn;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quary_page);
        txt= (EditText) findViewById(R.id.editText6);
        btn= (Button) findViewById(R.id.button3);
    }
}
