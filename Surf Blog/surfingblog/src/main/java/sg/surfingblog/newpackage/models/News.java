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
public class News {

    private int id;

    private String newsURL;

    private String newsAbbrText;

    private String picURL;

    private Boolean isActive;

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
     * @return the newsURL
     */
    public String getNewsURL() {
        return newsURL;
    }

    /**
     * @param newsURL the newsURL to set
     */
    public void setNewsURL(String newsURL) {
        this.newsURL = newsURL;
    }

    /**
     * @return the newsAbbrText
     */
    public String getNewsAbbrText() {
        return newsAbbrText;
    }

    /**
     * @param newsAbbrText the newsAbbrText to set
     */
    public void setNewsAbbrText(String newsAbbrText) {
        this.newsAbbrText = newsAbbrText;
    }

    /**
     * @return the picURL
     */
    public String getPicURL() {
        return picURL;
    }

    /**
     * @param picURL the picURL to set
     */
    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }

    /**
     * @return the isActive
     */
    public Boolean getIsActive() {
        return isActive;
    }

    /**
     * @param isActive the isActive to set
     */
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

}
