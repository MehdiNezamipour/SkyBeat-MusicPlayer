package org.mehdi.nezamipour.skybeat.models;

import java.io.Serializable;

public class Album implements Serializable {


    private String mId;
    private String mTitle;
    private String mArtist;
    private int mSongsNumber;
    private String mAlbumArt;


    public Album(String id, String title, String artist, int songsNumber,String albumArt) {
        mId = id;
        mTitle = title;
        mArtist = artist;
        mSongsNumber = songsNumber;
        mAlbumArt = albumArt;
    }

    public String getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getArtist() {
        return mArtist;
    }

    public int getSongsNumber() {
        return mSongsNumber;
    }

    public String getAlbumArt() {
        return mAlbumArt;
    }
}
