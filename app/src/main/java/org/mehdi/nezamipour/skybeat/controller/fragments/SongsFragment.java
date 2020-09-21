package org.mehdi.nezamipour.skybeat.controller.fragments;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import org.mehdi.nezamipour.skybeat.R;
import org.mehdi.nezamipour.skybeat.controller.services.MediaPlayerService;
import org.mehdi.nezamipour.skybeat.models.Audio;
import org.mehdi.nezamipour.skybeat.utils.AudioUtils;
import org.mehdi.nezamipour.skybeat.utils.StorageUtils;

import java.util.ArrayList;


public class SongsFragment extends Fragment {


    public static final String BUNDLE_SERVICE_STATE = "org.mehdi.nezamipour.skybeat.ServiceState";
    public static final String Broadcast_PLAY_NEW_AUDIO = "org.mehdi.nezamipour.skybeat.PlayNewAudio";
    private MediaPlayerService mPlayer;
    boolean mServiceBound = false;

    private RecyclerView mRecyclerViewSong;
    private SongsAdapter mAdapter;
    private ArrayList<Audio> mAudio;

    public SongsFragment() {
        // Required empty public constructor
    }

    public static SongsFragment newInstance() {
        SongsFragment fragment = new SongsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    //Binding this Client to the AudioPlayer Service
    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MediaPlayerService.LocalBinder binder = (MediaPlayerService.LocalBinder) service;
            mPlayer = binder.getService();
            mServiceBound = true;
            Toast.makeText(getActivity(), "Service Bound", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceBound = false;
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mServiceBound = savedInstanceState.getBoolean(BUNDLE_SERVICE_STATE);
        }
        mAudio = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_songs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        //TEST
        //playAudio("https://upload.wikimedia.org/wikipedia/commons/6/6c/Grieg_Lyric_Pieces_Kobold.ogg");

        mAudio = AudioUtils.loadAudio(getContext());
        if (mAdapter == null) {
            mAdapter = new SongsAdapter(mAudio);
            mRecyclerViewSong.setAdapter(mAdapter);
        }
        // set audio list
        else {
            mAdapter.setAudio(mAudio);
            mAdapter.notifyDataSetChanged();
        }

        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerViewSong.addItemDecoration(itemDecor);
    }

    private void findViews(View view) {
        mRecyclerViewSong = view.findViewById(R.id.recyclerView_songs);
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(BUNDLE_SERVICE_STATE, mServiceBound);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mServiceBound) {
            getActivity().unbindService(serviceConnection);
            //service is active
            mPlayer.stopSelf();
        }
    }

    private void playAudio(int audioIndex) {
        //Check is service is active
        StorageUtils storage = new StorageUtils(getActivity().getApplicationContext());
        if (!mServiceBound) {
            //Store Serializable audioList to SharedPreferences
            storage.storeAudio(mAudio);
            storage.storeAudioIndex(audioIndex);

            Intent playerIntent = new Intent(getActivity(), MediaPlayerService.class);
            getActivity().startService(playerIntent);
            getActivity().bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        } else {
            //Store the new audioIndex to SharedPreferences
            storage.storeAudioIndex(audioIndex);

            //Service is active
            //Send a broadcast to the service -> PLAY_NEW_AUDIO
            Intent broadcastIntent = new Intent(Broadcast_PLAY_NEW_AUDIO);
            getActivity().sendBroadcast(broadcastIntent);
        }
    }


    public class SongsAdapter extends RecyclerView.Adapter<SongHolder> {

        private ArrayList<Audio> mAudio;

        public SongsAdapter(ArrayList<Audio> audio) {
            mAudio = audio;
        }

        public void setAudio(ArrayList<Audio> audio) {
            mAudio = audio;
        }

        @NonNull
        @Override
        public SongHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(R.layout.songs_row_layout, viewGroup, false);
            return new SongHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SongHolder songHolder, int position) {
            songHolder.bindSong(mAudio.get(position));
        }

        @Override
        public int getItemCount() {
            return mAudio.size();
        }


    }

    public class SongHolder extends RecyclerView.ViewHolder {

        private MaterialCardView mCardViewSong;
        private ImageView mImageViewMoreOptionSong;
        private TextView mTextViewSongArtist;
        private TextView mTextViewSongTitle;
        private Audio mAudio;

        public SongHolder(@NonNull View itemView) {
            super(itemView);
            mImageViewMoreOptionSong = itemView.findViewById(R.id.imageView_more_option_song);
            mTextViewSongArtist = itemView.findViewById(R.id.textView_song_artist);
            mTextViewSongTitle = itemView.findViewById(R.id.textView_song_title);
            mCardViewSong = itemView.findViewById(R.id.cardView_song);

            mImageViewMoreOptionSong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO : open a dialog for more option for song like share ,add to playlist and etc...
                }
            });
            mCardViewSong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO for open PlaySongFragment and play the song
                }
            });
        }

        //TODO bindSong method
        public void bindSong(Audio audio) {
            mAudio = audio;
            mTextViewSongTitle.setText(mAudio.getTitle());
            mTextViewSongArtist.setText(mAudio.getArtist());
        }
    }


}