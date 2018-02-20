package com.bit.apps.kanadra.model;

/**
 * Created by ahmed on 02/01/18.
 */

public class Card {
    private String title;
    private int image;

    public Card(String title, int image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
