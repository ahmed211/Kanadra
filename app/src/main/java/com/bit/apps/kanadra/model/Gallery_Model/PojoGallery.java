package com.bit.apps.kanadra.model.Gallery_Model;

import java.util.List;

/**
 * Created by ahmed on 2/3/2018.
 */

public class PojoGallery {


//    private Videos videos;
//
//    private Images images;
//
//    public Videos getVideos ()
//    {
//        return videos;
//    }
//
//    public void setVideos (Videos videos)
//    {
//        this.videos = videos;
//    }
//
//    public Images getImages ()
//    {
//        return images;
//    }
//
//    public void setImages (Images images)
//    {
//        this.images = images;
//    }


    private List<Gallery> images, videos;
    public void setImages(List<Gallery> images) {
        this.images = images;
    }

    public void setVideos(List<Gallery> videos) {
        this.videos = videos;
    }

    public List<Gallery> getImages() {

        return images;
    }

    public List<Gallery> getVideos() {
        return videos;
    }
}
