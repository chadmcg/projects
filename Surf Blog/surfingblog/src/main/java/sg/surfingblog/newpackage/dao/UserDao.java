/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.surfingblog.newpackage.dao;

import java.util.List;
import sg.surfingblog.newpackage.models.Role;
import sg.surfingblog.newpackage.models.SiteUser;

/**
 *
 * @author Chad
 */
public interface UserDao {
    
    SiteUser getUserById(int id) throws InvalidIdException;
    SiteUser getUserByUsername(String username);
    //May be able to delete
    List<SiteUser> getAllUsers();
    void updateUser(SiteUser user);
    void deleteUser(int id);
    SiteUser createUser(SiteUser user);
    
    Role getRoleById(int id);
    Role getRoleByRole(String role);
    List<Role> getAllRoles();
    void deleteRole(int id);
    void updateRole(Role role);
    Role createRole(Role role);
    
    void deleteAllTables();
    
}
