/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.superherosightings.models;

import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Chad
 */
public class Supe {
    
    int id;
    
    @NotBlank(message="A name must be provided.")
    @Size(message="The name must be less than 30 characters")
    String name;
    
    @NotBlank(message="A description must be provided.")
    @Size(message="The name must be less than 30 characters")
    String description;
   
    Power supePower;
    List<Organization> orgsForSupe;
    
    public boolean hasOrg(int orgId){
        return orgsForSupe.stream().anyMatch(o -> o.getId()==orgId);
    }

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
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.id;
        hash = 71 * hash + Objects.hashCode(this.name);
        hash = 71 * hash + Objects.hashCode(this.description);
        hash = 71 * hash + Objects.hashCode(this.supePower);
        hash = 71 * hash + Objects.hashCode(this.orgsForSupe);
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
        final Supe other = (Supe) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.supePower, other.supePower)) {
            return false;
        }
        return Objects.equals(this.orgsForSupe, other.orgsForSupe);
    }
    
    
}
