/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.surfingblog.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sg.surfingblog.newpackage.dao.SurfingDao;
import sg.surfingblog.newpackage.models.Beach;

/**
 *
 * @author Chad
 */

@Controller
public class HomeController {
    
    @Autowired
    SurfingDao sDao;

    @GetMapping({"/", "/home"})
    public String displayHomePage(Model model) {
        
        List<Beach> allBeaches = sDao.getAllBeaches();
        model.addAttribute("allBeaches", allBeaches);
        
        return "home";
    }
}
