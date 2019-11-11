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
    
    public Supe addNewSupe(Supe sup) throws DaoException;
    public Power addNewPower(Power pow) throws DaoException;
    public Organization addNewOrganization(Organization org) throws DaoException;
    public Location addNewLocation(Location loc) throws DaoException;
    public Sighting addNewSighting(Sighting sight) throws DaoException;
    
    public List<Supe> getAllSupes();
    public List<Power> getAllPowers();
    public List<Organization> getAllOrganizations();
    public List<Location> getAllLocations();
    public List<Sighting> getAllSightings();
    
    public Supe getSupeById(int supeId) throws DaoException;
    public Power getPowerById(int powerId) throws DaoException;
    public Organization getOrganizationById(int orgId)throws DaoException;
    public Location getLocationById(int locId)throws DaoException;
    public Sighting getSightingById(int sightId)throws DaoException;
    
    public List<Supe> getSupesByLocation(int locId);
    public List<Supe> getSupesByOrg(int orgId);
    public List<Location> getLocationsBySupe(int supeId);
    public List<Sighting> getSightingsByDate (LocalDate date);
    public List<Organization> getOrganizationsBySupe(int supeId);
    
    public List<Sighting> getMostRecentSightings();
    
    public void updateSupe(Supe sup) throws DaoException;
    public void updatePower(Power pow)throws DaoException;
    public void updateOrganization(Organization org) throws DaoException;
    public void updateLocation(Location loc)throws DaoException;
    public void updateSighting(Sighting sight) throws DaoException;
    
    public void deleteSupeFromDB(int supeId);
    public void deletePowerFromDB(int powId);
    public void deleteOrganizationFromDB(int orgId);
    public void deleteLocationFromDB(int locId);
    public void deleteSightingFromDB(int sightId);
    public void deleteAllDataFromDB();
    
    public void addTestDataToDB();
    
    
}
