/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.surfingblog.controllers;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import sg.surfingblog.newpackage.dao.UserDao;
import sg.surfingblog.newpackage.models.Role;
import sg.surfingblog.newpackage.models.SiteUser;

/**
 *
 * @author Chad
 */
@Controller
public class LoginController {

    @Autowired
    UserDao users;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/createNewAccount")
    public String showNewAccountForm() {
        return "createNewAccount";
    }

    @PostMapping("/createNewUser")
    public String addUser(String username, String password) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setEnabled(true);

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(users.getRoleByRole("ROLE_USER"));
        user.setRoles(userRoles);

        users.createUser(user);

        return "redirect:/login";

    }
    
    
}
