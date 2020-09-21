package org.mehdi.nezamipour.skybeat.models;

public class Artist {
    private String mName;
    private int mNumberOfSongs;


    public Artist(String name, int numberOfSongs) {
        mName = name;
        mNumberOfSongs = numberOfSongs;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getNumberOfSongs() {
        return mNumberOfSongs;
    }

    public void setNumberOfSongs(int numberOfSongs) {
        mNumberOfSongs = numberOfSongs;
    }
}
