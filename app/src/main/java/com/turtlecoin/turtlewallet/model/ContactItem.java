package com.turtlecoin.turtlewallet.model;


import java.io.Serializable;

public class ContactItem implements Serializable {
    private Long id;
    private String name;
    private String address;

    public ContactItem(Long id, String name, String address ) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Long getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
