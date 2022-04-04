package com.example.Marble_Game.Activity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.Marble_Game.LocaDBFunc.SqlDbModifier;

import static com.example.Marble_Game.util.Constants.TBname;

/**
 * Created by User on 2017/1/7.
 */

public class SearchDB extends AsyncTask<Object, Object, ContentValues[]> {

    private Context context;
    private ProgressDialog pd;

    public AsyncTaskResult<ContentValues[]> ATR=null;
    public SearchDB(Context context){
        this.context=context;
    }

    @Override
    protected ContentValues[] doInBackground(Object... strings) {
        SqlDbModifier DBM=new SqlDbModifier(context, TBname);
        ContentValues[] values;

        if(DBM.DBCreate(TBname)){
            values=new ContentValues[DBM.DBAmount(TBname)];
            for(int i=0;i<values.length;i++){
                ContentValues local_values=new ContentValues();
                local_values.put("PlayerName",DBM.ReadDBfile(TBname,"PlayerName",i));
                local_values.put("Score",DBM.ReadDBfile(TBname,"Score",i));
                local_values.put("Time",DBM.ReadDBfile(TBname,"Time",i));
                local_values.put("_id",DBM.ReadDBfile(TBname,"_id",i));
                values[i]=local_values;
                Log.i("item","Read "+Integer.toString(i)+" item");
            }
        }
        else values=new ContentValues[0];
        return values;
    }

    @Override
    protected void onPostExecute(ContentValues[] contentValues) {
        super.onPostExecute(contentValues);
        pd.cancel();
        ATR.taskFinish(contentValues);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd=new ProgressDialog(context);
        pd.setMessage("\tLoading...");
        pd.setCancelable(false);
        pd.show();
    }

}
