/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.superherosightings.service;

import com.mycompany.superherosightings.daos.Dao;
import com.mycompany.superherosightings.daos.DaoException;
import com.mycompany.superherosightings.models.Location;
import com.mycompany.superherosightings.models.Organization;
import com.mycompany.superherosightings.models.Power;
import com.mycompany.superherosightings.models.Sighting;
import com.mycompany.superherosightings.models.Supe;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Chad
 */

@Component
public class Service {
    
    @Autowired
    Dao supeDao;

    public List<Sighting> getMostRecentSightings() {
        
        return supeDao.getMostRecentSightings();
        
    }

    public List<Supe> getAllSupes() {
        
        return supeDao.getAllSupes();
        
    }

    public List<Power> getAllPowers() {
        
        return supeDao.getAllPowers();
        
    }

    public List<Organization> getAllOrganizations() {
       
        return supeDao.getAllOrganizations();
        
    }

    public Supe getSupeById(Integer id) throws DaoException {
        
        return supeDao.getSupeById(id);
        
    }

    public Power getPowerById(int powerId) throws DaoException{
       
        return supeDao.getPowerById(powerId);
        
    }

    public Organization getOrganizationById(int orgId) throws DaoException {
        
        return supeDao.getOrganizationById(orgId);
        
    }

    public void addNewSupe(Supe newSupe) throws DaoException {
        
       supeDao.addNewSupe(newSupe);
        
    }

    public void deleteSupeFromDB(int id) {
        
        supeDao.deleteSupeFromDB(id);
        
    }

    public void updateSupe(Supe editedSupe) throws DaoException {
       
        supeDao.updateSupe(editedSupe);
        
    }

    public void addNewPower(Power addedPower) throws DaoException {
        
        supeDao.addNewPower(addedPower);
        
    }

    public void deletePowerFromDB(int id) {
        
        supeDao.deletePowerFromDB(id);
        
    }

    public void updatePower(Power editedPower) throws DaoException {
        
        supeDao.updatePower(editedPower);
        
    }

    public List<Location> getAllLocations() {
        
        return supeDao.getAllLocations();
        
    }

    public void addNewLocation(Location addedLocation) throws DaoException {
        
        supeDao.addNewLocation(addedLocation);
        
    }

    public void deleteLocationFromDB(int id) {
        
        supeDao.deleteLocationFromDB(id);
        
    }

    public Location getLocationById(int id) throws DaoException {
        
        return supeDao.getLocationById(id);
        
    }

    public void updateLocation(Location editedLocation) throws DaoException {
        
        supeDao.updateLocation(editedLocation);
        
    }

    public void addNewOrganization(Organization organization) throws DaoException {
        
        supeDao.addNewOrganization(organization);
        
    }

    public void deleteOrganizationFromDB(int id) {
        supeDao.deleteOrganizationFromDB(id);
    }

    public void updateOrganization(Organization editedOrganization) throws DaoException {
        
        supeDao.updateOrganization(editedOrganization);
        
    }

    public List<Sighting> getAllSightings() {
        
        return supeDao.getAllSightings();
        
    }

    public void addNewSighting(Sighting newSighting) throws DaoException {
        
        supeDao.addNewSighting(newSighting);
        
    }

    public void deleteSightingFromDB(int id) {
        
        supeDao.deleteSightingFromDB(id);
        
    }

    public Sighting getSightingById(int id) throws DaoException {
        
       return supeDao.getSightingById(id);
       
    }

    public void updateSighting(Sighting editedSighting) throws DaoException {
        
        supeDao.updateSighting(editedSighting);
        
    }
    
    
    
    
    
    
}
