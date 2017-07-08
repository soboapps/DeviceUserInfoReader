package com.soboapps.deviceuserinforeader;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class CustomCursorAdapterAll extends CursorAdapter {

    public CustomCursorAdapterAll(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // when the view will be created for first time,
        // we need to tell the adapters, how each item will look
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View retView = inflater.inflate(R.layout.all_device_view, parent, false);

        return retView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // here we are setting our data
        // that means, take the data from the cursor and put it in views

        TextView textViewDeviceMyId = (TextView) view.findViewById(R.id.tvMyId);
        textViewDeviceMyId.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));

        TextView textViewDeviceOwner = (TextView) view.findViewById(R.id.tvOwner);
        textViewDeviceOwner.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));

        TextView textViewDeviceEmail = (TextView) view.findViewById(R.id.tvEmail);
        textViewDeviceEmail.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(3))));

        TextView textViewDevicePhone = (TextView) view.findViewById(R.id.tvPhone);
        textViewDevicePhone.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(4))));

        TextView textViewDeviceManufacturer = (TextView) view.findViewById(R.id.tvManufacturer);
        textViewDeviceManufacturer.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(5))));

        TextView textViewDeviceModel = (TextView) view.findViewById(R.id.tvModel);
        textViewDeviceModel.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(6))));

        TextView textViewDeviceSerial = (TextView) view.findViewById(R.id.tvSerial);
        textViewDeviceSerial.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(7))));

        TextView textViewDeviceSim = (TextView) view.findViewById(R.id.tvSim);
        textViewDeviceSim.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(8))));

        TextView textViewDeviceDateIn = (TextView) view.findViewById(R.id.tvDateIn);
        textViewDeviceDateIn.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(9))));

        TextView textViewDeviceDateOut = (TextView) view.findViewById(R.id.tvDateOut);
        textViewDeviceDateOut.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(10))));

        TextView textViewDeviceTimeIn = (TextView) view.findViewById(R.id.tvTimeIn);
        textViewDeviceTimeIn.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(11))));

        TextView textViewDeviceTimeOut = (TextView) view.findViewById(R.id.tvTimeOut);
        textViewDeviceTimeOut.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(12))));

        TextView textViewDeviceSite = (TextView) view.findViewById(R.id.tvSite);
        textViewDeviceSite.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(13))));

        TextView textViewDeviceStatus = (TextView) view.findViewById(R.id.tvStatus);
        textViewDeviceStatus.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(14))));

    }


}