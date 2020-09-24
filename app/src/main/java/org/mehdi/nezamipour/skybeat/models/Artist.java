package org.mehdi.nezamipour.skybeat.models;

import java.io.Serializable;

public class Artist implements Serializable {

    private String mArtistId;
    private String mName;
    private int mNumberOfSongs;


    public Artist(String name, int numberOfSongs, String artistId) {
        mName = name;
        mNumberOfSongs = numberOfSongs;
        mArtistId = artistId;
    }

    public String getArtistId() {
        return mArtistId;
    }

    public String getName() {
        return mName;
    }

    public int getNumberOfSongs() {
        return mNumberOfSongs;
    }
}
