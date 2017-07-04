package com.soboapps.deviceuserinforeader;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBHelper";

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
    public static final String DEVICEINFO_STATUS = "status";

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
						DEVICEINFO_SERIAL_NUMBMER + " TEXT  not null unique, " +
                        DEVICEINFO_SIM + " TEXT, " +
						DEVICEINFO_DATE + " TEXT, " +
                        DEVICEINFO_TIME + " TEXT, " +
                        DEVICEINFO_STATUS + " TEXT not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DEVICEINFO_TABLE);
        onCreate(db);
    }

}