package com.soboapps.deviceuserinforeader;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jinlin.zxing.CaptureActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    int MyVersion = Build.VERSION.SDK_INT;

    public Button log_in;

    private int resultGet_Camera;
    private int resultWrite_External_Storage;
    private int resultRead_External_Storage;

    private static final int CAPTURE_ACTIVITY_RESULT_CODE = 0;
    final private int REQUEST_MULTIPLE_PERMISSIONS = 124;

    public String capturedQRcodeString;

    public final static String KEY_EXTRA_CONTACT_ID = "KEY_EXTRA_CONTACT_ID";

    private SQLController dbcon;
    private ListView listView;

    Boolean DeviceLocation = true;

    DBHelper DBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        log_in = (Button)findViewById(R.id.btnLogIn);

        resultGet_Camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        resultWrite_External_Storage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        resultRead_External_Storage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        dbcon = new SQLController(this);
        dbcon.open();

        listView = (ListView) findViewById(R.id.listView1);
        listView.setEmptyView(findViewById(R.id.empty));

        // Attach The Data From DataBase Into ListView Using Crusor Adapter
        Cursor cursor = dbcon.fetch();
        String[] from = new String[] { DBHelper._ID, DBHelper.DEVICEINFO_OWNER, DBHelper.DEVICEINFO_EMAIL,
                DBHelper.DEVICEINFO_PHONE_NUMBER, DBHelper.DEVICEINFO_MANUFACTURER, DBHelper.DEVICEINFO_MODEL,
                DBHelper.DEVICEINFO_SERIAL_NUMBMER, DBHelper.DEVICEINFO_SIM, DBHelper.DEVICEINFO_DATE,
                DBHelper.DEVICEINFO_TIME, DBHelper.DEVICEINFO_LOCATION };

        //int[] to = new int[] { R.id.id, R.id.title, R.id.desc };
        //SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.activity_main, cursor, from, to);
        //adapter.notifyDataSetChanged();
        //listView.setAdapter(adapter);

        log_in.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
                    if (!(checkIfAlreadyhavePermission())){

                        if (MyVersion == Build.VERSION_CODES.M) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                            //dialog.setCancelable(false);
                            //dialog.setTitle(R.string.alert_dialog_title);
                            dialog.setTitle("Restart Required...");
                            dialog.setMessage("Because of a Bug in Android M, you must restart the " +
                                    "Appication after you have allowed the permissions");
                            dialog.setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int id) {
                                    //Toast.makeText(getApplicationContext(),"No Permissions", Toast.LENGTH_LONG).show();
                                    requestForSpecificPermission();
                                }
                            });
                            final AlertDialog alert = dialog.create();
                            alert.show();
                        } else {
                            //Toast.makeText(getApplicationContext(),"No Permissions", Toast.LENGTH_LONG).show();
                            requestForSpecificPermission();
                        }

                    } else {
                        //Toast.makeText(getApplicationContext(),"Permissions Granted", Toast.LENGTH_LONG).show();
                        onScanDeviceUserInfo();
                    }
                }
            }
        });

        //openAndQueryDatabase();
        //displayResultList();
    }

    /*
    private void openAndQueryDatabase() {

        SQLiteDatabase newDB = null;
        try {
            DBHelper = new DBHelper(this.getApplicationContext());
            newDB = DBHelper.getWritableDatabase();
            Cursor c = newDB.rawQuery("SELECT OWNER, MODEL, SERIAL FROM " +
                    DBHelper.getDatabaseName() +
                    " where Location == True", null);

            if (c != null ) {
                if  (c.moveToFirst()) {
                    do {
                        String owner = c.getString(c.getColumnIndex("owner"));
                        String model = c.getString(c.getColumnIndex("model"));
                        results.add("Owner: " + owner + ",Model: " + model);
                    }while (c.moveToNext());
                }
            }
        } catch (SQLiteException se ) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        } finally {
            if (newDB != null)
                newDB.execSQL("DELETE FROM " + DBHelper.getDatabaseName());
            newDB.close();
        }

    }

    private void displayResultList() {
        TextView tView = new TextView(this);
        tView.setText("This data is retrieved from the database and only 4 " +
                "of the results are displayed");
        getListView().addHeaderView(tView);

        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, results));
        getListView().setTextFilterEnabled(true);

    }
    */

    private boolean checkIfAlreadyhavePermission() {
        if (resultGet_Camera == PackageManager.PERMISSION_GRANTED &&
                resultRead_External_Storage == PackageManager.PERMISSION_GRANTED &&
                resultWrite_External_Storage == PackageManager.PERMISSION_GRANTED) {
            return true;

        } else {

            return false;
        }
    }

    private void requestForSpecificPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_MULTIPLE_PERMISSIONS);
        if (MyVersion == Build.VERSION_CODES.M) {
            //Restart to because od bug in ver 6.x
            android.os.Process.killProcess(android.os.Process.myPid());
            } else {
            // Do Nothing
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //Toast.makeText(getApplicationContext(),"Permissions where Granted", Toast.LENGTH_LONG).show();
        onScanDeviceUserInfo();
    }


    public void onScanDeviceUserInfo() {
        // Click action to Scan QR Code
        //loggedIn = getResources().getString(R.string.logged_in);
        Intent intentIn = new Intent(MainActivity.this, CaptureActivity.class);
        startActivityForResult(intentIn, CAPTURE_ACTIVITY_RESULT_CODE);
        //Toast.makeText(getApplicationContext(),"Looged IN", Toast.LENGTH_LONG).show();
    }

    public void restart(){
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            this.finishAffinity();
        }
    }

    // This method is called when the second activity finishes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == CAPTURE_ACTIVITY_RESULT_CODE) {
            if (resultCode == RESULT_OK) {

                // get String data from Intent
                capturedQRcodeString = data.getStringExtra("qrcodevalue");
                //Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();

                //String str = capturedQRcodeString;
                //List<String> deviceUserInfoList = Arrays.asList(str.split(","));

                final String str = capturedQRcodeString;
                String device_info_list[] = str.split(",");

                final String owner = device_info_list[0];
                final String email = device_info_list[1];
                final String phone = device_info_list[2];
                final String manufacturer = device_info_list[3];
                final String model = device_info_list[4];
                final String serial = device_info_list[5];
                final String sim = device_info_list[6];
                final String date = device_info_list[7];
                final String time = device_info_list[8];
                //final String location = device_info_list[9];

                //OWNER,EMAIL,PHONE,MANUFACTURER,MODEL,SERIAL,DATE,TIME

                final String location = String.valueOf(DeviceLocation);

                String deviceUserInfoList =
                        "\n" + " " + getString(R.string.device_email) + " " +  email +
                        "\n" + " " + getString(R.string.device_phone) + " " +  phone +
                        "\n" + " " + getString(R.string.device_manufacture) + " " +  manufacturer +
                        "\n" + " " + getString(R.string.device_model) + " " +  model +
                        "\n" + " " + getString(R.string.device_serial) + " " +  serial +
                        "\n" + " " + getString(R.string.device_sim) + " " +  sim +
                        "\n" + " " + getString(R.string.device_date) + " " + date +
                        "\n" + " " + getString(R.string.device_date) + " " + time;
                        //"\n" + " " + getString(R.string.device_location) + " " + location;

                String allDeviceUserInfo =
                        "\n" + " " + getString(R.string.device_owner) + " " +  owner +
                        "\n" + " " + getString(R.string.device_email) + " " +  email +
                        "\n" + " " + getString(R.string.device_phone) + " " +  phone +
                        "\n" + " " + getString(R.string.device_manufacture) + " " +  manufacturer +
                        "\n" + " " + getString(R.string.device_model) + " " +  model +
                        "\n" + " " + getString(R.string.device_serial) + " " +  serial +
                        "\n" + " " + getString(R.string.device_sim) + " " +  sim +
                        "\n" + " " + getString(R.string.device_date) + " " + date +
                        "\n" + " " + getString(R.string.device_date) + " " + time +
                        "\n" + " " + getString(R.string.device_location) + " " + location;;

                //Toast.makeText(getApplicationContext(), String.valueOf(deviceUserInfoList), Toast.LENGTH_LONG).show();
                Log.e(TAG, String.valueOf(allDeviceUserInfo));
                Log.e(TAG, str);

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                //dialog.setCancelable(false);
                dialog.setTitle(owner);
                dialog.setMessage(deviceUserInfoList);
                dialog.setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        dbcon.insert(owner,email,phone,manufacturer,model,serial,sim,date,time,location);
                        dbcon.fetch();

                    }
                });

                final AlertDialog alert = dialog.create();
                alert.show();

            }
        }
    }

    public boolean setDeviceLocation(){
        // Todo
        // Need to look in the DB to see if this device exists or not
        // Need unique ID such as SN to look up
        // If Device does not exists, create a new record and mark Location as "IN"
        // If the Device does exist, look up ID and mark Location as "OUT"


        return DeviceLocation;
    }

}
