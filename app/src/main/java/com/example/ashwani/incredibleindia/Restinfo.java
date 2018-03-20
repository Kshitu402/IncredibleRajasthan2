package com.example.ashwani.incredibleindia;


public class Restinfo {

    String restname;
    String restspeciality;
    float rating;
    String timing;

    public Restinfo(float rating, String restname, String restspeciality, String timing) {
        this.rating = rating;
        this.restname = restname;
        this.restspeciality = restspeciality;
        this.timing = timing;
    }
}
