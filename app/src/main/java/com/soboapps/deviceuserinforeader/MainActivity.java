package com.soboapps.deviceuserinforeader;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jinlin.zxing.CaptureActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //private static final String TAG = "MainActivity";

    int MyVersion = Build.VERSION.SDK_INT;

    public Button log_in;

    private int resultGet_Camera;
    private int resultWrite_External_Storage;
    private int resultRead_External_Storage;

    private static final int CAPTURE_ACTIVITY_RESULT_CODE = 0;
    final private int REQUEST_MULTIPLE_PERMISSIONS = 124;

    public String capturedQRcodeString;
    public String status;
    public String deviceUserInfoList;
    public String allDeviceUserInfo;
    public String owner;
    public String email;
    public String phone;
    public String manufacturer;
    public String model;
    public String serial;
    public String sim;
    public String date;
    public String time;

    public final static String KEY_EXTRA_CONTACT_ID = "KEY_EXTRA_CONTACT_ID";

    private SQLController dbcon;
    //private DataListView dataListView;


    Boolean deviceStatus = true;

    DBHelper DBHelper;

    public static CustomCursorAdapter customAdapter;

    private static final int ENTER_DATA_REQUEST_CODE = 1;
    private static ListView listView;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper = new DBHelper(this);

        log_in = (Button)findViewById(R.id.btnLogIn);

        resultGet_Camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        resultWrite_External_Storage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        resultRead_External_Storage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        dbcon = new SQLController(this);
        dbcon.open();

        //listView = (ListView) findViewById(R.id.listView1);
        //listView.setEmptyView(findViewById(R.id.empty));

        listView = (ListView) findViewById(R.id.listView1);

        // Database query can be a time consuming task ..
        // so its safe to call database query in another thread
        // Handler, will handle this stuff for you <img src="http://s0.wp.com/wp-includes/images/smilies/icon_smile.gif?m=1129645325g" alt=":)" class="wp-smiley">

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                customAdapter = new CustomCursorAdapter(MainActivity.this, dbcon.getActiveDevices());
                listView.setAdapter(customAdapter);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "clicked on item: " + position);

                String Owner =((TextView)view.findViewById(R.id.tvOwner)).getText().toString();
                String Email =((TextView)view.findViewById(R.id.tvEmail)).getText().toString();
                String Phone =((TextView)view.findViewById(R.id.tvPhone)).getText().toString();
                String Manufacturer =((TextView)view.findViewById(R.id.tvManufacturer)).getText().toString();
                String Model =((TextView)view.findViewById(R.id.tvModel)).getText().toString();
                String Serial =((TextView)view.findViewById(R.id.tvSerial)).getText().toString();
                String Sim =((TextView)view.findViewById(R.id.tvSim)).getText().toString();
                String Date =((TextView)view.findViewById(R.id.tvDate)).getText().toString();
                String Time =((TextView)view.findViewById(R.id.tvTime)).getText().toString();
                String Status =((TextView)view.findViewById(R.id.tvStatus)).getText().toString();

                String alertDialogList =
                        "\n" + " " + getString(R.string.device_email) + " " +  Email +
                        "\n" + " " + getString(R.string.device_phone) + " " +  Phone +
                        "\n" + " " + getString(R.string.device_manufacture) + " " +  Manufacturer +
                        "\n" + " " + getString(R.string.device_model) + " " +  Model +
                        "\n" + " " + getString(R.string.device_serial) + " " +  Serial +
                        "\n" + " " + getString(R.string.device_sim) + " " +  Sim +
                        "\n" + " " + getString(R.string.device_date) + " " + Date +
                        "\n" + " " + getString(R.string.device_time) + " " + Time;
                        //"\n" + " " + getString(R.string.device_status) + " " + Status;;

                Log.d("string",alertDialogList);

                //Toast.makeText(MainActivity.this, alertDialogList, Toast.LENGTH_SHORT).show();

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                //dialog.setCancelable(false);
                dialog.setTitle(Owner);
                dialog.setMessage(alertDialogList);
                dialog.setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

                final AlertDialog alert = dialog.create();
                alert.show();
            }
        });



        // Attach The Data From DataBase Into ListView Using Crusor Adapter
        Cursor cursor = dbcon.fetchDevice();
        String[] from = new String[] { DBHelper._ID, DBHelper.DEVICEINFO_OWNER, DBHelper.DEVICEINFO_EMAIL,
                DBHelper.DEVICEINFO_PHONE_NUMBER, DBHelper.DEVICEINFO_MANUFACTURER, DBHelper.DEVICEINFO_MODEL,
                DBHelper.DEVICEINFO_SERIAL_NUMBMER, DBHelper.DEVICEINFO_SIM, DBHelper.DEVICEINFO_DATE,
                DBHelper.DEVICEINFO_TIME, DBHelper.DEVICEINFO_STATUS };

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


    }

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
            //Restart app because of bug in ver 6.x to set permissions
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

                //Get the QR Code and split it up into a nice neat string
                final String str = capturedQRcodeString;
                String device_info_list[] = str.split(",");

                owner = device_info_list[0];
                email = device_info_list[1];
                phone = device_info_list[2];
                manufacturer = device_info_list[3];
                model = device_info_list[4];
                serial = device_info_list[5];
                sim = device_info_list[6];
                date = device_info_list[7];
                time = device_info_list[8];

                status = String.valueOf(deviceStatus);

                deviceUserInfoList =
                        "\n" + " " + getString(R.string.device_email) + " " +  email +
                                "\n" + " " + getString(R.string.device_phone) + " " +  phone +
                                "\n" + " " + getString(R.string.device_manufacture) + " " +  manufacturer +
                                "\n" + " " + getString(R.string.device_model) + " " +  model +
                                "\n" + " " + getString(R.string.device_serial) + " " +  serial +
                                "\n" + " " + getString(R.string.device_sim) + " " +  sim +
                                "\n" + " " + getString(R.string.device_date) + " " + date +
                                "\n" + " " + getString(R.string.device_time) + " " + time;

                allDeviceUserInfo =
                        "\n" + " " + getString(R.string.device_owner) + " " +  owner +
                                "\n" + " " + getString(R.string.device_email) + " " +  email +
                                "\n" + " " + getString(R.string.device_phone) + " " +  phone +
                                "\n" + " " + getString(R.string.device_manufacture) + " " +  manufacturer +
                                "\n" + " " + getString(R.string.device_model) + " " +  model +
                                "\n" + " " + getString(R.string.device_serial) + " " +  serial +
                                "\n" + " " + getString(R.string.device_sim) + " " +  sim +
                                "\n" + " " + getString(R.string.device_date) + " " + date +
                                "\n" + " " + getString(R.string.device_time) + " " + time +
                                "\n" + " " + getString(R.string.device_status) + " " + status;;

                //Toast.makeText(getApplicationContext(), String.valueOf(deviceUserInfoList), Toast.LENGTH_LONG).show();
                Log.e(TAG, String.valueOf(allDeviceUserInfo));
                Log.e(TAG, str);

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                //dialog.setCancelable(false);
                dialog.setTitle(owner);
                dialog.setMessage(deviceUserInfoList);
                dialog.setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        String deviceID = String.valueOf(DBHelper._ID);
                        //long dID = Long.parseLong(deviceID);

                        dbcon.check(serial);

                        if (dbcon.check(serial)==true){
                            Toast.makeText(getApplicationContext()," true " + serial, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "false" + serial, Toast.LENGTH_LONG).show();
                        }

                        dbcon.insertDevice(owner,email,phone,manufacturer,model,serial,sim,date,time,status);

                        setDeviceStatus();

                        dbcon.getActiveDevices();

                        updateListView();


                    }
                });

                final AlertDialog alert = dialog.create();
                alert.show();


            }
        }
    }

    public boolean setDeviceStatus(){

        if (deviceStatus == true){
            deviceStatus = false;

            //return true;
        } else {
            if (deviceStatus == false){
                deviceStatus = true;

            }
            //return false;
        }
        // Todo
        // Need to look in the DB to see if this device exists or not
        // Need unique ID such as SN to look up
        // If Device does not exists, create a new record and mark Status true as "IN"
        // If the Device does exist, look up ID and mark Status false as "OUT"
        // Update the Date and Time on existing devices as well as GPS Location
        return deviceStatus;
    }

    public void updateListView() {
        customAdapter = new CustomCursorAdapter(MainActivity.this, dbcon.getActiveDevices());
        listView.setAdapter(customAdapter);
    }



}
