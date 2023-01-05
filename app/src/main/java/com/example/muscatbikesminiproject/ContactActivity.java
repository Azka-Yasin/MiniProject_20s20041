package com.example.muscatbikesminiproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ContactActivity extends AppCompatActivity {

    Button webiste;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        getSupportActionBar().setTitle("Contact Us");

        ActionBar bar= getSupportActionBar();
        ColorDrawable cd= new ColorDrawable(Color.parseColor("#fac457"));
        bar.setBackgroundDrawable(cd);

        webiste=(Button) findViewById(R.id.webbutton);

        webiste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stg="https://muscatbikes.com/";
                //implicit intent
                Intent obj =new Intent(Intent.ACTION_VIEW, Uri.parse(stg));
                startActivity(obj);
            }
        });
    }

}