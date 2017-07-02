package com.soboapps.deviceuserinforeader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SQLController {

    private static final String TAG = "SQLController";

    private DBHelper dbHelper;
    private Context ourcontext;
    private SQLiteDatabase database;

    public SQLController(Context c) {
        ourcontext = c;
    }

    public SQLController open() throws SQLException {
        dbHelper = new DBHelper(ourcontext);
        database = dbHelper.getWritableDatabase();
        return this;

    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String owner, String email, String phone, String manufacturer, String model,
                       String serial, String sim, String date, String time, String location) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.DEVICEINFO_OWNER, owner);
        contentValues.put(DBHelper.DEVICEINFO_EMAIL, email);
        contentValues.put(DBHelper.DEVICEINFO_PHONE_NUMBER, phone);
        contentValues.put(DBHelper.DEVICEINFO_MANUFACTURER, manufacturer);
        contentValues.put(DBHelper.DEVICEINFO_MODEL, model);
        contentValues.put(DBHelper.DEVICEINFO_SERIAL_NUMBMER, serial);
        contentValues.put(DBHelper.DEVICEINFO_SIM, sim);
        contentValues.put(DBHelper.DEVICEINFO_DATE, date);
        contentValues.put(DBHelper.DEVICEINFO_TIME, time);
        contentValues.put(DBHelper.DEVICEINFO_LOCATION, location);
        database.insert(DBHelper.DEVICEINFO_TABLE, null, contentValues);

    }

    public Cursor fetch() {
        String[] columns = new String[] { DBHelper.DEVICEINFO_OWNER, DBHelper.DEVICEINFO_EMAIL,
                DBHelper.DEVICEINFO_PHONE_NUMBER, DBHelper.DEVICEINFO_MANUFACTURER, DBHelper.DEVICEINFO_MODEL,
                DBHelper.DEVICEINFO_SERIAL_NUMBMER, DBHelper.DEVICEINFO_SIM, DBHelper.DEVICEINFO_DATE,
                DBHelper.DEVICEINFO_TIME, DBHelper.DEVICEINFO_LOCATION };
        Cursor cursor = database.query(DBHelper.DEVICEINFO_TABLE, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                Log.e(TAG, DatabaseUtils.dumpCurrentRowToString(cursor));
                cursor.moveToNext();
            }
        }
        return cursor;
    }

    public int update(long _id, String owner, String email, String phone, String manufacturer, String model,
                      String serial, String sim, String date, String time, String location) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.DEVICEINFO_OWNER, owner);
        contentValues.put(DBHelper.DEVICEINFO_EMAIL, email);
        contentValues.put(DBHelper.DEVICEINFO_PHONE_NUMBER, phone);
        contentValues.put(DBHelper.DEVICEINFO_MANUFACTURER, manufacturer);
        contentValues.put(DBHelper.DEVICEINFO_MODEL, model);
        contentValues.put(DBHelper.DEVICEINFO_SERIAL_NUMBMER, serial);
        contentValues.put(DBHelper.DEVICEINFO_SIM, sim);
        contentValues.put(DBHelper.DEVICEINFO_DATE, date);
        contentValues.put(DBHelper.DEVICEINFO_TIME, time);
        contentValues.put(DBHelper.DEVICEINFO_LOCATION, location);
        int i = database.update(DBHelper.DEVICEINFO_TABLE, contentValues,
                DBHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DBHelper.DEVICEINFO_TABLE, DBHelper._ID + "=" + _id, null);
    }
}