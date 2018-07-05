package com.example.dijonkariz.m_zito_hq;

import android.view.View;

public class Option {
    private String name;
    private int numOfOptions;
    private int thumbnail;

    public Option(){}

    public Option(String name, int numOfOptions, int thumbnail)
    {
        this.name = name;
        this.numOfOptions = numOfOptions;
        this.thumbnail = thumbnail;
    }

    public String getName(){ return name;}
    public void setName(String name){ this.name = name;}

    public int getNumOfOptions(){ return numOfOptions;}
    public void setNumOfOptions(int numOfOptions){ this.numOfOptions = numOfOptions;}

    public int getThumbnail(){ return thumbnail;}
    public void setThumbnail(int thumbnail){ this.thumbnail = thumbnail;}

}
