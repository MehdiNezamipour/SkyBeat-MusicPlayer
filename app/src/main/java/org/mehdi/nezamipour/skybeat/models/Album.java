package org.mehdi.nezamipour.skybeat.models;

import android.content.Context;

import org.mehdi.nezamipour.skybeat.utils.AudioUtils;

import java.io.Serializable;
import java.util.List;

public class Album implements Serializable {


    private String mId;
    private String mTitle;
    private String mArtist;
    private int mSongsNumber;
    private String mAlbumArt;
    private List<Audio> mAudios;


    public Album(String id, String title, String artist, int songsNumber, String albumArt) {
        mId = id;
        mTitle = title;
        mArtist = artist;
        mSongsNumber = songsNumber;
        mAlbumArt = albumArt;
    }

    public List<Audio> getAudios(Context context) {
        mAudios = AudioUtils.extractSongsOfAlbum(context, mId);
        return mAudios;
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

    public int getSongsNumber(Context context) {
        mSongsNumber = AudioUtils.extractSongsOfAlbum(context, mId).size();
        return mSongsNumber;
    }

    public String getAlbumArt() {
        return mAlbumArt;
    }
}
