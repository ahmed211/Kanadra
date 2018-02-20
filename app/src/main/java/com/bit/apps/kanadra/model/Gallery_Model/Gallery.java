package com.bit.apps.kanadra.model.Gallery_Model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

public class Gallery {

    private String id;
    private String title;
    private String path;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getPath ()
    {
        return path;
    }

    public void setPath (String path)
    {
        this.path = path;
    }
}
