package com.bit.apps.kanadra.model.Home_Model;

import com.bit.apps.kanadra.model.News_Model.Championships;

import java.util.List;

public class PojoHome
{
    private List<News> news;

    private List<Events> events;

    private List<Championships> championships;

    public List<News> getNews() {
        return news;
    }

    public List<Events> getEvents() {
        return events;
    }

    public List<Championships> getChampionships() {
        return championships;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    public void setEvents(List<Events> events) {
        this.events = events;
    }

    public void setChampionships(List<Championships> championships) {
        this.championships = championships;
    }
}