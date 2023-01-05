package com.example.muscatbikesminiproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    EditText logemail,logpassword;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");

        ActionBar bar= getSupportActionBar();
        ColorDrawable cd= new ColorDrawable(Color.parseColor("#fac457"));
        bar.setBackgroundDrawable(cd);

        logemail = findViewById(R.id.edLogEmail);
        logpassword = findViewById(R.id.edLogPassword);
        login= (Button) findViewById(R.id.LoginButton);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //only for now there is no authentication
                Intent intent = new Intent(getApplicationContext(), UserHomePageActivity.class);
                startActivity(intent);

                //make toast
            }
        });
    }
}