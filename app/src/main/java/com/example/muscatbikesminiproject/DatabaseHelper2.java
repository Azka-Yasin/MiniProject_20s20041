package com.example.muscatbikesminiproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper2 extends SQLiteOpenHelper
{
    public static final String DB_name2= "bikeservicebookings.db";
    public static final String table_name2="BikeServiceBookings";
    public static final String colName2="Name";
    public static final String colNumb2="Phone_Number";
    public static final String colDate2="Date";
    public static final String colTime2="Booked_Time";


    //constructor
    public DatabaseHelper2(Context cont)
    {
        super(cont,DB_name2,null,1);
    }

    public void onCreate(SQLiteDatabase sqldb2){
        sqldb2.execSQL("create table " +table_name2+ "(Name TEXT PRIMARY KEY , Phone_Number TEXT,Date TEXT, Booked_Time TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db2, int oldVersion1, int newVersion1)
    {
        db2.execSQL("DROP TABLE IF EXISTS " + table_name2);
        onCreate(db2);
    }

    public boolean Insert(String name2, String numb2, String date2, String time2)
    {
        SQLiteDatabase db2=this.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(colName2,name2);
        contentValues.put(colNumb2,numb2);
        contentValues.put(colDate2,date2);
        contentValues.put(colTime2,time2);

        long result=db2.insert(table_name2,null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }

    public boolean Update(String name2, String numb2, String date2, String time2)
    {
        SQLiteDatabase db2=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(colName2,name2);
        contentValues.put(colNumb2,numb2);
        contentValues.put(colDate2,date2);
        contentValues.put(colTime2,time2);

        db2.update(table_name2,contentValues,"NAME=?",new String[]{name2});
        return true;
    }
    public Integer Delete(String name2)
    {
        SQLiteDatabase db2=this.getWritableDatabase();
        return db2.delete(table_name2,"NAME=?",new String[]{name2});
    }
    public Cursor getAllData()
    {
        SQLiteDatabase db2=this.getWritableDatabase();
        Cursor cursor2=db2.rawQuery("select * from " +table_name2,null);
        return cursor2;
    }
}

