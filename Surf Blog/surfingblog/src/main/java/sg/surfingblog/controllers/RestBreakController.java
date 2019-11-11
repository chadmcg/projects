/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.surfingblog.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import sg.surfingblog.newpackage.dao.InvalidIdException;
import sg.surfingblog.newpackage.dao.SurfingDao;
import sg.surfingblog.newpackage.models.Break;
import sg.surfingblog.newpackage.models.BreakComment;

/**
 *
 * @author blee0
 */
@RestController
public class RestBreakController {

    @Autowired
    SurfingDao sDao;

//    @GetMapping("/break")
//    public List<Break> getAllBreaks() {
//        List<Break> toReturn = sDao.getAllBreaks();
//        
//        return toReturn;
//    }
    
    @GetMapping("/break/{id}")
    public Break getBreakById(@PathVariable Integer id) throws InvalidIdException {
        Break toReturn = sDao.getBreakById(id);

        return toReturn;
    }

    @GetMapping("/breakComments/{id}")
    public List<BreakComment> getBreakCommentById(@PathVariable Integer id) throws InvalidIdException {
        List<BreakComment> toReturn = sDao.getCommentsByBreak(id);

        return toReturn;
    }
    
    @DeleteMapping("/breakComments/delete/{id}")
    public void deleteBreakComment(@PathVariable Integer id) throws InvalidIdException {
        sDao.deleteBreakComment(id);
    }

}
