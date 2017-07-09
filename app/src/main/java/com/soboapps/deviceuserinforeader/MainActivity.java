package com.soboapps.deviceuserinforeader;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jinlin.zxing.CaptureActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Allocate space for variables
    ImageView ivCard;
    TextView tvCardsLeft, tvCardText, tvCardRule, tvCardDescription, tvKingsLeft;
    LinearLayout llRulesLayout;
    Button buttonTimer;

    private Menu menu;

    int MyVersion = Build.VERSION.SDK_INT;

    private int resultGet_Camera;
    private int resultWrite_External_Storage;
    private int resultRead_External_Storage;

    private static final int CAPTURE_ACTIVITY_RESULT_CODE = 0;
    final private int REQUEST_MULTIPLE_PERMISSIONS = 124;

    public String getDate;
    public String getTime;
    public String capturedQRcodeString;
    public String status;
    public String deviceUserInfoList;
    public String allDeviceUserInfo;
    public String myid;
    public String owner;
    public String email;
    public String phone;
    public String manufacturer;
    public String model;
    public String serial;
    public String sim;
    public String date;
    public String time;
    public String datein;
    public String dateout;
    public String timein;
    public String timeout;
    public String site;

    public String myNewId;

    public int id;

    private SQLController dbcon;

    DBHelper DBHelper;

    public static CustomCursorAdapter customAdapter;

    private static ListView listView;

    private static final String TAG = MainActivity.class.getSimpleName();

    public FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        DBHelper = new DBHelper(this);

        resultGet_Camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        resultWrite_External_Storage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        resultRead_External_Storage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        dbcon = new SQLController(this);
        dbcon.open();

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

                String MyId =((TextView)view.findViewById(R.id.tvMyId)).getText().toString();
                String Owner =((TextView)view.findViewById(R.id.tvOwner)).getText().toString();
                String Email =((TextView)view.findViewById(R.id.tvEmail)).getText().toString();
                String Phone =((TextView)view.findViewById(R.id.tvPhone)).getText().toString();
                String Manufacturer =((TextView)view.findViewById(R.id.tvManufacturer)).getText().toString();
                String Model =((TextView)view.findViewById(R.id.tvModel)).getText().toString();
                String Serial =((TextView)view.findViewById(R.id.tvSerial)).getText().toString();
                String Sim =((TextView)view.findViewById(R.id.tvSim)).getText().toString();
                String DateIn =((TextView)view.findViewById(R.id.tvDateIn)).getText().toString();
                String DateOut =((TextView)view.findViewById(R.id.tvDateOut)).getText().toString();
                String TimeIn =((TextView)view.findViewById(R.id.tvTimeIn)).getText().toString();
                String TimeOut =((TextView)view.findViewById(R.id.tvTimeOut)).getText().toString();
                String Site =((TextView)view.findViewById(R.id.tvSite)).getText().toString();
                String Status =((TextView)view.findViewById(R.id.tvStatus)).getText().toString();

                String alertDialogList =
                        "\n" + " " + getString(R.string.device_myid) + " " +  MyId +
                                "\n" + " " + getString(R.string.device_email) + " " +  Email +
                                "\n" + " " + getString(R.string.device_phone) + " " +  Phone +
                                "\n" + " " + getString(R.string.device_manufacture) + " " +  Manufacturer +
                                "\n" + " " + getString(R.string.device_model) + " " +  Model +
                                "\n" + " " + getString(R.string.device_serial) + " " +  Serial +
                                "\n" + " " + getString(R.string.device_sim) + " " +  Sim +
                                "\n" + " " + getString(R.string.device_date_in) + " " + DateIn +
                                "\n" + " " + getString(R.string.device_date_out) + " " + DateOut +
                                "\n" + " " + getString(R.string.device_time_in) + " " + TimeIn +
                                "\n" + " " + getString(R.string.device_time_out) + " " + TimeOut +
                                "\n" + " " + getString(R.string.device_site) + " " + Site;
                //"\n" + " " + getString(R.string.device_status) + " " + Status;;

                Log.d("string",alertDialogList);

                // Toast used for Debugging flow
                //Toast.makeText(MainActivity.this, alertDialogList, Toast.LENGTH_SHORT).show();

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
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


        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
                    if (!(checkIfAlreadyhavePermission())){

                        if (MyVersion == Build.VERSION_CODES.M) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                            dialog.setTitle("Restart Required...");
                            dialog.setMessage("Because of a Bug in Android M, you must restart the " +
                                    "Appication after you have allowed the permissions");
                            dialog.setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int id) {
                                    // Toast used for Debugging flow
                                    //Toast.makeText(getApplicationContext(),"No Permissions", Toast.LENGTH_LONG).show();
                                    requestForSpecificPermission();
                                }
                            });
                            final AlertDialog alert = dialog.create();
                            alert.show();
                        } else {
                            // Toast used for Debugging flow
                            //Toast.makeText(getApplicationContext(),"No Permissions", Toast.LENGTH_LONG).show();
                            requestForSpecificPermission();
                        }

                    } else {
                        // Toast used for Debugging flow
                        //Toast.makeText(getApplicationContext(),"Permissions Granted", Toast.LENGTH_LONG).show();
                        onScanDeviceUserInfo();
                    }
                }
            }
        });

    }  // end of onCreate Method



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
        // Toast used for Debugging flow
        //Toast.makeText(getApplicationContext(),"Permissions where Granted", Toast.LENGTH_LONG).show();
        onScanDeviceUserInfo();
    }

    public void onScanDeviceUserInfo() {
        // Click action to Scan QR Code
        Intent intentIn = new Intent(MainActivity.this, CaptureActivity.class);
        startActivityForResult(intentIn, CAPTURE_ACTIVITY_RESULT_CODE);
        // Toast used for Debugging flow
        //Toast.makeText(getApplicationContext(),"Looged IN", Toast.LENGTH_LONG).show();
    }

    public void onShowAllRecords() {
        // Click action to SHow All Records in DB
        Intent intent = new Intent(MainActivity.this, AllLoggedDevices.class);
        this.startActivity(intent);
    }

    public void onShowSQL() {
        // Click action to Show SQL DB
        Intent intent = new Intent(MainActivity.this, AndroidDatabaseManager.class);
        this.startActivity(intent);
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
                // Toast used for Debugging flow
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
                date = device_info_list[7]; //These are not being used to populate the record
                time = device_info_list[8]; //Currently  reading the scanners Date & Time of scan

                deviceUserInfoList =
                        "\n" + " " + getString(R.string.device_myid) + " " +  myid +
                                "\n" + " " + getString(R.string.device_email) + " " +  email +
                                "\n" + " " + getString(R.string.device_phone) + " " +  phone +
                                "\n" + " " + getString(R.string.device_manufacture) + " " +  manufacturer +
                                "\n" + " " + getString(R.string.device_model) + " " +  model +
                                "\n" + " " + getString(R.string.device_serial) + " " +  serial +
                                "\n" + " " + getString(R.string.device_sim) + " " +  sim +
                                "\n" + " " + getString(R.string.device_date) + " " + date +
                                "\n" + " " + getString(R.string.device_time) + " " + time;

                allDeviceUserInfo =
                        "\n" + " " + getString(R.string.device_myid) + " " +  myid +
                                "\n" + " " + getString(R.string.device_owner) + " " +  owner +
                                "\n" + " " + getString(R.string.device_email) + " " +  email +
                                "\n" + " " + getString(R.string.device_phone) + " " +  phone +
                                "\n" + " " + getString(R.string.device_manufacture) + " " +  manufacturer +
                                "\n" + " " + getString(R.string.device_model) + " " +  model +
                                "\n" + " " + getString(R.string.device_serial) + " " +  serial +
                                "\n" + " " + getString(R.string.device_sim) + " " +  sim +
                                "\n" + " " + getString(R.string.device_date_in) + " " + datein +
                                "\n" + " " + getString(R.string.device_date_out) + " " + dateout +
                                "\n" + " " + getString(R.string.device_time_in) + " " + timein +
                                "\n" + " " + getString(R.string.device_time_out) + " " + timeout +
                                "\n" + " " + getString(R.string.device_site) + " " + site +
                                "\n" + " " + getString(R.string.device_status) + " " + status;

                // Toast used for Debugging flow
                //Toast.makeText(getApplicationContext(), String.valueOf(deviceUserInfoList), Toast.LENGTH_LONG).show();
                Log.e(TAG, String.valueOf(allDeviceUserInfo));
                Log.e(TAG, str);

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle(owner);
                dialog.setMessage(deviceUserInfoList);
                dialog.setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        if (dbcon.checkExist(serial) == false){

                            getDate = new SimpleDateFormat("MM-dd-yyyy").format(Calendar.getInstance().getTime());
                            getTime = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

                            datein = getDate;
                            timein = getTime;
                            dateout = null;
                            timeout = null;
                            myNewId = null;
                            dbcon.insertDevice(myNewId,owner,email,phone,manufacturer,model,serial,sim,datein,dateout,timein,timeout,site,"true");
                            dbcon.insertDeviceArchive(myNewId,owner,email,phone,manufacturer,model,serial,sim,datein,dateout,timein,timeout,site,"true");

                            // Need to change this to something more elegant
                            Toast toast = Toast.makeText(getApplicationContext()," New Log Entry ", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.show();

                        } else {

                            String myStatus = dbcon.checkStatus(serial);
                            int myId = dbcon.getIdBySn(serial);

                            myId = dbcon.getIdBySn(serial);
                            myNewId = Integer.toString(myId) + serial;

                            // Toast used for Debugging flow
                            //Toast.makeText(getApplicationContext(), myNewId, Toast.LENGTH_LONG).show();
                            //Toast.makeText(getApplicationContext(), " Exisiting Device " + serial, Toast.LENGTH_LONG).show();

                            if (myStatus.equals("")) {
                                Toast.makeText(getApplicationContext(), " Error: Invalid QR Code " + serial, Toast.LENGTH_LONG).show();
                                return;
                            }

                            myId = dbcon.getIdBySn(serial);
                            myNewId = Integer.toString(myId) + serial;

                            getDate = new SimpleDateFormat("MM-dd-yyyy").format(Calendar.getInstance().getTime());
                            getTime = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

                            dateout = getDate;
                            timeout = getTime;

                            dbcon.updateDeviceArchive(myId,myNewId,owner,email,phone,manufacturer,model,serial,sim,datein,dateout,timein,timeout,"BOP","false");

                            // Need to change this to something more elegant
                            Toast toast = Toast.makeText(getApplicationContext()," User Logged Out ", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.show();

                            dbcon.deleteDevice(myId);

                            // Toast used for Debugging flow
                            //Toast.makeText(getApplicationContext(), "Device Deleted and Archived", Toast.LENGTH_LONG).show();

                        }

                        dbcon.getAllDevices();
                        dbcon.getAllDevicesArchive();
                        updateListView();

                    }
                });

                final AlertDialog alert = dialog.create();
                alert.show();

            }
        }
    }

    public void updateListView() {
        customAdapter = new CustomCursorAdapter(MainActivity.this, dbcon.getActiveDevices());
        listView.setAdapter(customAdapter);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logbook) {
            onShowAllRecords();
            Toast toast=Toast.makeText(getApplicationContext(), getResources().getString(R.string.device_log_book), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0); // last two args are X and Y are used for setting position
            toast.show();//showing the toast is important**

        } else if (id == R.id.nav_settings) {
            //Intent intent = new Intent(getApplication(), RulesActivity.class);
            //startActivity(intent);
            Toast toast=Toast.makeText(getApplicationContext(), getResources().getString(R.string.action_settings), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0); // last two args are X and Y are used for setting position
            toast.show();//showing the toast is important**

        } else if (id == R.id.nav_help) {

            displayHelpPopUp();

        } else if (id == R.id.nav_aboutus) {

            // Button For Dev only
            onShowSQL();
            Toast toast=Toast.makeText(getApplicationContext(), getResources().getString(R.string.action_whoarewe), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0); // last two args are X and Y are used for setting position
            toast.show();//showing the toast is important**
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void displayHelpPopUp() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.dialog_help_message));
        builder.setNegativeButton(getResources().getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        alert.getWindow().getAttributes();
        alert.getWindow().setBackgroundDrawableResource(android.R.color.background_dark);

        TextView textView = (TextView) alert.findViewById(android.R.id.message);
        textView.setTextSize(24);
        textView.setTextColor(Color.WHITE);
        Button btn1 = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        btn1.setTextSize(20);
    }
}
