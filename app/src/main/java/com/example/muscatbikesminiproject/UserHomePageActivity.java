package com.example.muscatbikesminiproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class UserHomePageActivity extends AppCompatActivity {

    Button bkride, bkservice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);
        getSupportActionBar().setTitle("Home Page");

        ActionBar bar= getSupportActionBar();
        ColorDrawable cd= new ColorDrawable(Color.parseColor("#fac457"));
        bar.setBackgroundDrawable(cd);

        bkride=(Button) findViewById(R.id.BkRideBtn);
        bkservice=(Button) findViewById(R.id.BkServiceBtn);

        bkride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BookRideActivity.class);
                startActivity(intent);
            }
        });

        bkservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BookServiceActivity.class);
                startActivity(intent);
            }
        });


    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.option_home, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case R.id.option1:
                //put logout code here


                //firebase code---change later
                //FirebaseAuth.getinstance.signout();
                //startActivity(new Intent(getApplicationContext,OptionsActivity.class));
                //finish();
                return true;

            case R.id.option3:
                Intent op3intent = new Intent(getApplicationContext(), ContactActivity.class);
                startActivity(op3intent);
                return true;
            case R.id.option4:
                //close app code
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


}