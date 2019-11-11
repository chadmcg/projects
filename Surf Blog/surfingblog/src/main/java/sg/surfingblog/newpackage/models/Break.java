/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.surfingblog.newpackage.models;

import java.math.BigDecimal;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.security.core.userdetails.User;

/**
 *
 * @author Chad
 */
public class Break {

    private int id;

    @NotBlank(message = "Name must not be empty.")
    private String name;

    private Beach beach;

    @NotNull(message = "Latitude name must not be empty.")
    @DecimalMin(value = "-90.000001", inclusive = false)
    @DecimalMax(value = "90.000001", inclusive = false)
    @Digits(integer = 9, fraction = 6, message = "Latitude must be 9 digits with 6 decimal places.")
    private BigDecimal latitude;

    @NotNull(message = "Longitude name must not be empty.")
    @DecimalMin(value = "-180.000001", inclusive = false)
    @DecimalMax(value = "180.000001", inclusive = false)
    @Digits(integer = 9, fraction = 6, message = "Longitude must be 9 digits with 6 decimal places.")
    private BigDecimal longitude;

    @NotBlank(message = "Blog must not be empty.")
    private String blog;

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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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
     * @return the latitude
     */
    public BigDecimal getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public BigDecimal getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the blog
     */
    public String getBlog() {
        return blog;
    }

    /**
     * @param blog the blog to set
     */
    public void setBlog(String blog) {
        this.blog = blog;
    }

}
