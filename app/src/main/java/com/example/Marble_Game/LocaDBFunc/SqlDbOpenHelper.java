package com.example.Marble_Game.LocaDBFunc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by User on 2016/12/10.
 */

public class SqlDbOpenHelper extends SQLiteOpenHelper {
    private final static int _DBVersion = 1; //<-- 版本
    private static String _DBName="Record"; //<-- db name
    private static String _TableName="Score"; //<-- table name

    public SqlDbOpenHelper(Context context) {
        super(context, _DBName, null, _DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL = "CREATE TABLE IF NOT EXISTS " + _TableName + "( " +
                "_id INTEGER PRIMARY KEY, " +
                "Time VARCHAR," +
                "PlayerName VARCHAR,"+
                "Score VARCHAR" +
                ");";
        sqLiteDatabase.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // TODO Auto-generated method stub
        final String SQL = "DROP TABLE " + _TableName;
        sqLiteDatabase.execSQL(SQL);
    }
}

