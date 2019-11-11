/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.surfingblog.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.surfingblog.newpackage.dao.InvalidIdException;
import sg.surfingblog.newpackage.dao.SurfingDao;
import sg.surfingblog.newpackage.dao.UserDao;
import sg.surfingblog.newpackage.models.Beach;
import sg.surfingblog.newpackage.models.BeachComment;
import sg.surfingblog.newpackage.models.Break;

/**
 *
 * @author Chad
 */
@RestController
@RequestMapping("beach")
public class RestBeachController {

    @Autowired
    SurfingDao sDao;

    @Autowired
    UserDao uDao;

    @GetMapping("/{id}")
    public Beach displaySpecificBeach(@PathVariable Integer id) throws InvalidIdException {

        Beach selectedBeach = sDao.getBeachById(id);

        return selectedBeach;

    }
    
    @GetMapping("/beachBreaks/{id}")
    public List<Break> displaySpecificBreaks(@PathVariable Integer id) throws InvalidIdException {

        List<Break> breaksForBeach = sDao.getBreaksByBeach(id);

        return breaksForBeach;

    }

}
