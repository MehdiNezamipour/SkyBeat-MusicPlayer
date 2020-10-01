package org.mehdi.nezamipour.skybeat.models;

import java.io.Serializable;

public class Audio implements Serializable {

    private String mTitle;
    private String mAlbum;
    private String mArtist;
    private String mData;
    private String mSongId;
    private String mAlbumId;
    private String mArtistId;

    public Audio(String data, String title, String album, String artist, String songId, String albumId, String artistId) {
        mData = data;
        mTitle = title;
        mAlbum = album;
        mArtist = artist;
        mSongId = songId;
        mAlbumId = albumId;
        mArtistId = artistId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAlbum() {
        return mAlbum;
    }

    public String getArtist() {
        return mArtist;
    }

    public String getData() {
        return mData;
    }

    public String getSongId() {
        return mSongId;
    }

    public String getAlbumId() {
        return mAlbumId;
    }

    public String getArtistId() {
        return mArtistId;
    }
}