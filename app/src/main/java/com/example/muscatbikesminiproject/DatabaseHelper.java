package com.example.muscatbikesminiproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.stream.Stream;

public class DatabaseHelper extends SQLiteOpenHelper
{
    public static final String DB_name= "bikerentals.db";
    public static final String table_name="BikeRentals";
    public static final String colName="Name";
    public static final String colNumb="Phone_Number";
    public static final String colHours="Hours_Booked";
    public static final String colDate="Date";
    public static final String colTime="Start_Time";

    //constructor
    public DatabaseHelper(Context con)
    {
        super(con,DB_name,null,1);
    }

    public void onCreate(SQLiteDatabase sqldb){
        sqldb.execSQL("create table " +table_name+ "(Name TEXT PRIMARY KEY , Phone_Number TEXT,Hours_Booked TEXT,Date TEXT, Start_Time TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
        onCreate(db);
    }

    public boolean insert(String name, String numb, int hrs, String date, String time)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(colName,name);
        contentValues.put(colNumb,numb);
        contentValues.put(colHours,hrs);
        contentValues.put(colDate,date);
        contentValues.put(colTime,time);

        long result=db.insert(table_name,null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }

    public boolean update(String name, String numb, int hrs, String date, String time)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(colName,name);
        contentValues.put(colNumb,numb);
        contentValues.put(colHours,hrs);
        contentValues.put(colDate,date);
        contentValues.put(colTime,time);

        db.update(table_name,contentValues,"NAME=?",new String[]{name});
        return true;
    }
    public Integer delete(String name)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(table_name,"NAME=?",new String[]{name});
    }
    public Cursor getAllData()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from " +table_name,null);
        return cursor;
    }
}

