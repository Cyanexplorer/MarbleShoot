package com.example.Marble_Game.LocaDBFunc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by User on 2016/12/10.
 */

public class SqlDbModifier {
    static private Cursor cursor;
    final private SqlDbOpenHelper helper;
    final private int MaxStorage=10;
    public SqlDbModifier(Context context, String DBname){
        helper = new SqlDbOpenHelper(context);
    }

    //used for fining out the amount of data
    public int DBAmount(String TBname){
        SQLiteDatabase DBR=helper.getReadableDatabase();
        cursor = DBR.query(
                TBname, null, null, null, null, null, null);
        cursor.moveToFirst();
        return cursor == null?0: cursor.getCount();
    }

    public boolean DBFull(String TBname){
        return DBAmount(TBname) >= MaxStorage?true:false;
    }

    //used for checking whether there is any data already stored in database
    public boolean DBCreate(String TBname){
        SQLiteDatabase DBR=helper.getReadableDatabase();
        cursor = DBR.query(
                TBname, null, null, null, null, null, null);
        int amount=cursor.getCount();
        DBR.close();
        return amount==0?false:true;
    }

    //database file read
    public String ReadDBfile(String TBname, String tar, int order){
        SQLiteDatabase DBR=helper.getReadableDatabase();
        cursor = DBR.query(
                TBname, new String[]{tar}, null, null, null, null, null);
        cursor.move(order+1);
        String result = cursor.getString(0);
        DBR.close();
        return result;
    }

    public int ReadDBfileInt(String TBname, String tar, int order){
        SQLiteDatabase DBR=helper.getReadableDatabase();
        cursor = DBR.query(
                TBname, new String[]{tar}, null, null, null, null, null);
        cursor.moveToPosition(order+1);
        int result = cursor.getInt(0);
        DBR.close();
        return result;
    }

    public void AddDBfile(ContentValues values, String TBname){
        values.put("_id",DBAmount(TBname)+1);
        SQLiteDatabase  DBW=helper.getWritableDatabase();
        DBW.insert(TBname,null,values);
        DBW.close();
    }

    public void UpdateDBfile(ContentValues values, String TBname, String request){
        SQLiteDatabase  DBW=helper.getWritableDatabase();
        DBW.update(TBname,values,request,null);
        DBW.close();
    }

    public void DeleteDBfile(String TBname){
        SQLiteDatabase DBW=helper.getWritableDatabase();
        boolean t=DBW.delete(TBname,"_id=1",null)>0;
        Log.i("Delete",Boolean.toString(t));
        for(int i=0;i<DBAmount(TBname);i++){
            ContentValues values=new ContentValues();
            values.put("_id",Integer.toString(ReadDBfileInt(TBname,"_id",i)-1));
            UpdateDBfile(values,TBname,"_id="+Integer.toString(ReadDBfileInt(TBname,"_id",i)));
        }
        DBW.close();
    }
}
