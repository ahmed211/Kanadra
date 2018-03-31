package com.bit.apps.kanadra.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ahmed on 3/5/2018.
 */

public class ArrayListPhoto{
    @SerializedName("images")
    @Expose
    private ArrayList<String> images;
    public ArrayListPhoto(ArrayList<String> images) {
        this.images=images;
    }
}