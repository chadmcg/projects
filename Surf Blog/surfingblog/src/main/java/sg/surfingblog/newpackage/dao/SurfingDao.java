/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.surfingblog.newpackage.dao;

import java.util.List;
import sg.surfingblog.newpackage.models.Beach;
import sg.surfingblog.newpackage.models.Break;
import sg.surfingblog.newpackage.models.BeachComment;
import sg.surfingblog.newpackage.models.BreakComment;
import sg.surfingblog.newpackage.models.News;

/**
 *
 * @author blee0
 */
public interface SurfingDao {
    
    List<Break> getBreaksByBeach(int id) throws InvalidIdException;
    
    List<BeachComment> getCommentsByBeach(int id) throws InvalidIdException;
    
    List<BreakComment> getCommentsByBreak(int id) throws InvalidIdException;

    List<News> getAllActiveNews();
    
    List<News> getAllNews();

    News getNewsById(int id) throws InvalidIdException;

    News addNews(News toAdd) throws SurfingDaoException;

    void updateNews(News updatedNews) throws SurfingDaoException, InvalidIdException;

    void deleteNews(int id) throws InvalidIdException;

    List<Beach> getAllBeaches();

    Beach getBeachById(int id) throws InvalidIdException;

    Beach addBeach(Beach toAdd) throws SurfingDaoException;

    void updateBeach(Beach updatedBeach) throws SurfingDaoException, InvalidIdException;

    void deleteBeach(int id) throws InvalidIdException;

    List<Break> getAllBreaks();

    Break getBreakById(int id) throws InvalidIdException;

    Break addBreak(Break toAdd) throws SurfingDaoException, InvalidIdException;

    void updateBreak(Break updatedBreak) throws SurfingDaoException, InvalidIdException;

    void deleteBreak(int id) throws InvalidIdException;

    List<BeachComment> getAllBeachComments();

    BeachComment getBeachCommentById(int id) throws InvalidIdException;

    BeachComment addBeachComment(BeachComment toAdd) throws SurfingDaoException, InvalidIdException;

    void updateBeachComment(BeachComment updatedBeachComment) throws SurfingDaoException, InvalidIdException;

    void deleteBeachComment(int id) throws InvalidIdException;
    
    List<BreakComment> getAllBreakComments();

    BreakComment getBreakCommentById(int id) throws InvalidIdException;

    BreakComment addBreakComment(BreakComment toAdd) throws SurfingDaoException, InvalidIdException;

    void updateBreakComment(BreakComment updatedBreakComment) throws SurfingDaoException, InvalidIdException ;

    void deleteBreakComment(int id) throws InvalidIdException;
    
    void deleteAllTables();

}

