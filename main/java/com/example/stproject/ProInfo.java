package com.example.stproject;

public class ProInfo {
    private String Address;
    private String contact;
    private String Email;
    private String Name;
    private String service;
    private String des;

    public ProInfo()
    {

    }
    public ProInfo(String address, String contact, String email, String name, String service, String des) {
        this.Address = address;
        this.contact = contact;
        this.Email = email;
        this.Name = name;
        this.service = service;
        this.des = des;
    }

    public ProInfo(String address, String contact, String email, String name) {
        this.Address = address;
        this.contact = contact;
        this.Email = email;
        this.Name = name;
    }

    public String getName() {
        return Name;
    }

    public String getAddress() {
        return Address;
    }

    public String getContact() {
        return contact;
    }

    public String getEmail() {
        return Email;
    }

    public String getService() {
        return service;
    }

    public String getDes() {
        return des;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setService(String service) {
        this.service = service;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
