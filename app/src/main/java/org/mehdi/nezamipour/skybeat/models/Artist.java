package org.mehdi.nezamipour.skybeat.models;

import org.mehdi.nezamipour.skybeat.utils.AudioUtils;

import java.io.Serializable;
import java.util.ArrayList;

public class Artist implements Serializable {

    private String mArtistId;
    private String mName;
    private int mNumberOfSongs;
    private ArrayList<String> mAudiosData;


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
