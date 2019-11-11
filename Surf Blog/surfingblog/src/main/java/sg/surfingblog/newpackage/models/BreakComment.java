/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.surfingblog.newpackage.models;

/**
 *
 * @author blee0
 */
public class BreakComment {

    private int id;

    private SiteUser user;

    private Break beachBreak;

    private String commentText;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the user
     */
    public SiteUser getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(SiteUser user) {
        this.user = user;
    }

    /**
     * @return the beachBreak
     */
    public Break getBeachBreak() {
        return beachBreak;
    }

    /**
     * @param beachBreak the beachBreak to set
     */
    public void setBeachBreak(Break beachBreak) {
        this.beachBreak = beachBreak;
    }

    /**
     * @return the commentText
     */
    public String getCommentText() {
        return commentText;
    }

    /**
     * @param commentText the commentText to set
     */
    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

}
