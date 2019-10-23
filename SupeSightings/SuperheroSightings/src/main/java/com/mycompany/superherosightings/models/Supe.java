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
public class Supe {
    
    int id;
    String name;
    String description;
    Power supePower;
    List<Organization> orgsForSupe;

    public List<Organization> getOrgsForSupe() {
        return orgsForSupe;
    }

    public void setOrgsForSupe(List<Organization> orgsForSupe) {
        this.orgsForSupe = orgsForSupe;
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

    public Power getSupePower() {
        return supePower;
    }

    public void setSupePower(Power supePower) {
        this.supePower = supePower;
    }
    
    
    
}
