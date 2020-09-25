package org.mehdi.nezamipour.skybeat.controller.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.mehdi.nezamipour.skybeat.controller.fragments.PlaySongFragment;
import org.mehdi.nezamipour.skybeat.models.Album;
import org.mehdi.nezamipour.skybeat.models.Artist;
import org.mehdi.nezamipour.skybeat.models.Audio;
import org.mehdi.nezamipour.skybeat.repositories.AudioRepository;
import org.mehdi.nezamipour.skybeat.utils.AudioUtils;

import java.io.IOException;
import java.util.ArrayList;

public class MediaPlayerService extends Service implements MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnBufferingUpdateListener, AudioManager.OnAudioFocusChangeListener {

    public static final String EXTRA_ALBUM = " org.mehdi.nezamipour.skybeat.album";
    public static final String EXTRA_ARTIST = " org.mehdi.nezamipour.skybeat.artist";
    public static final String EXTRA_AUDIO_INDEX = "audioIndex";

    private AudioRepository mRepository;
    private final IBinder mBinder = new LocalBinder();

    private int mAudioIndex = -1;
    private int mResumePosition;
    private ArrayList<Audio> mAudioList;
    private MediaPlayer mMediaPlayer = new MediaPlayer();
    private AudioManager mAudioManager;
    private Audio mActiveAudio;
    private Album mAlbum;
    private Artist mArtist;


    public static Intent newIntent(Context context) {
        return new Intent(context, MediaPlayerService.class);
    }

    public static Intent newIntent(Context context, Album album) {
        Intent intent = new Intent(context, MediaPlayerService.class);
        intent.putExtra("album", album);
        return intent;
    }

    public static Intent newIntent(Context context, Artist artist) {
        Intent intent = new Intent(context, MediaPlayerService.class);
        intent.putExtra("artist", artist);
        return intent;
    }


    public MediaPlayerService() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    //The system calls this method when an activity, requests the service be started
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mRepository = AudioRepository.getInstance(getApplicationContext());
        mAudioList = mRepository.getAudioList();


        try {
            mAudioIndex = intent.getIntExtra(EXTRA_AUDIO_INDEX, -1);
            mAlbum = (Album) intent.getSerializableExtra(EXTRA_ALBUM);
            mArtist = (Artist) intent.getSerializableExtra(EXTRA_ARTIST);
            if (mAlbum != null)
                mAudioList = AudioUtils.extractSongsOfAlbum(getApplicationContext(), mAlbum);
            else if (mArtist != null) {
                mAudioList = AudioUtils.extractSongsOfArtist(getApplicationContext(), mArtist);
            } else
                mAudioList = mRepository.getAudioList();

            if (mAudioIndex != -1 && mAudioIndex < mAudioList.size()) {

                mActiveAudio = mAudioList.get(mAudioIndex);
                if (mMediaPlayer.isPlaying()){
                    mMediaPlayer.stop();
                }
                    initMediaPlayer();
            } else {
                stopSelf();
            }
        } catch (NullPointerException e) {
            stopSelf();
        }
        //Request audio focus
        if (!requestAudioFocus()) {
            //Could not gain focus
            stopSelf();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            stopMedia();
            mMediaPlayer.release();
        }
        removeAudioFocus();
    }

    private void initMediaPlayer() {
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnBufferingUpdateListener(this);

        //Reset so that the MediaPlayer is not pointing to another data source
        mMediaPlayer.reset();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            // Set the data source to the mediaFile location
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.setDataSource(mActiveAudio.getData());
        } catch (IOException e) {
            e.printStackTrace();
            stopSelf();
        }
        mMediaPlayer.prepareAsync();
    }


    //Handle actions of media player public methods that client use this
    public void skipToNext() {
        if (mAudioIndex == mAudioList.size() - 1) {
            mAudioIndex = 0;
            mActiveAudio = mAudioList.get(mAudioIndex);

        } else {
            mActiveAudio = mAudioList.get(++mAudioIndex);
        }

        stopMedia();
        mMediaPlayer.reset();
        initMediaPlayer();
    }

    public void skipToPrevious() {
        if (mAudioIndex == 0) {
            mAudioIndex = mAudioList.size() - 1;
            mActiveAudio = mAudioList.get(mAudioIndex);
        } else {
            mActiveAudio = mAudioList.get(--mAudioIndex);
        }

        stopMedia();
        mMediaPlayer.reset();
        initMediaPlayer();
    }

    public void playMedia() {
        if (!mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
        }
    }

    public void stopMedia() {
        if (mMediaPlayer == null)
            return;
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
    }

    public void pauseMedia() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mResumePosition = mMediaPlayer.getCurrentPosition();
        }
    }

    public void resumeMedia() {
        if (!mMediaPlayer.isPlaying()) {
            mMediaPlayer.seekTo(mResumePosition);
            mMediaPlayer.start();
        }
    }

    public int getMediaDuration() {
        return mMediaPlayer.getDuration();
    }


    public int getMediaCurrentPos() {
        return mMediaPlayer.getCurrentPosition();
    }

    public void seekMediaToPos(int pos) {
        mMediaPlayer.seekTo(pos);
    }


    @Override
    public void onAudioFocusChange(int focusChange) {
        //Invoked when the audio focus of the system is updated.
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                // resume playback
                if (mMediaPlayer == null)
                    initMediaPlayer();
                else if (!mMediaPlayer.isPlaying())
                    mMediaPlayer.start();
                mMediaPlayer.setVolume(1.0f, 1.0f);
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                // Lost focus for an unbounded amount of time: stop playback and release media player
                if (mMediaPlayer.isPlaying())
                    mMediaPlayer.stop();
                mMediaPlayer.release();
                mMediaPlayer = null;
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                // Lost focus for a short time, but we have to stop
                // playback. We don't release the media player because playback
                // is likely to resume
                if (mMediaPlayer.isPlaying())
                    mMediaPlayer.pause();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                // Lost focus for a short time, but it's ok to keep playing
                // at an attenuated level
                if (mMediaPlayer.isPlaying())
                    mMediaPlayer.setVolume(0.1f, 0.1f);
                break;
        }
    }

    private boolean requestAudioFocus() {
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int result = mAudioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            //Focus gained
            return true;
        }
        //Could not gain focus
        return false;
    }

    private boolean removeAudioFocus() {
        return AudioManager.AUDIOFOCUS_REQUEST_GRANTED == mAudioManager.abandonAudioFocus(this);
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        //TODO LATER FOR ONLINE MUSIC SERVICE
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        skipToNext();
    }


    @Override
    public void onPrepared(MediaPlayer mp) {
        playMedia();
    }

    public class LocalBinder extends Binder {
        public MediaPlayerService getService() {
            return MediaPlayerService.this;
        }

    }


}
