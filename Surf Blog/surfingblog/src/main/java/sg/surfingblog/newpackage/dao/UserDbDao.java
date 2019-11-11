/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.surfingblog.newpackage.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sg.surfingblog.newpackage.models.Role;
import sg.surfingblog.newpackage.models.SiteUser;

/**
 *
 * @author Chad
 */
@Component
public class UserDbDao implements UserDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public SiteUser getUserById(int id) throws InvalidIdException {

        String select = "SELECT id, username, password, enabled FROM user WHERE id = ?";

        SiteUser toReturn = null;
        try {
            toReturn = jdbc.queryForObject(select, new UserMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
        }

        if (toReturn == null) {
            throw new InvalidIdException("Invalid User Id.");
        }

        Set<Role> userRoles = getRolesForUser(id);
        toReturn.setRoles(userRoles);

        return toReturn;

    }

    @Override
    public SiteUser getUserByUsername(String username) {

        String select = "SELECT id, username, password, enabled FROM user WHERE username = ?";

        SiteUser toReturn = jdbc.queryForObject(select, new UserMapper(), username);

        Set<Role> userRoles = getRolesForUser(toReturn.getId());

        toReturn.setRoles(userRoles);

        return toReturn;

    }

    @Override
    public List<SiteUser> getAllUsers() {

        String select = "SELECT id, username, password, enabled FROM user";

        List<SiteUser> allUsers = jdbc.query(select, new UserMapper());

        for (SiteUser toFinish : allUsers) {

            Set<Role> userRoles = this.getRolesForUser(toFinish.getId());
            toFinish.setRoles(userRoles);

        }

        return allUsers;

    }

    @Override
    public void updateUser(SiteUser user) {

        String updateStatement = "UPDATE user SET username = ?, password = ?, enabled = ? WHERE Id = ?";

        int updateRowsAffected = jdbc.update(updateStatement, user.getUsername(), user.getPassword(), user.isEnabled(), user.getId());

        //Check that rows affected = 1
        String deleteStatement = "DELETE FROM user_role WHERE user_id = ?";

        int deleteRowsAffected = jdbc.update(deleteStatement, user.getId());

        String insert = "INSERT INTO user_role (user_id, role_id) VALUES (?,?)";

        for (Role toAdd : user.getRoles()) {

            int insertRowsAffected = jdbc.update(insert, user.getId(), toAdd.getId());

            //TODO: Make sure that one row was affected; otherwise house is on fire
        }

    }

    @Override
    public void deleteUser(int id) {

        String roleDelete = "DELETE FROM user_role WHERE user_id = ?";

        int roleRowsAffected = jdbc.update(roleDelete, id);

        String userDelete = "DELETE FROM user WHERE id = ?";

        int deleteRowsAffected = jdbc.update(userDelete, id);

    }

    @Transactional
    @Override
    public SiteUser createUser(SiteUser user) {

        String insertStatement = "INSERT INTO User (username, password, enabled) VALUES (?,?,?)";

        int insertRows = jdbc.update(insertStatement, user.getUsername(), user.getPassword(), user.isEnabled());

        int newId = jdbc.queryForObject("select LAST_INSERT_ID()", Integer.class);

        user.setId(newId);

        String roleInsert = "INSERT INTO user_role (user_id, role_id) VALUES (?,?);";
        for (Role toAdd : user.getRoles()) {

            int roleRowsAffected = jdbc.update(roleInsert, newId, toAdd.getId());

            //TODO: MAKE SURE 1 ROW WAS AFFECTED  
        }

        return user;

    }

    private Set<Role> getRolesForUser(int id) {

        String selectStatement = "SELECT id, `role`\n"
                + "FROM `role`\n"
                + "INNER JOIN user_role ON id = role_id\n"
                + "WHERE user_id =?";

        Set<Role> roles = new HashSet<Role>(jdbc.query(selectStatement, new RoleMapper(), id));

        return roles;

    }

    @Override
    public Role getRoleById(int id) {

        String select = "SELECT id, role FROM role WHERE id = ?";

        Role toReturn = jdbc.queryForObject(select, new RoleMapper(), id);

        return toReturn;

    }

    @Override
    public Role getRoleByRole(String role) {

        String select = "SELECT id, role FROM role WHERE role = ?";

        Role toReturn = jdbc.queryForObject(select, new RoleMapper(), role);

        return toReturn;

    }

    @Override
    public List<Role> getAllRoles() {
        String select = "SELECT id, role  FROM role";

        List<Role> allRoles = jdbc.query(select, new RoleMapper());

        return allRoles;
    }

    @Override
    public void deleteRole(int id) {

        String userDelete = "DELETE FROM user_role WHERE role_id = ?";

        int userRowsAffected = jdbc.update(userDelete, id);

        String roleDelete = "DELETE FROM role WHERE id = ?";

        int deleteRowsAffected = jdbc.update(roleDelete, id);
    }

    @Override
    public void updateRole(Role role) {

        String updateStatement = "UPDATE role SET role = ? WHERE Id = ?";

        int updateRowsAffected = jdbc.update(updateStatement, role.getRole(), role.getId());

        //Check that rows affected = 1
    }

    @Override
    @Transactional

    public Role createRole(Role role) {

        String insertStatement = "INSERT INTO Role (role) VALUES (?)";

        int insertRows = jdbc.update(insertStatement, role.getRole());

        int newId = jdbc.queryForObject("select LAST_INSERT_ID()", Integer.class);

        role.setId(newId);

        return role;

    }

    @Override
    public void deleteAllTables() {

        String deleteRelationships = "Delete \n"
                + "From user_role \n"
                + "Where user_id > 0";

        String deleteUsers = "Delete \n"
                + "From User \n"
                + "Where id > 0";

        jdbc.update(deleteRelationships);
        jdbc.update(deleteUsers);

    }

    public static final class UserMapper implements RowMapper<SiteUser> {

        @Override
        public SiteUser mapRow(ResultSet results, int rowNumber) throws SQLException {
            SiteUser user = new SiteUser();
            user.setId(results.getInt("id"));
            user.setUsername(results.getString("username"));
            user.setPassword(results.getString("password"));
            user.setEnabled(results.getBoolean("enabled"));
            return user;
        }
    }

    public static final class RoleMapper implements RowMapper<Role> {

        @Override
        public Role mapRow(ResultSet results, int rowNumber) throws SQLException {

            Role toReturn = new Role();

            toReturn.setId(results.getInt("id"));
            toReturn.setRole(results.getString("role"));

            return toReturn;

        }

    }

}
