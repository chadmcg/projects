/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.superherosightings.daos;

import com.mycompany.superherosightings.TestApplicationConfiguration;
import com.mycompany.superherosightings.models.Location;
import com.mycompany.superherosightings.models.Organization;
import com.mycompany.superherosightings.models.Power;
import com.mycompany.superherosightings.models.Sighting;
import com.mycompany.superherosightings.models.Supe;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Chad
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
@ActiveProfiles(profiles = "testingDB")
public class DaoDatabaseImplTest {

    @Autowired
    Dao testDao;

    public DaoDatabaseImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

        testDao.deleteAllDataFromDB();

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addNewSupe method, of class DaoDatabaseImpl.
     */
    @Test
    public void testAddNewSupeAndGetAllSupesGoldenPath() {

        try {
            //A new supe is created
            Supe newSupeOne = new Supe();
            newSupeOne.setName("Test Supe 1");
            newSupeOne.setDescription("Test Supe 1 - Description");
            
            ////A new power is created
            Power pow = new Power();
            pow.setName("Test Pow 1");
            Power addedPow = testDao.addNewPower(pow);
            newSupeOne.setSupePower(addedPow);
            
            ////Two new organizations are created
            Organization org1 = new Organization();
            org1.setName("Test Org 1");
            org1.setDescription("Test Org 1 - Description");
            org1.setAddress("Test Org 1 - Address");
            Organization addedOrg = testDao.addNewOrganization(org1);
            
            Organization org2 = new Organization();
            org2.setName("Test Org 2");
            org2.setDescription("Test Org 2 - Description");
            org2.setAddress("Test Org 2 - Address");
            Organization addedOrg2 = testDao.addNewOrganization(org2);
            
            List<Organization> orgsForSupeOne = new ArrayList<>();
            orgsForSupeOne.add(addedOrg);
            orgsForSupeOne.add(addedOrg2);
            
            newSupeOne.setOrgsForSupe(orgsForSupeOne);
            
            //Supe is added to DB
            Supe addedSupeOne = testDao.addNewSupe(newSupeOne);
            
            //A second supe is created
            Supe newSupeTwo = new Supe();
            newSupeTwo.setName("Test Supe 2");
            newSupeTwo.setDescription("Test Supe 2 - Description");
            
            ////A new power is created and associated with second supe
            Power pow2 = new Power();
            pow2.setName("Test Pow 2");
            Power addedPow2 = testDao.addNewPower(pow2);
            newSupeTwo.setSupePower(addedPow2);
            
            ////Two new organizations are created and associated with second supe
            Organization org3 = new Organization();
            org3.setName("Test Org 3");
            org3.setDescription("Test Org 3 - Description");
            org3.setAddress("Test Org 3 - Address");
            Organization addedOrg3 = testDao.addNewOrganization(org3);
            
            Organization org4 = new Organization();
            org4.setName("Test Org 4");
            org4.setDescription("Test Org 4 - Description");
            org4.setAddress("Test Org 4 - Address");
            Organization addedOrg4 = testDao.addNewOrganization(org4);
            
            List<Organization> orgsForSupeTwo = new ArrayList<>();
            orgsForSupeTwo.add(addedOrg3);
            orgsForSupeTwo.add(addedOrg4);
            
            newSupeTwo.setOrgsForSupe(orgsForSupeTwo);
            
            //Second supe is added to DB
            Supe addedSupeTwo = testDao.addNewSupe(newSupeTwo);
            
            //Retrieve all supes from the DB
            List<Supe> allSupes = testDao.getAllSupes();
            
            //Verify that the number of supes is correct
            assertEquals(2, allSupes.size());
            
            //Verify that the attributes of the first supe are correct
            assertEquals(newSupeOne.getId(), allSupes.get(0).getId());
            assertEquals("Test Supe 1", allSupes.get(0).getName());
            assertEquals("Test Supe 1 - Description", allSupes.get(0).getDescription());
            
            ////Verify that the properties of the first supe's power are correct
            assertEquals(addedPow.getId(), allSupes.get(0).getSupePower().getId());
            assertEquals("Test Pow 1", allSupes.get(0).getSupePower().getName());
            
            ////Verify that first supe is associated with the correct number of orgs
            assertEquals(2, allSupes.get(0).getOrgsForSupe().size());
            
            ////Verify that the properties of the first supe's orgs are correct
            assertEquals(addedOrg.getId(), allSupes.get(0).getOrgsForSupe().get(0).getId());
            assertEquals("Test Org 1", allSupes.get(0).getOrgsForSupe().get(0).getName());
            assertEquals("Test Org 1 - Description", allSupes.get(0).getOrgsForSupe().get(0).getDescription());
            assertEquals("Test Org 1 - Address", allSupes.get(0).getOrgsForSupe().get(0).getAddress());
            
            assertEquals(addedOrg2.getId(), allSupes.get(0).getOrgsForSupe().get(1).getId());
            assertEquals("Test Org 2", allSupes.get(0).getOrgsForSupe().get(1).getName());
            assertEquals("Test Org 2 - Description", allSupes.get(0).getOrgsForSupe().get(1).getDescription());
            assertEquals("Test Org 2 - Address", allSupes.get(0).getOrgsForSupe().get(1).getAddress());
            
            //Verify that the attributes of the second supe are correct
            assertEquals(newSupeTwo.getId(), allSupes.get(1).getId());
            assertEquals("Test Supe 2", allSupes.get(1).getName());
            assertEquals("Test Supe 2 - Description", allSupes.get(1).getDescription());
            
            ////Verify that the properties of the seond supe's power are correct
            assertEquals(addedPow2.getId(), allSupes.get(1).getSupePower().getId());
            assertEquals("Test Pow 2", allSupes.get(1).getSupePower().getName());
            
            ////Verify that second supe is associated with the correct number of orgs
            assertEquals(2, allSupes.get(1).getOrgsForSupe().size());
            
            ////Verify that the properties of the second supe's orgs are correct
            assertEquals(addedOrg3.getId(), allSupes.get(1).getOrgsForSupe().get(0).getId());
            assertEquals("Test Org 3", allSupes.get(1).getOrgsForSupe().get(0).getName());
            assertEquals("Test Org 3 - Description", allSupes.get(1).getOrgsForSupe().get(0).getDescription());
            assertEquals("Test Org 3 - Address", allSupes.get(1).getOrgsForSupe().get(0).getAddress());
            
            assertEquals(addedOrg4.getId(), allSupes.get(1).getOrgsForSupe().get(1).getId());
            assertEquals("Test Org 4", allSupes.get(1).getOrgsForSupe().get(1).getName());
            assertEquals("Test Org 4 - Description", allSupes.get(1).getOrgsForSupe().get(1).getDescription());
            assertEquals("Test Org 4 - Address", allSupes.get(1).getOrgsForSupe().get(1).getAddress());
        } catch (DaoException ex) {
            fail("It is not expected that an exception will be thrown.");
        }
        }
        
    @Test
    public void testAddNewSupeNullSupe(){
        
        try {
            Supe newSupe = null;
            
            testDao.addNewSupe(newSupe);
            fail("It is expected that a DaoException will be thrown.");
            
        } catch (DaoException ex) {
            //It is expected that a DaoException will be thrown.
        }
        
    }
    
    @Test
    public void testAddNewSupeNullPower(){
        
        try {
            Supe newSupe = new Supe();
            
            newSupe.setName("Name");
            newSupe.setDescription("Description");
            
            Power newPower = null;
            
            newSupe.setSupePower(newPower);
            
            Supe addedSupe = testDao.addNewSupe(newSupe);
            fail("It is expected that a DaoException will be thrown.");
            
        } catch (DaoException ex) {
            //It is expected that a DaoException will be thrown.
        }
        
    }

    

    @Test
    public void testGetSupeByIdGoldenPath() {

        try {
            //Two new supes are created
            //A new supe is created
            Supe newSupeOne = new Supe();
            newSupeOne.setName("Test Supe A");
            newSupeOne.setDescription("Test Supe A - Description");
            
            ////A new power is created
            Power pow = new Power();
            pow.setName("Test Pow A");
            Power addedPow = testDao.addNewPower(pow);
            newSupeOne.setSupePower(addedPow);
            
            ////Two new organizations are created
            Organization org1 = new Organization();
            org1.setName("Test Org A");
            org1.setDescription("Test Org A - Description");
            org1.setAddress("Test Org A - Address");
            Organization addedOrg = testDao.addNewOrganization(org1);
            
            Organization org2 = new Organization();
            org2.setName("Test Org B");
            org2.setDescription("Test Org B - Description");
            org2.setAddress("Test Org B - Address");
            Organization addedOrg2 = testDao.addNewOrganization(org2);
            
            List<Organization> orgsForSupeOne = new ArrayList<>();
            orgsForSupeOne.add(addedOrg);
            orgsForSupeOne.add(addedOrg2);
            
            newSupeOne.setOrgsForSupe(orgsForSupeOne);
            
            //Supe is added to DB
            Supe addedSupeOne = testDao.addNewSupe(newSupeOne);
            
            //A second supe is created
            Supe newSupeTwo = new Supe();
            newSupeTwo.setName("Test Supe B");
            newSupeTwo.setDescription("Test Supe B - Description");
            
            ////A new power is created and associated with second supe
            Power pow2 = new Power();
            pow2.setName("Test Pow B");
            Power addedPow2 = testDao.addNewPower(pow2);
            newSupeTwo.setSupePower(addedPow2);
            
            ////Two new organizations are created and associated with second supe
            Organization org3 = new Organization();
            org3.setName("Test Org C");
            org3.setDescription("Test Org C - Description");
            org3.setAddress("Test Org C - Address");
            Organization addedOrg3 = testDao.addNewOrganization(org3);
            
            Organization org4 = new Organization();
            org4.setName("Test Org D");
            org4.setDescription("Test Org D - Description");
            org4.setAddress("Test Org D - Address");
            Organization addedOrg4 = testDao.addNewOrganization(org4);
            
            List<Organization> orgsForSupeTwo = new ArrayList<>();
            orgsForSupeTwo.add(addedOrg3);
            orgsForSupeTwo.add(addedOrg4);
            
            newSupeTwo.setOrgsForSupe(orgsForSupeTwo);
            
            //Second supe is added to DB
            Supe addedSupeTwo = testDao.addNewSupe(newSupeTwo);
            
            //Retrieve the seond supe that was added
            Supe secondAddedSupe = testDao.getSupeById(addedSupeTwo.getId());
            
            //Verify that the properties of the retrieved supe are correct
            assertEquals("Test Supe B", secondAddedSupe.getName());
            assertEquals("Test Supe B - Description", secondAddedSupe.getDescription());
            
            ////Verify the properties for the power associated with the retrieved supe are correct
            assertEquals(addedPow2.getId(), secondAddedSupe.getSupePower().getId());
            assertEquals(addedPow2.getName(), secondAddedSupe.getSupePower().getName());
            
            ////Verify that the retrieved supe is associated with the correct # of orgs
            assertEquals(2, secondAddedSupe.getOrgsForSupe().size());
            
            ////Verify that the properties of the associated orgs are correct
            assertEquals(addedOrg3.getId(), secondAddedSupe.getOrgsForSupe().get(0).getId());
            assertEquals("Test Org C", secondAddedSupe.getOrgsForSupe().get(0).getName());
            assertEquals("Test Org C - Description", secondAddedSupe.getOrgsForSupe().get(0).getDescription());
            assertEquals("Test Org C - Address", secondAddedSupe.getOrgsForSupe().get(0).getAddress());
            
            assertEquals(addedOrg4.getId(), secondAddedSupe.getOrgsForSupe().get(1).getId());
            assertEquals("Test Org D", secondAddedSupe.getOrgsForSupe().get(1).getName());
            assertEquals("Test Org D - Description", secondAddedSupe.getOrgsForSupe().get(1).getDescription());
            assertEquals("Test Org D - Address", secondAddedSupe.getOrgsForSupe().get(1).getAddress());
        } catch (DaoException ex) {
            fail("It is not expected that an exception will be thrown.");
        }

    }
    
    @Test
    public void testGetSupeByIdInvalidId(){
    
        try {
            //Add test data
            testDao.addTestDataToDB();
            
            //-1 is not a valid id
            Supe retrievedSupe = testDao.getSupeById(-1);
            fail("It is expected that a DaoException will be thrown.");
            
        } catch (DaoException ex) {
           //It is expected that a DaoException will be thrown.
        }
}
    
    

    @Test
    public void testUpdateSupeGoldenPath() {

        try {
            //Two new supes are created
            //A new supe is created
            Supe newSupeOne = new Supe();
            newSupeOne.setName("Test Supe A");
            newSupeOne.setDescription("Test Supe A - Description");
            
            ////A new power is created
            Power pow = new Power();
            pow.setName("Test Pow A");
            Power addedPow = testDao.addNewPower(pow);
            newSupeOne.setSupePower(addedPow);
            
            ////Two new organizations are created
            Organization org1 = new Organization();
            org1.setName("Test Org A");
            org1.setDescription("Test Org A - Description");
            org1.setAddress("Test Org A - Address");
            Organization addedOrg = testDao.addNewOrganization(org1);
            
            Organization org2 = new Organization();
            org2.setName("Test Org B");
            org2.setDescription("Test Org B - Description");
            org2.setAddress("Test Org B - Address");
            Organization addedOrg2 = testDao.addNewOrganization(org2);
            
            List<Organization> orgsForSupeOne = new ArrayList<>();
            orgsForSupeOne.add(addedOrg);
            orgsForSupeOne.add(addedOrg2);
            
            newSupeOne.setOrgsForSupe(orgsForSupeOne);
            
            //Supe is added to DB
            Supe addedSupeOne = testDao.addNewSupe(newSupeOne);
            
            //A second supe is created
            Supe newSupeTwo = new Supe();
            newSupeTwo.setName("Test Supe B");
            newSupeTwo.setDescription("Test Supe B - Description");
            
            ////A new power is created and associated with second supe
            Power pow2 = new Power();
            pow2.setName("Test Pow B");
            Power addedPow2 = testDao.addNewPower(pow2);
            newSupeTwo.setSupePower(addedPow2);
            
            ////Two new organizations are created and associated with second supe
            Organization org3 = new Organization();
            org3.setName("Test Org C");
            org3.setDescription("Test Org C - Description");
            org3.setAddress("Test Org C - Address");
            Organization addedOrg3 = testDao.addNewOrganization(org3);
            
            Organization org4 = new Organization();
            org4.setName("Test Org D");
            org4.setDescription("Test Org D - Description");
            org4.setAddress("Test Org D - Address");
            Organization addedOrg4 = testDao.addNewOrganization(org4);
            
            List<Organization> orgsForSupeTwo = new ArrayList<>();
            orgsForSupeTwo.add(addedOrg3);
            orgsForSupeTwo.add(addedOrg4);
            
            newSupeTwo.setOrgsForSupe(orgsForSupeTwo);
            
            //Second supe is added to DB
            Supe addedSupeTwo = testDao.addNewSupe(newSupeTwo);
            
            //Retrieve all supes from the DB
            List<Supe> addedSupes = testDao.getAllSupes();
            
            //Verify that there are two supes
            assertEquals(2, addedSupes.size());
            
            //Retrieve the seond supe that was added
            Supe secondAddedSupe = testDao.getSupeById(addedSupeTwo.getId());
            
            //Verify the properties of the retrieved supe
            assertEquals("Test Supe B", secondAddedSupe.getName());
            assertEquals("Test Supe B - Description", secondAddedSupe.getDescription());
            
            ////Verify the properties for the power associated with the retrieved supe
            assertEquals(addedPow2.getId(), secondAddedSupe.getSupePower().getId());
            assertEquals(addedPow2.getName(), secondAddedSupe.getSupePower().getName());
            
            ////Verify the retrieved supe is associated with the correct # of orgs
            assertEquals(2, secondAddedSupe.getOrgsForSupe().size());
            
            ////Verify the properties of the associated orgs
            assertEquals(addedOrg3.getId(), secondAddedSupe.getOrgsForSupe().get(0).getId());
            assertEquals("Test Org C", secondAddedSupe.getOrgsForSupe().get(0).getName());
            assertEquals("Test Org C - Description", secondAddedSupe.getOrgsForSupe().get(0).getDescription());
            assertEquals("Test Org C - Address", secondAddedSupe.getOrgsForSupe().get(0).getAddress());
            
            assertEquals(addedOrg4.getId(), secondAddedSupe.getOrgsForSupe().get(1).getId());
            assertEquals("Test Org D", secondAddedSupe.getOrgsForSupe().get(1).getName());
            assertEquals("Test Org D - Description", secondAddedSupe.getOrgsForSupe().get(1).getDescription());
            assertEquals("Test Org D - Address", secondAddedSupe.getOrgsForSupe().get(1).getAddress());
            
            //Update the attributes of the second supe
            secondAddedSupe.setName("Test Supe B - Updated Name");
            secondAddedSupe.setDescription("Test Supe B - Updated Description");
            
            ////Update the attributes of the power
            Power updatedPower = new Power();
            updatedPower.setId(secondAddedSupe.getSupePower().getId());
            updatedPower.setName("Test Pow B - Updated");
            testDao.updatePower(updatedPower);
            
            ////Associate the updated power with the supe
            secondAddedSupe.setSupePower(updatedPower);
            
            ////Update the attributes of the orgs
            Organization updatedOrgThree = new Organization();
            updatedOrgThree.setId(addedOrg3.getId());
            updatedOrgThree.setName("Test Org C - Updated Name");
            updatedOrgThree.setDescription("Test Org C - Updated Description");
            updatedOrgThree.setAddress("Test Org C - Updated Address");
            testDao.updateOrganization(updatedOrgThree);
            
            Organization updatedOrgFour = new Organization();
            updatedOrgFour.setId(addedOrg4.getId());
            updatedOrgFour.setName("Test Org D - Updated Name");
            updatedOrgFour.setDescription("Test Org D - Updated Description");
            updatedOrgFour.setAddress("Test Org D - Updated Address");
            testDao.updateOrganization(updatedOrgFour);
            
            ////Associate the updated orgs with the supe
            List<Organization> updatedOrgs = new ArrayList<>();
            updatedOrgs.add(updatedOrgThree);
            updatedOrgs.add(updatedOrgFour);
            
            secondAddedSupe.setOrgsForSupe(updatedOrgs);
            
            //Add the updated to supe to the DB
            testDao.updateSupe(secondAddedSupe);
            
            //Retrieve the updated supe from the DB
            Supe updatedSupe = testDao.getSupeById(secondAddedSupe.getId());
            
            //Verify the properties of the updated supe
            assertEquals("Test Supe B - Updated Name", updatedSupe.getName());
            assertEquals("Test Supe B - Updated Description", updatedSupe.getDescription());
            
            ////Verify the props of the power associated with the supe
            assertEquals(updatedPower.getId(), updatedSupe.getSupePower().getId());
            assertEquals("Test Pow B - Updated", updatedSupe.getSupePower().getName());
            
            ////Verify the props of the orgs associated with the supe
            assertEquals(2, updatedSupe.getOrgsForSupe().size());
            
            assertEquals(updatedOrgThree.getId(), updatedSupe.getOrgsForSupe().get(0).getId());
            assertEquals("Test Org C - Updated Name", updatedSupe.getOrgsForSupe().get(0).getName());
            assertEquals("Test Org C - Updated Description", updatedSupe.getOrgsForSupe().get(0).getDescription());
            assertEquals("Test Org C - Updated Address", updatedSupe.getOrgsForSupe().get(0).getAddress());
            
            assertEquals(updatedOrgFour.getId(), updatedSupe.getOrgsForSupe().get(1).getId());
            assertEquals("Test Org D - Updated Name", updatedSupe.getOrgsForSupe().get(1).getName());
            assertEquals("Test Org D - Updated Description", updatedSupe.getOrgsForSupe().get(1).getDescription());
            assertEquals("Test Org D - Updated Address", updatedSupe.getOrgsForSupe().get(1).getAddress());
        } catch (DaoException ex) {
            fail("It is not expected that an exception will be thrown.");
        }

    }
    
    @Test
    public void testUpdateSupeNullSupe(){
        
        try {
            Supe newSupe = null;
            
            testDao.updateSupe(newSupe);
            fail("It is expected that a DaoException will be thrown.");
        } catch (DaoException ex) {
            //It is expected that a DaoException will be thrown
        }
        
    }
    
    @Test
    public void testUpdateSupeNullPower(){
        
        //Add test data
        testDao.addTestDataToDB();
        
        try {
            Supe updatedSupe = testDao.getSupeById(1001);
            
            Power newPower = null;
            
            updatedSupe.setSupePower(null);
            
            Supe addedSupe = testDao.addNewSupe(updatedSupe);
            fail("It is expected that a DaoException will be thrown.");
            
        } catch (DaoException ex) {
            //It is expected that a DaoException will be thrown.
        }
        
    }
    
    
    @Test
    public void testDeleteSupeFromDBGoldenPath() {
        
        //Add test data to DB
        testDao.addTestDataToDB();
        
        //Verify the number of supes in the test DB
        List<Supe> allSupes = new ArrayList<>();
        allSupes = testDao.getAllSupes();
        assertEquals(10, allSupes.size());
        
        //Verify the number of sightings in the test DB
        List<Sighting> allSightings = new ArrayList<>();
        allSightings = testDao.getAllSightings();
        assertEquals(42, allSightings.size());
        
        //Delete location 1008
        testDao.deleteSupeFromDB(1008);
        
        //Verify that there are now 9 locations
        List<Supe> allSupesUpdated = new ArrayList<>();
        allSupesUpdated = testDao.getAllSupes();
        assertEquals(9, allSupesUpdated.size());
        
        //Verify tha there are now 38 sightings
        List<Sighting> allSightingsUpdated = new ArrayList<>();
        allSightingsUpdated = testDao.getAllSightings();
        assertEquals(38, allSightingsUpdated.size());
        
    }


    @Test
    public void testAddNewPowerAndGetAllPowersGoldenPath() {

        try {
            //Two new powers are created
            Power newPower1 = new Power();
            newPower1.setName("Test Power 1");
            
            Power newPower2 = new Power();
            newPower2.setName("Test Power 2");
            
            //Powers are added to the test DB
            Power addedPower1 = testDao.addNewPower(newPower1);
            Power addedPower2 = testDao.addNewPower(newPower2);
            
            //All powers are retrieved from the test DB
            List<Power> allPowers = testDao.getAllPowers();
            
            //Verify that two powers are returned
            assertEquals(2, allPowers.size());
            
            //Verify that the properties of the first power are correct
            assertEquals(addedPower1.getId(), allPowers.get(0).getId());
            assertEquals("Test Power 1", allPowers.get(0).getName());
            
            //Verify tha the properties of the second power are correct
            assertEquals(addedPower2.getId(), allPowers.get(1).getId());
            assertEquals("Test Power 2", allPowers.get(1).getName());
        } catch (DaoException ex) {
            fail("It is not expected that an exception will be thrown.");
        }

    }
    
    @Test
    public void testAddNewPowerNullPower(){
        
        try {
            Power newPower = null;
            
            testDao.addNewPower(newPower);
            fail("It is expected that a DaoException will be thrown.");
        } catch (DaoException ex) {
            //It is expected that a DaoException will be thrown
        }
        
        
    }

    @Test
    public void testGetPowerByIdGoldenPath() {

        try {
            //Three new powers are created
            Power newPower1 = new Power();
            newPower1.setName("Test Power A");
            Power addedPower1 = testDao.addNewPower(newPower1);
            
            Power newPower2 = new Power();
            newPower2.setName("Test Power B");
            Power addedPower2 = testDao.addNewPower(newPower2);
            
            Power newPower3 = new Power();
            newPower3.setName("Test Power C");
            Power addedPower3 = testDao.addNewPower(newPower3);
            
            //Retrieve the first power that was added
            Power firstAddedPower = testDao.getPowerById(addedPower1.getId());
            
            //Verify that the properties of the retrieved power are correct
            assertEquals("Test Power A", firstAddedPower.getName());
            
            //Retrieve the third power that was added
            Power thirdAddedPower = testDao.getPowerById(addedPower3.getId());
            
            //Verify that the properties of the retrieved power are correct
            assertEquals("Test Power C", thirdAddedPower.getName());
        } catch (DaoException ex) {
            fail("It is not expected that an exception will be thrown.");
        }

    }
    
    @Test
    public void testGetPowerInvalidId(){
        
        try {
            //Add test data
            testDao.addTestDataToDB();
            
            //-1 is not a valid power id
            Power addedPower = testDao.getPowerById(-1);
            fail("It is expected that a DaoException will be thrown");
            
        } catch (DaoException ex) {
            //It is expected that a DaoException will be thrown
        }
        
    }

    @Test
    public void testUpdatePowerGoldenPath() {

        try {
            //Create two new powers
            Power newPowerOne = new Power();
            newPowerOne.setName("Test Power 1");
            
            Power newPowerTwo = new Power();
            newPowerTwo.setName("Test Power 2");
            
            //Add the powers to the DB
            Power addedPowerOne = testDao.addNewPower(newPowerOne);
            Power addedPowerTwo = testDao.addNewPower(newPowerTwo);
            
            //Retrieve all powers from the DB
            List<Power> addedPowers = testDao.getAllPowers();
            
            //Verify the first power's attributes
            Power powerOne = addedPowers.get(0);
            assertEquals("Test Power 1", powerOne.getName());
            
            //Update the attributes of the first power
            powerOne.setName("Test Power 1 - Updated Name");
            
            //Call the updatePower method
            testDao.updatePower(powerOne);
            
            //Retrieve all powers from the DB
            List<Power> updatedPowers = testDao.getAllPowers();
            
            //Verify that there are still two powers
            assertEquals(2, updatedPowers.size());
            
            //Retrieve the updated power
            Power updatedPower = testDao.getPowerById(powerOne.getId());
            
            //Verify the updated power's attributes
            assertEquals("Test Power 1 - Updated Name", updatedPower.getName());
        } catch (DaoException ex) {
            fail("It is not expected that an exception will be thrown.");
        }

    }
    
    @Test
    public void testUpdatePowerNullPower(){
        
        try {
            Power newPower = null;
            
            testDao.updatePower(newPower);
            fail("It is expected that a DaoException will be thrown.");
        } catch (DaoException ex) {
            //It is expected that a DaoException will be thrown
        }
        
    }
    
    @Test
    public void testDeletePowerFromDBGoldenPath() {
        
        //Add test data to DB
        testDao.addTestDataToDB();
        
        //Verify the number of sightings in the test DB
        List<Sighting> allSightings = new ArrayList<>();
        allSightings = testDao.getAllSightings();
        assertEquals(42, allSightings.size());
        
        //Verify the number of supes in the test DB
        List<Supe> allSupes = new ArrayList<>();
        allSupes = testDao.getAllSupes();
        assertEquals(10, allSupes.size());
        
        //Verify the number of powers in the test DB
        List<Power> allPowers = new ArrayList<>();
        allPowers = testDao.getAllPowers();
        assertEquals(8, allPowers.size());
        
       //Delete power 202
        testDao.deletePowerFromDB(202);
    
       //Verify that there are now 38 sightings
        List<Sighting> allSightingsUpdated = new ArrayList<>();
        allSightingsUpdated = testDao.getAllSightings();
        assertEquals(33, allSightingsUpdated.size());
        
        //Verify that there are now 9 supes
        List<Supe> allSupesUpdated = new ArrayList<>();
        allSupesUpdated = testDao.getAllSupes();
        assertEquals(8, allSupesUpdated.size());
        
        //Verify that there are now 7 powers
        List<Power> allPowersUpdated = new ArrayList<>();
        allPowersUpdated = testDao.getAllPowers();
        assertEquals(7, allPowersUpdated.size());
        
        
    }

    @Test
    public void testAddNewLocationAndGetAllLocationsGoldenPath() {

        try {
            //Two new locations are created
            Location newLocationOne = new Location();
            newLocationOne.setName("Test Location 1");
            newLocationOne.setAddress("Test Address 1");
            newLocationOne.setLatitude(new BigDecimal("60.684976"));
            newLocationOne.setLongitude(new BigDecimal("17.153166"));
            
            Location newLocationTwo = new Location();
            newLocationTwo.setName("Test Location 2");
            newLocationTwo.setAddress("Test Address 2");
            newLocationTwo.setLatitude(new BigDecimal("33.461435"));
            newLocationTwo.setLongitude(new BigDecimal("9.029470"));
            
            //Locations are added to the test DB
            Location addedLocationOne = testDao.addNewLocation(newLocationOne);
            Location addedLocationTwo = testDao.addNewLocation(newLocationTwo);
            
            //All locations are retrieved from the test DB
            List<Location> allLocations = testDao.getAllLocations();
            
            //Verify that two locations are returned
            assertEquals(2, allLocations.size());
            
            //Verify that the properties of the first location are correct
            assertEquals(addedLocationOne.getId(), allLocations.get(0).getId());
            assertEquals("Test Location 1", allLocations.get(0).getName());
            assertEquals("Test Address 1", allLocations.get(0).getAddress());
            assertEquals(new BigDecimal("60.684976"), allLocations.get(0).getLatitude());
            assertEquals(new BigDecimal("17.153166"), allLocations.get(0).getLongitude());
            
            //Verify that the properties of the second location are correct
            assertEquals(addedLocationTwo.getId(), allLocations.get(1).getId());
            assertEquals("Test Location 2", allLocations.get(1).getName());
            assertEquals("Test Address 2", allLocations.get(1).getAddress());
            assertEquals(new BigDecimal("33.461435"), allLocations.get(1).getLatitude());
            assertEquals(new BigDecimal("9.029470"), allLocations.get(1).getLongitude());
        } catch (DaoException ex) {
            fail("It is not expected that an exception will be thrown.");
        }

    }
    
    @Test
    public void testAddLocationNullLocation(){
        
        try {
            Location newLocation = null;
            
            testDao.addNewLocation(newLocation);
            fail("It is expected that a DaoException will be thrown.");
            
        } catch (DaoException ex) {
           //It is expected that a DaoException will be thrown
        }
        
        
    }

    @Test
    public void testGetLocationByIdGoldenPath() {

        try {
            //Three new locations are created
            Location newLocationOne = new Location();
            newLocationOne.setName("Test Location A");
            newLocationOne.setAddress("Test Address A");
            newLocationOne.setLatitude(new BigDecimal("35.071102"));
            newLocationOne.setLongitude(new BigDecimal("115.573030"));
            Location addedLocationOne = testDao.addNewLocation(newLocationOne);
            
            Location newLocationTwo = new Location();
            newLocationTwo.setName("Test Location B");
            newLocationTwo.setAddress("Test Address B");
            newLocationTwo.setLatitude(new BigDecimal("59.262542"));
            newLocationTwo.setLongitude(new BigDecimal("17.978333"));
            Location addedLocationTwo = testDao.addNewLocation(newLocationTwo);
            
            Location newLocationThree = new Location();
            newLocationThree.setName("Test Location C");
            newLocationThree.setAddress("Test Address C");
            newLocationThree.setLatitude(new BigDecimal("28.343050"));
            newLocationThree.setLongitude(new BigDecimal("121.572480"));
            Location addedLocationThree = testDao.addNewLocation(newLocationThree);
            
            //Retrieve the first location that was added
            Location firstAddedLocation = testDao.getLocationById(addedLocationOne.getId());
            
            //Verify that the properties of the retrieved location are correct
            assertEquals("Test Location A", firstAddedLocation.getName());
            assertEquals("Test Address A", firstAddedLocation.getAddress());
            assertEquals(new BigDecimal("35.071102"), firstAddedLocation.getLatitude());
            assertEquals(new BigDecimal("115.573030"), firstAddedLocation.getLongitude());
            
            //Retrieve the third location that was added
            Location thirdAddedLocation = testDao.getLocationById(addedLocationThree.getId());
            
            //Verify that the properties of the retrieved location are correct
            assertEquals("Test Location C", thirdAddedLocation.getName());
            assertEquals("Test Address C", thirdAddedLocation.getAddress());
            assertEquals(new BigDecimal("28.343050"), thirdAddedLocation.getLatitude());
            assertEquals(new BigDecimal("121.572480"), thirdAddedLocation.getLongitude());
        } catch (DaoException ex) {
            fail("It is not expected that an exception will be thrown.");
        }

    }
    
    
    @Test
    public void testGetLocationByIdInvalidId(){
        
        try {
            //Add test data
            testDao.addTestDataToDB();
            
            //-1 is not a valid location id
            Location addedLocation = testDao.getLocationById(-1);
            fail("It is expected that a DaoException will be thrown");
            
        } catch (DaoException ex) {
            //It is expected that a DaoException will be thrown
        }
        
    }
    

    @Test
    public void testUpdateLocationGoldenPath() {

        try {
            //Create two new locations
            Location newLocationOne = new Location();
            newLocationOne.setName("Test Location 1");
            newLocationOne.setAddress("Test Address 1");
            newLocationOne.setLatitude(new BigDecimal("12.345678"));
            newLocationOne.setLongitude(new BigDecimal("34.567890"));
            
            Location newLocationTwo = new Location();
            newLocationTwo.setName("Test Location 2");
            newLocationTwo.setAddress("Test Address 2");
            newLocationTwo.setLatitude(new BigDecimal("56.789012"));
            newLocationTwo.setLongitude(new BigDecimal("78.901234"));
            
            //Add the locations to the DB
            Location addedLocationOne = testDao.addNewLocation(newLocationOne);
            Location addedLocationTwo = testDao.addNewLocation(newLocationTwo);
            
            //Retrieve all locations from the DB
            List<Location> addedLocations = testDao.getAllLocations();
            
            //Verify the first location's attributes
            Location locationOne = addedLocations.get(0);
            assertEquals("Test Location 1", locationOne.getName());
            assertEquals("Test Address 1", locationOne.getAddress());
            assertEquals(new BigDecimal("12.345678"), locationOne.getLatitude());
            assertEquals(new BigDecimal("34.567890"), locationOne.getLongitude());
            
            //Update the attributes of the first location
            locationOne.setName("Test Location 1 - Updated Name");
            locationOne.setAddress("Test Address 1 - Updated Address");
            locationOne.setLatitude(new BigDecimal("89.012345"));
            locationOne.setLongitude(new BigDecimal("90.123456"));
            
            //Call the updateLocation method
            testDao.updateLocation(locationOne);
            
            //Retrieve all locations from the DB
            List<Location> updatedLocations = testDao.getAllLocations();
            
            //Verify that there are still two locations
            assertEquals(2, updatedLocations.size());
            
            //Retrieve the updated location
            Location updatedLocation = testDao.getLocationById(locationOne.getId());
            
            //Verify the updated location's attributes
            assertEquals("Test Location 1 - Updated Name", updatedLocation.getName());
            assertEquals("Test Address 1 - Updated Address", updatedLocation.getAddress());
            assertEquals(new BigDecimal("89.012345"), updatedLocation.getLatitude());
            assertEquals(new BigDecimal("90.123456"), updatedLocation.getLongitude());
        } catch (DaoException ex) {
            fail("It is not expected that an exception will be thrown.");
        }

    }
    
    @Test
    public void testUpdateLocationNullLocation(){
        
        try {
            Location newLocation = null;
            testDao.updateLocation(newLocation);
            fail("It is expected that a DaoException will be thrown.");
            
        } catch (DaoException ex) {
            //It is expected that a DaoException will be thrown
        }
        
        
        
        
    }
    
    @Test
    public void testDeleteLocationFromDBGoldenPath() {
        
        //Add test data to DB
        testDao.addTestDataToDB();
        
        //Verify the number of locations in the test DB
        List<Location> allLocations = new ArrayList<>();
        allLocations = testDao.getAllLocations();
        assertEquals(20, allLocations.size());
        
        //Verify the number of sightings in the test DB
        List<Sighting> allSightings = new ArrayList<>();
        allSightings = testDao.getAllSightings();
        assertEquals(42, allSightings.size());
        
        //Delete location 3001
        testDao.deleteLocationFromDB(3001);
        
        //Verify that there are now 19 locations
        List<Location> allLocationsUpdated = new ArrayList<>();
        allLocationsUpdated = testDao.getAllLocations();
        assertEquals(19, allLocationsUpdated.size());
        
        //Verify that there are now 39 sightings
        List<Sighting> allSightingsUpdated = new ArrayList<>();
        allSightingsUpdated = testDao.getAllSightings();
        assertEquals(39, allSightingsUpdated.size());
        
    }
    

    @Test
    public void testAddNewOrganizationAndGetAllOrganizationsGoldenPath() {

        try {
            //Two new organizations are created
            Organization newOrganizationOne = new Organization();
            newOrganizationOne.setName("Test Org 1");
            newOrganizationOne.setDescription("Test Org 1 Description");
            newOrganizationOne.setAddress("Test Org 1 Address");
            
            Organization newOrganizationTwo = new Organization();
            newOrganizationTwo.setName("Test Org 2");
            newOrganizationTwo.setDescription("Test Org 2 Description");
            newOrganizationTwo.setAddress("Test Org 2 Address");
            
            //Organizations are added to the test DB
            Organization addedOrganizationOne = testDao.addNewOrganization(newOrganizationOne);
            Organization addedOrganizationTwo = testDao.addNewOrganization(newOrganizationTwo);
            
            //All locations are retrieved from the test DB
            List<Organization> allOrganizations = testDao.getAllOrganizations();
            
            //Verify that two locations are returned
            assertEquals(2, allOrganizations.size());
            
            //Verify that the properties of the first organization are correct
            assertEquals(addedOrganizationOne.getId(), allOrganizations.get(0).getId());
            assertEquals("Test Org 1", allOrganizations.get(0).getName());
            assertEquals("Test Org 1 Description", allOrganizations.get(0).getDescription());
            assertEquals("Test Org 1 Address", allOrganizations.get(0).getAddress());
            
            //Verify that the properties of the second organization are correct
            assertEquals(addedOrganizationTwo.getId(), allOrganizations.get(1).getId());
            assertEquals("Test Org 2", allOrganizations.get(1).getName());
            assertEquals("Test Org 2 Description", allOrganizations.get(1).getDescription());
            assertEquals("Test Org 2 Address", allOrganizations.get(1).getAddress());
        } catch (DaoException ex) {
            fail("It is not expected that an exception will be thrown.");
        }

    }
    
    @Test
    public void testAddNewOrganizationNullOrganization(){
        
        try {
            Organization newOrg = null;
            testDao.addNewOrganization(newOrg);
            fail("It is expected that a DaoException will be thrown.");
        } catch (DaoException ex) {
            //It is expected that a DaoException will be thrown
        }
        
    }

    @Test
    public void testGetOrganizationByIdGoldenPath() {

        try {
            //Three new organizations are created
            Organization newOrganizationOne = new Organization();
            newOrganizationOne.setName("Test Org A");
            newOrganizationOne.setDescription("Test Org A - Description");
            newOrganizationOne.setAddress("Test Org A - Address");
            Organization addedOrganizationOne = testDao.addNewOrganization(newOrganizationOne);
            
            Organization newOrganizationTwo = new Organization();
            newOrganizationTwo.setName("Test Org B");
            newOrganizationTwo.setDescription("Test Org B - Description");
            newOrganizationTwo.setAddress("Test Org B - Address");
            Organization addedOrganizationTwo = testDao.addNewOrganization(newOrganizationTwo);
            
            Organization newOrganizationThree = new Organization();
            newOrganizationThree.setName("Test Org C");
            newOrganizationThree.setDescription("Test Org C - Description");
            newOrganizationThree.setAddress("Test Org C - Address");
            Organization addedOrganizationThree = testDao.addNewOrganization(newOrganizationThree);
            
            //Retrieve the first organization that was added
            Organization firstAddedOrganization = testDao.getOrganizationById(addedOrganizationOne.getId());
            
            //Verify that the properties of the retrieved organization are correct
            assertEquals("Test Org A", firstAddedOrganization.getName());
            assertEquals("Test Org A - Description", firstAddedOrganization.getDescription());
            assertEquals("Test Org A - Address", firstAddedOrganization.getAddress());
            
            //Retrieve the third organization that was added
            Organization thirdAddedOrganization = testDao.getOrganizationById(addedOrganizationThree.getId());
            
            //Verify that the properties of the retrieved location are correct
            assertEquals("Test Org C", thirdAddedOrganization.getName());
            assertEquals("Test Org C - Description", thirdAddedOrganization.getDescription());
            assertEquals("Test Org C - Address", thirdAddedOrganization.getAddress());
        } catch (DaoException ex) {
            fail("It is not expected that an exception will be thrown.");
        }

    }
    
    @Test
    public void testGetOrganizationByIdInvalidId(){
        
        try {
            //Add test data
            testDao.addTestDataToDB();
            
            //-1 is not a valid organization id
            Organization addedOrganization = testDao.getOrganizationById(-1);
            fail("It is expected that a DaoException will be thrown");
            
        } catch (DaoException ex) {
            //It is expected that a DaoException will be thrown
        }
        
    }

    @Test
    public void testUpdateOrganizationGoldenPath() {

        try {
            //Create two new orgs
            Organization newOrgOne = new Organization();
            newOrgOne.setName("Test Org 1");
            newOrgOne.setDescription("Test Org 1 - Description");
            newOrgOne.setAddress("Test Org 1 - Address");
            
            Organization newOrgTwo = new Organization();
            newOrgTwo.setName("Test Org 2");
            newOrgTwo.setDescription("Test Org 2 - Description");
            newOrgTwo.setAddress("Test Org 2 - Address");
            
            //Add the organizations to the DB
            Organization addedOrgOne = testDao.addNewOrganization(newOrgOne);
            Organization addedOrgTwo = testDao.addNewOrganization(newOrgTwo);
            
            //Retrieve all orgs from the DB
            List<Organization> addedOrganizations = testDao.getAllOrganizations();
            
            //Verify the first org's attributes
            Organization orgOne = addedOrganizations.get(0);
            assertEquals("Test Org 1", orgOne.getName());
            assertEquals("Test Org 1 - Description", orgOne.getDescription());
            assertEquals("Test Org 1 - Address", orgOne.getAddress());
            
            //Update the attributes of the first org
            orgOne.setName("Test Org 1 - Updated Name");
            orgOne.setDescription("Test Org 1 - Updated Description");
            orgOne.setAddress("Test Org 1 - Updated Address");
            
            //Call the updateOrganization method
            testDao.updateOrganization(orgOne);
            
            //Retrieve all organizations from the DB
            List<Organization> updatedOrgs = testDao.getAllOrganizations();
            
            //Verify that there are still two orgs
            assertEquals(2, updatedOrgs.size());
            
            //Retrieve the updated org
            Organization updatedOrg = testDao.getOrganizationById(orgOne.getId());
            
            //Verify the updated org's attributes
            assertEquals("Test Org 1 - Updated Name", updatedOrg.getName());
            assertEquals("Test Org 1 - Updated Description", updatedOrg.getDescription());
            assertEquals("Test Org 1 - Updated Address", updatedOrg.getAddress());
        } catch (DaoException ex) {
            fail("It is not expected that an exception will be thrown.");
        }

    }
    
    @Test
    public void testUpdateOrganizationNullOrganization(){
        
        try {
            Organization newOrg = null;
            testDao.updateOrganization(newOrg);
            fail("It is expected that a DaoException will be thrown.");
        } catch (DaoException ex) {
           //It is expected that a DaoException will be thrown
        }
        
    }
    
    @Test
    public void testDeleteOrganizationFromDBGoldenPath() {
        
        //Add test data to DB
        testDao.addTestDataToDB();
        
        //Verify the number of orgs in the test DB
        List<Organization> allOrgs = new ArrayList<>();
        allOrgs = testDao.getAllOrganizations();
        assertEquals(4, allOrgs.size());
        
        //Delete org 303
        testDao.deleteOrganizationFromDB(303);
        
        //Verify that there are now 3 orgs
        List<Organization> allOrgsUpdated = new ArrayList<>();
        allOrgsUpdated = testDao.getAllOrganizations();
        assertEquals(3, allOrgsUpdated.size());
        
        
    }

    @Test
    public void testAddNewSightingAndGetAllSightingsGoldenPath() {

        try {
            //A new sighting is created
            Sighting newSightingOne = new Sighting();
            newSightingOne.setDate("2019-10-14");
            
            ////Create new supe
            Supe newSupe = new Supe();
            newSupe.setName("Test Supe");
            newSupe.setDescription("Test Supe - Description");
            
            //////Create new power
            Power newPow = new Power();
            newPow.setName("Test Power");
            Power addedPower = testDao.addNewPower(newPow);
            
            //////Associate power with supe
            newSupe.setSupePower(addedPower);
            
            //////A new org is created
            Organization org1 = new Organization();
            org1.setName("Test Org 1");
            org1.setDescription("Test Org 1 - Description");
            org1.setAddress("Test Org 1 - Address");
            Organization addedOrg = testDao.addNewOrganization(org1);
            
            //////Associate org with supe
            List<Organization> orgsForSupe = new ArrayList<>();
            orgsForSupe.add(org1);
            newSupe.setOrgsForSupe(orgsForSupe);
            
            ////Add supe to DB
            Supe addedSupe = testDao.addNewSupe(newSupe);
            
            ////Associate supe with sighting
            newSightingOne.setSightedSupe(addedSupe);
            
            ////Create new location
            Location newLoc = new Location();
            newLoc.setName("New Loc");
            newLoc.setAddress("New Loc - Address");
            newLoc.setLatitude(new BigDecimal("60.684976"));
            newLoc.setLongitude(new BigDecimal("17.151234"));
            Location addedLoc = testDao.addNewLocation(newLoc);
            
            ////Associate location with sighting
            newSightingOne.setSightingLocation(addedLoc);
            
            //A second sighting is created
            Sighting newSightingTwo = new Sighting();
            newSightingTwo.setDate("2019-10-13");
            
            ////Create new supe
            Supe newSupeTwo = new Supe();
            newSupeTwo.setName("Test Supe 2");
            newSupeTwo.setDescription("Test Supe 2 - Description");
            
            //////Create new power
            Power newPowTwo = new Power();
            newPowTwo.setName("Test Power 2");
            Power addedPowerTwo = testDao.addNewPower(newPowTwo);
            
            //////Associate power with supe
            newSupeTwo.setSupePower(addedPowerTwo);
            
            //////A new org is created
            Organization org2 = new Organization();
            org2.setName("Test Org 2");
            org2.setDescription("Test Org 2 - Description");
            org2.setAddress("Test Org 2 - Address");
            Organization addedOrgTwo = testDao.addNewOrganization(org2);
            
            //////Associate org with supe
            List<Organization> orgsForSupeTwo = new ArrayList<>();
            orgsForSupeTwo.add(org2);
            newSupeTwo.setOrgsForSupe(orgsForSupeTwo);
            
            ////Add supe to DB
            Supe addedSupeTwo = testDao.addNewSupe(newSupeTwo);
            
            ////Associate supe with sighting
            newSightingTwo.setSightedSupe(addedSupeTwo);
            
            ////Create new location
            Location newLocTwo = new Location();
            newLocTwo.setName("New Loc 2");
            newLocTwo.setAddress("New Loc 2 - Address");
            newLocTwo.setLatitude(new BigDecimal("61.684976"));
            newLocTwo.setLongitude(new BigDecimal("18.151234"));
            Location addedLocTwo = testDao.addNewLocation(newLocTwo);
            
            ////Associate location with sighting
            newSightingTwo.setSightingLocation(addedLocTwo);
            
            //Sightings are added to the test DB
            Sighting addedSightingOne = testDao.addNewSighting(newSightingOne);
            Sighting addedSightingTwo = testDao.addNewSighting(newSightingTwo);
            
            //All sightings are retrieved from the test DB
            List<Sighting> allSightings = testDao.getAllSightings();
            
            //Verify that two sightings are returned
            assertEquals(2, allSightings.size());
            
            //Verify that the properties of the first sighting are correct
            assertEquals(newSightingOne.getId(),allSightings.get(0).getId());
            assertEquals("2019-10-14",allSightings.get(0).getDate());
            assertEquals(addedSupe.getId(), allSightings.get(0).getSightedSupe().getId());
            assertEquals(addedLoc.getId(), allSightings.get(0).getSightingLocation().getId());
            
            //Verify that the properties of the second sighting are correct
            assertEquals(newSightingTwo.getId(),allSightings.get(1).getId());
            assertEquals("2019-10-13",allSightings.get(1).getDate());
            assertEquals(addedSupeTwo.getId(), allSightings.get(1).getSightedSupe().getId());
            assertEquals(addedLocTwo.getId(), allSightings.get(1).getSightingLocation().getId());
        } catch (DaoException ex) {
            fail("It is not expected that an exception will be thrown.");
        }
       
    }
    
    @Test
    public void testAddNewSightingNullSighting(){
        
        try {
            Sighting newSighting = null;
            testDao.addNewSighting(newSighting);
            fail("It is expected that a DaoException will be thrown");
        } catch (DaoException ex) {
            //It is expected that a DaoException will be thrown
        }
        
    }
    
    @Test
    public void testAddNewSightingNullLocation(){
        
        //Add test data
        testDao.addTestDataToDB();
        
        try {
            Sighting newSighting = new Sighting();
            newSighting.setDate("07/25/2019");
            newSighting.setSightedSupe(testDao.getSupeById(1001));
            
            Location newLocation = null;
            newSighting.setSightingLocation(newLocation);
            
            Sighting addedSighting = testDao.addNewSighting(newSighting);
            fail("It is expected that a DaoException will be thrown.");
            
        } catch (DaoException ex) {
            //It is expected that a DaoException will be thrown.
        }
        
    }
    
    
    @Test
    public void testAddNewSightingNullSupe(){
        
        //Add test data
        testDao.addTestDataToDB();
        
        try {
            Sighting newSighting = new Sighting();
            newSighting.setDate("07/25/2019");
            
            Location newLocation = testDao.getLocationById(3001);
            newSighting.setSightingLocation(newLocation);
            
            newSighting.setSightedSupe(null);
            
            Sighting addedSighting = testDao.addNewSighting(newSighting);
            fail("It is expected that a DaoException will be thrown.");
            
        } catch (DaoException ex) {
            //It is expected that a DaoException will be thrown.
        }
        
    }
    
    @Test
    public void testGetSightingByIdGoldenPath() {
        try {
            //A new sighting is created
            Sighting newSightingOne = new Sighting();
            newSightingOne.setDate("2019-10-14");
            
            ////Create new supe
            Supe newSupe = new Supe();
            newSupe.setName("Test Supe");
            newSupe.setDescription("Test Supe - Description");
            
            //////Create new power
            Power newPow = new Power();
            newPow.setName("Test Power");
            Power addedPower = testDao.addNewPower(newPow);
            
            //////Associate power with supe
            newSupe.setSupePower(addedPower);
            
            //////A new org is created
            Organization org1 = new Organization();
            org1.setName("Test Org 1");
            org1.setDescription("Test Org 1 - Description");
            org1.setAddress("Test Org 1 - Address");
            Organization addedOrg = testDao.addNewOrganization(org1);
            
            //////Associate org with supe
            List<Organization> orgsForSupe = new ArrayList<>();
            orgsForSupe.add(org1);
            newSupe.setOrgsForSupe(orgsForSupe);
            
            ////Add supe to DB
            Supe addedSupe = testDao.addNewSupe(newSupe);
            
            ////Associate supe with sighting
            newSightingOne.setSightedSupe(addedSupe);
            
            ////Create new location
            Location newLoc = new Location();
            newLoc.setName("New Loc");
            newLoc.setAddress("New Loc - Address");
            newLoc.setLatitude(new BigDecimal("60.684976"));
            newLoc.setLongitude(new BigDecimal("17.151234"));
            Location addedLoc = testDao.addNewLocation(newLoc);
            
            ////Associate location with sighting
            newSightingOne.setSightingLocation(addedLoc);
            
            //A second sighting is created
            Sighting newSightingTwo = new Sighting();
            newSightingTwo.setDate("2019-10-13");
            
            ////Create new supe
            Supe newSupeTwo = new Supe();
            newSupeTwo.setName("Test Supe 2");
            newSupeTwo.setDescription("Test Supe 2 - Description");
            
            //////Create new power
            Power newPowTwo = new Power();
            newPowTwo.setName("Test Power 2");
            Power addedPowerTwo = testDao.addNewPower(newPowTwo);
            
            //////Associate power with supe
            newSupeTwo.setSupePower(addedPowerTwo);
            
            //////A new org is created
            Organization org2 = new Organization();
            org2.setName("Test Org 2");
            org2.setDescription("Test Org 2 - Description");
            org2.setAddress("Test Org 2 - Address");
            Organization addedOrgTwo = testDao.addNewOrganization(org2);
            
            //////Associate org with supe
            List<Organization> orgsForSupeTwo = new ArrayList<>();
            orgsForSupeTwo.add(org2);
            newSupeTwo.setOrgsForSupe(orgsForSupeTwo);
            
            ////Add supe to DB
            Supe addedSupeTwo = testDao.addNewSupe(newSupeTwo);
            
            ////Associate supe with sighting
            newSightingTwo.setSightedSupe(addedSupeTwo);
            
            ////Create new location
            Location newLocTwo = new Location();
            newLocTwo.setName("New Loc 2");
            newLocTwo.setAddress("New Loc 2 - Address");
            newLocTwo.setLatitude(new BigDecimal("61.684976"));
            newLocTwo.setLongitude(new BigDecimal("18.151234"));
            Location addedLocTwo = testDao.addNewLocation(newLocTwo);
            
            ////Associate location with sighting
            newSightingTwo.setSightingLocation(addedLocTwo);
            
            //Sightings are added to the test DB
            Sighting addedSightingOne = testDao.addNewSighting(newSightingOne);
            Sighting addedSightingTwo = testDao.addNewSighting(newSightingTwo);
            
            //Retrieve the first sighting
            Sighting sightingOne = testDao.getSightingById(addedSightingOne.getId());
            
            //Verify that the first sighting's properties are correct
            assertEquals(addedSightingOne.getId(),sightingOne.getId());
            assertEquals("2019-10-14",sightingOne.getDate());
            assertEquals(addedSupe.getId(), sightingOne.getSightedSupe().getId());
            assertEquals(addedLoc.getId(), sightingOne.getSightingLocation().getId());
        } catch (DaoException ex) {
            fail("It is not expected that an exception will be thrown.");
        }
        
    }
    
    @Test
    public void testGetSightingsByIdInvalidId(){
        
        try {
            //Add test data
            testDao.addTestDataToDB();
            
            //-1 is not a valid sighting id
            Sighting addedSighting = testDao.getSightingById(-1);
            fail("It is expected that a DaoException will be thrown");
            
        } catch (DaoException ex) {
            //It is expected that a DaoException will be thrown
        }
      
    }
    
    
    
    @Test
    public void testGetLocationsBySupeGoldenPath(){
        
        //Add test data to DB
        testDao.addTestDataToDB();
        
        //Get locations for test supe 1001
        List<Location> locationsForSupe = testDao.getLocationsBySupe(1001);
        
        //Verify that two locations are returned
        assertEquals(2, locationsForSupe.size());
        
        //Verify that the properties of the returned locations are correct
        assertEquals(3001, locationsForSupe.get(0).getId());
        assertEquals("Location 1 name", locationsForSupe.get(0).getName());
        assertEquals("Location 1 address", locationsForSupe.get(0).getAddress());
        assertEquals(new BigDecimal("60.684976"), locationsForSupe.get(0).getLatitude());
        assertEquals(new BigDecimal("17.153166"), locationsForSupe.get(0).getLongitude());
        
        assertEquals(3011, locationsForSupe.get(1).getId());
        assertEquals("Location 11 name", locationsForSupe.get(1).getName());
        assertEquals("Location 11 address", locationsForSupe.get(1).getAddress());
        assertEquals(new BigDecimal("35.071102"), locationsForSupe.get(1).getLatitude());
        assertEquals(new BigDecimal("115.573030"), locationsForSupe.get(1).getLongitude());
        
 
    }
    
    @Test
    public void testGetSightingsByDateGoldenPath(){
        
        //Add test data to DB
        testDao.addTestDataToDB();
        
        //Get sightings from test database for 7/25
        LocalDate sightingDate = LocalDate.parse("07/25/2019", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        List<Sighting> sightingsForDate = testDao.getSightingsByDate(sightingDate);
        
        //Verify that 1 sighting is returned
        assertEquals(1, sightingsForDate.size());
        
        //Verify that the properties of the sighting are correct
        assertEquals(17, sightingsForDate.get(0).getId());
        assertEquals(3017, sightingsForDate.get(0).getSightingLocation().getId());
        assertEquals(1007, sightingsForDate.get(0).getSightedSupe().getId());
    }
    
    @Test
    public void testGetOrganizationsBySupeGoldenPath(){
        
        //Add test data to DB
        testDao.addTestDataToDB();
        
        //Get from DB organizations that are associated with supe 1001
        List<Organization> orgsForSupe = testDao.getOrganizationsBySupe(1001);
        
        //Verify that the correct number of orgs are returned
        assertEquals(2, orgsForSupe.size());
        
        //Verify that the properties of the returned orgs are correct
        assertEquals(301, orgsForSupe.get(0).getId());
        assertEquals("Org 1 name", orgsForSupe.get(0).getName());
        assertEquals("Org 1 address", orgsForSupe.get(0).getAddress());
        assertEquals("Org 1 description", orgsForSupe.get(0).getDescription());
        
        assertEquals(303, orgsForSupe.get(1).getId());
        assertEquals("Org 3 name", orgsForSupe.get(1).getName());
        assertEquals("Org 3 address", orgsForSupe.get(1).getAddress());
        assertEquals("Org 3 description", orgsForSupe.get(1).getDescription());
        
    }
    
    @Test
    public void testGetSupesByLocationGoldenPath(){
        
        //Add test data to DB
        testDao.addTestDataToDB();
        
        //Get supes from DB that are associated with location 3001
        List<Supe> supesForLocation = testDao.getSupesByLocation(3001);
        
        //Verify that 1 supe is returned 
        assertEquals(1, supesForLocation.size());
        
        //Verify that the properties of the returned supe are correct
        assertEquals(1001, supesForLocation.get(0).getId());
        assertEquals("Super 1", supesForLocation.get(0).getName());
        assertEquals("Super 1 description", supesForLocation.get(0).getDescription());
        assertEquals(201, supesForLocation.get(0).getSupePower().getId()); 
        
    }
    
    @Test
    public void testUpdateSightingGoldenPath() {
        
        try {
            //A new sighting is created
            Sighting newSightingOne = new Sighting();
            newSightingOne.setDate("2019-10-14");
            
            ////Create new supe
            Supe newSupe = new Supe();
            newSupe.setName("Test Supe");
            newSupe.setDescription("Test Supe - Description");
            
            //////Create new power
            Power newPow = new Power();
            newPow.setName("Test Power");
            Power addedPower = testDao.addNewPower(newPow);
            
            //////Associate power with supe
            newSupe.setSupePower(addedPower);
            
            //////A new org is created
            Organization org1 = new Organization();
            org1.setName("Test Org 1");
            org1.setDescription("Test Org 1 - Description");
            org1.setAddress("Test Org 1 - Address");
            Organization addedOrg = testDao.addNewOrganization(org1);
            
            //////Associate org with supe
            List<Organization> orgsForSupe = new ArrayList<>();
            orgsForSupe.add(org1);
            newSupe.setOrgsForSupe(orgsForSupe);
            
            ////Add supe to DB
            Supe addedSupe = testDao.addNewSupe(newSupe);
            
            ////Associate supe with sighting
            newSightingOne.setSightedSupe(addedSupe);
            
            ////Create new location
            Location newLoc = new Location();
            newLoc.setName("New Loc");
            newLoc.setAddress("New Loc - Address");
            newLoc.setLatitude(new BigDecimal("60.684976"));
            newLoc.setLongitude(new BigDecimal("17.151234"));
            Location addedLoc = testDao.addNewLocation(newLoc);
            
            ////Associate location with sighting
            newSightingOne.setSightingLocation(addedLoc);
            
            //Sightings is added to the test DB
            Sighting addedSightingOne = testDao.addNewSighting(newSightingOne);
            
            //All sightings are retrieved from the test DB
            List<Sighting> allSightings = testDao.getAllSightings();
            
            //Verify that one sighting is returned
            assertEquals(1, allSightings.size());
            
            //Verify that the properties of the first sighting are correct
            assertEquals(newSightingOne.getId(),allSightings.get(0).getId());
            assertEquals("2019-10-14",allSightings.get(0).getDate());
            assertEquals(addedSupe.getId(), allSightings.get(0).getSightedSupe().getId());
            assertEquals(addedLoc.getId(), allSightings.get(0).getSightingLocation().getId());
            
            //Update the properties of the first sighting
            Sighting updatedSighting = new Sighting();
            updatedSighting.setId(addedSightingOne.getId());
            updatedSighting.setDate("2019-10-13");
            
            ////Create new supe
            Supe newSupeTwo = new Supe();
            newSupeTwo.setName("Test Supe 2");
            newSupeTwo.setDescription("Test Supe 2 - Description");
            
            //////Create new power
            Power newPowTwo = new Power();
            newPowTwo.setName("Test Power 2");
            Power addedPowerTwo = testDao.addNewPower(newPowTwo);
            
            //////Associate power with supe
            newSupeTwo.setSupePower(addedPowerTwo);
            
            //////A new org is created
            Organization org2 = new Organization();
            org2.setName("Test Org 2");
            org2.setDescription("Test Org 2 - Description");
            org2.setAddress("Test Org 2 - Address");
            Organization addedOrgTwo = testDao.addNewOrganization(org2);
            
            //////Associate org with supe
            List<Organization> orgsForSupeTwo = new ArrayList<>();
            orgsForSupeTwo.add(org2);
            newSupeTwo.setOrgsForSupe(orgsForSupeTwo);
            
            ////Add supe to DB
            Supe addedSupeTwo = testDao.addNewSupe(newSupeTwo);
            
            ////Associate supe with updated sighting
            updatedSighting.setSightedSupe(addedSupeTwo);
            
            ////Create new location
            Location newLocTwo = new Location();
            newLocTwo.setName("New Loc 2");
            newLocTwo.setAddress("New Loc 2 - Address");
            newLocTwo.setLatitude(new BigDecimal("61.684976"));
            newLocTwo.setLongitude(new BigDecimal("18.151234"));
            Location addedLocTwo = testDao.addNewLocation(newLocTwo);
            
            ////Associate location with updated sighting
            updatedSighting.setSightingLocation(addedLocTwo);
            
            //Updated sighting is added to DB
            testDao.updateSighting(updatedSighting);
            
            //Retrieve the updated sighting from the DB
            Sighting updatedSightingFromDB = testDao.getSightingById(newSightingOne.getId());
            
            //Verify that the properties of the updated sighting are correct
            assertEquals(newSightingOne.getId(), updatedSightingFromDB.getId());
            assertEquals("2019-10-13",updatedSightingFromDB.getDate());
            assertEquals(addedSupeTwo.getId(), updatedSightingFromDB.getSightedSupe().getId());
            assertEquals(addedLocTwo.getId(), updatedSightingFromDB.getSightingLocation().getId());
        } catch (DaoException ex) {
            fail("It is not expected that an exception will be thrown.");
        }
        
        
    }
    
    @Test
    public void testUpdateSightingNullSighting(){
        
        try {
            Sighting newSighting = null;
            testDao.updateSighting(newSighting);
            fail("It is expected that a DaoException will be thrown");
        } catch (DaoException ex) {
            //It is expected that a DaoException will be thrown
        }
        
    }
    
    @Test
    public void testDeleteSightingFromDBGoldenPath() {
        
        try {
            //A new sighting is created
            Sighting newSightingOne = new Sighting();
            newSightingOne.setDate("2019-10-14");
            
            ////Create new supe
            Supe newSupe = new Supe();
            newSupe.setName("Test Supe");
            newSupe.setDescription("Test Supe - Description");
            
            //////Create new power
            Power newPow = new Power();
            newPow.setName("Test Power");
            Power addedPower = testDao.addNewPower(newPow);
            
            //////Associate power with supe
            newSupe.setSupePower(addedPower);
            
            //////A new org is created
            Organization org1 = new Organization();
            org1.setName("Test Org 1");
            org1.setDescription("Test Org 1 - Description");
            org1.setAddress("Test Org 1 - Address");
            Organization addedOrg = testDao.addNewOrganization(org1);
            
            //////Associate org with supe
            List<Organization> orgsForSupe = new ArrayList<>();
            orgsForSupe.add(org1);
            newSupe.setOrgsForSupe(orgsForSupe);
            
            ////Add supe to DB
            Supe addedSupe = testDao.addNewSupe(newSupe);
            
            ////Associate supe with sighting
            newSightingOne.setSightedSupe(addedSupe);
            
            ////Create new location
            Location newLoc = new Location();
            newLoc.setName("New Loc");
            newLoc.setAddress("New Loc - Address");
            newLoc.setLatitude(new BigDecimal("60.684976"));
            newLoc.setLongitude(new BigDecimal("17.151234"));
            Location addedLoc = testDao.addNewLocation(newLoc);
            
            ////Associate location with sighting
            newSightingOne.setSightingLocation(addedLoc);
            
            //Sighting is added to the test DB
            Sighting addedSightingOne = testDao.addNewSighting(newSightingOne);
            
            //A second sighting is created
            Sighting newSightingTwo = new Sighting();
            newSightingTwo.setDate("2019-10-13");
            
            ////Create new supe
            Supe newSupeTwo = new Supe();
            newSupeTwo.setName("Test Supe 2");
            newSupeTwo.setDescription("Test Supe 2 - Description");
            
            //////Create new power
            Power newPowTwo = new Power();
            newPowTwo.setName("Test Power 2");
            Power addedPowerTwo = testDao.addNewPower(newPowTwo);
            
            //////Associate power with supe
            newSupeTwo.setSupePower(addedPowerTwo);
            
            //////A new org is created
            Organization org2 = new Organization();
            org2.setName("Test Org 2");
            org2.setDescription("Test Org 2 - Description");
            org2.setAddress("Test Org 2 - Address");
            Organization addedOrgTwo = testDao.addNewOrganization(org2);
            
            //////Associate org with supe
            List<Organization> orgsForSupeTwo = new ArrayList<>();
            orgsForSupeTwo.add(org2);
            newSupeTwo.setOrgsForSupe(orgsForSupeTwo);
            
            ////Add supe to DB
            Supe addedSupeTwo = testDao.addNewSupe(newSupeTwo);
            
            ////Associate supe with sighting
            newSightingTwo.setSightedSupe(addedSupeTwo);
            
            ////Create new location
            Location newLocTwo = new Location();
            newLocTwo.setName("New Loc 2");
            newLocTwo.setAddress("New Loc 2 - Address");
            newLocTwo.setLatitude(new BigDecimal("61.684976"));
            newLocTwo.setLongitude(new BigDecimal("18.151234"));
            Location addedLocTwo = testDao.addNewLocation(newLocTwo);
            
            ////Associate location with sighting
            newSightingTwo.setSightingLocation(addedLocTwo);
            
            //Sighting is added to the test DB
            Sighting addedSightingTwo = testDao.addNewSighting(newSightingTwo);
            
            //All sightings are retrieved from the test DB
            List<Sighting> allSightings = testDao.getAllSightings();
            
            //Verify that two sightings are returned
            assertEquals(2, allSightings.size());
            
            //Delete the first sighting
            testDao.deleteSightingFromDB(addedSightingOne.getId());
            
            //Retrieve all sightings from the test DB
            List<Sighting> allSightingsUpdated = testDao.getAllSightings();
            
            //Verify that one sighting is returned
            assertEquals(1, allSightingsUpdated.size());
            
            //Verify that the properties of the second sighting are correct
            assertEquals(addedSightingTwo.getId(), allSightingsUpdated.get(0).getId());
            assertEquals("2019-10-13",allSightingsUpdated.get(0).getDate());
            assertEquals(addedSupeTwo.getId(), allSightingsUpdated.get(0).getSightedSupe().getId());
            assertEquals(addedLocTwo.getId(), allSightingsUpdated.get(0).getSightingLocation().getId());
        } catch (DaoException ex) {
            fail("It is not expected that an exception will be thrown.");
        }
        
    }


}
