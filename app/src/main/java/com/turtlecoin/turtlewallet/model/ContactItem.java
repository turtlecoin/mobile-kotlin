package com.turtlecoin.turtlewallet.model;


public class ContactItem {
    String name;
    String address;

    public ContactItem(String name, String address ) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
