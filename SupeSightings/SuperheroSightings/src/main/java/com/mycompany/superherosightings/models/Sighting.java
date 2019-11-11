/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.superherosightings.models;

import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;

/**
 *
 * @author Chad
 */
public class Sighting {
    
    int id;
    
    @NotBlank(message = "A sighting date must be provided.")
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
    
   
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + this.id;
        hash = 59 * hash + Objects.hashCode(this.date);
        hash = 59 * hash + Objects.hashCode(this.sightingLocation);
        hash = 59 * hash + Objects.hashCode(this.sightedSupe);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sighting other = (Sighting) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.sightingLocation, other.sightingLocation)) {
            return false;
        }
        if (!Objects.equals(this.sightedSupe, other.sightedSupe)) {
            return false;
        }
        return true;
    }

  
    
    
    
            
    
    
}
