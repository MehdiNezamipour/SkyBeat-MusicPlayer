package org.mehdi.nezamipour.skybeat.models;

import android.content.Context;

import org.mehdi.nezamipour.skybeat.utils.AudioUtils;

import java.io.Serializable;
import java.util.List;

public class Artist implements Serializable {

    private String mArtistId;
    private String mName;
    private int mNumberOfSongs;
    private List<Audio> mAudios;


    public Artist(String name, int numberOfSongs, String artistId) {
        mName = name;
        mNumberOfSongs = numberOfSongs;
        mArtistId = artistId;
    }

    public List<Audio> getAudios(Context context) {
        mAudios = AudioUtils.extractSongsOfArtist(context, mArtistId);
        return mAudios;
    }

    public String getArtistId() {
        return mArtistId;
    }

    public String getName() {
        return mName;
    }

    public int getNumberOfSongs(Context context) {
        mNumberOfSongs = AudioUtils.extractSongsOfArtist(context, mArtistId).size();
        return mNumberOfSongs;
    }

}
