package org.mehdi.nezamipour.skybeat.repositories;

import android.content.Context;

import org.mehdi.nezamipour.skybeat.models.Album;
import org.mehdi.nezamipour.skybeat.models.Artist;
import org.mehdi.nezamipour.skybeat.models.Audio;
import org.mehdi.nezamipour.skybeat.utils.AudioUtils;

import java.util.ArrayList;

public class AudioRepository {

    private static AudioRepository sRepository;
    private ArrayList<Audio> mAudioList;
    private ArrayList<Album> mAlbumList;
    private ArrayList<Artist> mArtistList;

    private AudioRepository(Context context) {
        mAudioList = AudioUtils.loadAudio(context);
        mAlbumList = AudioUtils.loadAlbum(context);
        mArtistList = AudioUtils.loadArtist(context);
    }

    public static AudioRepository getInstance(Context context) {
        if (sRepository == null)
            sRepository = new AudioRepository(context);
        return sRepository;
    }

    public ArrayList<Audio> getAudioList() {
        return mAudioList;
    }

    public ArrayList<Album> getAlbumList() {
        return mAlbumList;
    }

    public ArrayList<Artist> getArtistList() {
        return mArtistList;
    }

    public void updateRepository(Context context) {
        mAudioList = AudioUtils.loadAudio(context);
        mAlbumList = AudioUtils.loadAlbum(context);
        mArtistList = AudioUtils.loadArtist(context);
    }





}
