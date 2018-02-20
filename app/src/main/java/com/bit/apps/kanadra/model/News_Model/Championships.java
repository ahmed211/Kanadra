package com.bit.apps.kanadra.model.News_Model;

public class Championships
{
    private String id;

    private String name;

    private String date;

    private String media;
    private String note;

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {

        return note;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
    }

    public String getMedia ()
    {
        return media;
    }

    public void setMedia (String media)
    {
        this.media = media;
    }

}