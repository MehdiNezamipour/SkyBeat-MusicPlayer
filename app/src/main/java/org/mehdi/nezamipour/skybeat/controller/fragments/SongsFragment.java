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

import org.mehdi.nezamipour.skybeat.R;
import org.mehdi.nezamipour.skybeat.controller.services.MediaPlayerService;


public class SongsFragment extends Fragment {


    private MediaPlayerService mPlayer;
    boolean mServiceBound = false;

    private RecyclerView mRecyclerViewSong;
    private SongsAdapter mAdapter;

    public SongsFragment() {
        // Required empty public constructor
    }

    public static SongsFragment newInstance() {
        SongsFragment fragment = new SongsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_songs, container, false);
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
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("ServiceState", mServiceBound);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        mServiceBound = savedInstanceState.getBoolean("ServiceState");
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

    private void playAudio(String media) {
        //Check is service is active
        if (!mServiceBound) {
            Intent playerIntent = new Intent(getActivity(), MediaPlayerService.class);
            playerIntent.putExtra("media", media);
            getActivity().startService(playerIntent);
            getActivity().bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        } else {
            //TODO
            //Service is active
            //Send media with BroadcastReceiver
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerViewSong = view.findViewById(R.id.recyclerView_songs);
        //TODO SET ADAPTER

        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerViewSong.addItemDecoration(itemDecor);
    }


    public class SongsAdapter extends RecyclerView.Adapter<SongHolder> {


        @NonNull
        @Override
        public SongHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(R.layout.songs_row_layout, viewGroup, false);
            return new SongHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SongHolder songHolder, int i) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }


    }

    public class SongHolder extends RecyclerView.ViewHolder {

        private ImageView mImageViewMoreOptionSong;
        private TextView mTextViewSongArtist;
        private TextView mTextViewSongTitle;

        public SongHolder(@NonNull View itemView) {
            super(itemView);
            mImageViewMoreOptionSong = itemView.findViewById(R.id.imageView_more_option_song);
            mTextViewSongArtist = itemView.findViewById(R.id.textView_song_artist);
            mTextViewSongTitle = itemView.findViewById(R.id.textView_song_title);

        }
        //TODO bindSong method
        //public void bindSong (Music music){}
    }


}