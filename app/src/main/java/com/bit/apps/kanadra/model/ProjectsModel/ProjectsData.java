package com.bit.apps.kanadra.model.ProjectsModel;

import java.util.List;

/**
 * Created by ahmed on 2/18/2018.
 */
public class ProjectsData{
    private String timestamp;

    private String content;

    private String id;
    private String facebook;
    private String twitter;

    public String getFacebook() {
        return facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public String getInstagram() {
        return instagram;
    }

    private String instagram;

    private String lon;

    private String phone;

    private String title;

    private String expire_at;

    private String status;

    private String website;

    private String social;

    private List<Images> images;

    private String f_user_id;

    private String lat;

    public void setImages(List<Images> images) {
        this.images = images;
    }

    public List<Images> getImages() {

        return images;
    }



    public String getTimestamp ()
    {
        return timestamp;
    }

    public void setTimestamp (String timestamp)
    {
        this.timestamp = timestamp;
    }

    public String getContent ()
    {
        return content;
    }

    public void setContent (String content)
    {
        this.content = content;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getLon ()
    {
        return lon;
    }

    public void setLon (String lon)
    {
        this.lon = lon;
    }

    public String getPhone ()
    {
        return phone;
    }

    public void setPhone (String phone)
    {
        this.phone = phone;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getExpire_at ()
    {
        return expire_at;
    }

    public void setExpire_at (String expire_at)
    {
        this.expire_at = expire_at;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getWebsite ()
    {
        return website;
    }

    public void setWebsite (String website)
    {
        this.website = website;
    }

    public String getSocial ()
    {
        return social;
    }

    public void setSocial (String social)
    {
        this.social = social;
    }

    public String getF_user_id ()
    {
        return f_user_id;
    }

    public void setF_user_id (String f_user_id)
    {
        this.f_user_id = f_user_id;
    }

    public String getLat ()
    {
        return lat;
    }

    public void setLat (String lat)
    {
        this.lat = lat;
    }
}
