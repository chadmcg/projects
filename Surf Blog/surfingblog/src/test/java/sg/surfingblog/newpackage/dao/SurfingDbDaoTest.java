/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.surfingblog.newpackage.dao;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import sg.surfingblog.TestApplicationConfiguration;
import sg.surfingblog.newpackage.models.Beach;
import sg.surfingblog.newpackage.models.BeachComment;
import sg.surfingblog.newpackage.models.Break;
import sg.surfingblog.newpackage.models.BreakComment;
import sg.surfingblog.newpackage.models.News;
import sg.surfingblog.newpackage.models.Role;
import sg.surfingblog.newpackage.models.SiteUser;

/**
 *
 * @author blee0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
@Repository
@ActiveProfiles(profiles = "test")
public class SurfingDbDaoTest {

    @Autowired
    SurfingDao toTest;

    @Autowired
    UserDao userDao;

    public SurfingDbDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        //all tables deleted except user and roles 
        toTest.deleteAllTables();
        //userDao.deleteAllTables(); removed, adding new user was causing issues 
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getBreaksByBeach method, of class SurfingDbDao.
     */
    @Test
    public void testGetBreaksByBeachGoldenPath() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Break break1 = new Break();
            break1.setName("Break A");
            break1.setBeach(addedBeach);
            break1.setLatitude(new BigDecimal("20.716179"));
            break1.setLongitude(new BigDecimal("-158.214676"));
            break1.setBlog("test");
            Break addedBreak1 = toTest.addBreak(break1);

            Break break2 = new Break();
            break2.setName("Break B");
            break2.setBeach(addedBeach);
            break2.setLatitude(new BigDecimal("20.716185"));
            break2.setLongitude(new BigDecimal("-158.214684"));
            break2.setBlog("test2");
            Break addedBreak2 = toTest.addBreak(break2);

            Break break3 = new Break();
            break3.setName("Break C");
            break3.setBeach(addedBeach);
            break3.setLatitude(new BigDecimal("20.716634"));
            break3.setLongitude(new BigDecimal("-158.214965"));
            break3.setBlog("test3");
            Break addedBreak3 = toTest.addBreak(break3);

            List<Break> allBreaks = toTest.getBreaksByBeach(addedBeach.getId());

            assertEquals(3, allBreaks.size());

            Break firstBreak = allBreaks.get(0);
            assertEquals(addedBreak1.getId(), firstBreak.getId());
            assertEquals("Break A", firstBreak.getName());
            assertEquals(addedBeach.getId(), firstBreak.getBeach().getId());
            assertEquals(new BigDecimal("20.716179"), firstBreak.getLatitude());
            assertEquals(new BigDecimal("-158.214676"), firstBreak.getLongitude());
            assertEquals("test", firstBreak.getBlog());

            Break secondBreak = allBreaks.get(1);
            assertEquals(addedBreak2.getId(), secondBreak.getId());
            assertEquals("Break B", secondBreak.getName());
            assertEquals(addedBeach.getId(), secondBreak.getBeach().getId());
            assertEquals(new BigDecimal("20.716185"), secondBreak.getLatitude());
            assertEquals(new BigDecimal("-158.214684"), secondBreak.getLongitude());
            assertEquals("test2", secondBreak.getBlog());

            Break thirdBreak = allBreaks.get(2);
            assertEquals(addedBreak3.getId(), thirdBreak.getId());
            assertEquals("Break C", thirdBreak.getName());
            assertEquals(addedBeach.getId(), thirdBreak.getBeach().getId());
            assertEquals(new BigDecimal("20.716634"), thirdBreak.getLatitude());
            assertEquals(new BigDecimal("-158.214965"), thirdBreak.getLongitude());
            assertEquals("test3", thirdBreak.getBlog());

        } catch (SurfingDaoException | InvalidIdException ex) {
            fail();
        }

    }

    @Test
    public void testGetBreaksByInvalidBeach() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Break break1 = new Break();
            break1.setName("Break A");
            break1.setBeach(addedBeach);
            break1.setLatitude(new BigDecimal("20.716179"));
            break1.setLongitude(new BigDecimal("-158.214676"));
            break1.setBlog("test");
            Break addedBreak1 = toTest.addBreak(break1);

            Break break2 = new Break();
            break2.setName("Break B");
            break2.setBeach(addedBeach);
            break2.setLatitude(new BigDecimal("20.716185"));
            break2.setLongitude(new BigDecimal("-158.214684"));
            break2.setBlog("test2");
            Break addedBreak2 = toTest.addBreak(break2);

            Break break3 = new Break();
            break3.setName("Break C");
            break3.setBeach(addedBeach);
            break3.setLatitude(new BigDecimal("20.716634"));
            break3.setLongitude(new BigDecimal("-158.214965"));
            break3.setBlog("test3");
            Break addedBreak3 = toTest.addBreak(break3);
            List<Break> allBreaks = toTest.getBreaksByBeach(9382);

            fail();

        } catch (SurfingDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of getCommentsByBeach method, of class SurfingDbDao.
     */
    @Test
    public void testGetCommentsByBeachGoldenPath() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            BeachComment bc1 = new BeachComment();
            bc1.setUser(userDao.getUserById(2));
            bc1.setBeach(addedBeach);
            bc1.setCommentText("ea commodo consequat. Duis aute irure dolor in");
            BeachComment firstBC = toTest.addBeachComment(bc1);

            BeachComment bc2 = new BeachComment();
            bc2.setUser(userDao.getUserById(2));
            bc2.setBeach(addedBeach);
            bc2.setCommentText("vel illum qui");
            BeachComment secondBC = toTest.addBeachComment(bc2);

            BeachComment bc3 = new BeachComment();
            bc3.setUser(userDao.getUserById(2));
            bc3.setBeach(addedBeach);
            bc3.setCommentText("aute irure dolor in");
            BeachComment thirdBC = toTest.addBeachComment(bc3);

            List<BeachComment> allComments = toTest.getCommentsByBeach(addedBeach.getId());

            assertEquals(3, allComments.size());

            BeachComment firstComment = allComments.get(2);
            assertEquals(firstBC.getId(), firstComment.getId());
            assertEquals(2, firstComment.getUser().getId());
            assertEquals(addedBeach.getId(), firstComment.getBeach().getId());
            assertEquals("ea commodo consequat. Duis aute irure dolor in", firstComment.getCommentText());

            BeachComment secondComment = allComments.get(1);
            assertEquals(secondBC.getId(), secondComment.getId());
            assertEquals(2, secondComment.getUser().getId());
            assertEquals(addedBeach.getId(), secondComment.getBeach().getId());
            assertEquals("vel illum qui", secondComment.getCommentText());

            BeachComment thirdComment = allComments.get(0);
            assertEquals(thirdBC.getId(), thirdComment.getId());
            assertEquals(2, thirdComment.getUser().getId());
            assertEquals(addedBeach.getId(), thirdComment.getBeach().getId());
            assertEquals("aute irure dolor in", thirdComment.getCommentText());

        } catch (SurfingDaoException | InvalidIdException ex) {
            fail();
        }

    }

    @Test
    public void testGetCommentsByInvalidBeach() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            BeachComment bc1 = new BeachComment();
            bc1.setUser(userDao.getUserById(2));
            bc1.setBeach(addedBeach);
            bc1.setCommentText("ea commodo consequat. Duis aute irure dolor in");
            BeachComment firstBC = toTest.addBeachComment(bc1);

            BeachComment bc2 = new BeachComment();
            bc2.setUser(userDao.getUserById(2));
            bc2.setBeach(addedBeach);
            bc2.setCommentText("vel illum qui");
            BeachComment secondBC = toTest.addBeachComment(bc2);

            BeachComment bc3 = new BeachComment();
            bc3.setUser(userDao.getUserById(2));
            bc3.setBeach(addedBeach);
            bc3.setCommentText("aute irure dolor in");
            BeachComment thirdBC = toTest.addBeachComment(bc3);

            List<BeachComment> allComments = toTest.getCommentsByBeach(2433);

            fail();

        } catch (SurfingDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of getCommentsByBreak method, of class SurfingDbDao.
     */
    @Test
    public void testGetCommentsByBreakGoldenPath() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Break break1 = new Break();
            break1.setName("Break A");
            break1.setBeach(addedBeach);
            break1.setLatitude(new BigDecimal("20.716179"));
            break1.setLongitude(new BigDecimal("-158.214676"));
            break1.setBlog("test");
            Break addedBreak1 = toTest.addBreak(break1);

            BreakComment bc1 = new BreakComment();
            bc1.setUser(userDao.getUserById(2));
            bc1.setBeachBreak(addedBreak1);
            bc1.setCommentText("perspiciatis unde omnis iste natus error sit volupt");
            BreakComment addedBC1 = toTest.addBreakComment(bc1);

            BreakComment bc2 = new BreakComment();
            bc2.setUser(userDao.getUserById(2));
            bc2.setBeachBreak(addedBreak1);
            bc2.setCommentText("dolor sit amet, consectetu");
            BreakComment addedBC2 = toTest.addBreakComment(bc2);

            BreakComment bc3 = new BreakComment();
            bc3.setUser(userDao.getUserById(2));
            bc3.setBeachBreak(addedBreak1);
            bc3.setCommentText("commodi consequatur? Quis autem vel eum iure");
            BreakComment addedBC3 = toTest.addBreakComment(bc3);

            List<BreakComment> allComments = toTest.getCommentsByBreak(addedBreak1.getId());

            assertEquals(3, allComments.size());

            BreakComment firstComment = allComments.get(2);
            assertEquals(addedBC1.getId(), firstComment.getId());
            assertEquals(2, firstComment.getUser().getId());
            assertEquals(addedBreak1.getId(), firstComment.getBeachBreak().getId());
            assertEquals("perspiciatis unde omnis iste natus error sit volupt", firstComment.getCommentText());

            BreakComment secondComment = allComments.get(1);
            assertEquals(addedBC2.getId(), secondComment.getId());
            assertEquals(2, secondComment.getUser().getId());
            assertEquals(addedBreak1.getId(), secondComment.getBeachBreak().getId());
            assertEquals("dolor sit amet, consectetu", secondComment.getCommentText());

            BreakComment thirdComment = allComments.get(0);
            assertEquals(addedBC3.getId(), thirdComment.getId());
            assertEquals(2, thirdComment.getUser().getId());
            assertEquals(addedBreak1.getId(), thirdComment.getBeachBreak().getId());
            assertEquals("commodi consequatur? Quis autem vel eum iure", thirdComment.getCommentText());

        } catch (SurfingDaoException | InvalidIdException ex) {
            fail();
        }

    }

    @Test
    public void testGetCommentsByInvalidBreak() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Break break1 = new Break();
            break1.setName("Break A");
            break1.setBeach(addedBeach);
            break1.setLatitude(new BigDecimal("20.716179"));
            break1.setLongitude(new BigDecimal("-158.214676"));
            break1.setBlog("test");
            Break addedBreak1 = toTest.addBreak(break1);

            BreakComment bc1 = new BreakComment();
            bc1.setUser(userDao.getUserById(2));
            bc1.setBeachBreak(addedBreak1);
            bc1.setCommentText("perspiciatis unde omnis iste natus error sit volupt");
            BreakComment addedBC1 = toTest.addBreakComment(bc1);

            BreakComment bc2 = new BreakComment();
            bc2.setUser(userDao.getUserById(2));
            bc2.setBeachBreak(addedBreak1);
            bc2.setCommentText("dolor sit amet, consectetu");
            BreakComment addedBC2 = toTest.addBreakComment(bc2);

            BreakComment bc3 = new BreakComment();
            bc3.setUser(userDao.getUserById(2));
            bc3.setBeachBreak(addedBreak1);
            bc3.setCommentText("commodi consequatur? Quis autem vel eum iure");
            BreakComment addedBC3 = toTest.addBreakComment(bc3);

            List<BreakComment> allComments = toTest.getCommentsByBreak(4396);

            fail();

        } catch (SurfingDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of getAllActiveNews method, of class SurfingDbDao.
     */
    @Test
    public void testGetAllActiveNewsGoldenPath() {

        try {
            News headline1 = new News();
            headline1.setNewsURL("https://www.surfline.com/surf-news/2019-red-bull-queen-bay-forecast-outlook/66513");
            headline1.setNewsAbbrText("Disappointing surf forecast for Waimea Bay");
            headline1.setPicURL("https://d14fqx6aetz9ka.cloudfront.net/wp-content/uploads/2019/10/15172128/Screen-Shot-2019-10-15-at-7.56.53-PM.png");
            headline1.setIsActive(true);
            News newsAdded1 = toTest.addNews(headline1);

            News headline2 = new News();
            headline2.setNewsURL("https://www.surfertoday.com/surfing/aritz-aranburu-inducted-into-the-spanish-surfing-walk-of-fame");
            headline2.setNewsAbbrText("Congrats to Aritz Aranburu -- the newest member of the Spanish Surfing Walk of Fame");
            headline2.setPicURL("https://www.surfertoday.com/images/stories/aritzaranburu8.jpg");
            headline2.setIsActive(true);
            News newsAdded2 = toTest.addNews(headline2);

            News headline3 = new News();
            headline3.setNewsURL("https://www.surfertoday.com/surfing/jeremy-flores-wins-2019-quiksilver-pro-france");
            headline3.setNewsAbbrText("Flores and Moore win the 2019 Quiksilver and Roxy Pro France");
            headline3.setPicURL("https://www.surfertoday.com/images/stories/jeremyflores18.jpg");
            headline3.setIsActive(true);
            News newsAdded3 = toTest.addNews(headline3);

            News headline4 = new News();
            headline4.setNewsURL("https://www.surfertoday.com/surfing/kathmandu-buys-rip-curl");
            headline4.setNewsAbbrText("Ripcurl purchased by Kathmandu");
            headline4.setPicURL("https://www.surfertoday.com/images/stories/mickfanning67.jpg");
            headline4.setIsActive(false);
            News newsAdded4 = toTest.addNews(headline4);

            List<News> allActive = toTest.getAllActiveNews();

            assertEquals(3, allActive.size());

            News firstNews = allActive.get(0);
            assertEquals(newsAdded1.getId(), firstNews.getId());
            assertEquals("https://www.surfline.com/surf-news/2019-red-bull-queen-bay-forecast-outlook/66513", firstNews.getNewsURL());
            assertEquals("Disappointing surf forecast for Waimea Bay", firstNews.getNewsAbbrText());
            assertEquals("https://d14fqx6aetz9ka.cloudfront.net/wp-content/uploads/2019/10/15172128/Screen-Shot-2019-10-15-at-7.56.53-PM.png", firstNews.getPicURL());
            assertEquals(true, firstNews.getIsActive());

            News secondNews = allActive.get(1);
            assertEquals(newsAdded2.getId(), secondNews.getId());
            assertEquals("https://www.surfertoday.com/surfing/aritz-aranburu-inducted-into-the-spanish-surfing-walk-of-fame", secondNews.getNewsURL());
            assertEquals("Congrats to Aritz Aranburu -- the newest member of the Spanish Surfing Walk of Fame", secondNews.getNewsAbbrText());
            assertEquals("https://www.surfertoday.com/images/stories/aritzaranburu8.jpg", secondNews.getPicURL());
            assertEquals(true, secondNews.getIsActive());

            News thirdNews = allActive.get(2);
            assertEquals(newsAdded3.getId(), thirdNews.getId());
            assertEquals("https://www.surfertoday.com/surfing/jeremy-flores-wins-2019-quiksilver-pro-france", thirdNews.getNewsURL());
            assertEquals("Flores and Moore win the 2019 Quiksilver and Roxy Pro France", thirdNews.getNewsAbbrText());
            assertEquals("https://www.surfertoday.com/images/stories/jeremyflores18.jpg", thirdNews.getPicURL());
            assertEquals(true, thirdNews.getIsActive());

        } catch (SurfingDaoException ex) {
            fail();
        }
    }

    @Test
    public void testGetAllNewsGoldenPath() {

        try {
            News headline1 = new News();
            headline1.setNewsURL("https://www.surfline.com/surf-news/2019-red-bull-queen-bay-forecast-outlook/66513");
            headline1.setNewsAbbrText("Disappointing surf forecast for Waimea Bay");
            headline1.setPicURL("https://d14fqx6aetz9ka.cloudfront.net/wp-content/uploads/2019/10/15172128/Screen-Shot-2019-10-15-at-7.56.53-PM.png");
            headline1.setIsActive(true);
            News newsAdded1 = toTest.addNews(headline1);

            News headline2 = new News();
            headline2.setNewsURL("https://www.surfertoday.com/surfing/aritz-aranburu-inducted-into-the-spanish-surfing-walk-of-fame");
            headline2.setNewsAbbrText("Congrats to Aritz Aranburu -- the newest member of the Spanish Surfing Walk of Fame");
            headline2.setPicURL("https://www.surfertoday.com/images/stories/aritzaranburu8.jpg");
            headline2.setIsActive(true);
            News newsAdded2 = toTest.addNews(headline2);

            News headline3 = new News();
            headline3.setNewsURL("https://www.surfertoday.com/surfing/jeremy-flores-wins-2019-quiksilver-pro-france");
            headline3.setNewsAbbrText("Flores and Moore win the 2019 Quiksilver and Roxy Pro France");
            headline3.setPicURL("https://www.surfertoday.com/images/stories/jeremyflores18.jpg");
            headline3.setIsActive(true);
            News newsAdded3 = toTest.addNews(headline3);

            News headline4 = new News();
            headline4.setNewsURL("https://www.surfertoday.com/surfing/kathmandu-buys-rip-curl");
            headline4.setNewsAbbrText("Ripcurl purchased by Kathmandu");
            headline4.setPicURL("https://www.surfertoday.com/images/stories/mickfanning67.jpg");
            headline4.setIsActive(false);
            News newsAdded4 = toTest.addNews(headline4);

            List<News> allNews = toTest.getAllNews();

            assertEquals(4, allNews.size());

            News firstNews = allNews.get(0);
            assertEquals(newsAdded1.getId(), firstNews.getId());
            assertEquals("https://www.surfline.com/surf-news/2019-red-bull-queen-bay-forecast-outlook/66513", firstNews.getNewsURL());
            assertEquals("Disappointing surf forecast for Waimea Bay", firstNews.getNewsAbbrText());
            assertEquals("https://d14fqx6aetz9ka.cloudfront.net/wp-content/uploads/2019/10/15172128/Screen-Shot-2019-10-15-at-7.56.53-PM.png", firstNews.getPicURL());
            assertEquals(true, firstNews.getIsActive());

            News secondNews = allNews.get(1);
            assertEquals(newsAdded2.getId(), secondNews.getId());
            assertEquals("https://www.surfertoday.com/surfing/aritz-aranburu-inducted-into-the-spanish-surfing-walk-of-fame", secondNews.getNewsURL());
            assertEquals("Congrats to Aritz Aranburu -- the newest member of the Spanish Surfing Walk of Fame", secondNews.getNewsAbbrText());
            assertEquals("https://www.surfertoday.com/images/stories/aritzaranburu8.jpg", secondNews.getPicURL());
            assertEquals(true, secondNews.getIsActive());

            News thirdNews = allNews.get(2);
            assertEquals(newsAdded3.getId(), thirdNews.getId());
            assertEquals("https://www.surfertoday.com/surfing/jeremy-flores-wins-2019-quiksilver-pro-france", thirdNews.getNewsURL());
            assertEquals("Flores and Moore win the 2019 Quiksilver and Roxy Pro France", thirdNews.getNewsAbbrText());
            assertEquals("https://www.surfertoday.com/images/stories/jeremyflores18.jpg", thirdNews.getPicURL());
            assertEquals(true, thirdNews.getIsActive());

            News fourthNews = allNews.get(3);
            assertEquals(newsAdded4.getId(), fourthNews.getId());
            assertEquals("https://www.surfertoday.com/surfing/kathmandu-buys-rip-curl", fourthNews.getNewsURL());
            assertEquals("Ripcurl purchased by Kathmandu", fourthNews.getNewsAbbrText());
            assertEquals("https://www.surfertoday.com/images/stories/mickfanning67.jpg", fourthNews.getPicURL());
            assertEquals(false, fourthNews.getIsActive());

        } catch (SurfingDaoException ex) {
            fail();
        }

    }

    /**
     * Test of getNewsById method, of class SurfingDbDao.
     */
    @Test
    public void testGetNewsByIdGoldenPath() {

        try {
            News headline1 = new News();
            headline1.setNewsURL("https://www.surfline.com/surf-news/2019-red-bull-queen-bay-forecast-outlook/66513");
            headline1.setNewsAbbrText("Disappointing surf forecast for Waimea Bay");
            headline1.setPicURL("https://d14fqx6aetz9ka.cloudfront.net/wp-content/uploads/2019/10/15172128/Screen-Shot-2019-10-15-at-7.56.53-PM.png");
            headline1.setIsActive(true);
            News newsAdded1 = toTest.addNews(headline1);

            News headline2 = new News();
            headline2.setNewsURL("https://www.surfertoday.com/surfing/aritz-aranburu-inducted-into-the-spanish-surfing-walk-of-fame");
            headline2.setNewsAbbrText("Congrats to Aritz Aranburu -- the newest member of the Spanish Surfing Walk of Fame");
            headline2.setPicURL("https://www.surfertoday.com/images/stories/aritzaranburu8.jpg");
            headline2.setIsActive(true);
            News newsAdded2 = toTest.addNews(headline2);

            News toCheck1 = toTest.getNewsById(newsAdded1.getId());
            assertEquals(newsAdded1.getId(), toCheck1.getId());
            assertEquals("https://www.surfline.com/surf-news/2019-red-bull-queen-bay-forecast-outlook/66513", toCheck1.getNewsURL());
            assertEquals("Disappointing surf forecast for Waimea Bay", toCheck1.getNewsAbbrText());
            assertEquals("https://d14fqx6aetz9ka.cloudfront.net/wp-content/uploads/2019/10/15172128/Screen-Shot-2019-10-15-at-7.56.53-PM.png", toCheck1.getPicURL());
            assertEquals(true, toCheck1.getIsActive());

            News toCheck2 = toTest.getNewsById(newsAdded2.getId());
            assertEquals(newsAdded2.getId(), toCheck2.getId());
            assertEquals("https://www.surfertoday.com/surfing/aritz-aranburu-inducted-into-the-spanish-surfing-walk-of-fame", toCheck2.getNewsURL());
            assertEquals("Congrats to Aritz Aranburu -- the newest member of the Spanish Surfing Walk of Fame", toCheck2.getNewsAbbrText());
            assertEquals("https://www.surfertoday.com/images/stories/aritzaranburu8.jpg", toCheck2.getPicURL());
            assertEquals(true, toCheck2.getIsActive());

        } catch (SurfingDaoException | InvalidIdException ex) {
            fail();
        }
    }

    @Test
    public void testGetNewsByInvalidId() {

        try {
            News headline1 = new News();
            headline1.setNewsURL("https://www.surfline.com/surf-news/2019-red-bull-queen-bay-forecast-outlook/66513");
            headline1.setNewsAbbrText("Disappointing surf forecast for Waimea Bay");
            headline1.setPicURL("https://d14fqx6aetz9ka.cloudfront.net/wp-content/uploads/2019/10/15172128/Screen-Shot-2019-10-15-at-7.56.53-PM.png");
            headline1.setIsActive(true);
            News newsAdded1 = toTest.addNews(headline1);

            News headline2 = new News();
            headline2.setNewsURL("https://www.surfertoday.com/surfing/aritz-aranburu-inducted-into-the-spanish-surfing-walk-of-fame");
            headline2.setNewsAbbrText("Congrats to Aritz Aranburu -- the newest member of the Spanish Surfing Walk of Fame");
            headline2.setPicURL("https://www.surfertoday.com/images/stories/aritzaranburu8.jpg");
            headline2.setIsActive(true);
            News newsAdded2 = toTest.addNews(headline2);

            News toCheck1 = toTest.getNewsById(newsAdded1.getId());
            assertEquals(newsAdded1.getId(), toCheck1.getId());
            assertEquals("https://www.surfline.com/surf-news/2019-red-bull-queen-bay-forecast-outlook/66513", toCheck1.getNewsURL());
            assertEquals("Disappointing surf forecast for Waimea Bay", toCheck1.getNewsAbbrText());
            assertEquals("https://d14fqx6aetz9ka.cloudfront.net/wp-content/uploads/2019/10/15172128/Screen-Shot-2019-10-15-at-7.56.53-PM.png", toCheck1.getPicURL());
            assertEquals(true, toCheck1.getIsActive());

            News toCheck2 = toTest.getNewsById(2431);

            fail();

        } catch (SurfingDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of addNews method, of class SurfingDbDao.
     */
    @Test
    public void testAddNewsGoldenPath() {
        //tested above
    }

    @Test
    public void testAddNullNews() {

        try {
            News headline1 = null;
            News newsAdded1 = toTest.addNews(headline1);

            fail();

        } catch (SurfingDaoException ex) {
        }
    }

    /**
     * Test of updateNews method, of class SurfingDbDao.
     */
    @Test
    public void testUpdateNewsGoldenPath() {

        try {
            News headline1 = new News();
            headline1.setNewsURL("https://www.surfline.com/surf-news/2019-red-bull-queen-bay-forecast-outlook/66513");
            headline1.setNewsAbbrText("Disappointing surf forecast for Waimea Bay");
            headline1.setPicURL("https://d14fqx6aetz9ka.cloudfront.net/wp-content/uploads/2019/10/15172128/Screen-Shot-2019-10-15-at-7.56.53-PM.png");
            headline1.setIsActive(true);
            News newsAdded1 = toTest.addNews(headline1);

            News updatedNews = new News();
            updatedNews.setId(newsAdded1.getId());
            updatedNews.setNewsURL("https://www.surftoday.com/test");
            updatedNews.setNewsAbbrText("this is a test");
            updatedNews.setPicURL("https://www.surftoday.com/images/stories/test.png");
            updatedNews.setIsActive(false);
            toTest.updateNews(updatedNews);

            News toCheck = toTest.getNewsById(newsAdded1.getId());

            assertEquals(newsAdded1.getId(), toCheck.getId());
            assertEquals("https://www.surftoday.com/test", toCheck.getNewsURL());
            assertEquals("this is a test", toCheck.getNewsAbbrText());
            assertEquals("https://www.surftoday.com/images/stories/test.png", toCheck.getPicURL());
            assertEquals(false, toCheck.getIsActive());

        } catch (SurfingDaoException | InvalidIdException ex) {
            fail();
        }
    }

    @Test
    public void testUpdateNullNews() {

        try {
            News headline1 = new News();
            headline1.setNewsURL("https://www.surfline.com/surf-news/2019-red-bull-queen-bay-forecast-outlook/66513");
            headline1.setNewsAbbrText("Disappointing surf forecast for Waimea Bay");
            headline1.setPicURL("https://d14fqx6aetz9ka.cloudfront.net/wp-content/uploads/2019/10/15172128/Screen-Shot-2019-10-15-at-7.56.53-PM.png");
            headline1.setIsActive(true);
            News newsAdded1 = toTest.addNews(headline1);

            News updatedNews = null;
            toTest.updateNews(updatedNews);

            fail();

        } catch (InvalidIdException ex) {
            fail();
        } catch (SurfingDaoException ex) {
        }
    }

    @Test
    public void testUpdateNewsInvalidId() {

        try {
            News headline1 = new News();
            headline1.setNewsURL("https://www.surfline.com/surf-news/2019-red-bull-queen-bay-forecast-outlook/66513");
            headline1.setNewsAbbrText("Disappointing surf forecast for Waimea Bay");
            headline1.setPicURL("https://d14fqx6aetz9ka.cloudfront.net/wp-content/uploads/2019/10/15172128/Screen-Shot-2019-10-15-at-7.56.53-PM.png");
            headline1.setIsActive(true);
            News newsAdded1 = toTest.addNews(headline1);

            News updatedNews = new News();
            updatedNews.setId(3489);
            updatedNews.setNewsURL("https://www.surftoday.com/test");
            updatedNews.setNewsAbbrText("this is a test");
            updatedNews.setPicURL("https://www.surftoday.com/images/stories/test.png");
            updatedNews.setIsActive(false);
            toTest.updateNews(updatedNews);

            fail();

        } catch (SurfingDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of deleteNews method, of class SurfingDbDao.
     */
    @Test
    public void testDeleteNewsGoldenPath() {

        try {
            News headline1 = new News();
            headline1.setNewsURL("https://www.surfline.com/surf-news/2019-red-bull-queen-bay-forecast-outlook/66513");
            headline1.setNewsAbbrText("Disappointing surf forecast for Waimea Bay");
            headline1.setPicURL("https://d14fqx6aetz9ka.cloudfront.net/wp-content/uploads/2019/10/15172128/Screen-Shot-2019-10-15-at-7.56.53-PM.png");
            headline1.setIsActive(true);
            News newsAdded1 = toTest.addNews(headline1);

            News headline2 = new News();
            headline2.setNewsURL("https://www.surfertoday.com/surfing/aritz-aranburu-inducted-into-the-spanish-surfing-walk-of-fame");
            headline2.setNewsAbbrText("Congrats to Aritz Aranburu -- the newest member of the Spanish Surfing Walk of Fame");
            headline2.setPicURL("https://www.surfertoday.com/images/stories/aritzaranburu8.jpg");
            headline2.setIsActive(true);
            News newsAdded2 = toTest.addNews(headline2);

            News headline3 = new News();
            headline3.setNewsURL("https://www.surfertoday.com/surfing/jeremy-flores-wins-2019-quiksilver-pro-france");
            headline3.setNewsAbbrText("Flores and Moore win the 2019 Quiksilver and Roxy Pro France");
            headline3.setPicURL("https://www.surfertoday.com/images/stories/jeremyflores18.jpg");
            headline3.setIsActive(true);
            News newsAdded3 = toTest.addNews(headline3);

            News headline4 = new News();
            headline4.setNewsURL("https://www.surfertoday.com/surfing/kathmandu-buys-rip-curl");
            headline4.setNewsAbbrText("Ripcurl purchased by Kathmandu");
            headline4.setPicURL("https://www.surfertoday.com/images/stories/mickfanning67.jpg");
            headline4.setIsActive(false);
            News newsAdded4 = toTest.addNews(headline4);

            toTest.deleteNews(newsAdded2.getId());

            List<News> allNews = toTest.getAllNews();

            assertEquals(3, allNews.size());

            News firstNews = allNews.get(0);
            assertEquals(newsAdded1.getId(), firstNews.getId());
            assertEquals("https://www.surfline.com/surf-news/2019-red-bull-queen-bay-forecast-outlook/66513", firstNews.getNewsURL());
            assertEquals("Disappointing surf forecast for Waimea Bay", firstNews.getNewsAbbrText());
            assertEquals("https://d14fqx6aetz9ka.cloudfront.net/wp-content/uploads/2019/10/15172128/Screen-Shot-2019-10-15-at-7.56.53-PM.png", firstNews.getPicURL());
            assertEquals(true, firstNews.getIsActive());

            News thirdNews = allNews.get(1);
            assertEquals(newsAdded3.getId(), thirdNews.getId());
            assertEquals("https://www.surfertoday.com/surfing/jeremy-flores-wins-2019-quiksilver-pro-france", thirdNews.getNewsURL());
            assertEquals("Flores and Moore win the 2019 Quiksilver and Roxy Pro France", thirdNews.getNewsAbbrText());
            assertEquals("https://www.surfertoday.com/images/stories/jeremyflores18.jpg", thirdNews.getPicURL());
            assertEquals(true, thirdNews.getIsActive());

            News fourthNews = allNews.get(2);
            assertEquals(newsAdded4.getId(), fourthNews.getId());
            assertEquals("https://www.surfertoday.com/surfing/kathmandu-buys-rip-curl", fourthNews.getNewsURL());
            assertEquals("Ripcurl purchased by Kathmandu", fourthNews.getNewsAbbrText());
            assertEquals("https://www.surfertoday.com/images/stories/mickfanning67.jpg", fourthNews.getPicURL());
            assertEquals(false, fourthNews.getIsActive());

        } catch (SurfingDaoException | InvalidIdException ex) {
            fail();
        }
    }

    @Test
    public void testDeleteNewsInvalidId() {

        try {
            News headline1 = new News();
            headline1.setNewsURL("https://www.surfline.com/surf-news/2019-red-bull-queen-bay-forecast-outlook/66513");
            headline1.setNewsAbbrText("Disappointing surf forecast for Waimea Bay");
            headline1.setPicURL("https://d14fqx6aetz9ka.cloudfront.net/wp-content/uploads/2019/10/15172128/Screen-Shot-2019-10-15-at-7.56.53-PM.png");
            headline1.setIsActive(true);
            News newsAdded1 = toTest.addNews(headline1);

            News headline2 = new News();
            headline2.setNewsURL("https://www.surfertoday.com/surfing/aritz-aranburu-inducted-into-the-spanish-surfing-walk-of-fame");
            headline2.setNewsAbbrText("Congrats to Aritz Aranburu -- the newest member of the Spanish Surfing Walk of Fame");
            headline2.setPicURL("https://www.surfertoday.com/images/stories/aritzaranburu8.jpg");
            headline2.setIsActive(true);
            News newsAdded2 = toTest.addNews(headline2);

            News headline3 = new News();
            headline3.setNewsURL("https://www.surfertoday.com/surfing/jeremy-flores-wins-2019-quiksilver-pro-france");
            headline3.setNewsAbbrText("Flores and Moore win the 2019 Quiksilver and Roxy Pro France");
            headline3.setPicURL("https://www.surfertoday.com/images/stories/jeremyflores18.jpg");
            headline3.setIsActive(true);
            News newsAdded3 = toTest.addNews(headline3);

            News headline4 = new News();
            headline4.setNewsURL("https://www.surfertoday.com/surfing/kathmandu-buys-rip-curl");
            headline4.setNewsAbbrText("Ripcurl purchased by Kathmandu");
            headline4.setPicURL("https://www.surfertoday.com/images/stories/mickfanning67.jpg");
            headline4.setIsActive(false);
            News newsAdded4 = toTest.addNews(headline4);

            toTest.deleteNews(4592);

            fail();

        } catch (SurfingDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of getAllBeaches method, of class SurfingDbDao.
     */
    @Test
    public void testGetAllBeachesGoldenPath() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Beach beach2 = new Beach();
            beach2.setName("Beach B");
            beach2.setZipCode(94388);
            Beach addedBeach2 = toTest.addBeach(beach2);

            Beach beach3 = new Beach();
            beach3.setName("Beach C");
            beach3.setZipCode(92755);
            Beach addedBeach3 = toTest.addBeach(beach3);

            List<Beach> allBeaches = toTest.getAllBeaches();

            assertEquals(3, allBeaches.size());

            Beach firstBeach = allBeaches.get(0);
            assertEquals(addedBeach.getId(), firstBeach.getId());
            assertEquals("Beach A", firstBeach.getName());
            assertEquals(96701, firstBeach.getZipCode());

            Beach secondBeach = allBeaches.get(1);
            assertEquals(addedBeach2.getId(), secondBeach.getId());
            assertEquals("Beach B", secondBeach.getName());
            assertEquals(94388, secondBeach.getZipCode());

            Beach thirdBeach = allBeaches.get(2);
            assertEquals(addedBeach3.getId(), thirdBeach.getId());
            assertEquals("Beach C", thirdBeach.getName());
            assertEquals(92755, thirdBeach.getZipCode());

        } catch (SurfingDaoException ex) {
            fail();
        }

    }

    /**
     * Test of getBeachById method, of class SurfingDbDao.
     */
    @Test
    public void testGetBeachByIdGoldenPath() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Beach beach2 = new Beach();
            beach2.setName("Beach B");
            beach2.setZipCode(94388);
            Beach addedBeach2 = toTest.addBeach(beach2);

            Beach beach3 = new Beach();
            beach3.setName("Beach C");
            beach3.setZipCode(92755);
            Beach addedBeach3 = toTest.addBeach(beach3);

            Beach firstBeach = toTest.getBeachById(addedBeach.getId());
            assertEquals(addedBeach.getId(), firstBeach.getId());
            assertEquals("Beach A", firstBeach.getName());
            assertEquals(96701, firstBeach.getZipCode());

            Beach thirdBeach = toTest.getBeachById(addedBeach3.getId());
            assertEquals(addedBeach3.getId(), thirdBeach.getId());
            assertEquals("Beach C", thirdBeach.getName());
            assertEquals(92755, thirdBeach.getZipCode());

        } catch (SurfingDaoException | InvalidIdException ex) {
            fail();
        }
    }

    @Test
    public void testGetBeachByInvalidId() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Beach beach2 = new Beach();
            beach2.setName("Beach B");
            beach2.setZipCode(94388);
            Beach addedBeach2 = toTest.addBeach(beach2);

            Beach beach3 = new Beach();
            beach3.setName("Beach C");
            beach3.setZipCode(92755);
            Beach addedBeach3 = toTest.addBeach(beach3);

            Beach firstBeach = toTest.getBeachById(1887);

            fail();

        } catch (SurfingDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of addBeach method, of class SurfingDbDao.
     */
    @Test
    public void testAddBeachGoldenPath() {
        //test above
    }

    @Test
    public void testAddNullBeach() {

        try {
            Beach beach1 = null;
            Beach addedBeach = toTest.addBeach(beach1);

            fail();

        } catch (SurfingDaoException ex) {
        }
    }

    /**
     * Test of updateBeach method, of class SurfingDbDao.
     */
    @Test
    public void testUpdateBeachGoldenPath() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Beach updatedBeach = new Beach();
            updatedBeach.setId(addedBeach.getId());
            updatedBeach.setName("Zoo Beach");
            updatedBeach.setZipCode(93372);
            toTest.updateBeach(updatedBeach);

            Beach toCheck = toTest.getBeachById(addedBeach.getId());

            assertEquals(addedBeach.getId(), toCheck.getId());
            assertEquals("Zoo Beach", toCheck.getName());
            assertEquals(93372, toCheck.getZipCode());

        } catch (SurfingDaoException | InvalidIdException ex) {
            fail();
        }
    }

    @Test
    public void testUpdateNullBeach() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Beach updatedBeach = null;
            toTest.updateBeach(updatedBeach);

            fail();

        } catch (InvalidIdException ex) {
            fail();
        } catch (SurfingDaoException ex) {
        }
    }

    @Test
    public void testUpdateBeachInvalidId() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Beach updatedBeach = new Beach();
            updatedBeach.setId(6744);
            updatedBeach.setName("Zoo Beach");
            updatedBeach.setZipCode(93372);
            toTest.updateBeach(updatedBeach);

            fail();

        } catch (SurfingDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of deleteBeach method, of class SurfingDbDao.
     */
    @Test
    public void testDeleteBeachGoldenPath() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Beach beach2 = new Beach();
            beach2.setName("Beach B");
            beach2.setZipCode(94388);
            Beach addedBeach2 = toTest.addBeach(beach2);

            Beach beach3 = new Beach();
            beach3.setName("Beach C");
            beach3.setZipCode(92755);
            Beach addedBeach3 = toTest.addBeach(beach3);

            toTest.deleteBeach(addedBeach.getId());

            List<Beach> allBeaches = toTest.getAllBeaches();

            assertEquals(2, allBeaches.size());

            Beach secondBeach = allBeaches.get(0);
            assertEquals(addedBeach2.getId(), secondBeach.getId());
            assertEquals("Beach B", secondBeach.getName());
            assertEquals(94388, secondBeach.getZipCode());

            Beach thirdBeach = allBeaches.get(1);
            assertEquals(addedBeach3.getId(), thirdBeach.getId());
            assertEquals("Beach C", thirdBeach.getName());
            assertEquals(92755, thirdBeach.getZipCode());

        } catch (SurfingDaoException | InvalidIdException ex) {
            fail();
        }
    }

    @Test
    public void testDeleteBeachInvalidId() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Beach beach2 = new Beach();
            beach2.setName("Beach B");
            beach2.setZipCode(94388);
            Beach addedBeach2 = toTest.addBeach(beach2);

            Beach beach3 = new Beach();
            beach3.setName("Beach C");
            beach3.setZipCode(92755);
            Beach addedBeach3 = toTest.addBeach(beach3);

            toTest.deleteBeach(3382);

            fail();

        } catch (SurfingDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of getAllBreaks method, of class SurfingDbDao.
     */
    @Test
    public void testGetAllBreaksGoldenPath() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Break break1 = new Break();
            break1.setName("Break A");
            break1.setBeach(addedBeach);
            break1.setLatitude(new BigDecimal("20.716179"));
            break1.setLongitude(new BigDecimal("-158.214676"));
            break1.setBlog("test");
            Break addedBreak1 = toTest.addBreak(break1);

            Break break2 = new Break();
            break2.setName("Break B");
            break2.setBeach(addedBeach);
            break2.setLatitude(new BigDecimal("20.716185"));
            break2.setLongitude(new BigDecimal("-158.214684"));
            break2.setBlog("test2");
            Break addedBreak2 = toTest.addBreak(break2);

            Break break3 = new Break();
            break3.setName("Break C");
            break3.setBeach(addedBeach);
            break3.setLatitude(new BigDecimal("20.716634"));
            break3.setLongitude(new BigDecimal("-158.214965"));
            break3.setBlog("test3");
            Break addedBreak3 = toTest.addBreak(break3);

            List<Break> allBreaks = toTest.getAllBreaks();

            assertEquals(3, allBreaks.size());

            Break firstBreak = allBreaks.get(0);
            assertEquals(addedBreak1.getId(), firstBreak.getId());
            assertEquals("Break A", firstBreak.getName());
            assertEquals(addedBeach.getId(), firstBreak.getBeach().getId());
            assertEquals(new BigDecimal("20.716179"), firstBreak.getLatitude());
            assertEquals(new BigDecimal("-158.214676"), firstBreak.getLongitude());
            assertEquals("test", firstBreak.getBlog());

            Break secondBreak = allBreaks.get(1);
            assertEquals(addedBreak2.getId(), secondBreak.getId());
            assertEquals("Break B", secondBreak.getName());
            assertEquals(addedBeach.getId(), secondBreak.getBeach().getId());
            assertEquals(new BigDecimal("20.716185"), secondBreak.getLatitude());
            assertEquals(new BigDecimal("-158.214684"), secondBreak.getLongitude());
            assertEquals("test2", secondBreak.getBlog());

            Break thirdBreak = allBreaks.get(2);
            assertEquals(addedBreak3.getId(), thirdBreak.getId());
            assertEquals("Break C", thirdBreak.getName());
            assertEquals(addedBeach.getId(), thirdBreak.getBeach().getId());
            assertEquals(new BigDecimal("20.716634"), thirdBreak.getLatitude());
            assertEquals(new BigDecimal("-158.214965"), thirdBreak.getLongitude());
            assertEquals("test3", thirdBreak.getBlog());

        } catch (SurfingDaoException | InvalidIdException ex) {
            fail();
        }

    }

    /**
     * Test of getBreakById method, of class SurfingDbDao.
     */
    @Test
    public void testGetBreakByIdGoldenPath() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Break break1 = new Break();
            break1.setName("Break A");
            break1.setBeach(addedBeach);
            break1.setLatitude(new BigDecimal("20.716179"));
            break1.setLongitude(new BigDecimal("-158.214676"));
            break1.setBlog("test");
            Break addedBreak1 = toTest.addBreak(break1);

            Break break2 = new Break();
            break2.setName("Break B");
            break2.setBeach(addedBeach);
            break2.setLatitude(new BigDecimal("20.716185"));
            break2.setLongitude(new BigDecimal("-158.214684"));
            break2.setBlog("test2");
            Break addedBreak2 = toTest.addBreak(break2);

            Break toCheck = toTest.getBreakById(addedBreak2.getId());
            assertEquals(addedBreak2.getId(), toCheck.getId());
            assertEquals("Break B", toCheck.getName());
            assertEquals(addedBeach.getId(), toCheck.getBeach().getId());
            assertEquals(new BigDecimal("20.716185"), toCheck.getLatitude());
            assertEquals(new BigDecimal("-158.214684"), toCheck.getLongitude());
            assertEquals("test2", toCheck.getBlog());

        } catch (SurfingDaoException | InvalidIdException ex) {
            fail();
        }
    }

    @Test
    public void testGetBreakByInvalidId() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Break break1 = new Break();
            break1.setName("Break A");
            break1.setBeach(addedBeach);
            break1.setLatitude(new BigDecimal("20.716179"));
            break1.setLongitude(new BigDecimal("-158.214676"));
            break1.setBlog("test");
            Break addedBreak1 = toTest.addBreak(break1);

            Break break2 = new Break();
            break2.setName("Break B");
            break2.setBeach(addedBeach);
            break2.setLatitude(new BigDecimal("20.716185"));
            break2.setLongitude(new BigDecimal("-158.214684"));
            break2.setBlog("test2");
            Break addedBreak2 = toTest.addBreak(break2);

            Break toCheck = toTest.getBreakById(5331);

            fail();

        } catch (SurfingDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of addBreak method, of class SurfingDbDao.
     */
    @Test
    public void testAddBreakGoldenPath() {
        //tested above
    }

    @Test
    public void testAddNullBreak() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Break break1 = null;
            Break addedBreak1 = toTest.addBreak(break1);

            fail();

        } catch (InvalidIdException ex) {
            fail();
        } catch (SurfingDaoException ex) {
        }
    }

    @Test
    public void testAddBreakNullBeach() {

        try {
            Beach beach1 = null;

            Break break1 = new Break();
            break1.setName("Break A");
            break1.setBeach(beach1);
            break1.setLatitude(new BigDecimal("20.716179"));
            break1.setLongitude(new BigDecimal("-158.214676"));
            break1.setBlog("test");
            Break addedBreak1 = toTest.addBreak(break1);

            fail();

        } catch (InvalidIdException ex) {
            fail();
        } catch (SurfingDaoException ex) {
        }
    }

    @Test
    public void testAddBreakInvalidBeachId() {

        try {
            Beach beach1 = new Beach();
            beach1.setId(7334);
            beach1.setName("Beach A");
            beach1.setZipCode(96701);

            Break break1 = new Break();
            break1.setName("Break A");
            break1.setBeach(beach1);
            break1.setLatitude(new BigDecimal("20.716179"));
            break1.setLongitude(new BigDecimal("-158.214676"));
            break1.setBlog("test");
            Break addedBreak1 = toTest.addBreak(break1);

            fail();

        } catch (SurfingDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of updateBreak method, of class SurfingDbDao.
     */
    @Test
    public void testUpdateBreakGoldenPath() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Break break1 = new Break();
            break1.setName("Break A");
            break1.setBeach(addedBeach);
            break1.setLatitude(new BigDecimal("20.716179"));
            break1.setLongitude(new BigDecimal("-158.214676"));
            break1.setBlog("test");
            Break addedBreak1 = toTest.addBreak(break1);

            Break updatedBreak = new Break();
            updatedBreak.setId(addedBreak1.getId());
            updatedBreak.setName("Break Break");
            updatedBreak.setBeach(addedBeach);
            updatedBreak.setLatitude(new BigDecimal("20.735678"));
            updatedBreak.setLongitude(new BigDecimal("-158.214583"));
            updatedBreak.setBlog("test3");
            toTest.updateBreak(updatedBreak);

            Break toCheck = toTest.getBreakById(addedBreak1.getId());
            assertEquals(addedBreak1.getId(), toCheck.getId());
            assertEquals("Break Break", toCheck.getName());
            assertEquals(addedBeach.getId(), toCheck.getBeach().getId());
            assertEquals(new BigDecimal("20.735678"), toCheck.getLatitude());
            assertEquals(new BigDecimal("-158.214583"), toCheck.getLongitude());
            assertEquals("test3", toCheck.getBlog());

        } catch (SurfingDaoException | InvalidIdException ex) {
            fail();
        }
    }

    @Test
    public void testUpdateNullBreak() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Break break1 = new Break();
            break1.setName("Break A");
            break1.setBeach(addedBeach);
            break1.setLatitude(new BigDecimal("20.716179"));
            break1.setLongitude(new BigDecimal("-158.214676"));
            break1.setBlog("test");
            Break addedBreak1 = toTest.addBreak(break1);

            Break updatedBreak = null;
            toTest.updateBreak(updatedBreak);

            fail();

        } catch (InvalidIdException ex) {
            fail();
        } catch (SurfingDaoException ex) {
        }
    }

    @Test
    public void testUpdateBreakNullBeach() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Break break1 = new Break();
            break1.setName("Break A");
            break1.setBeach(addedBeach);
            break1.setLatitude(new BigDecimal("20.716179"));
            break1.setLongitude(new BigDecimal("-158.214676"));
            break1.setBlog("test");
            Break addedBreak1 = toTest.addBreak(break1);

            Beach nullBeach = null;

            Break updatedBreak = new Break();
            updatedBreak.setId(addedBreak1.getId());
            updatedBreak.setName("Break Break");
            updatedBreak.setBeach(nullBeach);
            updatedBreak.setLatitude(new BigDecimal("20.735678"));
            updatedBreak.setLongitude(new BigDecimal("-158.214583"));
            updatedBreak.setBlog("test");
            toTest.updateBreak(updatedBreak);

            fail();

        } catch (InvalidIdException ex) {
            fail();
        } catch (SurfingDaoException ex) {
        }
    }

    @Test
    public void testUpdateBreakInvalidBeachId() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Break break1 = new Break();
            break1.setName("Break A");
            break1.setBeach(addedBeach);
            break1.setLatitude(new BigDecimal("20.716179"));
            break1.setLongitude(new BigDecimal("-158.214676"));
            break1.setBlog("test");
            Break addedBreak1 = toTest.addBreak(break1);

            Beach invalidBeach = new Beach();
            invalidBeach.setId(3372);
            invalidBeach.setName("fake beach");
            invalidBeach.setZipCode(51522);

            Break updatedBreak = new Break();
            updatedBreak.setId(addedBreak1.getId());
            updatedBreak.setName("Break Break");
            updatedBreak.setBeach(invalidBeach);
            updatedBreak.setLatitude(new BigDecimal("20.735678"));
            updatedBreak.setLongitude(new BigDecimal("-158.214583"));
            updatedBreak.setBlog("test");
            toTest.updateBreak(updatedBreak);

            fail();

        } catch (SurfingDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of deleteBreak method, of class SurfingDbDao.
     */
    @Test
    public void testDeleteBreakGoldenPath() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Break break1 = new Break();
            break1.setName("Break A");
            break1.setBeach(addedBeach);
            break1.setLatitude(new BigDecimal("20.716179"));
            break1.setLongitude(new BigDecimal("-158.214676"));
            break1.setBlog("test");
            Break addedBreak1 = toTest.addBreak(break1);

            Break break2 = new Break();
            break2.setName("Break B");
            break2.setBeach(addedBeach);
            break2.setLatitude(new BigDecimal("20.716185"));
            break2.setLongitude(new BigDecimal("-158.214684"));
            break2.setBlog("test2");
            Break addedBreak2 = toTest.addBreak(break2);

            Break break3 = new Break();
            break3.setName("Break C");
            break3.setBeach(addedBeach);
            break3.setLatitude(new BigDecimal("20.716634"));
            break3.setLongitude(new BigDecimal("-158.214965"));
            break3.setBlog("test3");
            Break addedBreak3 = toTest.addBreak(break3);

            toTest.deleteBreak(addedBreak3.getId());

            List<Break> allBreaks = toTest.getAllBreaks();

            assertEquals(2, allBreaks.size());

            Break firstBreak = allBreaks.get(0);
            assertEquals(addedBreak1.getId(), firstBreak.getId());
            assertEquals("Break A", firstBreak.getName());
            assertEquals(addedBeach.getId(), firstBreak.getBeach().getId());
            assertEquals(new BigDecimal("20.716179"), firstBreak.getLatitude());
            assertEquals(new BigDecimal("-158.214676"), firstBreak.getLongitude());
            assertEquals("test", firstBreak.getBlog());

            Break secondBreak = allBreaks.get(1);
            assertEquals(addedBreak2.getId(), secondBreak.getId());
            assertEquals("Break B", secondBreak.getName());
            assertEquals(addedBeach.getId(), secondBreak.getBeach().getId());
            assertEquals(new BigDecimal("20.716185"), secondBreak.getLatitude());
            assertEquals(new BigDecimal("-158.214684"), secondBreak.getLongitude());
            assertEquals("test2", secondBreak.getBlog());

        } catch (SurfingDaoException | InvalidIdException ex) {
            fail();
        }
    }

    @Test
    public void testDeleteBreakInvalidId() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Break break1 = new Break();
            break1.setName("Break A");
            break1.setBeach(addedBeach);
            break1.setLatitude(new BigDecimal("20.716179"));
            break1.setLongitude(new BigDecimal("-158.214676"));
            break1.setBlog("test");
            Break addedBreak1 = toTest.addBreak(break1);

            Break break2 = new Break();
            break2.setName("Break B");
            break2.setBeach(addedBeach);
            break2.setLatitude(new BigDecimal("20.716185"));
            break2.setLongitude(new BigDecimal("-158.214684"));
            break2.setBlog("test2");
            Break addedBreak2 = toTest.addBreak(break2);

            Break break3 = new Break();
            break3.setName("Break C");
            break3.setBeach(addedBeach);
            break3.setLatitude(new BigDecimal("20.716634"));
            break3.setLongitude(new BigDecimal("-158.214965"));
            break3.setBlog("test3");
            Break addedBreak3 = toTest.addBreak(break3);

            toTest.deleteBreak(2988);

            fail();

        } catch (SurfingDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of getAllBeachComments method, of class SurfingDbDao.
     */
    @Test
    public void testGetAllBeachCommentsGoldenPath() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Beach beach2 = new Beach();
            beach2.setName("Beach B");
            beach2.setZipCode(98420);
            Beach addedBeach2 = toTest.addBeach(beach2);

            BeachComment bc1 = new BeachComment();
            bc1.setUser(userDao.getUserById(2));
            bc1.setBeach(addedBeach);
            bc1.setCommentText("ea commodo consequat. Duis aute irure dolor in");
            BeachComment firstBC = toTest.addBeachComment(bc1);

            BeachComment bc2 = new BeachComment();
            bc2.setUser(userDao.getUserById(2));
            bc2.setBeach(addedBeach);
            bc2.setCommentText("vel illum qui");
            BeachComment secondBC = toTest.addBeachComment(bc2);

            BeachComment bc3 = new BeachComment();
            bc3.setUser(userDao.getUserById(2));
            bc3.setBeach(addedBeach2);
            bc3.setCommentText("aute irure dolor in");
            BeachComment thirdBC = toTest.addBeachComment(bc3);

            List<BeachComment> allComments = toTest.getAllBeachComments();

            assertEquals(3, allComments.size());

            BeachComment firstComment = allComments.get(0);
            assertEquals(firstBC.getId(), firstComment.getId());
            assertEquals(2, firstComment.getUser().getId());
            assertEquals(addedBeach.getId(), firstComment.getBeach().getId());
            assertEquals("ea commodo consequat. Duis aute irure dolor in", firstComment.getCommentText());

            BeachComment thirdComment = allComments.get(2);
            assertEquals(thirdBC.getId(), thirdComment.getId());
            assertEquals(2, thirdComment.getUser().getId());
            assertEquals(addedBeach2.getId(), thirdComment.getBeach().getId());
            assertEquals("aute irure dolor in", thirdComment.getCommentText());

        } catch (SurfingDaoException | InvalidIdException ex) {
            fail();
        }
    }

    /**
     * Test of getBeachCommentById method, of class SurfingDbDao.
     */
    @Test
    public void testGetBeachCommentByIdGoldenPath() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Beach beach2 = new Beach();
            beach2.setName("Beach B");
            beach2.setZipCode(98420);
            Beach addedBeach2 = toTest.addBeach(beach2);

            BeachComment bc1 = new BeachComment();
            bc1.setUser(userDao.getUserById(2));
            bc1.setBeach(addedBeach);
            bc1.setCommentText("ea commodo consequat. Duis aute irure dolor in");
            BeachComment firstBC = toTest.addBeachComment(bc1);

            BeachComment bc2 = new BeachComment();
            bc2.setUser(userDao.getUserById(2));
            bc2.setBeach(addedBeach);
            bc2.setCommentText("vel illum qui");
            BeachComment secondBC = toTest.addBeachComment(bc2);

            BeachComment bc3 = new BeachComment();
            bc3.setUser(userDao.getUserById(2));
            bc3.setBeach(addedBeach2);
            bc3.setCommentText("aute irure dolor in");
            BeachComment thirdBC = toTest.addBeachComment(bc3);

            BeachComment toCheck = toTest.getBeachCommentById(secondBC.getId());
            assertEquals(secondBC.getId(), toCheck.getId());
            assertEquals(2, toCheck.getUser().getId());
            assertEquals(addedBeach.getId(), toCheck.getBeach().getId());
            assertEquals("vel illum qui", toCheck.getCommentText());

        } catch (InvalidIdException | SurfingDaoException ex) {
            fail();
        }
    }

    @Test
    public void testGetBeachCommentByInvalidId() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Beach beach2 = new Beach();
            beach2.setName("Beach B");
            beach2.setZipCode(98420);
            Beach addedBeach2 = toTest.addBeach(beach2);

            BeachComment bc1 = new BeachComment();
            bc1.setUser(userDao.getUserById(2));
            bc1.setBeach(addedBeach);
            bc1.setCommentText("ea commodo consequat. Duis aute irure dolor in");
            BeachComment firstBC = toTest.addBeachComment(bc1);

            BeachComment bc2 = new BeachComment();
            bc2.setUser(userDao.getUserById(2));
            bc2.setBeach(addedBeach);
            bc2.setCommentText("vel illum qui");
            BeachComment secondBC = toTest.addBeachComment(bc2);

            BeachComment bc3 = new BeachComment();
            bc3.setUser(userDao.getUserById(2));
            bc3.setBeach(addedBeach2);
            bc3.setCommentText("aute irure dolor in");
            BeachComment thirdBC = toTest.addBeachComment(bc3);

            BeachComment toCheck = toTest.getBeachCommentById(2339);

            fail();

        } catch (SurfingDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of addBeachComment method, of class SurfingDbDao.
     */
    @Test
    public void testAddBeachCommentGoldenPath() {
        //tested above
    }

    @Test
    public void testAddNullBeachComment() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            BeachComment bc1 = null;
            BeachComment firstBC = toTest.addBeachComment(bc1);

            fail();

        } catch (InvalidIdException ex) {
            fail();
        } catch (SurfingDaoException ex) {
        }
    }

    @Test
    public void testAddBeachCommentNullBeach() {

        try {
            Beach beach1 = null;

            BeachComment bc1 = new BeachComment();
            bc1.setUser(userDao.getUserById(2));
            bc1.setBeach(beach1);
            bc1.setCommentText("ea commodo consequat. Duis aute irure dolor in");
            BeachComment firstBC = toTest.addBeachComment(bc1);

            fail();

        } catch (InvalidIdException ex) {
            fail();
        } catch (SurfingDaoException ex) {
        }
    }

    @Test
    public void testAddBeachCommentNullUser() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Beach beach2 = new Beach();
            beach2.setName("Beach B");
            beach2.setZipCode(98420);
            Beach addedBeach2 = toTest.addBeach(beach2);

            BeachComment bc1 = new BeachComment();
            bc1.setUser(null);
            bc1.setBeach(addedBeach);
            bc1.setCommentText("ea commodo consequat. Duis aute irure dolor in");
            BeachComment firstBC = toTest.addBeachComment(bc1);

            fail();

        } catch (InvalidIdException ex) {
            fail();
        } catch (SurfingDaoException ex) {
        }
    }

    @Test
    public void testAddBeachCommentInvalidBeachId() {

        try {
            Beach beach1 = new Beach();
            beach1.setId(4429);
            beach1.setName("Beach A");
            beach1.setZipCode(96701);

            BeachComment bc1 = new BeachComment();
            bc1.setUser(userDao.getUserById(2));
            bc1.setBeach(beach1);
            bc1.setCommentText("ea commodo consequat. Duis aute irure dolor in");
            BeachComment firstBC = toTest.addBeachComment(bc1);

            fail();

        } catch (SurfingDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    @Test
    public void testAddBeachCommentInvalidUserId() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            SiteUser invalidUser = new SiteUser();
            invalidUser.setId(5);

            BeachComment bc1 = new BeachComment();
            bc1.setUser(invalidUser);
            bc1.setBeach(addedBeach);
            bc1.setCommentText("ea commodo consequat. Duis aute irure dolor in");
            BeachComment firstBC = toTest.addBeachComment(bc1);

            fail();

        } catch (SurfingDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of updateBeachComment method, of class SurfingDbDao.
     */
    @Test
    public void testUpdateBeachCommentGoldenPath() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Beach beach2 = new Beach();
            beach2.setName("Beach B");
            beach2.setZipCode(98420);
            Beach addedBeach2 = toTest.addBeach(beach2);

            BeachComment bc1 = new BeachComment();
            bc1.setUser(userDao.getUserById(2));
            bc1.setBeach(addedBeach);
            bc1.setCommentText("ea commodo consequat. Duis aute irure dolor in");
            BeachComment firstBC = toTest.addBeachComment(bc1);

            BeachComment updatedBC = new BeachComment();
            updatedBC.setId(firstBC.getId());
            updatedBC.setUser(userDao.getUserById(2));
            updatedBC.setBeach(addedBeach2);
            updatedBC.setCommentText("tempora incidunt");
            toTest.updateBeachComment(updatedBC);

            BeachComment toCheck = toTest.getBeachCommentById(firstBC.getId());
            assertEquals(firstBC.getId(), toCheck.getId());
            assertEquals(2, toCheck.getUser().getId());
            assertEquals(addedBeach2.getId(), toCheck.getBeach().getId());
            assertEquals("tempora incidunt", toCheck.getCommentText());

        } catch (SurfingDaoException | InvalidIdException ex) {
            fail();
        }
    }

    @Test
    public void testUpdateNullBeachComment() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Beach beach2 = new Beach();
            beach2.setName("Beach B");
            beach2.setZipCode(98420);
            Beach addedBeach2 = toTest.addBeach(beach2);

            BeachComment bc1 = new BeachComment();
            bc1.setUser(userDao.getUserById(2));
            bc1.setBeach(addedBeach);
            bc1.setCommentText("ea commodo consequat. Duis aute irure dolor in");
            BeachComment firstBC = toTest.addBeachComment(bc1);

            BeachComment updatedBC = null;
            toTest.updateBeachComment(updatedBC);

            fail();

        } catch (InvalidIdException ex) {
            fail();
        } catch (SurfingDaoException ex) {
        }
    }

    @Test
    public void testUpdateBeachCommentNullBeach() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Beach beach2 = null;

            BeachComment bc1 = new BeachComment();
            bc1.setUser(userDao.getUserById(2));
            bc1.setBeach(addedBeach);
            bc1.setCommentText("ea commodo consequat. Duis aute irure dolor in");
            BeachComment firstBC = toTest.addBeachComment(bc1);

            BeachComment updatedBC = new BeachComment();
            updatedBC.setId(firstBC.getId());
            updatedBC.setUser(userDao.getUserById(2));
            updatedBC.setBeach(beach2);
            updatedBC.setCommentText("tempora incidunt");
            toTest.updateBeachComment(updatedBC);

            fail();

        } catch (InvalidIdException ex) {
            fail();
        } catch (SurfingDaoException ex) {
        }
    }

    @Test
    public void testUpdateBeachCommentNullUser() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Beach beach2 = new Beach();
            beach2.setName("Beach B");
            beach2.setZipCode(98420);
            Beach addedBeach2 = toTest.addBeach(beach2);

            BeachComment bc1 = new BeachComment();
            bc1.setUser(userDao.getUserById(2));
            bc1.setBeach(addedBeach);
            bc1.setCommentText("ea commodo consequat. Duis aute irure dolor in");
            BeachComment firstBC = toTest.addBeachComment(bc1);

            BeachComment updatedBC = new BeachComment();
            updatedBC.setId(firstBC.getId());
            updatedBC.setUser(null);
            updatedBC.setBeach(addedBeach2);
            updatedBC.setCommentText("tempora incidunt");
            toTest.updateBeachComment(updatedBC);

            fail();

        } catch (InvalidIdException ex) {
            fail();
        } catch (SurfingDaoException ex) {
        }
    }

    @Test
    public void testUpdateBeachCommentInvalidBeachId() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Beach beach2 = new Beach();
            beach2.setId(8336);
            beach2.setName("Beach B");
            beach2.setZipCode(98420);

            BeachComment bc1 = new BeachComment();
            bc1.setUser(userDao.getUserById(2));
            bc1.setBeach(addedBeach);
            bc1.setCommentText("ea commodo consequat. Duis aute irure dolor in");
            BeachComment firstBC = toTest.addBeachComment(bc1);

            BeachComment updatedBC = new BeachComment();
            updatedBC.setId(firstBC.getId());
            updatedBC.setUser(userDao.getUserById(2));
            updatedBC.setBeach(beach2);
            updatedBC.setCommentText("tempora incidunt");
            toTest.updateBeachComment(updatedBC);

            fail();

        } catch (SurfingDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }

    }

    @Test
    public void testUpdateBeachCommentInvalidUserId() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Beach beach2 = new Beach();
            beach2.setName("Beach B");
            beach2.setZipCode(98420);
            Beach addedBeach2 = toTest.addBeach(beach2);

            BeachComment bc1 = new BeachComment();
            bc1.setUser(userDao.getUserById(2));
            bc1.setBeach(addedBeach);
            bc1.setCommentText("ea commodo consequat. Duis aute irure dolor in");
            BeachComment firstBC = toTest.addBeachComment(bc1);

            SiteUser invalidUser = new SiteUser();
            invalidUser.setId(7);

            BeachComment updatedBC = new BeachComment();
            updatedBC.setId(firstBC.getId());
            updatedBC.setUser(invalidUser);
            updatedBC.setBeach(addedBeach2);
            updatedBC.setCommentText("tempora incidunt");
            toTest.updateBeachComment(updatedBC);

            fail();

        } catch (SurfingDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of deleteBeachComment method, of class SurfingDbDao.
     */
    @Test
    public void testDeleteBeachCommentGoldenPath() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Beach beach2 = new Beach();
            beach2.setName("Beach B");
            beach2.setZipCode(98420);
            Beach addedBeach2 = toTest.addBeach(beach2);

            BeachComment bc1 = new BeachComment();
            bc1.setUser(userDao.getUserById(2));
            bc1.setBeach(addedBeach);
            bc1.setCommentText("ea commodo consequat. Duis aute irure dolor in");
            BeachComment firstBC = toTest.addBeachComment(bc1);

            BeachComment bc2 = new BeachComment();
            bc2.setUser(userDao.getUserById(2));
            bc2.setBeach(addedBeach);
            bc2.setCommentText("vel illum qui");
            BeachComment secondBC = toTest.addBeachComment(bc2);

            toTest.deleteBeachComment(firstBC.getId());

            List<BeachComment> allComments = toTest.getAllBeachComments();

            assertEquals(1, allComments.size());

            BeachComment toCheck = allComments.get(0);
            assertEquals(secondBC.getId(), toCheck.getId());
            assertEquals(2, toCheck.getUser().getId());
            assertEquals(addedBeach.getId(), toCheck.getBeach().getId());
            assertEquals("vel illum qui", toCheck.getCommentText());

        } catch (SurfingDaoException | InvalidIdException ex) {
            Logger.getLogger(SurfingDbDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testDeleteInvalidIdBeachComment() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Beach beach2 = new Beach();
            beach2.setName("Beach B");
            beach2.setZipCode(98420);
            Beach addedBeach2 = toTest.addBeach(beach2);

            BeachComment bc1 = new BeachComment();
            bc1.setUser(userDao.getUserById(2));
            bc1.setBeach(addedBeach);
            bc1.setCommentText("ea commodo consequat. Duis aute irure dolor in");
            BeachComment firstBC = toTest.addBeachComment(bc1);

            toTest.deleteBeachComment(5421);

            fail();

        } catch (SurfingDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of getAllBreakComments method, of class SurfingDbDao.
     */
    @Test
    public void testGetAllBreakCommentsGoldenPath() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Break break1 = new Break();
            break1.setName("Break A");
            break1.setBeach(addedBeach);
            break1.setLatitude(new BigDecimal("20.716179"));
            break1.setLongitude(new BigDecimal("-158.214676"));
            break1.setBlog("test");
            Break addedBreak1 = toTest.addBreak(break1);

            Break break2 = new Break();
            break2.setName("Break B");
            break2.setBeach(addedBeach);
            break2.setLatitude(new BigDecimal("20.725689"));
            break2.setLongitude(new BigDecimal("-158.214351"));
            break2.setBlog("test2");
            Break addedBreak2 = toTest.addBreak(break2);

            BreakComment bc1 = new BreakComment();
            bc1.setUser(userDao.getUserById(2));
            bc1.setBeachBreak(addedBreak1);
            bc1.setCommentText("perspiciatis unde omnis iste natus error sit volupt");
            BreakComment addedBC1 = toTest.addBreakComment(bc1);

            BreakComment bc2 = new BreakComment();
            bc2.setUser(userDao.getUserById(2));
            bc2.setBeachBreak(addedBreak1);
            bc2.setCommentText("dolor sit amet, consectetu");
            BreakComment addedBC2 = toTest.addBreakComment(bc2);

            BreakComment bc3 = new BreakComment();
            bc3.setUser(userDao.getUserById(2));
            bc3.setBeachBreak(addedBreak1);
            bc3.setCommentText("commodi consequatur? Quis autem vel eum iure");
            BreakComment addedBC3 = toTest.addBreakComment(bc3);

            BreakComment bc4 = new BreakComment();
            bc4.setUser(userDao.getUserById(2));
            bc4.setBeachBreak(addedBreak2);
            bc4.setCommentText("minim veniam, quis nostrud");
            BreakComment addedBC4 = toTest.addBreakComment(bc4);

            List<BreakComment> allComments = toTest.getAllBreakComments();

            assertEquals(4, allComments.size());

            BreakComment firstComment = allComments.get(0);
            assertEquals(addedBC1.getId(), firstComment.getId());
            assertEquals(2, firstComment.getUser().getId());
            assertEquals(addedBreak1.getId(), firstComment.getBeachBreak().getId());
            assertEquals("perspiciatis unde omnis iste natus error sit volupt", firstComment.getCommentText());

            BreakComment fourthComment = allComments.get(3);
            assertEquals(addedBC4.getId(), fourthComment.getId());
            assertEquals(2, fourthComment.getUser().getId());
            assertEquals(addedBreak2.getId(), fourthComment.getBeachBreak().getId());
            assertEquals("minim veniam, quis nostrud", fourthComment.getCommentText());

        } catch (SurfingDaoException | InvalidIdException ex) {
            fail();
        }
    }

    /**
     * Test of getBreakCommentById method, of class SurfingDbDao.
     */
    @Test
    public void testGetBreakCommentByIdGoldenPath() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Break break1 = new Break();
            break1.setName("Break A");
            break1.setBeach(addedBeach);
            break1.setLatitude(new BigDecimal("20.716179"));
            break1.setLongitude(new BigDecimal("-158.214676"));
            break1.setBlog("test");
            Break addedBreak1 = toTest.addBreak(break1);

            Break break2 = new Break();
            break2.setName("Break B");
            break2.setBeach(addedBeach);
            break2.setLatitude(new BigDecimal("20.725689"));
            break2.setLongitude(new BigDecimal("-158.214351"));
            break2.setBlog("test2");
            Break addedBreak2 = toTest.addBreak(break2);

            BreakComment bc1 = new BreakComment();
            bc1.setUser(userDao.getUserById(2));
            bc1.setBeachBreak(addedBreak1);
            bc1.setCommentText("perspiciatis unde omnis iste natus error sit volupt");
            BreakComment addedBC1 = toTest.addBreakComment(bc1);

            BreakComment bc2 = new BreakComment();
            bc2.setUser(userDao.getUserById(2));
            bc2.setBeachBreak(addedBreak1);
            bc2.setCommentText("dolor sit amet, consectetu");
            BreakComment addedBC2 = toTest.addBreakComment(bc2);

            BreakComment bc3 = new BreakComment();
            bc3.setUser(userDao.getUserById(2));
            bc3.setBeachBreak(addedBreak1);
            bc3.setCommentText("commodi consequatur? Quis autem vel eum iure");
            BreakComment addedBC3 = toTest.addBreakComment(bc3);

            BreakComment bc4 = new BreakComment();
            bc4.setUser(userDao.getUserById(2));
            bc4.setBeachBreak(addedBreak2);
            bc4.setCommentText("minim veniam, quis nostrud");
            BreakComment addedBC4 = toTest.addBreakComment(bc4);

            BreakComment toCheck = toTest.getBreakCommentById(addedBC3.getId());
            assertEquals(addedBC3.getId(), toCheck.getId());
            assertEquals(2, toCheck.getUser().getId());
            assertEquals(addedBreak1.getId(), toCheck.getBeachBreak().getId());
            assertEquals("commodi consequatur? Quis autem vel eum iure", toCheck.getCommentText());

        } catch (SurfingDaoException | InvalidIdException ex) {
            fail();
        }
    }

    @Test
    public void testGetBreakCommentByInvalidId() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Break break1 = new Break();
            break1.setName("Break A");
            break1.setBeach(addedBeach);
            break1.setLatitude(new BigDecimal("20.716179"));
            break1.setLongitude(new BigDecimal("-158.214676"));
            break1.setBlog("test");
            Break addedBreak1 = toTest.addBreak(break1);

            BreakComment bc1 = new BreakComment();
            bc1.setUser(userDao.getUserById(2));
            bc1.setBeachBreak(addedBreak1);
            bc1.setCommentText("perspiciatis unde omnis iste natus error sit volupt");
            BreakComment addedBC1 = toTest.addBreakComment(bc1);

            BreakComment toCheck = toTest.getBreakCommentById(8832);

            fail();

        } catch (SurfingDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of addBreakComment method, of class SurfingDbDao.
     */
    @Test
    public void testAddBreakCommentGoldenPath() {
        //testd above
    }

    @Test
    public void testAddNullBreakComment() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Break break1 = new Break();
            break1.setName("Break A");
            break1.setBeach(addedBeach);
            break1.setLatitude(new BigDecimal("20.716179"));
            break1.setLongitude(new BigDecimal("-158.214676"));
            break1.setBlog("test");
            Break addedBreak1 = toTest.addBreak(break1);

            BreakComment bc1 = null;
            BreakComment addedBC1 = toTest.addBreakComment(bc1);

            fail();

        } catch (InvalidIdException ex) {
            fail();
        } catch (SurfingDaoException ex) {
        }
    }

    @Test
    public void testAddBreakCommentNullBreak() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Break break1 = null;

            BreakComment bc1 = new BreakComment();
            bc1.setUser(userDao.getUserById(2));
            bc1.setBeachBreak(break1);
            bc1.setCommentText("perspiciatis unde omnis iste natus error sit volupt");
            BreakComment addedBC1 = toTest.addBreakComment(bc1);

            fail();

        } catch (InvalidIdException ex) {
            fail();
        } catch (SurfingDaoException ex) {
        }
    }

    @Test
    public void testAddBreakCommentNullUser() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Break break1 = new Break();
            break1.setName("Break A");
            break1.setBeach(addedBeach);
            break1.setLatitude(new BigDecimal("20.716179"));
            break1.setLongitude(new BigDecimal("-158.214676"));
            break1.setBlog("test");
            Break addedBreak1 = toTest.addBreak(break1);

            BreakComment bc1 = new BreakComment();
            bc1.setUser(null);
            bc1.setBeachBreak(addedBreak1);
            bc1.setCommentText("perspiciatis unde omnis iste natus error sit volupt");
            BreakComment addedBC1 = toTest.addBreakComment(bc1);

            fail();

        } catch (InvalidIdException ex) {
            fail();
        } catch (SurfingDaoException ex) {
        }
    }

    @Test
    public void testAddBreakCommentNullBeach() {

        try {
            Beach beach1 = null;

            Break break1 = new Break();
            break1.setName("Break A");
            break1.setBeach(beach1);
            break1.setLatitude(new BigDecimal("20.716179"));
            break1.setLongitude(new BigDecimal("-158.214676"));
            break1.setBlog("test");
            Break addedBreak1 = toTest.addBreak(break1);

            BreakComment bc1 = new BreakComment();
            bc1.setUser(userDao.getUserById(2));
            bc1.setBeachBreak(addedBreak1);
            bc1.setCommentText("perspiciatis unde omnis iste natus error sit volupt");
            BreakComment addedBC1 = toTest.addBreakComment(bc1);

            fail();

        } catch (InvalidIdException ex) {
            fail();
        } catch (SurfingDaoException ex) {
        }
    }

    @Test
    public void testAddBreakCommentInvalidBreakId() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Break break1 = new Break();
            break1.setId(9822);
            break1.setName("Break A");
            break1.setBeach(addedBeach);
            break1.setLatitude(new BigDecimal("20.716179"));
            break1.setLongitude(new BigDecimal("-158.214676"));
            break1.setBlog("test");

            BreakComment bc1 = new BreakComment();
            bc1.setUser(userDao.getUserById(2));
            bc1.setBeachBreak(break1);
            bc1.setCommentText("perspiciatis unde omnis iste natus error sit volupt");
            BreakComment addedBC1 = toTest.addBreakComment(bc1);

            fail();

        } catch (SurfingDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }

    }

    @Test
    public void testAddBreakCommentInvalidUserId() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Break break1 = new Break();
            break1.setName("Break A");
            break1.setBeach(addedBeach);
            break1.setLatitude(new BigDecimal("20.716179"));
            break1.setLongitude(new BigDecimal("-158.214676"));
            break1.setBlog("test");
            Break addedBreak1 = toTest.addBreak(break1);

            SiteUser invalidUser = new SiteUser();
            invalidUser.setId(8);

            BreakComment bc1 = new BreakComment();
            bc1.setUser(invalidUser);
            bc1.setBeachBreak(addedBreak1);
            bc1.setCommentText("perspiciatis unde omnis iste natus error sit volupt");
            BreakComment addedBC1 = toTest.addBreakComment(bc1);

            fail();

        } catch (SurfingDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }

    }

    @Test
    public void testAddBreakCommentInvalidBeachId() {

        try {
            Beach beach1 = new Beach();
            beach1.setId(9336);
            beach1.setName("Beach A");
            beach1.setZipCode(96701);

            Break break1 = new Break();
            break1.setName("Break A");
            break1.setBeach(beach1);
            break1.setLatitude(new BigDecimal("20.716179"));
            break1.setLongitude(new BigDecimal("-158.214676"));
            break1.setBlog("test");
            Break addedBreak1 = toTest.addBreak(break1);

            BreakComment bc1 = new BreakComment();
            bc1.setUser(userDao.getUserById(2));
            bc1.setBeachBreak(addedBreak1);
            bc1.setCommentText("perspiciatis unde omnis iste natus error sit volupt");
            BreakComment addedBC1 = toTest.addBreakComment(bc1);

            fail();

        } catch (SurfingDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }

    }

    /**
     * Test of updateBreakComment method, of class SurfingDbDao.
     */
    @Test
    public void testUpdateBreakCommentGoldenPath() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Break break1 = new Break();
            break1.setName("Break A");
            break1.setBeach(addedBeach);
            break1.setLatitude(new BigDecimal("20.716179"));
            break1.setLongitude(new BigDecimal("-158.214676"));
            break1.setBlog("test");
            Break addedBreak1 = toTest.addBreak(break1);

            Break break2 = new Break();
            break2.setName("Break B");
            break2.setBeach(addedBeach);
            break2.setLatitude(new BigDecimal("20.725689"));
            break2.setLongitude(new BigDecimal("-158.214351"));
            break2.setBlog("test2");
            Break addedBreak2 = toTest.addBreak(break2);

            BreakComment bc1 = new BreakComment();
            bc1.setUser(userDao.getUserById(2));
            bc1.setBeachBreak(addedBreak1);
            bc1.setCommentText("perspiciatis unde omnis iste natus error sit volupt");
            BreakComment addedBC1 = toTest.addBreakComment(bc1);

            BreakComment updatedBC = new BreakComment();
            updatedBC.setId(addedBC1.getId());
            updatedBC.setUser(userDao.getUserById(2));
            updatedBC.setBeachBreak(addedBreak2);
            updatedBC.setCommentText("aspernatur aut odit aut fugit, sed quia consequuntur");
            toTest.updateBreakComment(updatedBC);

            BreakComment toCheck = toTest.getBreakCommentById(addedBC1.getId());
            assertEquals(addedBC1.getId(), toCheck.getId());
            assertEquals(2, toCheck.getUser().getId());
            assertEquals(addedBreak2.getId(), toCheck.getBeachBreak().getId());
            assertEquals("aspernatur aut odit aut fugit, sed quia consequuntur", toCheck.getCommentText());

        } catch (SurfingDaoException | InvalidIdException ex) {
            Logger.getLogger(SurfingDbDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testUpdateNullBreakComment() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Break break1 = new Break();
            break1.setName("Break A");
            break1.setBeach(addedBeach);
            break1.setLatitude(new BigDecimal("20.716179"));
            break1.setLongitude(new BigDecimal("-158.214676"));
            break1.setBlog("test");
            Break addedBreak1 = toTest.addBreak(break1);

            Break break2 = new Break();
            break2.setName("Break B");
            break2.setBeach(addedBeach);
            break2.setLatitude(new BigDecimal("20.725689"));
            break2.setLongitude(new BigDecimal("-158.214351"));
            break2.setBlog("test2");
            Break addedBreak2 = toTest.addBreak(break2);

            BreakComment bc1 = new BreakComment();
            bc1.setUser(userDao.getUserById(2));
            bc1.setBeachBreak(addedBreak1);
            bc1.setCommentText("perspiciatis unde omnis iste natus error sit volupt");
            BreakComment addedBC1 = toTest.addBreakComment(bc1);

            BreakComment updatedBC = null;
            toTest.updateBreakComment(updatedBC);

            fail();

        } catch (InvalidIdException ex) {
            fail();
        } catch (SurfingDaoException ex) {
        }
    }

    @Test
    public void testUpdateBreakCommentNullBreak() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Break break1 = new Break();
            break1.setName("Break A");
            break1.setBeach(addedBeach);
            break1.setLatitude(new BigDecimal("20.716179"));
            break1.setLongitude(new BigDecimal("-158.214676"));
            break1.setBlog("test");
            Break addedBreak1 = toTest.addBreak(break1);

            Break break2 = null;

            BreakComment bc1 = new BreakComment();
            bc1.setUser(userDao.getUserById(2));
            bc1.setBeachBreak(addedBreak1);
            bc1.setCommentText("perspiciatis unde omnis iste natus error sit volupt");
            BreakComment addedBC1 = toTest.addBreakComment(bc1);

            BreakComment updatedBC = new BreakComment();
            updatedBC.setId(addedBC1.getId());
            updatedBC.setUser(userDao.getUserById(2));
            updatedBC.setBeachBreak(break2);
            updatedBC.setCommentText("aspernatur aut odit aut fugit, sed quia consequuntur");
            toTest.updateBreakComment(updatedBC);

            fail();

        } catch (InvalidIdException ex) {
            fail();
        } catch (SurfingDaoException ex) {
        }
    }

    @Test
    public void testUpdateBreakCommentNullUser() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Break break1 = new Break();
            break1.setName("Break A");
            break1.setBeach(addedBeach);
            break1.setLatitude(new BigDecimal("20.716179"));
            break1.setLongitude(new BigDecimal("-158.214676"));
            break1.setBlog("test");
            Break addedBreak1 = toTest.addBreak(break1);

            Break break2 = new Break();
            break2.setName("Break B");
            break2.setBeach(addedBeach);
            break2.setLatitude(new BigDecimal("20.725689"));
            break2.setLongitude(new BigDecimal("-158.214351"));
            break2.setBlog("test2");
            Break addedBreak2 = toTest.addBreak(break2);

            BreakComment bc1 = new BreakComment();
            bc1.setUser(userDao.getUserById(2));
            bc1.setBeachBreak(addedBreak1);
            bc1.setCommentText("perspiciatis unde omnis iste natus error sit volupt");
            BreakComment addedBC1 = toTest.addBreakComment(bc1);

            BreakComment updatedBC = new BreakComment();
            updatedBC.setId(addedBC1.getId());
            updatedBC.setUser(null);
            updatedBC.setBeachBreak(addedBreak2);
            updatedBC.setCommentText("aspernatur aut odit aut fugit, sed quia consequuntur");
            toTest.updateBreakComment(updatedBC);

            fail();

        } catch (InvalidIdException ex) {
            fail();
        } catch (SurfingDaoException ex) {
        }
    }

    @Test
    public void testUpdateBreakCommentNullBeach() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Break break1 = new Break();
            break1.setName("Break A");
            break1.setBeach(addedBeach);
            break1.setLatitude(new BigDecimal("20.716179"));
            break1.setLongitude(new BigDecimal("-158.214676"));
            break1.setBlog("test");
            Break addedBreak1 = toTest.addBreak(break1);

            Beach beach2 = null;

            Break break2 = new Break();
            break2.setName("Break B");
            break2.setBeach(beach2);
            break2.setLatitude(new BigDecimal("20.725689"));
            break2.setLongitude(new BigDecimal("-158.214351"));
            break2.setBlog("test2");
            Break addedBreak2 = toTest.addBreak(break2);

            BreakComment bc1 = new BreakComment();
            bc1.setUser(userDao.getUserById(2));
            bc1.setBeachBreak(addedBreak1);
            bc1.setCommentText("perspiciatis unde omnis iste natus error sit volupt");
            BreakComment addedBC1 = toTest.addBreakComment(bc1);

            BreakComment updatedBC = new BreakComment();
            updatedBC.setId(addedBC1.getId());
            updatedBC.setUser(userDao.getUserById(2));
            updatedBC.setBeachBreak(addedBreak2);
            updatedBC.setCommentText("aspernatur aut odit aut fugit, sed quia consequuntur");
            toTest.updateBreakComment(updatedBC);

            fail();

        } catch (InvalidIdException ex) {
            fail();
        } catch (SurfingDaoException ex) {
        }
    }

    @Test
    public void testUpdateBreakCommentInvalidBreakId() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Break break1 = new Break();
            break1.setName("Break A");
            break1.setBeach(addedBeach);
            break1.setLatitude(new BigDecimal("20.716179"));
            break1.setLongitude(new BigDecimal("-158.214676"));
            break1.setBlog("test");
            Break addedBreak1 = toTest.addBreak(break1);

            Break break2 = new Break();
            break2.setId(7629);
            break2.setName("Break B");
            break2.setBeach(addedBeach);
            break2.setLatitude(new BigDecimal("20.725689"));
            break2.setLongitude(new BigDecimal("-158.214351"));
            break2.setBlog("test2");

            BreakComment bc1 = new BreakComment();
            bc1.setUser(userDao.getUserById(2));
            bc1.setBeachBreak(addedBreak1);
            bc1.setCommentText("perspiciatis unde omnis iste natus error sit volupt");
            BreakComment addedBC1 = toTest.addBreakComment(bc1);

            BreakComment updatedBC = new BreakComment();
            updatedBC.setId(addedBC1.getId());
            updatedBC.setUser(userDao.getUserById(2));
            updatedBC.setBeachBreak(break2);
            updatedBC.setCommentText("aspernatur aut odit aut fugit, sed quia consequuntur");
            toTest.updateBreakComment(updatedBC);

            fail();

        } catch (SurfingDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }

    }

    @Test
    public void testUpdateBreakCommentInvalidUserId() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Break break1 = new Break();
            break1.setName("Break A");
            break1.setBeach(addedBeach);
            break1.setLatitude(new BigDecimal("20.716179"));
            break1.setLongitude(new BigDecimal("-158.214676"));
            break1.setBlog("test");
            Break addedBreak1 = toTest.addBreak(break1);

            Break break2 = new Break();
            break2.setName("Break B");
            break2.setBeach(addedBeach);
            break2.setLatitude(new BigDecimal("20.725689"));
            break2.setLongitude(new BigDecimal("-158.214351"));
            break2.setBlog("test2");
            Break addedBreak2 = toTest.addBreak(break2);

            BreakComment bc1 = new BreakComment();
            bc1.setUser(userDao.getUserById(2));
            bc1.setBeachBreak(addedBreak1);
            bc1.setCommentText("perspiciatis unde omnis iste natus error sit volupt");
            BreakComment addedBC1 = toTest.addBreakComment(bc1);

            SiteUser invalidUser = new SiteUser();
            invalidUser.setId(7);

            BreakComment updatedBC = new BreakComment();
            updatedBC.setId(addedBC1.getId());
            updatedBC.setUser(invalidUser);
            updatedBC.setBeachBreak(addedBreak2);
            updatedBC.setCommentText("aspernatur aut odit aut fugit, sed quia consequuntur");
            toTest.updateBreakComment(updatedBC);

            fail();

        } catch (SurfingDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    @Test
    public void testUpdateBreakCommentInvalidBeachId() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Beach invalidBeach = new Beach();
            invalidBeach.setId(7289);

            Break break1 = new Break();
            break1.setName("Break A");
            break1.setBeach(addedBeach);
            break1.setLatitude(new BigDecimal("20.716179"));
            break1.setLongitude(new BigDecimal("-158.214676"));
            break1.setBlog("test");
            Break addedBreak1 = toTest.addBreak(break1);

            Break break2 = new Break();
            break2.setName("Break B");
            break2.setBeach(invalidBeach);
            break2.setLatitude(new BigDecimal("20.725689"));
            break2.setLongitude(new BigDecimal("-158.214351"));
            break2.setBlog("test2");
            Break addedBreak2 = toTest.addBreak(break2);

            BreakComment bc1 = new BreakComment();
            bc1.setUser(userDao.getUserById(2));
            bc1.setBeachBreak(addedBreak1);
            bc1.setCommentText("perspiciatis unde omnis iste natus error sit volupt");
            BreakComment addedBC1 = toTest.addBreakComment(bc1);

            BreakComment updatedBC = new BreakComment();
            updatedBC.setId(addedBC1.getId());
            updatedBC.setUser(userDao.getUserById(2));
            updatedBC.setBeachBreak(addedBreak2);
            updatedBC.setCommentText("aspernatur aut odit aut fugit, sed quia consequuntur");
            toTest.updateBreakComment(updatedBC);

            fail();

        } catch (SurfingDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of deleteBreakComment method, of class SurfingDbDao.
     */
    @Test
    public void testDeleteBreakCommentGoldenPath() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Break break1 = new Break();
            break1.setName("Break A");
            break1.setBeach(addedBeach);
            break1.setLatitude(new BigDecimal("20.716179"));
            break1.setLongitude(new BigDecimal("-158.214676"));
            break1.setBlog("test");
            Break addedBreak1 = toTest.addBreak(break1);

            BreakComment bc1 = new BreakComment();
            bc1.setUser(userDao.getUserById(2));
            bc1.setBeachBreak(addedBreak1);
            bc1.setCommentText("perspiciatis unde omnis iste natus error sit volupt");
            BreakComment addedBC1 = toTest.addBreakComment(bc1);

            BreakComment bc2 = new BreakComment();
            bc2.setUser(userDao.getUserById(2));
            bc2.setBeachBreak(addedBreak1);
            bc2.setCommentText("dolor sit amet, consectetu");
            BreakComment addedBC2 = toTest.addBreakComment(bc2);

            BreakComment bc3 = new BreakComment();
            bc3.setUser(userDao.getUserById(2));
            bc3.setBeachBreak(addedBreak1);
            bc3.setCommentText("commodi consequatur? Quis autem vel eum iure");
            BreakComment addedBC3 = toTest.addBreakComment(bc3);

            toTest.deleteBreakComment(addedBC1.getId());

            List<BreakComment> allComments = toTest.getAllBreakComments();

            assertEquals(2, allComments.size());

            BreakComment secondComment = allComments.get(0);
            assertEquals(addedBC2.getId(), secondComment.getId());
            assertEquals(2, secondComment.getUser().getId());
            assertEquals(addedBreak1.getId(), secondComment.getBeachBreak().getId());
            assertEquals("dolor sit amet, consectetu", secondComment.getCommentText());

            BreakComment thirdComment = allComments.get(1);
            assertEquals(addedBC3.getId(), thirdComment.getId());
            assertEquals(2, thirdComment.getUser().getId());
            assertEquals(addedBreak1.getId(), thirdComment.getBeachBreak().getId());
            assertEquals("commodi consequatur? Quis autem vel eum iure", thirdComment.getCommentText());

        } catch (SurfingDaoException | InvalidIdException ex) {
            Logger.getLogger(SurfingDbDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testDeleteBreakCommentInvalidId() {

        try {
            Beach beach1 = new Beach();
            beach1.setName("Beach A");
            beach1.setZipCode(96701);
            Beach addedBeach = toTest.addBeach(beach1);

            Break break1 = new Break();
            break1.setName("Break A");
            break1.setBeach(addedBeach);
            break1.setLatitude(new BigDecimal("20.716179"));
            break1.setLongitude(new BigDecimal("-158.214676"));
            break1.setBlog("test");
            Break addedBreak1 = toTest.addBreak(break1);

            BreakComment bc1 = new BreakComment();
            bc1.setUser(userDao.getUserById(2));
            bc1.setBeachBreak(addedBreak1);
            bc1.setCommentText("perspiciatis unde omnis iste natus error sit volupt");
            BreakComment addedBC1 = toTest.addBreakComment(bc1);

            BreakComment bc2 = new BreakComment();
            bc2.setUser(userDao.getUserById(2));
            bc2.setBeachBreak(addedBreak1);
            bc2.setCommentText("dolor sit amet, consectetu");
            BreakComment addedBC2 = toTest.addBreakComment(bc2);

            BreakComment bc3 = new BreakComment();
            bc3.setUser(userDao.getUserById(2));
            bc3.setBeachBreak(addedBreak1);
            bc3.setCommentText("commodi consequatur? Quis autem vel eum iure");
            BreakComment addedBC3 = toTest.addBreakComment(bc3);

            toTest.deleteBreakComment(4529);

            fail();

        } catch (SurfingDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

}
