/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.superherosightings.models;

import java.time.LocalDate;

/**
 *
 * @author Chad
 */
public class Sighting {
    
    int id;
    String date;
    Location sightingLocation;
    Supe sightedSupe;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Location getSightingLocation() {
        return sightingLocation;
    }

    public void setSightingLocation(Location sightingLocation) {
        this.sightingLocation = sightingLocation;
    }

    public Supe getSightedSupe() {
        return sightedSupe;
    }

    public void setSightedSupe(Supe sightedSupe) {
        this.sightedSupe = sightedSupe;
    }
    
    
    
            
    
    
}
