package com.soboapps.deviceuserinforeader;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBHelper";

    public static final String DATABASE_NAME = "DEVICE_USER_INFO.DB";

    private static final int DATABASE_VERSION = 1;

    public static final String DEVICEINFO_TABLE = "deviceinfo";
    public static final String DEVICEINFO_TABLE_ARCHIVE = "deviceinfoarchive";

    public static final String _ID = "_id";
    public static final String DEVICEINFO_MY_ID = "myid";
    public static final String DEVICEINFO_OWNER = "owner";
	public static final String DEVICEINFO_EMAIL = "email";
    public static final String DEVICEINFO_PHONE_NUMBER = "phone";
    public static final String DEVICEINFO_MANUFACTURER = "manufacturer";
    public static final String DEVICEINFO_MODEL = "model";
    public static final String DEVICEINFO_SERIAL_NUMBMER = "serial";
    public static final String DEVICEINFO_SIM = "sim";
    public static final String DEVICEINFO_DATE_IN = "datein";
    public static final String DEVICEINFO_DATE_OUT = "dateout";
    public static final String DEVICEINFO_TIME_IN = "timein";
    public static final String DEVICEINFO_TIME_OUT = "timeout";
    public static final String DEVICEINFO_SITE = "site";
    public static final String DEVICEINFO_STATUS = "status";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + DEVICEINFO_TABLE +
                        "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        DEVICEINFO_MY_ID + " TEXT, " +
                        DEVICEINFO_OWNER + " TEXT, " +
                        DEVICEINFO_EMAIL + " TEXT, " +
						DEVICEINFO_PHONE_NUMBER + " TEXT, " +
						DEVICEINFO_MANUFACTURER + " TEXT, " +
						DEVICEINFO_MODEL + " TEXT, " +
						DEVICEINFO_SERIAL_NUMBMER + " TEXT, " +
                        DEVICEINFO_SIM + " TEXT, " +
						DEVICEINFO_DATE_IN + " TEXT, " +
                        DEVICEINFO_DATE_OUT + " TEXT, " +
                        DEVICEINFO_TIME_IN + " TEXT, " +
                        DEVICEINFO_TIME_OUT + " TEXT, " +
                        DEVICEINFO_SITE + " TEXT, " +
                        DEVICEINFO_STATUS + " TEXT);");

        String sql =
                "INSERT or replace INTO deviceinfo (myid, owner, email, phone, manufacturer, model, serial," +
                        "sim, datein, dateout, timein, timeout, site, status) VALUES('ID','Owner','Email','Phone_Num','Manufacture'," +
                        "'Model','Serial_Num','SIM','Date_In','Date_Out','Time_In','Time_Out','Site','true')";
        db.execSQL(sql);


        // Create Archive Log Table
        db.execSQL(
                "CREATE TABLE " + DEVICEINFO_TABLE_ARCHIVE +
                        "(" + _ID + " INTEGER PRIMARY KEY, " +
                        DEVICEINFO_MY_ID + " TEXT, " +
                        DEVICEINFO_OWNER + " TEXT, " +
                        DEVICEINFO_EMAIL + " TEXT, " +
                        DEVICEINFO_PHONE_NUMBER + " TEXT, " +
                        DEVICEINFO_MANUFACTURER + " TEXT, " +
                        DEVICEINFO_MODEL + " TEXT, " +
                        DEVICEINFO_SERIAL_NUMBMER + " TEXT, " +
                        DEVICEINFO_SIM + " TEXT, " +
                        DEVICEINFO_DATE_IN + " TEXT, " +
                        DEVICEINFO_DATE_OUT + " TEXT, " +
                        DEVICEINFO_TIME_IN + " TEXT, " +
                        DEVICEINFO_TIME_OUT + " TEXT, " +
                        DEVICEINFO_SITE + " TEXT, " +
                        DEVICEINFO_STATUS + " TEXT);");

        String sqlarchive =
                "INSERT or replace INTO deviceinfoarchive (myid, owner, email, phone, manufacturer, model, serial," +
                        "sim, datein, dateout, timein, timeout, site, status) VALUES('ID','Owner','Email','Phone_Num','Manufacture'," +
                        "'Model','Serial_Num','SIM','Date_In','Date_Out','Time_In','Time_Out','Site','true')";
        db.execSQL(sqlarchive);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DEVICEINFO_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DEVICEINFO_TABLE_ARCHIVE);
        onCreate(db);
    }

    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "message" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }
    }

}