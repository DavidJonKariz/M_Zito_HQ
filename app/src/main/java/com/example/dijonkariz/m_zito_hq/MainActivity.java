package com.example.dijonkariz.m_zito_hq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view) {
        Intent lin = new Intent(getApplicationContext(), Login.class);
        startActivity(lin);
    }

    public void register(View view) {
        Intent lin = new Intent(getApplicationContext(), Register.class);
        startActivity(lin);
    }
}
