/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.superherosightings.daos;

import com.mycompany.superherosightings.models.Location;
import com.mycompany.superherosightings.models.Organization;
import com.mycompany.superherosightings.models.Power;
import com.mycompany.superherosightings.models.Sighting;
import com.mycompany.superherosightings.models.Supe;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Chad
 */
@Repository
@Profile({"productionDB", "testingDB"})
public class DaoDatabaseImpl implements Dao {

    @Autowired
    JdbcTemplate template;

    @Override
    @Transactional
    public Supe addNewSupe(Supe sup) throws DaoException {

        if (sup == null || sup.getSupePower() == null) {

            throw new DaoException("Error: The supe to add is null.");
        }

        final String sql = "INSERT INTO Supes(Name, Description, PowerId) VALUES (?,?,?);";

        template.update(sql, sup.getName(), sup.getDescription(), sup.getSupePower().getId());

        int newSupId = template.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sup.setId(newSupId);

        if (!(sup.getOrgsForSupe() == null)) {
            insertSupeOrg(sup);
        }

        return sup;

    }

    private void insertSupeOrg(Supe sup) {

        final String sql = "INSERT INTO Supes_Organizations(SupesId, OrganizationsId) VALUES (?,?);";

        for (Organization org : sup.getOrgsForSupe()) {

            template.update(sql,
                    sup.getId(),
                    org.getId());

        }
    }

    @Override
    @Transactional
    public Power addNewPower(Power pow) throws DaoException {

        if (pow == null) {
            throw new DaoException("Error: The power to add is null.");
        }

        final String sql = "INSERT INTO Powers(Name) VALUES(?);";

        template.update(sql, pow.getName());

        int newPowId = template.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        pow.setId(newPowId);

        return pow;

    }

    @Override
    @Transactional
    public Organization addNewOrganization(Organization org) throws DaoException {

        if (org == null) {
            throw new DaoException("Error: The power to add is null.");
        }

        final String org_sql = "INSERT INTO Organizations(Name,Description, Address) VALUES(?,?,?);";

        template.update(org_sql,
                org.getName(),
                org.getDescription(),
                org.getAddress());

        int newOrgId = template.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        org.setId(newOrgId);

        final String bdg_sql = "INSERT INTO Supes_Organizations(SupesId, OrganizationsId) VALUES (?,?);";

        List<Supe> supesInOrg = org.getSupesInOrg();

        if (!(supesInOrg == null)) {

            for (Supe sup : supesInOrg) {

                template.update(bdg_sql,
                        sup.getId(),
                        org.getId());
            }

        }

        return org;
    }

    @Override
    @Transactional
    public Location addNewLocation(Location loc) throws DaoException {

        if (loc == null) {
            throw new DaoException("Error: The location to add is null.");
        }

        final String sql = "INSERT INTO Locations(Name, Address, Latitude, Longitude) VALUES (?,?,?,?);";

        template.update(sql,
                loc.getName(),
                loc.getAddress(),
                loc.getLatitude(),
                loc.getLongitude());

        int newLocId = template.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        loc.setId(newLocId);

        return loc;

    }

    @Override
    @Transactional
    public Sighting addNewSighting(Sighting sight) throws DaoException {

        if (sight == null || sight.getSightedSupe() == null || sight.getSightingLocation() == null) {

            throw new DaoException("Error: The sighting to add is null.");
        }

        final String sql = "INSERT INTO Sightings(SightingDate, LocationId, SuperId) VALUES (?,?,?);";

        template.update(sql,
                sight.getDate(),
                sight.getSightingLocation().getId(),
                sight.getSightedSupe().getId());

        int sightId = template.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sight.setId(sightId);

        return sight;

    }

    @Override
    public List<Supe> getAllSupes() {

        final String sql = "SELECT Id, Name, Description FROM Supes;";

        List<Supe> supes = template.query(sql, new SupeMapper());

        associatePowerAndOrgs(supes);

        return supes;

    }

    private void associatePowerAndOrgs(List<Supe> supes) {

        for (Supe sup : supes) {

            sup.setSupePower(getPowerForSupe(sup.getId()));
            sup.setOrgsForSupe(getOrgsForSupe(sup.getId()));

        }

    }

    @Override
    public List<Power> getAllPowers() {

        final String sql = "SELECT Id, Name FROM Powers;";

        return template.query(sql, new PowerMapper());

    }

    @Override
    public List<Organization> getAllOrganizations() {
        final String sql = "SELECT Id, Name, Description, Address FROM Organizations;";

        List<Organization> orgs = template.query(sql, new OrganizationMapper());

        for (Organization org : orgs) {

            org.setSupesInOrg(this.getSupesByOrg(org.getId()));
        }

        return orgs;

    }

    @Override
    public List<Location> getAllLocations() {

        final String sql = "SELECT Id, Name, Address, Latitude, Longitude FROM Locations;";

        return template.query(sql, new LocationMapper());

    }

    @Override
    public List<Sighting> getAllSightings() {

        final String sql = "SELECT Id, SightingDate FROM Sightings;";

        List<Sighting> sightings = template.query(sql, new SightingMapper());

        associateSupesAndLocations(sightings);

        return sightings;

    }

    private void associateSupesAndLocations(List<Sighting> sightings) {

        for (Sighting sig : sightings) {

            sig.setSightingLocation(getLocationForSighting(sig.getId()));
            sig.setSightedSupe(getSupeForSighting(sig.getId()));

        }

    }

    @Override
    public Supe getSupeById(int supeId) throws DaoException {

        try {

            final String sql = "SELECT Id, Name, Description FROM Supes WHERE id = ?;";

            Supe supeDB = template.queryForObject(sql, new SupeMapper(), supeId);

            supeDB.setSupePower(getPowerForSupe(supeId));

            supeDB.setOrgsForSupe(getOrgsForSupe(supeId));

            return supeDB;

        } catch (EmptyResultDataAccessException ex) {

            throw new DaoException("Error: Supe with that id is not in the DB");
        }

    }

    private Power getPowerForSupe(int supeId) {

        final String sql = "SELECT p.Id, p.Name "
                + "FROM Supes s "
                + "INNER JOIN Powers p ON s.PowerID = p.Id "
                + "WHERE s.Id = ?;";

        return template.queryForObject(sql, new PowerMapper(), supeId);

    }

    private List<Organization> getOrgsForSupe(int supeId) {

        final String sql = "SELECT o.Id, o.Name, o.Description, o.Address "
                + "FROM Organizations o "
                + "INNER JOIN Supes_Organizations so ON o.Id = so.OrganizationsId "
                + "WHERE so.SupesId = ?;";

        return template.query(sql, new OrganizationMapper(), supeId);

    }

    @Override
    public Power getPowerById(int powerId) throws DaoException {

        try {

            final String sql = "SELECT Id, Name FROM Powers WHERE Id = ?;";

            return template.queryForObject(sql, new PowerMapper(), powerId);

        } catch (EmptyResultDataAccessException ex) {

            throw new DaoException("Error: Power with that id is not in the DB");

        }

    }

    @Override
    @Transactional
    public Organization getOrganizationById(int orgId) throws DaoException{

        try {
        
        final String sql = "SELECT Id, Name, Description, Address FROM Organizations WHERE Id = ?;";

        Organization org = template.queryForObject(sql, new OrganizationMapper(), orgId);

        org.setSupesInOrg(getSupesByOrg(orgId));

        return org;
        
        }catch (EmptyResultDataAccessException ex) {

            throw new DaoException("Error: Organization with that id is not in the DB");

    }
    }

    @Override
    public Location getLocationById(int locId) throws DaoException{
        
        try{

        final String sql = "SELECT Id, Name, Address, Latitude, Longitude FROM Locations WHERE Id = ?;";

        return template.queryForObject(sql, new LocationMapper(), locId);
        
        }catch (EmptyResultDataAccessException ex) {

            throw new DaoException("Error: Location with that id is not in the DB");

    }

    }

    @Override
    public Sighting getSightingById(int sightId) throws DaoException{
        
        try{

        final String sql = "SELECT Id, SightingDate FROM Sightings WHERE Id = ?;";

        Sighting sightingDB = template.queryForObject(sql, new SightingMapper(), sightId);

        sightingDB.setSightingLocation(getLocationForSighting(sightId));
        sightingDB.setSightedSupe(getSupeForSighting(sightId));

        return sightingDB;
        
        } catch (EmptyResultDataAccessException ex) {

            throw new DaoException("Error: Location with that id is not in the DB");

    }

    }

    private Location getLocationForSighting(int sightId) {

        final String sql = "SELECT loc.Id, Name, loc.Address, loc.Latitude, loc.Longitude "
                + "FROM Locations loc "
                + "INNER JOIN Sightings sig ON loc.Id = sig.LocationId "
                + "WHERE sig.Id = ?;";

        return template.queryForObject(sql, new LocationMapper(), sightId);

    }

    private Supe getSupeForSighting(int sightId) {

        final String sql = "SELECT sup.Id, sup.Name, sup.Description "
                + "FROM Supes sup "
                + "INNER JOIN Sightings sig ON sup.Id = sig.SuperId "
                + "WHERE sig.Id = ?;";

        return template.queryForObject(sql, new SupeMapper(), sightId);

    }

    @Override
    public List<Supe> getSupesByLocation(int locId) {

        String sql = "SELECT distinct(sup.Id), sup.`Name`, sup.`Description`\n"
                + "FROM Supes sup\n"
                + "INNER JOIN Sightings sig ON sig.SuperId = sup.Id\n"
                + "INNER JOIN Locations loc ON loc.Id = sig.LocationId\n"
                + "WHERE loc.Id = ?;";

        List<Supe> supesForLocation = template.query(sql, new SupeMapper(), locId);

        for (Supe supeForLocation : supesForLocation) {

            supeForLocation.setSupePower(getPowerForSupe(supeForLocation.getId()));
            supeForLocation.setOrgsForSupe(getOrgsForSupe(supeForLocation.getId()));

        }

        return supesForLocation;

    }

    @Override
    public List<Supe> getSupesByOrg(int orgId) {

        final String sql = "SELECT sup.*\n"
                + "FROM Supes sup\n"
                + "INNER JOIN Supes_Organizations so ON so.SupesId = sup.Id\n"
                + "WHERE so.OrganizationsId = ?;";

        return template.query(sql, new SupeMapper(), orgId);

    }

    @Override
    public List<Location> getLocationsBySupe(int supeId) {

        String sql = "SELECT distinct(loc.Id), loc.`Name`, loc.Address, loc.Latitude, loc.Longitude\n"
                + "FROM Locations loc\n"
                + "INNER JOIN Sightings sig ON sig.LocationId = loc.Id\n"
                + "INNER JOIN Supes sup ON sup.Id = sig.SuperId\n"
                + "WHERE sup.Id = ?;";

        return template.query(sql, new LocationMapper(), supeId);

    }

    @Override
    public List<Sighting> getSightingsByDate(LocalDate date) {

        String sql = "SELECT Id, SightingDate\n"
                + "FROM Sightings\n"
                + "WHERE SightingDate = ?;";

        List<Sighting> sightingsForDate = template.query(sql, new SightingMapper(), date);

        for (Sighting sightingForDate : sightingsForDate) {

            sightingForDate.setSightingLocation(getLocationForSighting(sightingForDate.getId()));
            sightingForDate.setSightedSupe(this.getSupeForSighting(sightingForDate.getId()));

        }

        return sightingsForDate;
    }

    @Override
    public List<Organization> getOrganizationsBySupe(int supeId) {

        String sql = "SELECT org.Id, org.`Name`, org.`Description`, org.Address\n"
                + "FROM Organizations org\n"
                + "INNER JOIN Supes_Organizations bdg on org.Id = bdg.OrganizationsId\n"
                + "WHERE bdg.SupesId = ?;";

        return template.query(sql, new OrganizationMapper(), supeId);

    }

    @Override
    @Transactional
    public void updateSupe(Supe sup) throws DaoException {

        if (sup == null || sup.getSupePower() == null) {

            throw new DaoException("Error: The supe to update is null.");

        }

        final String update_sql = "UPDATE Supes SET Name = ?, Description = ?, PowerId = ? WHERE Id = ?;";

        template.update(update_sql,
                sup.getName(),
                sup.getDescription(),
                sup.getSupePower().getId(),
                sup.getId());

        final String delete_sql = "DELETE FROM Supes_Organizations WHERE SupesId = ?;";

        if (!(sup.getOrgsForSupe() == null)) {

            template.update(delete_sql, sup.getId());

            insertSupeOrg(sup);

        }

    }

    @Override
    public void updatePower(Power pow) throws DaoException {

        if (pow == null) {
            throw new DaoException("Error: The power to update is null.");

        }

        final String sql = "UPDATE Powers SET Name = ? WHERE Id = ?;";

        template.update(sql,
                pow.getName(),
                pow.getId());
    }

    @Override
    public void updateOrganization(Organization org) throws DaoException {

        if (org == null) {
            throw new DaoException("Error: The organization to update is null.");
        }

        final String addSQL = "UPDATE Organizations SET Name = ?, Description = ?, Address = ? WHERE Id = ?;";

        template.update(addSQL,
                org.getName(),
                org.getDescription(),
                org.getAddress(),
                org.getId());

        final String deleteBdgSQL = "DELETE FROM Supes_Organizations WHERE OrganizationsId=?;";

        template.update(deleteBdgSQL, org.getId());

        final String addBdgSQL = "INSERT INTO Supes_Organizations(SupesId, OrganizationsId) VALUES (?,?);";

        List<Supe> supesInOrg = org.getSupesInOrg();

        if (!(supesInOrg == null)) {

            for (Supe sup : supesInOrg) {

                template.update(addBdgSQL,
                        sup.getId(),
                        org.getId());
            }
        }

    }

    @Override
    public void updateLocation(Location loc) throws DaoException {

        if (loc == null) {
            throw new DaoException("Error: The location to update is null.");
        }

        final String sql = "UPDATE Locations SET Name = ?, Address = ?, Latitude = ?, Longitude = ? WHERE Id = ?;";

        template.update(sql,
                loc.getName(),
                loc.getAddress(),
                loc.getLatitude(),
                loc.getLongitude(),
                loc.getId());
    }

    @Override
    public void updateSighting(Sighting sight) throws DaoException {

        if (sight == null) {
            throw new DaoException("Error: The sighting to update is null.");
        }

        final String sql = "UPDATE Sightings SET SightingDate = ?, LocationId = ?, SuperId = ? WHERE Id = ?;";

        template.update(sql,
                sight.getDate(),
                sight.getSightingLocation().getId(),
                sight.getSightedSupe().getId(),
                sight.getId());

    }

    @Override
    @Transactional
    public void deleteSupeFromDB(int supeId) {

        final String bdgSQL = "DELETE "
                + "FROM Supes_Organizations "
                + "WHERE SupesId = ?;";
        template.update(bdgSQL, supeId);

        final String sigSQL = "DELETE sig.*\n"
                + "FROM Sightings sig\n"
                + "INNER JOIN Supes sup ON sig.SuperId = sup.Id\n"
                + "WHERE sup.Id = ?;";
        template.update(sigSQL, supeId);

        final String supSQL = "DELETE From Supes WHERE Id = ?;";
        template.update(supSQL, supeId);

    }

    @Override
    public void deletePowerFromDB(int powId) {

        final String bdgSQL = "DELETE so.*\n"
                + "FROM Supes_Organizations so\n"
                + "INNER JOIN Supes s ON s.Id = so.SupesId\n"
                + "INNER JOIN Powers p ON p.Id = s.PowerId\n"
                + "WHERE p.Id = ?;";

        template.update(bdgSQL, powId);

        final String sigSQL = "DELETE sig.*\n"
                + "FROM Sightings sig\n"
                + "INNER JOIN Supes s ON s.Id = sig.SuperId\n"
                + "INNER JOIN Powers p ON p.Id = s.PowerId\n"
                + "WHERE p.Id = ?;";
        template.update(sigSQL, powId);

        final String supSQL = "DELETE From Supes WHERE PowerId = ?;";
        template.update(supSQL, powId);

        final String powSQL = "DELETE From Powers WHERE Id = ?;";
        template.update(powSQL, powId);

    }

    @Override
    @Transactional
    public void deleteOrganizationFromDB(int orgId) {

        final String bdgSQL = "DELETE "
                + "FROM Supes_Organizations "
                + "WHERE OrganizationsId = ?;";

        template.update(bdgSQL, orgId);

        final String orgSQL = "DELETE "
                + "FROM Organizations "
                + "WHERE Id = ?;";

        template.update(orgSQL, orgId);

    }

    @Override
    @Transactional
    public void deleteLocationFromDB(int locId) {

        final String sigSQL = "DELETE sig.* "
                + "FROM Sightings sig "
                + "INNER JOIN Locations loc ON sig.LocationID = Loc.ID "
                + "WHERE loc.Id = ?;";

        template.update(sigSQL, locId);

        final String locSQL = "DELETE FROM Locations WHERE Id = ?;";
        template.update(locSQL, locId);

    }

    @Override
    public void deleteSightingFromDB(int sightId) {

        final String sigSQL = "DELETE FROM Sightings WHERE Id = ?;";

        template.update(sigSQL, sightId);

    }

    @Override
    @Transactional
    public void deleteAllDataFromDB() {

        final String sigSQL = "DELETE FROM Sightings WHERE Id>0";
        template.update(sigSQL);

        final String locSQL = "DELETE FROM Locations WHERE Id>0";
        template.update(locSQL);

        final String bdgSQL = "DELETE FROM Supes_Organizations WHERE SupesId>0;";
        template.update(bdgSQL);

        final String orgSQL = "DELETE FROM Organizations WHERE Id>0";
        template.update(orgSQL);

        final String supSQL = "DELETE FROM Supes WHERE Id>0;";
        template.update(supSQL);

        final String powSQL = "DELETE FROM Powers WHERE Id>0;";
        template.update(powSQL);

    }

    @Override
    @Transactional
    public void addTestDataToDB() {

        final String pow_SQL = "insert into Powers (Id, `Name`) "
                + "values ('201','Power 1'),"
                + "('202','Power 2'),"
                + "('203','Power 3'),"
                + "('204','Power 4'),"
                + "('205','Power 5'),"
                + "('206','Power 6'),"
                + "('207','Power 7'),"
                + "('208','Power 8')";

        template.update(pow_SQL);

        final String sup_SQL = "insert into Supes(Id, `Name`, `Description`, PowerId)\n"
                + "values ('1001','Super 1','Super 1 description','201'),\n"
                + "('1002','Super 2','Super 2 description','202'),\n"
                + "('1003','Super 3','Super 3 description','203'),\n"
                + "('1004','Super 4','Super 4 description','204'),\n"
                + "('1005','Super 5','Super 5 description','205'),\n"
                + "('1006','Super 6','Super 6 description','206'),\n"
                + "('1007','Super 7','Super 7 description','207'),\n"
                + "('1008','Super 8','Super 8 description','208'),\n"
                + "('1009','Super 9','Super 9 description','201'),\n"
                + "('1010','Super 10','Super 10 description','202');";

        template.update(sup_SQL);

        final String org_SQL = "insert into Organizations(Id, `Name`,`Description`,Address)\n"
                + "values('301','Org 1 name','Org 1 description','Org 1 address'),\n"
                + "('302','Org 2 name','Org 2 description','Org 2 address'),\n"
                + "('303','Org 3 name','Org 3 description','Org 3 address'),\n"
                + "('304','Org 4 name','Org 4 description','Org 4 address');";

        template.update(org_SQL);

        final String bdg_SQL = "insert into Supes_Organizations(SupesId,OrganizationsId)\n"
                + "values ('1001','301'),\n"
                + "('1002','301'),\n"
                + "('1003','301'),\n"
                + "('1004','301'),\n"
                + "('1005','302'),\n"
                + "('1006','302'),\n"
                + "('1007','302'),\n"
                + "('1008','302'),\n"
                + "('1009','302'),\n"
                + "('1010','302'),\n"
                + "('1001','303'),\n"
                + "('1005','303'),\n"
                + "('1009','303'),\n"
                + "('1010','303'),\n"
                + "('1002','304'),\n"
                + "('1008','304');";

        template.update(bdg_SQL);

        final String loc_SQL = "insert into Locations(Id,`Name`, Address, Latitude, Longitude)\n"
                + "values ('3001','Location 1 name','Location 1 address','60.684976','17.153166'),\n"
                + "('3002','Location 2 name','Location 2 address','15.657635','-85.99684'),\n"
                + "('3003','Location 3 name','Location 3 address','16.37937','102.36929'),\n"
                + "('3004','Location 4 name','Location 4 address','59.262542','17.978333'),\n"
                + "('3005','Location 5 name','Location 5 address','33.461435','9.02947'),\n"
                + "('3006','Location 6 name','Location 6 address','19.503769','-99.13242'),\n"
                + "('3007','Location 7 name','Location 7 address','-6.264907','106.75675'),\n"
                + "('3008','Location 8 name','Location 8 address','32.478018','117.16756'),\n"
                + "('3009','Location 9 name','Location 9 address','18.114529','121.40235'),\n"
                + "('3010','Location 10 name','Location 10 address','7.586604','0.608574'),\n"
                + "('3011','Location 11 name','Location 11 address','35.071102','115.57303'),\n"
                + "('3012','Location 12 name','Location 12 address','-31.3699','27.03523'),\n"
                + "('3013','Location 13 name','Location 13 address','39.91983','116.41571'),\n"
                + "('3014','Location 14 name','Location 14 address','18.399999','121.51667'),\n"
                + "('3015','Location 15 name','Location 15 address','18.792633','109.87814'),\n"
                + "('3016','Location 16 name','Location 16 address','56.262189','34.302827'),\n"
                + "('3017','Location 17 name','Location 17 address','37.940058','110.99388'),\n"
                + "('3018','Location 18 name','Location 18 address','28.34305','121.57248'),\n"
                + "('3019','Location 19 name','Location 19 address','20.72401','-97.53081'),\n"
                + "('3020','Location 20 name','Location 20 address','-8.4335','115.5012');";

        template.update(loc_SQL);

        final String sig_SQL = "insert into Sightings(Id, SightingDate, LocationId,SuperId)\n"
                + "values('1','2019-02-15','3001','1001'),\n"
                + "('2','2019-10-10','3002','1002'),\n"
                + "('3','2018-11-10','3003','1003'),\n"
                + "('4','2019-02-06','3004','1004'),\n"
                + "('5','2019-05-25','3005','1005'),\n"
                + "('6','2019-05-02','3006','1006'),\n"
                + "('7','2019-09-17','3007','1007'),\n"
                + "('8','2019-07-11','3008','1008'),\n"
                + "('9','2019-07-01','3009','1009'),\n"
                + "('10','2019-02-07','3010','1010'),\n"
                + "('11','2019-06-17','3011','1001'),\n"
                + "('12','2018-12-15','3012','1002'),\n"
                + "('13','2019-02-24','3013','1003'),\n"
                + "('14','2019-10-02','3014','1004'),\n"
                + "('15','2019-05-06','3015','1005'),\n"
                + "('16','2019-03-31','3016','1006'),\n"
                + "('17','2019-07-25','3017','1007'),\n"
                + "('18','2019-05-19','3018','1008'),\n"
                + "('19','2019-01-04','3019','1009'),\n"
                + "('20','2019-08-23','3020','1010'),\n"
                + "('21','2019-07-30','3001','1001'),\n"
                + "('22','2018-12-20','3002','1002'),\n"
                + "('23','2019-04-09','3003','1003'),\n"
                + "('24','2019-01-14','3004','1004'),\n"
                + "('25','2019-03-20','3005','1005'),\n"
                + "('26','2019-10-09','3006','1006'),\n"
                + "('27','2019-09-23','3007','1007'),\n"
                + "('28','2019-02-22','3008','1008'),\n"
                + "('29','2019-07-26','3009','1009'),\n"
                + "('30','2019-08-18','3010','1010'),\n"
                + "('31','2019-08-28','3011','1001'),\n"
                + "('32','2019-08-16','3012','1002'),\n"
                + "('33','2019-03-20','3013','1003'),\n"
                + "('34','2019-04-30','3014','1004'),\n"
                + "('35','2019-07-17','3015','1005'),\n"
                + "('36','2019-08-18','3016','1006'),\n"
                + "('37','2019-02-19','3017','1007'),\n"
                + "('38','2019-09-02','3018','1008'),\n"
                + "('39','2019-08-31','3019','1009'),\n"
                + "('40','2018-10-14','3020','1010'),\n"
                + "('41','2018-11-24','3001','1001'),\n"
                + "('42','2019-04-08','3002','1002');";

        template.update(sig_SQL);

    }

    @Override
    public List<Sighting> getMostRecentSightings() {

        final String sql = "select sig.Id, sig.SightingDate, sig.LocationId\n"
                + "from Sightings sig\n"
                + "order by sig.SightingDate desc\n"
                + "limit 10;";

        List<Sighting> recentSightings = template.query(sql, new SightingMapper());

        associateSupesAndLocations(recentSightings);

        return recentSightings;

    }

    private static final class PowerMapper implements RowMapper<Power> {

        @Override
        public Power mapRow(ResultSet rs, int i) throws SQLException {

            Power pFromDB = new Power();

            pFromDB.setId(rs.getInt("Id"));
            pFromDB.setName(rs.getString("Name"));

            return pFromDB;

        }
    }

    private static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int i) throws SQLException {

            Location lFromDB = new Location();

            lFromDB.setId(rs.getInt("Id"));
            lFromDB.setName(rs.getString("Name"));
            lFromDB.setAddress(rs.getString("Address"));
            lFromDB.setLatitude(rs.getBigDecimal("Latitude"));
            lFromDB.setLongitude(rs.getBigDecimal("Longitude"));

            return lFromDB;

        }
    }

    private static final class SupeMapper implements RowMapper<Supe> {

        @Override
        public Supe mapRow(ResultSet rs, int i) throws SQLException {

            Supe supFromDB = new Supe();

            supFromDB.setId(rs.getInt("Id"));
            supFromDB.setName(rs.getString("Name"));
            supFromDB.setDescription(rs.getString("Description"));

            return supFromDB;

        }
    }

    private static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int i) throws SQLException {

            Organization oFromDB = new Organization();

            oFromDB.setId(rs.getInt("Id"));
            oFromDB.setName(rs.getString("Name"));
            oFromDB.setDescription(rs.getString("Description"));
            oFromDB.setAddress(rs.getString("Address"));

            return oFromDB;

        }
    }

    private static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int i) throws SQLException {

            Sighting sigFromDB = new Sighting();

            sigFromDB.setId(rs.getInt("Id"));
            sigFromDB.setDate(rs.getString("SightingDate"));

//            sigFromDB.setDate(rs.getDate("SightingDate").toLocalDate());
            return sigFromDB;

        }
    }

}
