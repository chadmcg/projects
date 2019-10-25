/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.superherosightings.controllers;

import com.mycompany.superherosightings.daos.Dao;
import com.mycompany.superherosightings.models.Location;
import com.mycompany.superherosightings.models.Organization;
import com.mycompany.superherosightings.models.Power;
import com.mycompany.superherosightings.models.Sighting;
import com.mycompany.superherosightings.models.Supe;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Chad
 */
@Controller
public class SupeController {
    
    @Autowired
    Dao supeDao;
    
    @GetMapping("home")
   public String displayHomepage(Model model) {
       
       List<Sighting> recentSightings = supeDao.getMostRecentSightings();
       
       model.addAttribute("recentSightings", recentSightings);
       
       return "home";
   }
   
   @GetMapping("supes")
   public String displaySupes(Model model) {
       
       List<Supe> allSupes = supeDao.getAllSupes();
       List<Power> allPowers = supeDao.getAllPowers();
       
       model.addAttribute("allSupes", allSupes);
       model.addAttribute("allPowers", allPowers);
       
       return "supes";
       
   }
   
   @PostMapping("addSupe")
   public String addSupe(Supe newSupe, HttpServletRequest request) {
       
        if(request.getParameter("powerId").equals("Please select a power")){
           return "redirect:/supes";
       } else{
       
       int powerId = Integer.parseInt(request.getParameter("powerId"));
       
       newSupe.setSupePower(supeDao.getPowerById(powerId));
       
       supeDao.addNewSupe(newSupe);
       
       return "redirect:/supes";
       
   }
   }
   
   @GetMapping("deleteSupe")
   public String deleteSupe(HttpServletRequest request) {
       
       int id = Integer.parseInt(request.getParameter("id"));
       
       supeDao.deleteSupeFromDB(id);
       
       return "redirect:/supes";
       
   }
   
   @GetMapping("editSupe")
   public String editSupe(HttpServletRequest request, Model model) {
       
       int id = Integer.parseInt(request.getParameter("id"));
       
       Supe supe = supeDao.getSupeById(id);
       List<Power> allPowers = supeDao.getAllPowers();
       
       model.addAttribute("supe", supe);
       model.addAttribute("allPowers", allPowers);
       
       
       return "editSupe";
       
   }
   
   @PostMapping("editSupe")
   public String performEditSupe(HttpServletRequest request) {
       
       int sightingId = Integer.parseInt(request.getParameter("id"));
       int powerId = Integer.parseInt(request.getParameter("powerId"));
       
       Supe editedSupe = supeDao.getSupeById(sightingId);
       Power editedPower = supeDao.getPowerById(powerId);
       
       editedSupe.setName(request.getParameter("supeName"));
       editedSupe.setDescription(request.getParameter("supeDescription"));
       editedSupe.setSupePower(editedPower);
       
       
       supeDao.updateSupe(editedSupe);
       
       return "redirect:/supes";
       
   }
   
   
   @GetMapping("superpowers")
   public String displayPowers(Model model) {
       
       List<Power> allPowers = supeDao.getAllPowers();
       
       model.addAttribute("allPowers", allPowers);
       
       return "superpowers";
       
   }
   
   
   @PostMapping("addPower")
   public String addPower(HttpServletRequest request) {
       
       String powerName = request.getParameter("powerName");
       
       Power addedPower = new Power();
       addedPower.setName(powerName);
       
       supeDao.addNewPower(addedPower);
       
       return "redirect:/superpowers";
       
   }
   
   @GetMapping("deletePower")
   public String deletePower(HttpServletRequest request) {
       
       int id = Integer.parseInt(request.getParameter("id"));
       
       supeDao.deletePowerFromDB(id);
       
       return "redirect:/superpowers";
       
   }
   
   @GetMapping("editPower")
   public String editPower(HttpServletRequest request, Model model) {
       
       int id = Integer.parseInt(request.getParameter("id"));
       
       Power power = supeDao.getPowerById(id);
       
       model.addAttribute("power", power);
       
       return "editSuperpower";
       
   }
   
   @PostMapping("editPower")
   public String performEditPower(HttpServletRequest request) {
       
       int id = Integer.parseInt(request.getParameter("id"));
       
       Power editedPower = supeDao.getPowerById(id);
       
       editedPower.setName(request.getParameter("powerName"));
       
       supeDao.updatePower(editedPower);
       
       return "redirect:/superpowers";
       
   }
   
   
   
   
   
   
    @GetMapping("locations")
   public String displayLocations(Model model) {
       
       List<Location> allLocations = supeDao.getAllLocations();
       
       model.addAttribute("allLocations", allLocations);
       
       return "locations";
       
   }
   
    @PostMapping("addLocation")
   public String addLocation(HttpServletRequest request) {
       
       String locationName = request.getParameter("locationName");
       String locationAddress = request.getParameter("locationAddress");
       BigDecimal locationLatitude = new BigDecimal(request.getParameter("locationLatitude"));
       BigDecimal locationLongitude = new BigDecimal(request.getParameter("locationLatitude"));
   
       
       Location addedLocation = new Location();
       addedLocation.setName(locationName);
       addedLocation.setAddress(locationAddress);
       addedLocation.setLatitude(locationLatitude);
       addedLocation.setLongitude(locationLongitude);
       
       supeDao.addNewLocation(addedLocation);
       
       return "redirect:/locations";
       
   }
   
      @GetMapping("deleteLocation")
   public String deleteLocation(HttpServletRequest request) {
       
       int id = Integer.parseInt(request.getParameter("id"));
       
       supeDao.deleteLocationFromDB(id);
       
       return "redirect:/locations";
       
   }
   
   
   @GetMapping("editLocation")
   public String editLocation(HttpServletRequest request, Model model) {
       
       int id = Integer.parseInt(request.getParameter("id"));
       
       Location location = supeDao.getLocationById(id);
       
       model.addAttribute("location", location);
       
       return "editLocation";
       
   }
   
   @PostMapping("editLocation")
   public String performEditLocation(HttpServletRequest request) {
       
       int id = Integer.parseInt(request.getParameter("id"));
       
       Location editedLocation = supeDao.getLocationById(id);
       
       editedLocation.setName(request.getParameter("locationName"));
       editedLocation.setAddress(request.getParameter("locationAddress"));
       editedLocation.setLatitude(new BigDecimal(request.getParameter("locationLatitude")));
       editedLocation.setLongitude(new BigDecimal(request.getParameter("locationLongitude")));
       
       supeDao.updateLocation(editedLocation);
       
       return "redirect:/locations";
       
   }
   
   
   
   
   
   
     @GetMapping("organizations")
   public String displayOrganizations(Model model) {
       
       List<Organization> allOrganizations = supeDao.getAllOrganizations();
       
       model.addAttribute("allOrganizations", allOrganizations);
       
       return "organizations";
       
   }
   
   @PostMapping("addOrganization")
   public String addOrganization(HttpServletRequest request) {
       
       String organizationName = request.getParameter("organizationName");
       String organizationDescription = request.getParameter("organizationDescription");
       String organizationAddress = request.getParameter("organizationAddress");
       
       Organization addedOrganization = new Organization();
       addedOrganization.setName(organizationName);
       addedOrganization.setDescription(organizationDescription);
       addedOrganization.setAddress(organizationAddress);
       
       supeDao.addNewOrganization(addedOrganization);
       
       return "redirect:/organizations";
       
   }
   
      @GetMapping("deleteOrganization")
   public String deleteOrganization(HttpServletRequest request) {
       
       int id = Integer.parseInt(request.getParameter("id"));
       
       supeDao.deleteOrganizationFromDB(id);
       
       return "redirect:/organizations";
       
   }
   
   
   @GetMapping("editOrganization")
   public String editOrganization(HttpServletRequest request, Model model) {
       
       int id = Integer.parseInt(request.getParameter("id"));
       
       Organization organization = supeDao.getOrganizationById(id);
       
       model.addAttribute("organization", organization);
       
       return "editOrganization";
       
   }
   
   @PostMapping("editOrganization")
   public String performEditOrganization(HttpServletRequest request) {
       
       int id = Integer.parseInt(request.getParameter("id"));
       
       Organization editedOrganization = supeDao.getOrganizationById(id);
       
       editedOrganization.setName(request.getParameter("organizationName"));
       editedOrganization.setAddress(request.getParameter("organizationAddress"));
       editedOrganization.setDescription(request.getParameter("organizationDescription"));
       
       
       supeDao.updateOrganization(editedOrganization);
       
       return "redirect:/organizations";
       
   }
   
   
   
   
   
   
      @GetMapping("sightings")
   public String displaySightings(Model model) {
       
       List<Sighting> allSightings = supeDao.getAllSightings();
       List<Location> allLocations = supeDao.getAllLocations();
       List<Supe> allSupes = supeDao.getAllSupes();
       
       model.addAttribute("allSightings", allSightings);
       model.addAttribute("allLocations", allLocations);
       model.addAttribute("allSupes", allSupes);
       
       return "sightings";
       
   }
   
   @PostMapping("addSighting")
   public String addSighting(Sighting newSighting, HttpServletRequest request) {
       
       int locationId = Integer.parseInt(request.getParameter("locationId"));
       int supeId = Integer.parseInt(request.getParameter("supeId"));
       
       newSighting.setSightingLocation(supeDao.getLocationById(locationId));
       newSighting.setSightedSupe(supeDao.getSupeById(supeId));
       
       
       supeDao.addNewSighting(newSighting);
       
       return "redirect:/sightings";
       
   }
   
   @GetMapping("deleteSighting")
   public String deleteSighting(HttpServletRequest request) {
       
       int id = Integer.parseInt(request.getParameter("id"));
       
       supeDao.deleteSightingFromDB(id);
       
       return "redirect:/sightings";
       
   }
   
   @GetMapping("editSighting")
   public String editSighting(HttpServletRequest request, Model model) {
       
       int id = Integer.parseInt(request.getParameter("id"));
       
       Sighting sighting = supeDao.getSightingById(id);
       List<Location> allLocations = supeDao.getAllLocations();
       List<Supe> allSupes = supeDao.getAllSupes();
       
       model.addAttribute("sighting", sighting);
       model.addAttribute("allLocations", allLocations);
       model.addAttribute("allSupes", allSupes);
       
       return "editSighting";
       
   }
   
   @PostMapping("editSighting")
   public String performEditSighting(HttpServletRequest request) {
       
       int id = Integer.parseInt(request.getParameter("id"));
       int locationId = Integer.parseInt(request.getParameter("locationId"));
       int supeId = Integer.parseInt(request.getParameter("supeId"));
       
       Sighting editedSighting = supeDao.getSightingById(id);
       Location editedLocation = supeDao.getLocationById(locationId);
       Supe editedSupe = supeDao.getSupeById(supeId);
       
       editedSighting.setDate(request.getParameter("sightingDate"));
       editedSighting.setSightingLocation(editedLocation);
       editedSighting.setSightedSupe(editedSupe);
       
       
       supeDao.updateSighting(editedSighting);
       
       return "redirect:/sightings";
       
   }
   
    
    
    
}
