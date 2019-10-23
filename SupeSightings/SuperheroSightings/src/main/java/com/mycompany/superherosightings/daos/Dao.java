/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.superherosightings.daos;

import com.mycompany.superherosightings.models.Location;
import com.mycompany.superherosightings.models.Organization;
import com.mycompany.superherosightings.models.Power;
import com.mycompany.superherosightings.models.Sighting;
import com.mycompany.superherosightings.models.Supe;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Chad
 */
public interface Dao {
    
    public Supe addNewSupe(Supe sup);
    public Power addNewPower(Power pow);
    public Organization addNewOrganization(Organization org);
    public Location addNewLocation(Location loc);
    public Sighting addNewSighting(Sighting sight);
    
    public List<Supe> getAllSupes();
    public List<Power> getAllPowers();
    public List<Organization> getAllOrganizations();
    public List<Location> getAllLocations();
    public List<Sighting> getAllSightings();
    
    public Supe getSupeById(int supeId);
    public Power getPowerById(int powerId);
    public Organization getOrganizationById(int orgId);
    public Location getLocationById(int locId);
    public Sighting getSightingById(int sightId);
    
    public List<Supe> getSupesByLocation(int locId);
    public List<Supe> getSupesByOrg(int orgId);
    public List<Location> getLocationsBySupe(int supeId);
    public List<Sighting> getSightingsByDate (LocalDate date);
    public List<Organization> getOrganizationsBySupe(int supeId);
    
    public List<Sighting> getMostRecentSightings();
    
    public void updateSupe(Supe sup);
    public void updatePower(Power pow);
    public void updateOrganization(Organization org);
    public void updateLocation(Location loc);
    public void updateSighting(Sighting sight);
    
    public void deleteSupeFromDB(int supeId);
    public void deletePowerFromDB(int powId);
    public void deleteOrganizationFromDB(int orgId);
    public void deleteLocationFromDB(int locId);
    public void deleteSightingFromDB(int sightId);
    public void deleteAllDataFromDB();
    
    public void addTestDataToDB();
    
    
}
