package com.example.muscatbikesminiproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signup=(Button) findViewById(R.id.SignupButton);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //only for now there is no authentication
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

                //make toast
                //Toast.makeText(getApplicationContext(),"Account Created",Toast.LENGTH_LONG).show();
            }
        });
    }
}