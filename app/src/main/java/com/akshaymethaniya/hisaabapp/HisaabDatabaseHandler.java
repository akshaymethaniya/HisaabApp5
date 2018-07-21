package com.akshaymethaniya.hisaabapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by FOR ORACLE on 02-05-2018.
 */
public class HisaabDatabaseHandler extends SQLiteOpenHelper {

    private static final String DB_NAME="HisaabDb";
    private static final int DB_VERSION=2;
    HisaabDatabaseHandler(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TYPE=1 FOR 'TO TAKE' & TYPE=0 FOR 'TO GIVE'
        String sql = "CREATE TABLE HISAAB_RECORD (_ID INTEGER PRIMARY KEY AUTOINCREMENT, " + "NAME TEXT, " + "DESCRIPTION TEXT," + "VALUE INTEGER, " +"DATE TEXT, "+"STATUS INTEGER, "+ "TYPE INTEGER);";
        db.execSQL(sql);
    }
    public  void insertRecord(SQLiteDatabase db, String name, String desc, int value, String s, int type,int status){
        ContentValues values=new ContentValues();
        values.put("NAME",name);
        values.put("VALUE",value);
        values.put("TYPE",type);
        values.put("DESCRIPTION",desc);
        values.put("DATE",s);
        values.put("STATUS",status);
        db.insert("HISAAB_RECORD",null,values);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion==1){
            String sql = "CREATE TABLE HISAAB_RECORD (_ID INTEGER PRIMARY KEY AUTOINCREMENT, " + "NAME TEXT, " + "DESCRIPTION TEXT," + "VALUE INTEGER, " +"DATE TEXT, "+"STATUS INTEGER, "+ "TYPE INTEGER);";
            db.execSQL("drop table HISAAB_RECORD");
            db.execSQL(sql);
        }
    }
}
