package com.teamtreehouse.friendlyforecast.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.teamtreehouse.friendlyforecast.services.Forecast;

import java.sql.SQLException;

/**
 * Created by daniel on 7/05/15.
 */
public class ForecastDataSource {
    private SQLiteDatabase mDatabase;
    private ForecastHelper mForecastHelper;
    private Context mContext;

    public ForecastDataSource(Context context) {
        mContext = context;
        mForecastHelper = new ForecastHelper(mContext);
    }

    //open
    public void open() throws SQLException{
        mDatabase = mForecastHelper.getWritableDatabase();
    }

    //close
    public void close() {
        mDatabase.close();
    }

    //insert
    public void insertForecast(Forecast forecast) {
        mDatabase.beginTransaction();
        try {
            for (Forecast.HourData hour : forecast.hourly.data) {
                ContentValues values = new ContentValues();
                values.put(ForecastHelper.COLUMN_TEMPERATURE, hour.temperature);
                mDatabase.insert(ForecastHelper.TABLE_TEMPERATURES, null, values);
            }
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
    }

    //select
    public Cursor selectAllTemperatures () {
        Cursor cursor = mDatabase.query(
                ForecastHelper.TABLE_TEMPERATURES, //table
                new String[]{ForecastHelper.COLUMN_TEMPERATURE}, //column names
                null, //where clause
                null, //where params
                null, //group by
                null, //having by
                null  //order by
        );
        return cursor;
    }

    public Cursor selectTempsGreaterThan (String minTemp) {
        String whereClause = ForecastHelper.COLUMN_TEMPERATURE + " > ?";
        Cursor cursor = mDatabase.query(
                ForecastHelper.TABLE_TEMPERATURES, //table
                new String[]{ForecastHelper.COLUMN_TEMPERATURE}, //column names
                whereClause, //where clause
                new String[]{minTemp}, //where params
                null, //group by
                null, //having by
                null  //order by
        );
        return cursor;
    }

    //update
    public int updateTemperature(double newTemp) {
        ContentValues values = new ContentValues();
        values.put(ForecastHelper.COLUMN_TEMPERATURE, newTemp);
        int rowsUpdated = mDatabase.update(
                ForecastHelper.TABLE_TEMPERATURES,
                values, //values
                null, //
                null
        );
        return rowsUpdated;
    }

    //delete
    public void deleteAll() {
        mDatabase.delete(
                ForecastHelper.TABLE_TEMPERATURES,
                null,
                null
        );
    }

}
