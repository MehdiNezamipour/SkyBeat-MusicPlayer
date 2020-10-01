package org.mehdi.nezamipour.skybeat.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import org.mehdi.nezamipour.skybeat.R;
import org.mehdi.nezamipour.skybeat.controller.activities.PlaySongActivity;
import org.mehdi.nezamipour.skybeat.models.Audio;
import org.mehdi.nezamipour.skybeat.repositories.AudioRepository;

import java.util.ArrayList;


public class SongsFragment extends Fragment {


    private RecyclerView mRecyclerViewSong;
    private SongsAdapter mAdapter;
    private ArrayList<Audio> mAudios;


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
        AudioRepository repository = AudioRepository.getInstance(getContext());
        mAudios = (ArrayList<Audio>) repository.getAudios();
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

        if (mAdapter == null) {
            mAdapter = new SongsAdapter(mAudios);
            mRecyclerViewSong.setAdapter(mAdapter);
        }
        // set audio list
        else {
            mAdapter.setAudio(mAudios);
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
        super.onSaveInstanceState(savedInstanceState);

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
                    //TODO LATER : open a dialog for more option for song like share ,add to playlist and etc...
                }
            });

        }

        public void bindSong(Audio audio) {
            mAudio = audio;
            mTextViewSongTitle.setText(mAudio.getTitle());
            mTextViewSongArtist.setText(mAudio.getArtist());
            mCardViewSong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().startActivity(PlaySongActivity.newIntent(getContext(), getAdapterPosition()));
                }
            });
        }
    }


}