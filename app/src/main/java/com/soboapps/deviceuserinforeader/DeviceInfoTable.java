package com.soboapps.deviceuserinforeader;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DeviceInfoTable {

                /*
            // This is not used anywhere yet, but thought it might be good to have this  to store
            // information in a DB or CSV file at some point
            csvCodeInfo = deviceOwner + "," + getGmail + "," + getPhoneNumber + "," +
                    android.os.Build.MANUFACTURER + "," + android.os.Build.MODEL + "," +
                    android.os.Build.SERIAL + "," + getSimSn + "," + getDate + "," +  getTime;

            private static final String INSERT_QRCODE_INFO = "insert into "
                    + TABLE_DEVICE_INFO + "(_ID,OWNER,EMAIL,PHONE,MANUFACTURER,MODEL,SERIAL,DATE,TIME)"+
                    " values ('1','deviceOwner','getGmail','getPhoneNumber','android.os.Build.MANUFACTURER', + " +
                    "'android.os.Build.MODEL','android.os.Build.SERIAL','getSimSn','getDate','getTime')";

            public static void onCreate(SQLiteDatabase database) {
                database.execSQL(INSERT_QRCODE_INFO);
            }
            */




    // Database table
    public static final String TABLE_DEVICE_INFO = "deviceinfo";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_OWNER = "owner";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE_NUMBER = "phone";
    public static final String COLUMN_MANUFACTURER = "manufacturer";
    public static final String COLUMN_MODEL = "model";
    public static final String COLUMN_SERIAL_NUMBMER = "serial";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_DEVICE_INFO
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_OWNER + " text not null, "
            + COLUMN_EMAIL + " text not null, "
            + COLUMN_PHONE_NUMBER + " text not null, "
            + COLUMN_MANUFACTURER + " text not null, "
            + COLUMN_MODEL + " text not null, "
            + COLUMN_SERIAL_NUMBMER + " text not null, "
            + COLUMN_DATE + " text not null, "
            + COLUMN_TIME
            + " text not null"
            + ");" ;

    private static final String INSERT_FIRST = "insert into "
            + TABLE_DEVICE_INFO + "(_ID,OWNER,EMAIL,PHONE,MANUFACTURER,MODEL,SERIAL,DATE,TIME)"+
            " values ('1','Your Name','YourDevice','your@emailinfo.com','2025551212','05-18-1969','10:59:31')";


    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        database.execSQL(INSERT_FIRST);

    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(DeviceInfoTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_DEVICE_INFO);
        onCreate(database);
    }
}
