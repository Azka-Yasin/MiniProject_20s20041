package com.example.muscatbikesminiproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class BookRideActivity extends AppCompatActivity {

    EditText edRentalName, edRentalNumb, edhours;
    Button clearbtn, calcbtn, bookbikebtn, updatebtn, viewbtn, deletebtn;
    Spinner bikeSpinner;
    TextView displaycost, timeTV;
    DatePickerDialog datePickerDialog;
    Button dateButton, timeButton;
    //databse connectivity
    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ride);
        getSupportActionBar().setTitle("Book a Ride");
        ActionBar bar = getSupportActionBar();
        ColorDrawable cd = new ColorDrawable(Color.parseColor("#fac457"));
        bar.setBackgroundDrawable(cd);

        db=new DatabaseHelper(this);  //instance of database helper class

        initDatePicker();

        dateButton = findViewById(R.id.DatePickerButton);
        timeButton=findViewById(R.id.TimePickerButton);

        bikeSpinner = (Spinner) findViewById(R.id.bikespinner);
        clearbtn = (Button) findViewById(R.id.clearbtn1);
        calcbtn = (Button) findViewById(R.id.costbtn);
        bookbikebtn = (Button) findViewById(R.id.bookbtn1);
        edRentalName = (EditText) findViewById(R.id.edBookingName);
        edRentalNumb = (EditText) findViewById(R.id.edBookingPhone);
        edhours = (EditText) findViewById(R.id.edhours);
        displaycost = (TextView) findViewById(R.id.textView5);
        timeTV=(TextView) findViewById(R.id.textView7);
        updatebtn=(Button) findViewById(R.id.Updatebtn);
        viewbtn=(Button) findViewById(R.id.viewbtn);
        deletebtn=(Button) findViewById(R.id.delbtn);

//crud functions
        addData();
        updateData();
        deleteData();
        viewData();


//time picker

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();
                int hours=calendar.get(Calendar.HOUR_OF_DAY);
                int minutes=calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog= new TimePickerDialog(BookRideActivity.this, R.style.Theme_AppCompat_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        Calendar c= Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, i);
                        c.set(Calendar.MINUTE,i1);
                        c.setTimeZone(TimeZone.getDefault());
                        SimpleDateFormat format= new SimpleDateFormat("k:mm a");
                        String time= format.format(c.getTime());
                        timeTV.setText(time);
                    }
                },hours, minutes,false);
                timePickerDialog.show();
            }
        });
//drop down list

        String[] biketype = getResources().getStringArray(R.array.bikes);
        ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, biketype);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bikeSpinner.setAdapter(adapter2);

//buttons functions
        clearbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edRentalName.getText().clear();
                edRentalNumb.getText().clear();
                edhours.getText().clear();
                displaycost.setText("0 OMR");
                timeTV.setText("-------");
            }
        });

        calcbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numbofhrs = Integer.parseInt(edhours.getText().toString());
                if (numbofhrs > 6)
                    Toast.makeText(getApplicationContext(), "Maximum Hours Allowed: 6", Toast.LENGTH_LONG).show();
                else {
                    int hourlyrate = 5;
                    int finalcost = (hourlyrate * numbofhrs);
                    displaycost.setText(String.valueOf(finalcost) + " OMR");
                }
            }
        });
    }


    public void addData()
    {
        bookbikebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean ins =db.insert(edRentalName.getText().toString(), edRentalNumb.getText().toString(), Integer.parseInt(edhours.getText().toString()),dateButton.getText().toString(),timeTV.getText().toString());
                if (ins==true){
                    Toast.makeText(getApplicationContext(), "Data Inserted", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "Data not inserted", Toast.LENGTH_LONG).show();

            }
        });
    }
    //update
    public void updateData()
    {
        updatebtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                boolean upd=db.update(edRentalName.getText().toString(), edRentalNumb.getText().toString(), Integer.parseInt(edhours.getText().toString()),dateButton.getText().toString(),timeTV.getText().toString());
                if(upd==true)
                    Toast.makeText(getApplicationContext(),"Data Updated",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "Data not updated",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void viewData() {
        viewbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Cursor r=db.getAllData();
                if(r.getCount()==0)
                {
                    showMessage("Error", "Nothing found");
                    return;
                }
                StringBuffer b=new StringBuffer();
                while(r.moveToNext())
                {
                    b.append("Name:"+r.getString(0)+"\n");
                    b.append("PhoneNumber"+r.getString(1)+"\n");
                    b.append("HoursBooked:"+r.getString(2)+"\n");
                    b.append("Date:"+r.getString(3)+"\n");
                    b.append("Time:"+r.getString(4)+"\n");
                }
                showMessage("Bike Rental Details",b.toString());
            }
        });

    }

    //status bar
    public void showMessage(String title,String msg)
    {
        AlertDialog.Builder adb=new AlertDialog.Builder(this);
        adb.setCancelable(true);
        adb.setTitle(title);
        adb.setMessage(msg);
        adb.show();
    }

    public void deleteData() {
        deletebtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Integer del=db.delete(edRentalName.getText().toString());
                if(del>0)
                    Toast.makeText(getApplicationContext(),"Data deleted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(),"Dat not deleted",Toast.LENGTH_LONG).show();
            }
        });

    }

//for date picker
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };
        //to get today's date as default
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);

}

    private String makeDateString(int day, int month, int year) {

        return getMonthFormat(month) + " " + day +" "+ year;
    }

    private String getMonthFormat(int month) {
        if (month==1)
            return "JAN";
        if (month==2)
            return "FEB";
        if (month==3)
            return "MAR";
        if (month==4)
            return "APR";
        if (month==5)
            return "MAY";
        if (month==6)
            return "JUN";
        if (month==7)
            return "JUL";
        if (month==8)
            return "AUG";
        if (month==9)
            return "SEP";
        if (month==10)
            return "OCT";
        if (month==11)
            return "NOV";
        if (month==12)
            return "DEC";
        return "JAN";   //default
    }
    //date picker
    public void datePicker(View view) {
        datePickerDialog.show();
    }
}