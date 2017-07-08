package com.soboapps.deviceuserinforeader;

import android.app.Activity;
import android.content.DialogInterface;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class AllLoggedDevices extends Activity {

    private SQLController dbcon;

    DBHelper DBHelper;

    public static CustomCursorAdapterAll customAdapter;

    private static ListView listView;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_logged_devices);

        DBHelper = new DBHelper(this);
        dbcon = new SQLController(this);
        dbcon.open();

        listView = (ListView) findViewById(R.id.listView1);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                customAdapter = new CustomCursorAdapterAll(AllLoggedDevices.this, dbcon.getAllDevicesArchive());
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

                Log.d("string",alertDialogList);
                // Toast used for Debugging flow
                //Toast.makeText(MainActivity.this, alertDialogList, Toast.LENGTH_SHORT).show();

                AlertDialog.Builder dialog = new AlertDialog.Builder(AllLoggedDevices.this);
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

    }



}
