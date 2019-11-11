/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.surfingblog.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import sg.surfingblog.newpackage.dao.InvalidIdException;
import sg.surfingblog.newpackage.dao.SurfingDao;
import sg.surfingblog.newpackage.dao.SurfingDaoException;
import sg.surfingblog.newpackage.dao.UserDao;
import sg.surfingblog.newpackage.models.Beach;
import sg.surfingblog.newpackage.models.Break;
import sg.surfingblog.newpackage.models.BreakComment;
import sg.surfingblog.newpackage.models.SiteUser;

/**
 *
 * @author blee0
 */
@Controller
public class BreakController {

    @Autowired
    SurfingDao sDao;

    @Autowired
    UserDao uDao;

    Set<ConstraintViolation<Break>> breakViolations = new HashSet<>();

    @GetMapping("/break")
    public String displayBreak(Model model) throws InvalidIdException {

//        int id = Integer.parseInt(request.getParameter("id"));
        List<Break> allBreaks = sDao.getAllBreaks();
        model.addAttribute("allBreaks", allBreaks);

//        Break selectedBreak = sDao.getBreakById(id);
//        model.addAttribute("selectedBreak", selectedBreak);
//        
//        List<BreakComment> breakComments = sDao.getCommentsByBreak(id);
//        model.addAttribute("breakComments", breakComments);
        return "break";
    }

    @GetMapping("/breakDetail")
    public String breakDetail(Integer id, Model model) throws InvalidIdException {
        Break beachBreak = sDao.getBreakById(id);
        model.addAttribute("beachBreak", beachBreak);

        List<BreakComment> breakComments = sDao.getCommentsByBreak(id);
        model.addAttribute("breakComments", breakComments);

        return "breakDetail";
    }

    @PostMapping("/addBreakComment")
    public String addBreakComment(BreakComment newBreakComment, HttpServletRequest request) throws InvalidIdException, SurfingDaoException {
        String userName = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        SiteUser user = uDao.getUserByUsername(userName);

        int id = Integer.parseInt(request.getParameter("breakId"));

        Break breakToAdd = sDao.getBreakById(id);

        newBreakComment.setUser(user);
        newBreakComment.setBeachBreak(breakToAdd);

        sDao.addBreakComment(newBreakComment);

        return "redirect:/breakDetail?id=" + id;
    }

    @GetMapping("/deleteBreakComment")
    public String deleteBreakComment(Integer id, HttpServletRequest request) throws InvalidIdException {

        int breakId = sDao.getBreakCommentById(id).getBeachBreak().getId();

        sDao.deleteBreakComment(id);

        return "redirect:/breakDetail?id=" + breakId;
    }

    @GetMapping("/editBreak")
    public String editBreak(Integer id, Model model) throws InvalidIdException {
        Break beachBreak = sDao.getBreakById(id);
        List<Beach> beaches = sDao.getAllBeaches();
        model.addAttribute("beachBreak", beachBreak);
        model.addAttribute("beaches", beaches);
        return "editBreak";
    }

    @PostMapping("/editBreak")
    public String performEditBreak(@Valid Break beachBreak, BindingResult result, HttpServletRequest request, Model model) throws InvalidIdException, SurfingDaoException {
        String beachId = request.getParameter("beachId");

        beachBreak.setBeach(sDao.getBeachById(Integer.parseInt(beachId)));

        if (result.hasErrors()) {
            model.addAttribute("beachBreak", sDao.getBreakById(beachBreak.getId()));
            model.addAttribute("beaches", sDao.getAllBeaches());
            return "editBreak";
        }

        sDao.updateBreak(beachBreak);

        return "redirect:/breakDetail?id=" + beachBreak.getId();
    }

    @GetMapping("/addBreak")
    public String addBreak(Integer id, Model model) throws InvalidIdException {
        List<Beach> beaches = sDao.getAllBeaches();
        model.addAttribute("beaches", beaches);
        model.addAttribute("errors", breakViolations);
        return "addBreak";
    }

    @PostMapping("/addBreak")
    public String addSupe(Break beachBreak, HttpServletRequest request) throws InvalidIdException, SurfingDaoException {

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        breakViolations = validate.validate(beachBreak);

        String beachId = request.getParameter("beachId");

        beachBreak.setBeach(sDao.getBeachById(Integer.parseInt(beachId)));

        if (breakViolations.isEmpty()) {
            sDao.addBreak(beachBreak);
        } else {
            return "redirect:/addBreak";
        }

        return "redirect:/break";
    }

    @GetMapping("deleteBreak")
    public String deleteCourse(Integer id) throws InvalidIdException {
        sDao.deleteBreak(id);
        return "redirect:/break";
    }

}
