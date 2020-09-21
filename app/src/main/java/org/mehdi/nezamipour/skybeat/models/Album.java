package org.mehdi.nezamipour.skybeat.models;

public class Album {


    private String mTitle;
    private String mArtist;
    private int mSongsNumber;


    public Album(String title, String artist, int songsNumber) {
        mTitle = title;
        mArtist = artist;
        mSongsNumber = songsNumber;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        mArtist = artist;
    }

    public int getSongsNumber() {
        return mSongsNumber;
    }

    public void setSongsNumber(int songsNumber) {
        mSongsNumber = songsNumber;
    }
}
