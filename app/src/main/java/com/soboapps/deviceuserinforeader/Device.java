package com.soboapps.deviceuserinforeader;

/**
 * Created by sdmei on 7/2/2017.
 */

public class Device {
    private int _id;
    private String owner;
    private String email;
    private String phone;
    private String manufacturer;
    private String model;
    private String serial;
    private String sim;
    private String date;
    private String time;
    private String location;

    public Device(){

    }
    public Device(int _id,String owner, String email, String phone, String manufacturer, String model,
                  String serial, String sim, String date, String time, String location) {
        this._id=_id;
        this.owner=owner;
        this.email=email;
        this.email=phone;
        this.email=manufacturer;
        this.email=model;
        this.email=serial;
        this.email=sim;
        this.email=date;
        this.email=time;
        this.email=location;
    }
    public void set_Id(int _id) {
        this._id = _id;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setphone(String phone) {
        this.phone = phone;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int get_Id() {
        return _id;
    }

    public String getOwner() {
        return owner;
    }

    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }
    public String getSerial() {
        return serial;
    }

    public String getSim() {
        return sim;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }
}