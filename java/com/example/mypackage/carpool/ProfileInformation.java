package com.example.mypackage.carpool;

public class ProfileInformation {
    public String Name, Destination, Time, Role, Phone_no, Address;

    public ProfileInformation(){ }

    public ProfileInformation(String name, String destination, String time, String role, String phone_no, String address){
        this.Name = name;
        this.Destination = destination;
        this.Role = role;
        this.Time = time;
        this.Phone_no = phone_no;
        this.Address = address;

    }

    public ProfileInformation(String name, String phone_no, String role ){
        this.Name = name;
        this.Role = role;
        this.Phone_no = phone_no;
    }

    public String gettheName() {
        return Name;
    }

    public String gettheDestination() {
        return Destination;
    }

    public String gettheTime() {
        return Time;
    }

    public String gettheAddress() {
        return Address;
    }

    public String getthePhone_no() {
        return Phone_no;
    }



}
