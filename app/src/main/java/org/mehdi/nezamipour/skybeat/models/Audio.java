package org.mehdi.nezamipour.skybeat.models;

import java.io.Serializable;

public class Audio implements Serializable {

    private String mTitle;
    private String mAlbum;
    private String mArtist;
    private String mData;

    public Audio(String data, String title, String album, String artist) {
        this.mData = data;
        this.mTitle = title;
        this.mAlbum = album;
        this.mArtist = artist;
    }

    public String getData() {
        return mData;
    }

    public void setData(String data) {
        this.mData = data;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getAlbum() {
        return mAlbum;
    }

    public void setAlbum(String album) {
        this.mAlbum = album;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        this.mArtist = artist;
    }
}