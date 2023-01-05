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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class BookServiceActivity extends AppCompatActivity {

    EditText CustomerName, CustomerNumber;
    Button clearbtn2, bookservicebtn, updatebtn2, viewbtn2, deletebtn2;
    TextView timeTV2;
    DatePickerDialog datePickerDialog2;
    Button dateButton2, timeButton2;
    //database connectivity
    DatabaseHelper2 db2;
    RadioButton RB1, RB2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_service);
        getSupportActionBar().setTitle("Book a Service");
        ActionBar bar = getSupportActionBar();
        ColorDrawable cd = new ColorDrawable(Color.parseColor("#fac457"));
        bar.setBackgroundDrawable(cd);

        db2=new DatabaseHelper2(this);  //instance of database helper class

        initDatePicker();

        dateButton2 = findViewById(R.id.datePickerButton2);
        timeButton2=findViewById(R.id.timePickerButton2);

        clearbtn2 = (Button) findViewById(R.id.clearbtn2);
        bookservicebtn = (Button) findViewById(R.id.bookbtn2);
        CustomerName = (EditText) findViewById(R.id.edBookingName2);
        CustomerNumber = (EditText) findViewById(R.id.edBookingPhone2);
        timeTV2=(TextView) findViewById(R.id.textView3);
        updatebtn2=(Button) findViewById(R.id.updatebtn2);
        viewbtn2=(Button) findViewById(R.id.viewbtn22);
        deletebtn2=(Button) findViewById(R.id.delbtn22);
        RB1=(RadioButton) findViewById(R.id.fitbtn);
        RB2=(RadioButton)findViewById(R.id.repairbtn);

        //crud functions
        addData2();
        updateData2();
        deleteData2();
        viewData2();


//time picker

        timeButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();
                int hours=calendar.get(Calendar.HOUR_OF_DAY);
                int minutes=calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog= new TimePickerDialog(BookServiceActivity.this, R.style.Theme_AppCompat_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        Calendar c= Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, i);
                        c.set(Calendar.MINUTE,i1);
                        c.setTimeZone(TimeZone.getDefault());
                        SimpleDateFormat format= new SimpleDateFormat("k:mm a");
                        String time= format.format(c.getTime());
                        timeTV2.setText(time);
                    }
                },hours, minutes,false);
                timePickerDialog.show();
            }
        });
//buttons functions
        clearbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomerName.getText().clear();
                CustomerNumber.getText().clear();
                timeTV2.setText("-------");
            }
        });
    }
    public void addData2()
    {
        bookservicebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean ins2 =db2.Insert(CustomerName.getText().toString(), CustomerNumber.getText().toString(),dateButton2.getText().toString(),timeTV2.getText().toString());
                if (ins2==true){
                    Toast.makeText(getApplicationContext(), "Data Inserted", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "Data not inserted", Toast.LENGTH_LONG).show();

            }
        });
    }
    //update
    public void updateData2()
    {
        updatebtn2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                boolean upd2=db2.Update(CustomerName.getText().toString(), CustomerNumber.getText().toString(),dateButton2.getText().toString(),timeTV2.getText().toString());
                if(upd2==true)
                    Toast.makeText(getApplicationContext(),"Data Updated",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "Data not updated",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void viewData2() {
        viewbtn2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Cursor r=db2.getAllData();
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
                   //radiobutton replace b.append("HoursBooked:"+r.getString(2)+"\n");
                    b.append("Date:"+r.getString(3)+"\n");
                    b.append("Time:"+r.getString(4)+"\n");
                }
                showMessage("Bike Service Booking Details",b.toString());
            }
        });

    }

    //status bar
    public void showMessage(String title,String msg)
    {
        AlertDialog.Builder adb2=new AlertDialog.Builder(this);
        adb2.setCancelable(true);
        adb2.setTitle(title);
        adb2.setMessage(msg);
        adb2.show();
    }

    public void deleteData2() {
        deletebtn2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Integer del=db2.Delete(CustomerName.getText().toString());
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
                dateButton2.setText(date);
            }
        };
        //to get today's date as default
        Calendar cal2 = Calendar.getInstance();
        int year = cal2.get(Calendar.YEAR);
        int month = cal2.get(Calendar.MONTH);
        int day = cal2.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog2 = new DatePickerDialog(this, style, dateSetListener, year, month, day);

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
        datePickerDialog2.show();
    }
}