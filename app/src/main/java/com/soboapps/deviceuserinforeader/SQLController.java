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
    } // end of method

    public SQLController open() throws SQLException {
        dbHelper = new DBHelper(ourcontext);
        database = dbHelper.getWritableDatabase();
        return this;

    } // end of method


    public void close() {
        dbHelper.close();
    } // end of method


    public void insertDevice(String myid, String owner, String email, String phone, String manufacturer, String model,
                       String serial, String sim, String datein, String dateout, String timein, String timeout, String site, String status) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.DEVICEINFO_MY_ID, myid);
        contentValues.put(DBHelper.DEVICEINFO_OWNER, owner);
        contentValues.put(DBHelper.DEVICEINFO_EMAIL, email);
        contentValues.put(DBHelper.DEVICEINFO_PHONE_NUMBER, phone);
        contentValues.put(DBHelper.DEVICEINFO_MANUFACTURER, manufacturer);
        contentValues.put(DBHelper.DEVICEINFO_MODEL, model);
        contentValues.put(DBHelper.DEVICEINFO_SERIAL_NUMBMER, serial);
        contentValues.put(DBHelper.DEVICEINFO_SIM, sim);
        contentValues.put(DBHelper.DEVICEINFO_DATE_IN, datein);
        contentValues.put(DBHelper.DEVICEINFO_DATE_OUT, dateout);
        contentValues.put(DBHelper.DEVICEINFO_TIME_IN, timein);
        contentValues.put(DBHelper.DEVICEINFO_TIME_OUT, timeout);
        contentValues.put(DBHelper.DEVICEINFO_SITE, site);
        contentValues.put(DBHelper.DEVICEINFO_STATUS, status);
        database.insert(DBHelper.DEVICEINFO_TABLE, null, contentValues);

    } // end of method


    public void insertDeviceArchive(String myid, String owner, String email, String phone, String manufacturer, String model,
                             String serial, String sim, String datein, String dateout, String timein, String timeout, String site, String status) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.DEVICEINFO_MY_ID, myid);
        contentValues.put(DBHelper.DEVICEINFO_OWNER, owner);
        contentValues.put(DBHelper.DEVICEINFO_EMAIL, email);
        contentValues.put(DBHelper.DEVICEINFO_PHONE_NUMBER, phone);
        contentValues.put(DBHelper.DEVICEINFO_MANUFACTURER, manufacturer);
        contentValues.put(DBHelper.DEVICEINFO_MODEL, model);
        contentValues.put(DBHelper.DEVICEINFO_SERIAL_NUMBMER, serial);
        contentValues.put(DBHelper.DEVICEINFO_SIM, sim);
        contentValues.put(DBHelper.DEVICEINFO_DATE_IN, datein);
        contentValues.put(DBHelper.DEVICEINFO_DATE_OUT, dateout);
        contentValues.put(DBHelper.DEVICEINFO_TIME_IN, timein);
        contentValues.put(DBHelper.DEVICEINFO_TIME_OUT, timeout);
        contentValues.put(DBHelper.DEVICEINFO_SITE, site);
        contentValues.put(DBHelper.DEVICEINFO_STATUS, status);
        database.insert(DBHelper.DEVICEINFO_TABLE_ARCHIVE, null, contentValues);

    } // end of method

    /******************************************
     * <b>Description:</b> Returns database record
     * based on Serial Number
     *
     * @param sn - Serival Number
     * @return ContentValues
     ******************************************/
    public int getIdBySn(String sn) {

        ContentValues record = new ContentValues();

        Cursor cursor =  database.rawQuery("SELECT * FROM " + DBHelper.DEVICEINFO_TABLE + " WHERE " +
                DBHelper.DEVICEINFO_SERIAL_NUMBMER + "=?", new String[]{sn});

        if (cursor != null) {

            cursor.moveToFirst();
             int myIndex = cursor.getColumnIndex(DBHelper._ID);

             int iRecId = cursor.getInt(myIndex);
             record.put(DBHelper._ID,iRecId);
             return iRecId;

        }

   return -1;
    } // end of method



    public Cursor getActiveDevices() {
        //Cursor cursor = database.query(DBHelper.DEVICEINFO_TABLE, columns, null, null, null, null, null);
        Cursor cursor =  database.rawQuery("SELECT * FROM " + DBHelper.DEVICEINFO_TABLE + " WHERE " +
                DBHelper.DEVICEINFO_STATUS + "= ?", new String[] {"true"});
        if (cursor != null) {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                Log.e(TAG, DatabaseUtils.dumpCurrentRowToString(cursor));
                cursor.moveToNext();
            }

        }
        return cursor;
    } // end of method


    public Cursor getAllDevices() {
        Cursor cursor =  database.rawQuery( "SELECT * FROM " + DBHelper.DEVICEINFO_TABLE, null );
        if (cursor != null) {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                Log.e(TAG, DatabaseUtils.dumpCurrentRowToString(cursor));
                cursor.moveToNext();
            }
        }
        return cursor;
    } // end of method


    public Cursor getAllDevicesArchive() {
        Cursor cursor =  database.rawQuery( "SELECT * FROM " + DBHelper.DEVICEINFO_TABLE_ARCHIVE, null );
        if (cursor != null) {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                Log.e(TAG, DatabaseUtils.dumpCurrentRowToString(cursor));
                cursor.moveToNext();
            }
        }
        return cursor;
    } // end of method


    public int updateDeviceArchive(long _id, String myid, String owner, String email, String phone, String manufacturer, String model,
                            String serial, String sim, String datein, String dateout, String timein, String timeout, String site, String status) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.DEVICEINFO_MY_ID, myid);
        contentValues.put(DBHelper.DEVICEINFO_OWNER, owner);
        contentValues.put(DBHelper.DEVICEINFO_EMAIL, email);
        contentValues.put(DBHelper.DEVICEINFO_PHONE_NUMBER, phone);
        contentValues.put(DBHelper.DEVICEINFO_MANUFACTURER, manufacturer);
        contentValues.put(DBHelper.DEVICEINFO_MODEL, model);
        contentValues.put(DBHelper.DEVICEINFO_SERIAL_NUMBMER, serial);
        contentValues.put(DBHelper.DEVICEINFO_SIM, sim);
        contentValues.put(DBHelper.DEVICEINFO_DATE_IN, datein);
        contentValues.put(DBHelper.DEVICEINFO_DATE_OUT, dateout);
        contentValues.put(DBHelper.DEVICEINFO_TIME_IN, timein);
        contentValues.put(DBHelper.DEVICEINFO_TIME_OUT, timeout);
        contentValues.put(DBHelper.DEVICEINFO_SITE, site);
        contentValues.put(DBHelper.DEVICEINFO_STATUS, status);
        int i = database.update(DBHelper.DEVICEINFO_TABLE_ARCHIVE, contentValues,
                DBHelper._ID + "='"+_id+"'", null);
        return i;
    } // end of method

    public void deleteDevice(int _id) {
        database.delete(DBHelper.DEVICEINFO_TABLE, DBHelper._ID + "=" + _id, null);
    } // end of method


    /******************************
     * <b>Description:</b> This method delets Devices based on it Serial num
     * @param sn - Serial Number
     ******************************/
    public void deleteDevice(String sn) {
        database.delete(DBHelper.DEVICEINFO_TABLE, DBHelper.DEVICEINFO_SERIAL_NUMBMER+ "='"+sn+"'", null);
    } // end of method


    public boolean checkExist(String sn){

        Cursor cursor =  database.rawQuery("SELECT * FROM " + DBHelper.DEVICEINFO_TABLE + " WHERE " +
                DBHelper.DEVICEINFO_SERIAL_NUMBMER + "=?", new String[]{sn});


        if(cursor == null)
                 return false;

        if(cursor.moveToNext())
           return true;
        else
          return false;

     } // end of method


    public String checkStatus(String sn){

        Cursor cursor =  database.rawQuery("SELECT * FROM " + DBHelper.DEVICEINFO_TABLE + " WHERE " +
                DBHelper.DEVICEINFO_SERIAL_NUMBMER + "=?", new String[]{sn});

        if(cursor == null)
              return "";

        if(cursor.moveToNext()) {
            int myIndex = cursor.getColumnIndex(DBHelper.DEVICEINFO_STATUS);
            return cursor.getString(myIndex);
        } else {
            return "";
        }

    } // end of method


} // end of class