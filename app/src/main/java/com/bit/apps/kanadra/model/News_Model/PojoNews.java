package com.bit.apps.kanadra.model.News_Model;

import com.bit.apps.kanadra.model.Diwan_Model.DiwanItem;
import com.bit.apps.kanadra.model.ProjectsModel.ProjectsData;

import java.util.List;

/**
 * Created by ahmed on 2/14/2018.
 */

public class PojoNews {
    List<Item> news;
    List<Item> events;

    List<ProjectsData> projects;
    List<Championships> data;
    List<DiwanItem> diwan;

    public void setProjects(List<ProjectsData> projects) {
        this.projects = projects;
    }

    public List<ProjectsData> getProjects() {

        return projects;
    }



    public void setEvents(List<Item> events) {
        this.events = events;
    }

    public List<Item> getEvents() {

        return events;
    }


    public void setDiwan(List<DiwanItem> diwan) {
        this.diwan = diwan;
    }

    public List<DiwanItem> getDiwan() {

        return diwan;
    }

    public void setNews(List<Item> news) {
        this.news = news;
    }

    public List<Item> getNews() {

        return news;
    }

    public void setData(List<Championships> data) {
        this.data = data;
    }

    public List<Championships> getData() {

        return data;
    }
}
