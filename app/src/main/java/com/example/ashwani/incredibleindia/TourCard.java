package com.example.ashwani.incredibleindia;

/**
 * Created by ashwani on 29/9/17.
 */

public class TourCard {
    private String name;
    private String city;
    private int thumbnail;

    public TourCard(String name,String city, int thumbnail) {
        this.name = name;
        this.city = city;
        this.thumbnail = thumbnail;
    }

    public String getName(){
        return name;
    }

    public String getCity(){
        return city;
    }

    public int getThumbnail(){
        return thumbnail;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setCity(String city){
        this.city = city;
    }

    public void setThumbnail(int thumbnail){
        this.thumbnail = thumbnail;
    }
}
