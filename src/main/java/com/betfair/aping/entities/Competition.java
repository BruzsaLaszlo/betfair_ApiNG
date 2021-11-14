package com.betfair.aping.entities;

public class Competition {

    private String id;
    private String name;


    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return "Competition {" + '\n' +
                "id='" + id + '\'' +'\n' +
                ", name='" + name + '\'' +'\n' +
                '}';
    }

}
