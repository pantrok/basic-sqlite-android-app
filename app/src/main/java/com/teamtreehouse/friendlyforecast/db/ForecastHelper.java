package com.teamtreehouse.friendlyforecast.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by daniel on 7/05/15.
 */
public class ForecastHelper extends SQLiteOpenHelper {

    public static final String TABLE_TEMPERATURES = "TEMPERATURES";

    private static final String DB_NAME = "temperatures.db";
    private static final int DB_VERSION = 1;
    private static final String DB_CREATE = "CREATE TABLE "+ TABLE_TEMPERATURES +" ("+
            BaseColumns._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "TEMPERATURE REAL)";

    public ForecastHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
