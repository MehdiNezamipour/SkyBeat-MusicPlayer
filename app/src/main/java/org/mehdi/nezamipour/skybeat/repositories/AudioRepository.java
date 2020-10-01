package org.mehdi.nezamipour.skybeat.repositories;

import android.content.Context;

import org.mehdi.nezamipour.skybeat.models.Album;
import org.mehdi.nezamipour.skybeat.models.Artist;
import org.mehdi.nezamipour.skybeat.models.Audio;
import org.mehdi.nezamipour.skybeat.utils.AudioUtils;

import java.util.ArrayList;
import java.util.List;

public class AudioRepository {

    private static final int READ_EXTERNAL_PER = 111;
    private static AudioRepository sRepository;
    private ArrayList<Audio> mAudioList;
    private ArrayList<Album> mAlbumList;
    private ArrayList<Artist> mArtistList;
    private List<Audio> mAudios;
    private Context mContext;

    private AudioRepository(Context context) {
        mContext = context;
    }

    public static AudioRepository getInstance(Context context) {
        if (sRepository == null)
            sRepository = new AudioRepository(context);
        return sRepository;
    }

    public List<Audio> getAudios() {
        return mAudios;
    }

    public void setAudios(List<Audio> audios) {
        mAudios = audios;
    }

    public ArrayList<Audio> getAudioList() {
        mAudioList = AudioUtils.loadAudio(mContext);
        return mAudioList;
    }

    public ArrayList<Album> getAlbumList() {
        mAlbumList = AudioUtils.loadAlbum(mContext);
        return mAlbumList;
    }

    public ArrayList<Artist> getArtistList() {
        mArtistList = AudioUtils.loadArtist(mContext);
        return mArtistList;
    }

/*
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

    }

    @Override
    public void onRationaleAccepted(int requestCode) {

    }

    @Override
    public void onRationaleDenied(int requestCode) {

    }*/
}
