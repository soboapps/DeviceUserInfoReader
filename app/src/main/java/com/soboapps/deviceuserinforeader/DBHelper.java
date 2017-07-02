package com.soboapps.deviceuserinforeader;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DEVICE_USER_INFO.DB";
    private static final int DATABASE_VERSION = 1;

    public static final String DEVICEINFO_TABLE = "deviceinfo";
    public static final String _ID = "_id";
    public static final String DEVICEINFO_OWNER = "owner";
	public static final String DEVICEINFO_EMAIL = "email";
    public static final String DEVICEINFO_PHONE_NUMBER = "phone";
    public static final String DEVICEINFO_MANUFACTURER = "manufacturer";
    public static final String DEVICEINFO_MODEL = "model";
    public static final String DEVICEINFO_SERIAL_NUMBMER = "serial";
    public static final String DEVICEINFO_SIM = "sim";
    public static final String DEVICEINFO_DATE = "date";
    public static final String DEVICEINFO_TIME = "time";
    public static final String DEVICEINFO_LOCATION = "location";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + DEVICEINFO_TABLE +
                        "(" + _ID + " INTEGER PRIMARY KEY, " +
                        DEVICEINFO_OWNER + " TEXT, " +
                        DEVICEINFO_EMAIL + " TEXT, " +
						DEVICEINFO_PHONE_NUMBER + " TEXT, " +
						DEVICEINFO_MANUFACTURER + " TEXT, " +
						DEVICEINFO_MODEL + " TEXT, " +
						DEVICEINFO_SERIAL_NUMBMER + " TEXT, " +
                        DEVICEINFO_SIM + " TEXT, " +
						DEVICEINFO_DATE + " TEXT, " +
                        DEVICEINFO_TIME + " TEXT, " +
                        DEVICEINFO_LOCATION + " TEXT not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DEVICEINFO_TABLE);
        onCreate(db);
    }

    public boolean insertDevice(String owner, String email, String phone, String manufacturer, String model,
                                String serial, String sim, String date, String time, String location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

		contentValues.put(DEVICEINFO_OWNER, owner);
		contentValues.put(DEVICEINFO_EMAIL, email);
		contentValues.put(DEVICEINFO_PHONE_NUMBER, phone);
		contentValues.put(DEVICEINFO_MANUFACTURER, manufacturer);
		contentValues.put(DEVICEINFO_MODEL, model);
		contentValues.put(DEVICEINFO_SERIAL_NUMBMER, serial);
        contentValues.put(DEVICEINFO_SIM, sim);
		contentValues.put(DEVICEINFO_DATE, date);
		contentValues.put(DEVICEINFO_TIME, time);
        contentValues.put(DEVICEINFO_LOCATION, location);

        db.insert(DEVICEINFO_TABLE, null, contentValues);
        return true;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, DEVICEINFO_TABLE);
        return numRows;
    }

    public boolean updateDevice(Integer id, String owner, String email, String phone, String manufacturer, String model,
                                String serial, String sim, String date, String time, String location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
		contentValues.put(DEVICEINFO_OWNER, owner);
		contentValues.put(DEVICEINFO_EMAIL, email);
		contentValues.put(DEVICEINFO_PHONE_NUMBER, phone);
		contentValues.put(DEVICEINFO_MANUFACTURER, manufacturer);
		contentValues.put(DEVICEINFO_MODEL, model);
		contentValues.put(DEVICEINFO_SERIAL_NUMBMER, serial);
        contentValues.put(DEVICEINFO_SIM, sim);
		contentValues.put(DEVICEINFO_DATE, date);
		contentValues.put(DEVICEINFO_TIME, time);
        contentValues.put(DEVICEINFO_LOCATION, location);

        db.update(DEVICEINFO_TABLE, contentValues, _ID + " = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteDevice(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DEVICEINFO_TABLE,
                _ID + " = ? ",
                new String[] { Integer.toString(id) });
    }

    public Cursor getDevice(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("SELECT * FROM " + DEVICEINFO_TABLE + " WHERE " +
                _ID + "=?", new String[]{Integer.toString(id)});
        return res;
    }

    public Cursor getAllDevices() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + DEVICEINFO_TABLE, null );
        return res;
    }

    // Adding new Device
    public void addDevice(Device device, String owner, String email, String phone, String manufacturer, String model,
                          String serial, String sim, String date, String time, String location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DEVICEINFO_OWNER, owner);
        contentValues.put(DEVICEINFO_EMAIL, email);
        contentValues.put(DEVICEINFO_PHONE_NUMBER, phone);
        contentValues.put(DEVICEINFO_MANUFACTURER, manufacturer);
        contentValues.put(DEVICEINFO_MODEL, model);
        contentValues.put(DEVICEINFO_SERIAL_NUMBMER, serial);
        contentValues.put(DEVICEINFO_SIM, sim);
        contentValues.put(DEVICEINFO_DATE, date);
        contentValues.put(DEVICEINFO_TIME, time);
        contentValues.put(DEVICEINFO_LOCATION, location);
// Inserting Row
        db.insert(DEVICEINFO_TABLE, null, contentValues);
        db.close(); // Closing database connection
    }


}