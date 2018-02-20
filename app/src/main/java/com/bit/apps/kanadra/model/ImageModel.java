package com.bit.apps.kanadra.model;

/**
 * Created by ahmed on 2/13/2018.
 */

public class ImageModel {
    private String image_drawable;
    private String title;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {

        return title;
    }

    public String getImage_drawable() {
        return image_drawable;
    }
    public void setImage_drawable(String image_drawable) {
        this.image_drawable = image_drawable;
    }
}