/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.surfingblog.newpackage.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import sg.surfingblog.newpackage.models.Beach;
import sg.surfingblog.newpackage.models.BeachComment;
import sg.surfingblog.newpackage.models.Break;
import sg.surfingblog.newpackage.models.BreakComment;
import sg.surfingblog.newpackage.models.News;
import sg.surfingblog.newpackage.models.SiteUser;

/**
 *
 * @author blee0
 */
@Repository
@Profile({"production", "test"})
public class SurfingDbDao implements SurfingDao {

    @Autowired
    private JdbcTemplate template;

    @Autowired
    private UserDao userDao;

    @Override
    public List<Break> getBreaksByBeach(int id) throws InvalidIdException {

        try {
            Beach toCheck = getBeachById(id);
        } catch (InvalidIdException ex) {
            throw new InvalidIdException("Beach not found", ex);
        }

        String query = "Select br.id breakid, br.`name` breakname, br.latitude, br.longitude, br.blog, be.id beachid, be.`name` beachname, be.zipcode\n"
                + "From break br\n"
                + "Inner Join beach be On br.beachId = be.id\n"
                + "Where beachId = ?";

        List<Break> toReturn = template.query(query, new BreakMapper(), id);

        return toReturn;
    }

    @Override
    public List<BeachComment> getCommentsByBeach(int id) throws InvalidIdException {

        try {
            Beach toCheck = getBeachById(id);
        } catch (InvalidIdException ex) {
            throw new InvalidIdException("Beach not found", ex);
        }

        String query = "Select bc.id commentid, bc.userid, bc.beachid, bc.`comment`, be.`name` beachname, be.zipcode, u.username, u.`password`, u.enabled\n"
                + "From beach_comment bc\n"
                + "Inner Join `User` u On bc.userid = u.id\n"
                + "Inner Join beach be On bc.beachid = be.id\n"
                + "Where beachId = ? \n"
                + "ORDER BY bc.id DESC";

        List<BeachComment> toReturn = template.query(query, new BeachCommentMapper(), id);

        return toReturn;
    }

    @Override
    public List<BreakComment> getCommentsByBreak(int id) throws InvalidIdException {

        try {
            Break breakToCheck = getBreakById(id);
        } catch (InvalidIdException ex) {
            throw new InvalidIdException("BeachId, UserId, or BreakId not found", ex);
        }

        String query = "Select brc.id commentid, brc.userid, brc.breakid, brc.`comment`, br.`name` breakname, br.beachid, br.latitude, br.longitude, br.blog, be.`name` beachname, be.zipcode, u.username, u.`password`, u.enabled\n"
                + "From break_comment brc\n"
                + "Inner Join `User` u On brc.userid = u.id\n"
                + "Inner Join break br On brc.breakid = br.id\n"
                + "Inner Join beach be On br.beachid = be.id \n"
                + "Where breakId = ? \n"
                + "ORDER BY brc.id DESC";

        List<BreakComment> toReturn = template.query(query, new BreakCommentMapper(), id);

        return toReturn;
    }

    @Override
    public List<News> getAllActiveNews() {

        boolean active = true;

        String query = "Select * \n"
                + "From home_news_link \n"
                + "Where isactive = ?";

        List<News> toReturn = template.query(query, new NewsMapper(), active);

        return toReturn;
    }

    @Override
    public List<News> getAllNews() {

        String query = "Select * \n"
                + "From home_news_link ";

        List<News> toReturn = template.query(query, new NewsMapper());

        return toReturn;

    }

    @Override
    public News getNewsById(int id) throws InvalidIdException {

        String query = "Select * \n"
                + "From home_news_link \n"
                + "Where id = ?";

        News toReturn = null;
        try {
            toReturn = template.queryForObject(query, new NewsMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
        }

        if (toReturn == null) {
            throw new InvalidIdException("Invalid News Id.");
        }

        return toReturn;
    }

    @Override
    public News addNews(News toAdd) throws SurfingDaoException {

        if (toAdd == null) {
            throw new SurfingDaoException("News to add is null.");
        }

        String insert = "Insert into home_news_link(news_url, news_link_text, picture_url, isactive)\n"
                + "Values\n"
                + "(?, ?, ?, ?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement toReturn = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

                toReturn.setString(1, toAdd.getNewsURL());
                toReturn.setString(2, toAdd.getNewsAbbrText());
                toReturn.setString(3, toAdd.getPicURL());
                toReturn.setBoolean(4, toAdd.getIsActive());

                return toReturn;
            }
        };

        template.update(psc, holder);
        int newNewsId = holder.getKey().intValue();

        toAdd.setId(newNewsId);

        return toAdd;
    }

    @Override
    public void updateNews(News updatedNews) throws SurfingDaoException, InvalidIdException {

        if (updatedNews == null) {
            throw new SurfingDaoException("Power to update is null.");
        }

        String updateStatement = "Update home_news_link \n"
                + "Set news_url = ?,\n"
                + "news_link_text = ?,\n"
                + "picture_url = ?,\n"
                + "isactive = ?\n"
                + "Where id = ?";

        int rowsAffected = template.update(updateStatement, updatedNews.getNewsURL(), updatedNews.getNewsAbbrText(), updatedNews.getPicURL(), updatedNews.getIsActive(), updatedNews.getId());

        if (rowsAffected == 0) {
            throw new InvalidIdException("Could not edit News with id = " + updatedNews.getId());
        }

        if (rowsAffected > 1) {
            throw new SurfingDaoException("ERROR: NewsId IS NOT UNIQUE FOR News TABLE.");
        }
    }

    @Override
    public void deleteNews(int id) throws InvalidIdException {

        try {
            News toCheck = getNewsById(id);
        } catch (InvalidIdException ex) {
            throw new InvalidIdException("Invalid NewsId requested.");
        }

        String deleteStatement = "Delete \n"
                + "From home_news_link \n"
                + "Where id = ?";

        template.update(deleteStatement, id);
    }

    @Override
    public List<Beach> getAllBeaches() {

        String query = "Select * \n"
                + "From Beach";

        List<Beach> toReturn = template.query(query, new BeachMapper());

        return toReturn;
    }

    @Override
    public Beach getBeachById(int id) throws InvalidIdException {

        String query = "Select * \n"
                + "From Beach\n"
                + "Where id = ?";

        Beach toReturn = null;
        try {
            toReturn = template.queryForObject(query, new BeachMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
        }

        if (toReturn == null) {
            throw new InvalidIdException("Invalid Beach Id.");
        }

        return toReturn;
    }

    @Override
    public Beach addBeach(Beach toAdd) throws SurfingDaoException {

        if (toAdd == null) {
            throw new SurfingDaoException("Beach to add is null.");
        }

        String insert = "Insert into beach(`name`, zipcode)\n"
                + "Values \n"
                + "(?, ?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement toReturn = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

                toReturn.setString(1, toAdd.getName());
                toReturn.setInt(2, toAdd.getZipCode());

                return toReturn;
            }
        };

        template.update(psc, holder);
        int newBeachId = holder.getKey().intValue();

        toAdd.setId(newBeachId);

        return toAdd;
    }

    @Override
    public void updateBeach(Beach updatedBeach) throws SurfingDaoException, InvalidIdException {

        if (updatedBeach == null) {
            throw new SurfingDaoException("Beach to update is null.");
        }

        String updateStatement = "Update Beach \n"
                + "Set `name` = ?,\n"
                + "zipcode = ?\n"
                + "Where id = ?";

        int rowsAffected = template.update(updateStatement,
                updatedBeach.getName(),
                updatedBeach.getZipCode(),
                updatedBeach.getId());

        if (rowsAffected == 0) {
            throw new InvalidIdException("Could not edit Beach with id = " + updatedBeach.getId());
        }

        if (rowsAffected > 1) {
            throw new SurfingDaoException("ERROR: BeachId IS NOT UNIQUE FOR Beach TABLE.");
        }
    }

    @Override
    public void deleteBeach(int id) throws InvalidIdException {

        try {
            Beach toCheck = getBeachById(id);
        } catch (InvalidIdException ex) {
            throw new InvalidIdException("Invalid BeachId requested.");
        }

        String deleteBreakComments = "Delete bc.*\n"
                + "From break_comment bc\n"
                + "Inner Join break br On bc.breakid = br.id\n"
                + "Inner Join Beach be On br.beachid = be.id\n"
                + "Where be.id = ?";

        String deleteBeachComments = "Delete \n"
                + "From beach_comment \n"
                + "Where beachId = ?";

        String deleteBreaks = "Delete \n"
                + "From break \n"
                + "Where beachid = ?";

        String deleteBeaches = "Delete \n"
                + "From beach \n"
                + "Where id = ?";

        template.update(deleteBreakComments, id);

        template.update(deleteBeachComments, id);

        template.update(deleteBreaks, id);

        template.update(deleteBeaches, id);

        //need to verify deletions in MySQL again. Deleting 3, 2, 1 works. But 1, 2, 3 does not. 
    }

    @Override
    public List<Break> getAllBreaks() {

        String query = "Select br.id breakid, br.`name` breakname, br.latitude, br.longitude, br.blog, be.id beachid, be.`name` beachname, be.zipcode\n"
                + "From break br\n"
                + "Inner Join beach be On br.beachId = be.id";

        List<Break> toReturn = template.query(query, new BreakMapper());

        return toReturn;
    }

    @Override
    public Break getBreakById(int id) throws InvalidIdException {

        String query = "Select br.id breakid, br.`name` breakname, br.latitude, br.longitude, br.blog, be.id beachid, be.`name` beachname, be.zipcode\n"
                + "From break br\n"
                + "Inner Join beach be On br.beachId = be.id\n"
                + "Where br.id = ?";

        Break toReturn = null;
        try {
            toReturn = template.queryForObject(query, new BreakMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
        }

        if (toReturn == null) {
            throw new InvalidIdException("Invalid Break Id.");
        }

        return toReturn;
    }

    @Override
    public Break addBreak(Break toAdd) throws SurfingDaoException, InvalidIdException {

        if (toAdd == null || toAdd.getBeach() == null) {
            throw new SurfingDaoException("Break to add is null.");
        }

        try {
            Beach toCheck = getBeachById(toAdd.getBeach().getId());
        } catch (InvalidIdException ex) {
            throw new InvalidIdException("Beach not found", ex);
        }

        String insert = "Insert into Break(`name`, beachid, latitude, longitude, blog)\n"
                + "Values \n"
                + "(?, ?, ?, ?, ?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement toReturn = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

                toReturn.setString(1, toAdd.getName());
                toReturn.setInt(2, toAdd.getBeach().getId());
                toReturn.setBigDecimal(3, toAdd.getLatitude());
                toReturn.setBigDecimal(4, toAdd.getLongitude());
                toReturn.setString(5, toAdd.getBlog());

                return toReturn;
            }
        };

        template.update(psc, holder);
        int newBreakId = holder.getKey().intValue();

        toAdd.setId(newBreakId);

        return toAdd;
    }

    @Override
    public void updateBreak(Break updatedBreak) throws SurfingDaoException, InvalidIdException {

        if (updatedBreak == null || updatedBreak.getBeach() == null) {
            throw new SurfingDaoException("Break to Add is null.");
        }

        try {
            Beach toCheck = getBeachById(updatedBreak.getBeach().getId());
        } catch (InvalidIdException ex) {
            throw new InvalidIdException("Beach not found", ex);
        }

        String updateStatement = "Update Break \n"
                + "Set `name` = ?,\n"
                + "beachid = ?,\n"
                + "latitude = ?,\n"
                + "longitude = ?,\n"
                + "blog = ?\n"
                + "Where id = ?";

        int rowsAffected = template.update(updateStatement,
                updatedBreak.getName(),
                updatedBreak.getBeach().getId(),
                updatedBreak.getLatitude(),
                updatedBreak.getLongitude(),
                updatedBreak.getBlog(),
                updatedBreak.getId());

        if (rowsAffected == 0) {
            throw new InvalidIdException("Could not edit Break with id = " + updatedBreak.getId());
        }

        if (rowsAffected > 1) {
            throw new SurfingDaoException("ERROR: BreakId IS NOT UNIQUE FOR Break TABLE.");
        }
    }

    @Override
    public void deleteBreak(int id) throws InvalidIdException {

        try {
            Break toCheck = getBreakById(id);
        } catch (InvalidIdException ex) {
            throw new InvalidIdException("Invalid Break Id requested.");
        }

        String deleteBreakComments = "Delete \n"
                + "From break_comment \n"
                + "Where breakid = ?";

        String deleteBreak = "Delete \n"
                + "From break \n"
                + "Where id = ?";

        template.update(deleteBreakComments, id);
        template.update(deleteBreak, id);
    }

    @Override
    public List<BeachComment> getAllBeachComments() {

        String query = "Select bc.id commentid, bc.userid, bc.beachid, bc.`comment`, be.`name` beachname, be.zipcode, u.username, u.`password`, u.enabled\n"
                + "From beach_comment bc\n"
                + "Inner Join `User` u On bc.userid = u.id\n"
                + "Inner Join beach be On bc.beachid = be.id";

        List<BeachComment> toReturn = template.query(query, new BeachCommentMapper());

        return toReturn;
    }

    @Override
    public BeachComment getBeachCommentById(int id) throws InvalidIdException {

        String query = "Select bc.id commentid, bc.userid, bc.beachid, bc.`comment`, be.`name` beachname, be.zipcode, u.username, u.`password`, u.enabled\n"
                + "From beach_comment bc\n"
                + "Inner Join `User` u On bc.userid = u.id\n"
                + "Inner Join beach be On bc.beachid = be.id\n"
                + "Where bc.id = ?";

        BeachComment toReturn = null;
        try {
            toReturn = template.queryForObject(query, new BeachCommentMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
        }

        if (toReturn == null) {
            throw new InvalidIdException("Invalid BeachComment Id.");
        }

        return toReturn;
    }

    @Override
    public BeachComment addBeachComment(BeachComment toAdd) throws SurfingDaoException, InvalidIdException {

        if (toAdd == null || toAdd.getBeach() == null || toAdd.getUser() == null) {
            throw new SurfingDaoException("Beach Comment to add is null.");
        }

        try {
            Beach beachToCheck = getBeachById(toAdd.getBeach().getId());
            SiteUser userToCheck = userDao.getUserById(toAdd.getUser().getId());
        } catch (InvalidIdException ex) {
            throw new InvalidIdException("BeachId or UserId not found", ex);
        }

        String insert = "Insert into beach_comment(userid, beachid, `comment`)\n"
                + "Values\n"
                + "(?, ?, ?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement toReturn = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

                toReturn.setInt(1, toAdd.getUser().getId());
                toReturn.setInt(2, toAdd.getBeach().getId());
                toReturn.setString(3, toAdd.getCommentText());

                return toReturn;
            }
        };

        template.update(psc, holder);
        int newBeachCommentId = holder.getKey().intValue();

        toAdd.setId(newBeachCommentId);

        return toAdd;
    }

    @Override
    public void updateBeachComment(BeachComment updatedBeachComment) throws SurfingDaoException, InvalidIdException {

        if (updatedBeachComment == null || updatedBeachComment.getBeach() == null || updatedBeachComment.getUser() == null) {
            throw new SurfingDaoException("Beach to Add is null.");
        }

        try {
            Beach beachToCheck = getBeachById(updatedBeachComment.getBeach().getId());
            SiteUser userToCheck = userDao.getUserById(updatedBeachComment.getUser().getId());
        } catch (InvalidIdException ex) {
            throw new InvalidIdException("BeachId or UserId not found", ex);
        }

        String updateStatement = "Update beach_comment \n"
                + "Set userId = ?,\n"
                + "beachid = ?,\n"
                + "`comment` = ?\n"
                + "Where id = ?";

        int rowsAffected = template.update(updateStatement,
                updatedBeachComment.getUser().getId(),
                updatedBeachComment.getBeach().getId(),
                updatedBeachComment.getCommentText(),
                updatedBeachComment.getId());

        if (rowsAffected == 0) {
            throw new InvalidIdException("Could not edit Beach Comment with id = " + updatedBeachComment.getId());
        }

        if (rowsAffected > 1) {
            throw new SurfingDaoException("ERROR: Beach Comment Id IS NOT UNIQUE FOR Beach Comment TABLE.");
        }
    }

    @Override
    public void deleteBeachComment(int id) throws InvalidIdException {

        try {
            BeachComment toCheck = getBeachCommentById(id);
        } catch (InvalidIdException ex) {
            throw new InvalidIdException("Invalid Beach Comment Id requested.");
        }

        String deleteStatement = "Delete \n"
                + "From beach_comment \n"
                + "Where id = ?";

        template.update(deleteStatement, id);
    }

    @Override
    public List<BreakComment> getAllBreakComments() {

        String query = "Select brc.id commentid, brc.userid, brc.breakid, brc.`comment`, br.`name` breakname, br.beachid, br.latitude, br.longitude, br.blog, be.`name` beachname, be.zipcode, u.username, u.`password`, u.enabled\n"
                + "From break_comment brc\n"
                + "Inner Join `User` u On brc.userid = u.id\n"
                + "Inner Join break br On brc.breakid = br.id\n"
                + "Inner Join beach be On br.beachid = be.id";

        List<BreakComment> toReturn = template.query(query, new BreakCommentMapper());

        return toReturn;
    }

    @Override
    public BreakComment getBreakCommentById(int id) throws InvalidIdException {

        String query = "Select brc.id commentid, brc.userid, brc.breakid, brc.`comment`, br.`name` breakname, br.beachid, br.latitude, br.longitude, br.blog, be.`name` beachname, be.zipcode, u.username, u.`password`, u.enabled\n"
                + "From break_comment brc\n"
                + "Inner Join `User` u On brc.userid = u.id\n"
                + "Inner Join break br On brc.breakid = br.id\n"
                + "Inner Join beach be On br.beachid = be.id\n"
                + "Where brc.id = ?";

        BreakComment toReturn = null;
        try {
            toReturn = template.queryForObject(query, new BreakCommentMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
        }

        if (toReturn == null) {
            throw new InvalidIdException("Invalid BreakComment Id.");
        }

        return toReturn;
    }

    @Override
    public BreakComment addBreakComment(BreakComment toAdd) throws SurfingDaoException, InvalidIdException {

        if (toAdd == null || toAdd.getBeachBreak() == null || toAdd.getUser() == null || toAdd.getBeachBreak().getBeach() == null) {
            throw new SurfingDaoException("Break Comment to add is null.");
        }

        try {
            Break breakToCheck = getBreakById(toAdd.getBeachBreak().getId());
            Beach beachToCheck = getBeachById(toAdd.getBeachBreak().getBeach().getId());
            SiteUser userToCheck = userDao.getUserById(toAdd.getUser().getId());
        } catch (InvalidIdException ex) {
            throw new InvalidIdException("BeachId, UserId, or BreakId not found", ex);
        }

        String insert = "Insert into break_comment(userid, breakid, `comment`)\n"
                + "Values \n"
                + "(?, ?, ?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement toReturn = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

                toReturn.setInt(1, toAdd.getUser().getId());
                toReturn.setInt(2, toAdd.getBeachBreak().getId());
                toReturn.setString(3, toAdd.getCommentText());

                return toReturn;
            }
        };

        template.update(psc, holder);
        int newBreakCommentId = holder.getKey().intValue();

        toAdd.setId(newBreakCommentId);

        return toAdd;

    }

    @Override
    public void updateBreakComment(BreakComment updatedBreakComment) throws SurfingDaoException, InvalidIdException {

        if (updatedBreakComment == null || updatedBreakComment.getBeachBreak() == null || updatedBreakComment.getUser() == null || updatedBreakComment.getBeachBreak().getBeach() == null) {
            throw new SurfingDaoException("Beach Comment to add is null.");
        }

        try {
            Break breakToCheck = getBreakById(updatedBreakComment.getBeachBreak().getId());
            Beach beachToCheck = getBeachById(updatedBreakComment.getBeachBreak().getBeach().getId());
            SiteUser userToCheck = userDao.getUserById(updatedBreakComment.getUser().getId());
        } catch (InvalidIdException ex) {
            throw new InvalidIdException("BeachId, UserId, or BreakId not found", ex);
        }

        String updateStatement = "Update break_comment \n"
                + "Set userId = ?,\n"
                + "breakid = ?,\n"
                + "`comment` = ?\n"
                + "Where id = ?";

        int rowsAffected = template.update(updateStatement,
                updatedBreakComment.getUser().getId(),
                updatedBreakComment.getBeachBreak().getId(),
                updatedBreakComment.getCommentText(),
                updatedBreakComment.getId());

        if (rowsAffected == 0) {
            throw new InvalidIdException("Could not edit Break Comment with id = " + updatedBreakComment.getId());
        }

        if (rowsAffected > 1) {
            throw new SurfingDaoException("ERROR: Break Comment Id IS NOT UNIQUE FOR Break Comment TABLE.");
        }
    }

    @Override
    public void deleteBreakComment(int id) throws InvalidIdException {

        try {
            BreakComment toCheck = getBreakCommentById(id);
        } catch (InvalidIdException ex) {
            throw new InvalidIdException("Invalid Break Comment Id requested.");
        }

        String deleteStatement = "Delete \n"
                + "From break_comment \n"
                + "Where id = ?";

        template.update(deleteStatement, id);
    }

    @Override
    public void deleteAllTables() {

        String deleteNews = "Delete \n"
                + "From home_news_link \n"
                + "Where id > 0";

        String deleteBeachComments = "Delete \n"
                + "From beach_comment \n"
                + "Where id > 0";

        String deleteBreakComments = "Delete \n"
                + "From break_comment \n"
                + "Where id > 0";

        String deleteBreaks = "Delete \n"
                + "From break \n"
                + "Where id > 0";

        String deleteBeaches = "Delete \n"
                + "From beach \n"
                + "Where id > 0";

        template.update(deleteNews);
        template.update(deleteBeachComments);
        template.update(deleteBreakComments);
        template.update(deleteBreaks);
        template.update(deleteBeaches);

    }

    private class NewsMapper implements RowMapper<News> {

        @Override
        public News mapRow(ResultSet results, int rowNum) throws SQLException {
            News toReturn = new News();
            toReturn.setId(results.getInt("Id"));
            toReturn.setNewsURL(results.getString("News_url"));
            toReturn.setNewsAbbrText(results.getString("News_link_text"));
            toReturn.setPicURL(results.getString("Picture_url"));
            toReturn.setIsActive(results.getBoolean("IsActive"));

            return toReturn;
        }
    }

    private class BeachMapper implements RowMapper<Beach> {

        @Override
        public Beach mapRow(ResultSet results, int rowNum) throws SQLException {
            Beach toReturn = new Beach();
            toReturn.setId(results.getInt("Id"));
            toReturn.setName(results.getString("Name"));
            toReturn.setZipCode(results.getInt("Zipcode"));

            return toReturn;
        }
    }

    private class BreakMapper implements RowMapper<Break> {

        @Override
        public Break mapRow(ResultSet results, int rowNum) throws SQLException {
            Break toReturn = new Break();
            toReturn.setId(results.getInt("BreakId"));
            toReturn.setName(results.getString("BreakName"));
            toReturn.setLatitude(results.getBigDecimal("Latitude"));
            toReturn.setLongitude(results.getBigDecimal("Longitude"));
            toReturn.setBlog(results.getString("Blog"));

            Beach toAdd = new Beach();
            toAdd.setId(results.getInt("BeachId"));
            toAdd.setName(results.getString("BeachName"));
            toAdd.setZipCode(results.getInt("Zipcode"));
            toReturn.setBeach(toAdd);

            return toReturn;
        }
    }

    private class BeachCommentMapper implements RowMapper<BeachComment> {

        @Override
        public BeachComment mapRow(ResultSet results, int rowNum) throws SQLException {
            BeachComment toReturn = new BeachComment();
            toReturn.setId(results.getInt("CommentId"));
            toReturn.setCommentText(results.getString("Comment"));

            SiteUser user = new SiteUser();
            user.setId(results.getInt("Userid"));
            user.setUsername(results.getString("Username"));
            user.setPassword(results.getString("Password"));
            user.setEnabled(results.getBoolean("Enabled"));
            toReturn.setUser(user);

            Beach beachToAdd = new Beach();
            beachToAdd.setId(results.getInt("BeachId"));
            beachToAdd.setName(results.getString("BeachName"));
            beachToAdd.setZipCode(results.getInt("Zipcode"));
            toReturn.setBeach(beachToAdd);

            return toReturn;
        }
    }

    private class BreakCommentMapper implements RowMapper<BreakComment> {

        @Override
        public BreakComment mapRow(ResultSet results, int rowNum) throws SQLException {
            BreakComment toReturn = new BreakComment();
            toReturn.setId(results.getInt("CommentId"));
            toReturn.setCommentText(results.getString("Comment"));

            SiteUser user = new SiteUser();
            user.setId(results.getInt("Userid"));
            user.setUsername(results.getString("Username"));
            user.setPassword(results.getString("Password"));
            user.setEnabled(results.getBoolean("Enabled"));
            toReturn.setUser(user);

            Beach beachToAdd = new Beach();
            beachToAdd.setId(results.getInt("BeachId"));
            beachToAdd.setName(results.getString("BeachName"));
            beachToAdd.setZipCode(results.getInt("Zipcode"));

            Break breakToAdd = new Break();
            breakToAdd.setId(results.getInt("BreakId"));
            breakToAdd.setName(results.getString("BreakName"));
            breakToAdd.setBeach(beachToAdd);
            breakToAdd.setLatitude(results.getBigDecimal("Latitude"));
            breakToAdd.setLongitude(results.getBigDecimal("Longitude"));
            breakToAdd.setBlog(results.getString("Blog"));
            toReturn.setBeachBreak(breakToAdd);

            return toReturn;
        }
    }

}
