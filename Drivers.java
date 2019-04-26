package com.example.mypackage.carpool;

public class Drivers{

    private String driverDestination;
    private String driverName;
    private String driverPhone_no;
    private String driverTime;

    public Drivers(){

    }

    public Drivers(String destination, String name, String phone_no, String time) {
        this.driverDestination = destination;
        this.driverName = name;
        this.driverPhone_no = phone_no;
        this.driverTime = time;
    }

    public String getDestination() {
        return driverDestination;
    }

    public String getName() {
        return driverName;
    }

    public String getPhone_no() {
        return driverPhone_no;
    }

    public String getTime() {
        return driverTime;
    }

}
