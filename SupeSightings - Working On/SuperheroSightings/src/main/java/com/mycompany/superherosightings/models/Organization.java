/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.superherosightings.models;

import java.util.List;

/**
 *
 * @author Chad
 */
public class Organization {
    
    int id;
    String name;
    String description;
    String address;
    List<Supe> supesInOrg;

    public List<Supe> getSupesInOrg() {
        return supesInOrg;
    }

    public void setSupesInOrg(List<Supe> supesInOrg) {
        this.supesInOrg = supesInOrg;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
}
