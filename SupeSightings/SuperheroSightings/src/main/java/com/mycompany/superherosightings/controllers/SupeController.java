/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.superherosightings.controllers;

import com.mycompany.superherosightings.daos.Dao;
import com.mycompany.superherosightings.daos.DaoException;
import com.mycompany.superherosightings.models.Location;
import com.mycompany.superherosightings.models.Organization;
import com.mycompany.superherosightings.models.Power;
import com.mycompany.superherosightings.models.Sighting;
import com.mycompany.superherosightings.models.Supe;
import com.mycompany.superherosightings.service.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

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
    Service supeService;

    List<String> daoErrors = new ArrayList<>();

    @GetMapping("home")
    public String displayHomepage(Model model) {

        List<Sighting> recentSightings = supeService.getMostRecentSightings();

        model.addAttribute("recentSightings", recentSightings);

        return "home";
    }

    @GetMapping("supes")
    public String displaySupes(Model model) {

        List<Supe> allSupes = supeService.getAllSupes();
        List<Power> allPowers = supeService.getAllPowers();
        List<Organization> allOrgs = supeService.getAllOrganizations();

        model.addAttribute("allSupes", allSupes);
        model.addAttribute("allPowers", allPowers);
        model.addAttribute("allOrgs", allOrgs);
        model.addAttribute("daoErrors", daoErrors);
        model.addAttribute("supeIssues", supeIssues);
        model.addAttribute("supeViolations", supeViolations);

        return "supes";

    }

    @GetMapping("supeDetail")
    public String displaySupeDetails(Integer id, Model model) {

        daoErrors.clear();

        try {
            Supe supe = supeService.getSupeById(id);

            model.addAttribute("supe", supe);

            return "supeDetail";

        } catch (DaoException ex) {
            daoErrors.add(ex.toString());
            return "supeDetail";
        }

    }

    Set<ConstraintViolation<Supe>> supeViolations = new HashSet<>();
    List<String> supeIssues = new ArrayList<>();

    @PostMapping("addSupe")
    public String addSupe(Supe newSupe, HttpServletRequest request) {

        daoErrors.clear();
        supeIssues.clear();

        if (request.getParameter("powerId").equals("Please select a power")) {
            supeIssues.add("Please identify a power for the supe");
            return "redirect:/supes";
        }

        try {
            int powerId = Integer.parseInt(request.getParameter("powerId"));

            newSupe.setSupePower(supeService.getPowerById(powerId));

            String[] orgIds = request.getParameterValues("orgId");

            if (orgIds == null) {
                supeIssues.add("A supe must be associated with at least one org.");
                return "redirect:/supes";
            }

            List<Organization> orgs = new ArrayList<>();

            for (String orgId : orgIds) {

                orgs.add(supeService.getOrganizationById(Integer.parseInt(orgId)));
            }

            newSupe.setOrgsForSupe(orgs);

            Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
            supeViolations = validate.validate(newSupe);

            if (supeViolations.isEmpty()) {
                supeService.addNewSupe(newSupe);
            }

            return "redirect:/supes";

        } catch (DaoException ex) {
            daoErrors.add(ex.toString());
            return "redirect:/supes";
        }

    }

    @GetMapping("deleteSupe")
    public String deleteSupe(HttpServletRequest request) {

        int id = Integer.parseInt(request.getParameter("id"));

        supeService.deleteSupeFromDB(id);

        return "redirect:/supes";

    }

    @GetMapping("editSupe")
    public String editSupe(HttpServletRequest request, Model model) {

        daoErrors.clear();

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            Supe supe = supeService.getSupeById(id);
            List<Power> allPowers = supeService.getAllPowers();
            List<Organization> orgs = supeService.getAllOrganizations();

            model.addAttribute("supe", supe);
            model.addAttribute("allPowers", allPowers);
            model.addAttribute("orgs", orgs);

            return "editSupe";

        } catch (DaoException ex) {
            daoErrors.add(ex.toString());
            return "editSupe";
        }

    }

    @PostMapping("editSupe")
    public String performEditSupe(HttpServletRequest request) {

        daoErrors.clear();
        supeIssues.clear();

        if (request.getParameter("powerId").equals("Please select a power")) {
            supeIssues.add("Please identify a power for the supe");
            return "redirect:/supes";
        }

        try {

            int sightingId = Integer.parseInt(request.getParameter("id"));
            int powerId = Integer.parseInt(request.getParameter("powerId"));
            String[] orgIds = request.getParameterValues("orgId");
            
            Supe editedSupe = supeService.getSupeById(sightingId);
            Power editedPower = supeService.getPowerById(powerId);

            List<Organization> editedOrgs = new ArrayList<>();
            for (String orgId : orgIds) {
                editedOrgs.add(supeService.getOrganizationById(Integer.parseInt(orgId)));
            }

            editedSupe.setName(request.getParameter("supeName"));
            editedSupe.setDescription(request.getParameter("supeDescription"));
            editedSupe.setSupePower(editedPower);
            editedSupe.setOrgsForSupe(editedOrgs);

            supeService.updateSupe(editedSupe);

            return "redirect:/supes";

        } catch (DaoException ex) {
            daoErrors.add(ex.toString());
            return "redirect:/supes";
        }

    }

    @GetMapping("superpowers")
    public String displayPowers(Model model) {

        List<Power> allPowers = supeService.getAllPowers();

        model.addAttribute("allPowers", allPowers);
        model.addAttribute("errors", powerViolations);
        model.addAttribute("daoErrors", daoErrors);

        return "superpowers";

    }

    Set<ConstraintViolation<Power>> powerViolations = new HashSet<>();

    @PostMapping("addPower")
    public String addPower(HttpServletRequest request) {

        daoErrors.clear();

        try {
            String powerName = request.getParameter("powerName");

            Power addedPower = new Power();
            addedPower.setName(powerName);

            Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
            powerViolations = validate.validate(addedPower);

            if (powerViolations.isEmpty()) {
                supeService.addNewPower(addedPower);
            }

            return "redirect:/superpowers";

        } catch (DaoException ex) {
            daoErrors.add(ex.toString());
            return "redirect:/superpowers";
        }

    }

    @GetMapping("deletePower")
    public String deletePower(HttpServletRequest request) {

        int id = Integer.parseInt(request.getParameter("id"));

        supeService.deletePowerFromDB(id);

        return "redirect:/superpowers";

    }

    @GetMapping("editPower")
    public String editPower(HttpServletRequest request, Model model) {

        daoErrors.clear();

        try {

            int id = Integer.parseInt(request.getParameter("id"));

            Power power = supeService.getPowerById(id);

            model.addAttribute("power", power);

            return "editSuperpower";
        } catch (DaoException ex) {
            daoErrors.add(ex.toString());
            return "editSuperpower";
        }

    }

    @PostMapping("editPower")
    public String performEditPower(HttpServletRequest request) {

        daoErrors.clear();

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            Power editedPower = supeService.getPowerById(id);

            editedPower.setName(request.getParameter("powerName"));

            supeService.updatePower(editedPower);

            return "redirect:/superpowers";

        } catch (DaoException ex) {
            daoErrors.add(ex.toString());
            return "redirect:/superpowers";
        }

    }

    @GetMapping("locations")
    public String displayLocations(Model model) {

        List<Location> allLocations = supeService.getAllLocations();

        model.addAttribute("allLocations", allLocations);
        model.addAttribute("errors", locationViolations);
        model.addAttribute("daoErrors", daoErrors);

        return "locations";

    }

    Set<ConstraintViolation<Location>> locationViolations = new HashSet<>();

    @PostMapping("addLocation")
    public String addLocation(HttpServletRequest request) {

        daoErrors.clear();

        try {
            String locationName = request.getParameter("locationName");
            String locationAddress = request.getParameter("locationAddress");
            BigDecimal locationLatitude = new BigDecimal(request.getParameter("locationLatitude"));
            BigDecimal locationLongitude = new BigDecimal(request.getParameter("locationLatitude"));

            Location addedLocation = new Location();
            addedLocation.setName(locationName);
            addedLocation.setAddress(locationAddress);
            addedLocation.setLatitude(locationLatitude);
            addedLocation.setLongitude(locationLongitude);

            Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
            locationViolations = validate.validate(addedLocation);

            if (locationViolations.isEmpty()) {
                supeService.addNewLocation(addedLocation);
            }

            return "redirect:/locations";

        } catch (DaoException ex) {
            daoErrors.add(ex.toString());
            return "redirect:/locations";
        }

    }

    @GetMapping("deleteLocation")
    public String deleteLocation(HttpServletRequest request) {

        int id = Integer.parseInt(request.getParameter("id"));

        supeService.deleteLocationFromDB(id);

        return "redirect:/locations";

    }

    @GetMapping("editLocation")
    public String editLocation(HttpServletRequest request, Model model) {

        daoErrors.clear();

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            Location location = supeService.getLocationById(id);

            model.addAttribute("location", location);

            return "editLocation";

        } catch (DaoException ex) {
            daoErrors.add(ex.toString());
            return "editLocation";
        }

    }

    @PostMapping("editLocation")
    public String performEditLocation(HttpServletRequest request) {

        daoErrors.clear();

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            Location editedLocation = supeService.getLocationById(id);

            editedLocation.setName(request.getParameter("locationName"));
            editedLocation.setAddress(request.getParameter("locationAddress"));
            editedLocation.setLatitude(new BigDecimal(request.getParameter("locationLatitude")));
            editedLocation.setLongitude(new BigDecimal(request.getParameter("locationLongitude")));

            supeService.updateLocation(editedLocation);

            return "redirect:/locations";

        } catch (DaoException ex) {
            daoErrors.add(ex.toString());
            return "redirect:/locations";

        }

    }

    @GetMapping("organizations")
    public String displayOrganizations(Model model) {

        List<Organization> allOrganizations = supeService.getAllOrganizations();
        List<Supe> supes = supeService.getAllSupes();

        model.addAttribute("allOrganizations", allOrganizations);
        model.addAttribute("supes", supes);
        model.addAttribute("daoErrors", daoErrors);

        return "organizations";

    }

    @GetMapping("organizationDetail")
    public String displayOrganizationDetails(Integer id, Model model) {

        daoErrors.clear();

        try {
            Organization organization = supeService.getOrganizationById(id);

            model.addAttribute("organization", organization);
            model.addAttribute("daoErrors", daoErrors);
            return "organizationDetail";

        } catch (DaoException ex) {
            daoErrors.add(ex.toString());
            return "organizationDetail";
        }

    }

    @PostMapping("addOrganization")
    public String addOrganization(Organization organization, HttpServletRequest request) {

        daoErrors.clear();

        try {
            String[] supeIds = request.getParameterValues("supeId");

            List<Supe> supes = new ArrayList<>();

            for (String supeId : supeIds) {

                supes.add(supeService.getSupeById(Integer.parseInt(supeId)));

            }

            organization.setSupesInOrg(supes);

            supeService.addNewOrganization(organization);

            return "redirect:/organizations";

        } catch (DaoException ex) {
            daoErrors.add(ex.toString());
            return "redirect:/organizations";
        }

    }

    @GetMapping("deleteOrganization")
    public String deleteOrganization(HttpServletRequest request) {

        int id = Integer.parseInt(request.getParameter("id"));

        supeService.deleteOrganizationFromDB(id);

        return "redirect:/organizations";

    }

    @GetMapping("editOrganization")
    public String editOrganization(HttpServletRequest request, Model model) {

        daoErrors.clear();

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            Organization organization = supeService.getOrganizationById(id);
            model.addAttribute("organization", organization);

            List<Supe> supes = supeService.getAllSupes();
            model.addAttribute("supes", supes);

            return "editOrganization";

        } catch (DaoException ex) {
            daoErrors.add(ex.toString());
            return "editOrganization";
        }

    }

    @PostMapping("editOrganization")
    public String performEditOrganization(HttpServletRequest request) {

        daoErrors.clear();

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            Organization editedOrganization = supeService.getOrganizationById(id);

            editedOrganization.setName(request.getParameter("organizationName"));
            editedOrganization.setAddress(request.getParameter("organizationAddress"));
            editedOrganization.setDescription(request.getParameter("organizationDescription"));

            String[] supeIds = request.getParameterValues("supeId");

            List<Supe> updatedSupesList = new ArrayList<>();

            for (String supeId : supeIds) {
                updatedSupesList.add(supeService.getSupeById(Integer.parseInt(supeId)));
            }

            editedOrganization.setSupesInOrg(updatedSupesList);

            supeService.updateOrganization(editedOrganization);

            return "redirect:/organizations";

        } catch (DaoException ex) {
            daoErrors.add(ex.toString());
            return "redirect:/organizations";
        }

    }

    @GetMapping("sightings")
    public String displaySightings(Model model) {

        List<Sighting> allSightings = supeService.getAllSightings();
        List<Location> allLocations = supeService.getAllLocations();
        List<Supe> allSupes = supeService.getAllSupes();

        model.addAttribute("allSightings", allSightings);
        model.addAttribute("allLocations", allLocations);
        model.addAttribute("allSupes", allSupes);
        model.addAttribute("errors", sightingViolations);
        model.addAttribute("moreErrors", sightingIssues);
        model.addAttribute("daoErrors", daoErrors);

        return "sightings";

    }

    Set<ConstraintViolation<Sighting>> sightingViolations = new HashSet<>();
    List<String> sightingIssues = new ArrayList<>();

    @PostMapping("addSighting")
    public String addSighting(Sighting newSighting, HttpServletRequest request) {

        daoErrors.clear();

        try {
            sightingIssues.clear();

            if (request.getParameter("locationId").equals("Please select a location")
                    || request.getParameter("supeId").equals("Please select a supe")) {
                sightingIssues.add("Please provide a location and supe for the sighting");
                return "redirect:/sightings";
            }

            int locationId = Integer.parseInt(request.getParameter("locationId"));
            int supeId = Integer.parseInt(request.getParameter("supeId"));

            newSighting.setSightingLocation(supeService.getLocationById(locationId));
            newSighting.setSightedSupe(supeService.getSupeById(supeId));

            //supeService.addNewSighting(newSighting);
            Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
            sightingViolations = validate.validate(newSighting);

            if (sightingViolations.isEmpty() && sightingIssues.isEmpty()) {
                supeService.addNewSighting(newSighting);
            }

            return "redirect:/sightings";

        } catch (DaoException ex) {
            daoErrors.add(ex.toString());
            return "redirect:/sightings";
        }

    }

    @GetMapping("deleteSighting")
    public String deleteSighting(HttpServletRequest request) {

        int id = Integer.parseInt(request.getParameter("id"));

        supeService.deleteSightingFromDB(id);

        return "redirect:/sightings";

    }

    @GetMapping("editSighting")
    public String editSighting(HttpServletRequest request, Model model) {

        daoErrors.clear();

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            Sighting sighting = supeService.getSightingById(id);
            List<Location> allLocations = supeService.getAllLocations();
            List<Supe> allSupes = supeService.getAllSupes();

            model.addAttribute("sighting", sighting);
            model.addAttribute("allLocations", allLocations);
            model.addAttribute("allSupes", allSupes);
            model.addAttribute("daoErrors", daoErrors);

            return "editSighting";

        } catch (DaoException ex) {
            daoErrors.add(ex.toString());
            return "editSighting";
        }

    }

    @PostMapping("editSighting")
    public String performEditSighting(HttpServletRequest request) {

        daoErrors.clear();

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            int locationId = Integer.parseInt(request.getParameter("locationId"));
            int supeId = Integer.parseInt(request.getParameter("supeId"));

            Sighting editedSighting = supeService.getSightingById(id);
            Location editedLocation = supeService.getLocationById(locationId);
            Supe editedSupe = supeService.getSupeById(supeId);

            editedSighting.setDate(request.getParameter("sightingDate"));
            editedSighting.setSightingLocation(editedLocation);
            editedSighting.setSightedSupe(editedSupe);

            supeService.updateSighting(editedSighting);

            return "redirect:/sightings";

        } catch (DaoException ex) {
            daoErrors.add(ex.toString());
            return "redirect:/sightings";
        }

    }

}
