/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.surfingblog.newpackage.models;

/**
 *
 * @author Chad
 */
public class BeachComment {

    private int id;

    private SiteUser user;

    private Beach beach;

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
     * @return the beach
     */
    public Beach getBeach() {
        return beach;
    }

    /**
     * @param beach the beach to set
     */
    public void setBeach(Beach beach) {
        this.beach = beach;
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
