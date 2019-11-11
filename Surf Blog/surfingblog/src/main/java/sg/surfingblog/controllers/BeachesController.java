/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.surfingblog.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import sg.surfingblog.newpackage.dao.InvalidIdException;
import sg.surfingblog.newpackage.dao.SurfingDao;
import sg.surfingblog.newpackage.dao.SurfingDaoException;
import sg.surfingblog.newpackage.dao.UserDao;
import sg.surfingblog.newpackage.models.Beach;
import sg.surfingblog.newpackage.models.BeachComment;
import sg.surfingblog.newpackage.models.Break;

/**
 *
 * @author Chad
 */
@Controller
public class BeachesController {

    @Autowired
    SurfingDao sDao;

    @Autowired
    UserDao uDao;

    @GetMapping("beaches")
    public String displayBeaches(Model model) throws InvalidIdException {

        List<Beach> allBeaches = sDao.getAllBeaches();
        model.addAttribute("allBeaches", allBeaches);

        return "beaches";

    }

    @GetMapping("beachDetails{id}")
    public String displayBeachDetails(HttpServletRequest request, Model model) throws InvalidIdException {

        int id = Integer.parseInt(request.getParameter("id"));

        Beach selectedBeach = sDao.getBeachById(id);
        model.addAttribute("selectedBeach", selectedBeach);

        List<Break> allBreaksForBeach = sDao.getBreaksByBeach(id);
        model.addAttribute("allBreaksForBeach", allBreaksForBeach);

        List<BeachComment> beachComments = sDao.getCommentsByBeach(id);
        model.addAttribute("beachComments", beachComments);

        return "beachDetails";

    }

    Set<ConstraintViolation<Beach>> violations = new HashSet<>();

    @GetMapping("addBeach")
    public String addBeach(Model model) throws InvalidIdException {

        model.addAttribute("errors", violations);
        return "addBeach";

    }

    @PostMapping("addBeach")
    public String addBeach(HttpServletRequest request) throws SurfingDaoException {

        String beachName = request.getParameter("name");
        int beachZIP = Integer.parseInt(request.getParameter("zipcode"));

        Beach newBeach = new Beach();
        newBeach.setName(beachName);
        newBeach.setZipCode(beachZIP);

        violations.clear();
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(newBeach);

        if (violations.isEmpty()) {
            sDao.addBeach(newBeach);
            return "redirect:/beaches";
        }

        return "redirect:/addBeach";

    }

    @GetMapping("deleteBeach")
    public String deleteBeach(HttpServletRequest request) throws SurfingDaoException, InvalidIdException {

        int id = Integer.parseInt(request.getParameter("id"));

        sDao.deleteBeach(id);

        return "redirect:/beaches";

    }

    @GetMapping("editBeach")
    public String editBeach(HttpServletRequest request, Model model) throws InvalidIdException {

        int id = Integer.parseInt(request.getParameter("id"));

        Beach beachToEdit = sDao.getBeachById(id);

        model.addAttribute("beach", beachToEdit);
        model.addAttribute("editViolations", editViolations);

        return "editBeach";

    }

    List<String> editViolations = new ArrayList<>();
    @PostMapping("editBeach")
    public String performEditBeach(@Valid Beach beach, BindingResult result, HttpServletRequest request) throws InvalidIdException, SurfingDaoException {
        
        if (result.hasErrors()) {
            
            return "editBeach";
        }

        int id = Integer.parseInt(request.getParameter("id"));

        Beach beachToEdit = sDao.getBeachById(id);

        beachToEdit.setName(request.getParameter("name"));
        beachToEdit.setZipCode(Integer.parseInt(request.getParameter("zipcode")));

        violations.clear();
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(beachToEdit);

        if (violations.isEmpty()) {

            sDao.updateBeach(beachToEdit);
            return "redirect:/beaches";

        }

        return "editBeach";

    }

}
