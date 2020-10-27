package org.mehdi.nezamipour.skybeat.controller.fragments;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import org.mehdi.nezamipour.skybeat.R;
import org.mehdi.nezamipour.skybeat.controller.services.MediaPlayerService;
import org.mehdi.nezamipour.skybeat.enums.OrderOfPlay;
import org.mehdi.nezamipour.skybeat.models.Audio;
import org.mehdi.nezamipour.skybeat.repositories.AudioRepository;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class PlaySongFragment extends Fragment {

    public static final String ARG_AUDIO_INDEX = "org.mehdi.nezamipour.skybeat.audio";
    private static final String BUNDLE_SERVICE_STATE = "org.mehdi.nezamipour.skybeat.mServiceBound";
    public static final String EXTRA_AUDIO_INDEX = "audioIndex";
    public static final String BUNDLE_AUDIO_INDEX = "audioIndex";


    private MediaPlayerService mService;
    private boolean mBoundState = false;
    private boolean mMusicPlay = true;

    private ImageView mImageViewPlaySongMoreOption;
    private TextView mTextViewSongTitle;
    private TextView mTextViewSongArtist;
    private CircleImageView mCircleImageViewSongImage;
    private SeekBar mSeekBarSongProgress;
    private ImageButton mButtonPrevious;
    private ImageButton mButtonPlayOrStop;
    private ImageButton mButtonNext;
    private ImageButton mButtonOrderedOfPlay;


    private int mAudioIndex;
    private ArrayList<Audio> mAudioList;
    private Audio mAudio;
    private Handler mSeekBarHandler;
    private Integer mAudioDuration;

    public PlaySongFragment() {
        // Required empty public constructor
    }


    public static PlaySongFragment newInstance(int audioIndex) {
        PlaySongFragment fragment = new PlaySongFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_AUDIO_INDEX, audioIndex);
        fragment.setArguments(args);
        return fragment;
    }


    //Binding this Client to the AudioPlayer Service with bound service object in PlaySongFragment
    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MediaPlayerService.LocalBinder binder = (MediaPlayerService.LocalBinder) service;
            mService = binder.getService();
            mBoundState = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBoundState = false;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AudioRepository repository = AudioRepository.getInstance();
        if (getArguments() != null) {
            mAudioIndex = getArguments().getInt(ARG_AUDIO_INDEX);
        }
        if (savedInstanceState != null) {
            mBoundState = savedInstanceState.getBoolean(BUNDLE_SERVICE_STATE);
            mAudioIndex = savedInstanceState.getInt(BUNDLE_AUDIO_INDEX);
        }

        mAudioList = (ArrayList<Audio>) repository.getAudios();

        //Music Handler for methods
        mSeekBarHandler = new Handler();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_play_song, container, false);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(BUNDLE_SERVICE_STATE, mBoundState);
        savedInstanceState.putInt(BUNDLE_AUDIO_INDEX, mAudioIndex);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        playAudio(mAudioIndex);

        initUI();


        setListeners();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBoundState) {
            //getActivity().unbindService(mServiceConnection);
            //but not service self stop because we want to play song when fragment destroy
        }
    }


    private void playAudio(int audioIndex) {
        if (!mBoundState) {
            Intent playIntent = MediaPlayerService.newIntent(getContext());
            playIntent.putExtra(EXTRA_AUDIO_INDEX, audioIndex);
            getActivity().startService(playIntent);
            getActivity().bindService(playIntent, mServiceConnection, Context.BIND_AUTO_CREATE);

            mSeekBarSongProgress.setProgress(0);
            // Check if service bounded
            // Put data in it one time
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (mBoundState) { // Check if service bounded
                        mAudioDuration = mService.getMediaDuration();
                        mSeekBarSongProgress.setMax(mAudioDuration);
                        mSeekBarSongProgress.setProgress(mService.getMediaCurrentPos());

                    } else {
                        Log.v("Still waiting to bound", Boolean.toString(false));
                    }
                    mSeekBarHandler.postDelayed(this, 100);
                }
            };
            mSeekBarHandler.postDelayed(runnable, 100);
        }
    }


    private void findViews(View view) {
        mImageViewPlaySongMoreOption = view.findViewById(R.id.imageView_play_song_more_option);
        mTextViewSongTitle = view.findViewById(R.id.textView_play_song_title);
        mTextViewSongArtist = view.findViewById(R.id.textView_play_song_artist);
        mCircleImageViewSongImage = view.findViewById(R.id.circleImageView_song_image);
        mSeekBarSongProgress = view.findViewById(R.id.seekBar_music_progress);
        mButtonPrevious = view.findViewById(R.id.imageButton_previous);
        mButtonPlayOrStop = view.findViewById(R.id.imageButton_play_or_stop_song);
        mButtonNext = view.findViewById(R.id.imageButton_next);
        mButtonOrderedOfPlay = view.findViewById(R.id.button_ordered_of_play);

    }

    private void initUI() {
        mAudio = mAudioList.get(mAudioIndex);
        mTextViewSongTitle.setText(mAudio.getTitle());
        mTextViewSongArtist.setText(mAudio.getArtist());
        setSongImage();
    }

    private void updateUI() {
        mAudio = mAudioList.get(mAudioIndex);
        mTextViewSongTitle.setText(mAudio.getTitle());
        mTextViewSongArtist.setText(mAudio.getArtist());
        mButtonPlayOrStop.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_pause));
        setSongImage();

        mSeekBarSongProgress.setProgress(0);
        // Check if service bounded
        // Put data in it one time
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (mBoundState) { // Check if service bounded
                    mAudioDuration = mService.getMediaDuration();
                    mSeekBarSongProgress.setMax(mAudioDuration);
                    mSeekBarSongProgress.setProgress(mService.getMediaCurrentPos());

                } else {
                    Log.v("Still waiting to bound", Boolean.toString(false));
                }
                mSeekBarHandler.postDelayed(this, 100);
            }
        };
        mSeekBarHandler.postDelayed(runnable, 100);
    }

    private void setSongImage() {

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(mAudio.getData());
        byte[] art = retriever.getEmbeddedPicture();

        if (art != null) {
            mCircleImageViewSongImage.setImageBitmap(BitmapFactory.decodeByteArray(art, 0, art.length));
        } else {
            mCircleImageViewSongImage.setImageResource(R.drawable.pic);
        }
    }

    private void setListeners() {
        mImageViewPlaySongMoreOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO LATER
            }
        });
        mButtonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mService.skipToPrevious();
                    mAudioIndex = mService.getAudioIndex();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                updateUI();
            }
        });
        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mService.skipToNext();
                    mAudioIndex = mService.getAudioIndex();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                updateUI();
            }
        });
        mButtonPlayOrStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMusicPlay) {
                    mService.pauseMedia();
                    mMusicPlay = false;
                    mButtonPlayOrStop.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_play));
                } else {
                    mService.resumeMedia();
                    mMusicPlay = true;
                    mButtonPlayOrStop.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_pause));
                }
            }
        });
        mSeekBarSongProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mService.seekMediaToPos(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mButtonOrderedOfPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable currentDrawable = mButtonOrderedOfPlay.getDrawable();
                Drawable shuffle = getActivity().getResources().getDrawable(R.drawable.ic_shuffle_black);
                Drawable repeatList = getActivity().getResources().getDrawable(R.drawable.ic_repeat_list_black);
                Drawable repeatOne = getActivity().getResources().getDrawable(R.drawable.ic_repeat_one_black);

                if (currentDrawable.getConstantState().equals(repeatList.getConstantState())) {
                    mButtonOrderedOfPlay.setImageDrawable(repeatOne);
                    mService.setOrderOfPlay(OrderOfPlay.REPEAT_ONE);
                } else if (currentDrawable.getConstantState().equals(repeatOne.getConstantState())) {
                    mButtonOrderedOfPlay.setImageDrawable(shuffle);
                    mService.setOrderOfPlay(OrderOfPlay.SHUFFLE);
                } else if (currentDrawable.getConstantState().equals(shuffle.getConstantState())) {
                    mButtonOrderedOfPlay.setImageDrawable(repeatList);
                    mService.setOrderOfPlay(OrderOfPlay.REPEAT_LIST);
                }
            }
        });

    }

}