package org.mehdi.nezamipour.skybeat.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import org.mehdi.nezamipour.skybeat.controller.services.MediaPlayerService;
import org.mehdi.nezamipour.skybeat.models.Album;
import org.mehdi.nezamipour.skybeat.models.Artist;
import org.mehdi.nezamipour.skybeat.models.Audio;
import org.mehdi.nezamipour.skybeat.repositories.AudioRepository;

import java.util.ArrayList;

public class AudioUtils {


    public static ArrayList<Audio> loadAudio(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        ArrayList<Audio> audioList = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cursor = contentResolver.query(uri, null, selection, null, sortOrder);

        if (cursor != null && cursor.getCount() > 0) {
            audioList = new ArrayList<>();
            while (cursor.moveToNext()) {

                String songId = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String albumId = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                String artistId = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));

                // Save to audioList
                audioList.add(new Audio(data, title, album, artist, songId, albumId, artistId));
            }
        }
        assert cursor != null;
        cursor.close();
        return audioList;
    }

    public static ArrayList<Album> loadAlbum(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        ArrayList<Album> albumList = new ArrayList<>();
        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]
                {
                        MediaStore.Audio.Albums._ID,
                        MediaStore.Audio.Albums.ALBUM,
                        MediaStore.Audio.Albums.ARTIST,
                        MediaStore.Audio.Albums.NUMBER_OF_SONGS,
                        MediaStore.Audio.Albums.ALBUM_ART
                };

        String sortOrder = MediaStore.Audio.Albums.ALBUM + " ASC";
        Cursor cursor = contentResolver.query(uri, projection, null, null, sortOrder);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums._ID));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));
                String albumArt = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                int numberOfSongs = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS));

                // Save to albumList
                albumList.add(new Album(id, title, artist, numberOfSongs, albumArt));
            }
        }
        assert cursor != null;
        cursor.close();
        return albumList;
    }

    public static ArrayList<Artist> loadArtist(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        ArrayList<Artist> artistList = new ArrayList<>();
        Uri uri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]
                {
                        MediaStore.Audio.Artists._ID,
                        MediaStore.Audio.Artists.ARTIST,
                        MediaStore.Audio.Artists.NUMBER_OF_TRACKS,
                };
        String sortOrder = MediaStore.Audio.Artists.ARTIST + " ASC";
        Cursor cursor = contentResolver.query(uri, projection, null, null, sortOrder);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String artistId = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists._ID));
                String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST));
                int numberOfSongs = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS));

                // Save to albumList
                artistList.add(new Artist(name, numberOfSongs, artistId));
            }
        }
        assert cursor != null;
        cursor.close();
        return artistList;
    }


    public static ArrayList<Audio> extractSongsOfAlbum(Context context, Album album) {
        AudioRepository audioRepository = AudioRepository.getInstance(context);
        ArrayList<Audio> songsOfAlbum = new ArrayList<>();
        for (Audio audio : audioRepository.getAudioList()) {
            if (audio.getAlbumId().equals(album.getId())) {
                songsOfAlbum.add(audio);
            }
        }
        return songsOfAlbum;
    }

    public static ArrayList<Audio> extractSongsOfArtist(Context context, Artist artist) {
        AudioRepository audioRepository = AudioRepository.getInstance(context);
        ArrayList<Audio> songsOfArtist = new ArrayList<>();
        for (Audio audio : audioRepository.getAudioList()) {
            if (audio.getArtistId().equals(artist.getArtistId())) {
                songsOfArtist.add(audio);
            }
        }
        return songsOfArtist;
    }

}


