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
import org.mehdi.nezamipour.skybeat.controller.activities.SongsActivity;
import org.mehdi.nezamipour.skybeat.models.Artist;
import org.mehdi.nezamipour.skybeat.repositories.AudioRepository;

import java.util.ArrayList;

public class ArtistsFragment extends Fragment {

    private AudioRepository mRepository;
    private RecyclerView mRecyclerViewArtist;
    private ArtistAdapter mAdapter;
    private ArrayList<Artist> mArtists;

    public ArtistsFragment() {
        // Required empty public constructor
    }

    public static ArtistsFragment newInstance() {
        ArtistsFragment fragment = new ArtistsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArtists = new ArrayList<>();
        mRepository = AudioRepository.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_artists, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);

        mArtists = mRepository.getArtistList();
        if (mAdapter == null) {
            mAdapter = new ArtistAdapter(mArtists);
            mRecyclerViewArtist.setAdapter(mAdapter);
        } else {
            mAdapter.setArtists(mArtists);
            mAdapter.notifyDataSetChanged();
        }

        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerViewArtist.addItemDecoration(itemDecor);
    }

    private void findViews(View view) {
        mRecyclerViewArtist = view.findViewById(R.id.recyclerView_artists);
    }


    public class ArtistAdapter extends RecyclerView.Adapter<ArtistHolder> {

        ArrayList<Artist> mArtists;

        public ArtistAdapter(ArrayList<Artist> artists) {
            mArtists = artists;
        }

        public void setArtists(ArrayList<Artist> artists) {
            mArtists = artists;
        }

        @NonNull
        @Override
        public ArtistHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(R.layout.artists_row_layout, viewGroup, false);
            return new ArtistHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull ArtistHolder artistHolder, int position) {
            artistHolder.bindArtist(mArtists.get(position));
        }

        @Override
        public int getItemCount() {
            return mArtists.size();
        }
    }

    public class ArtistHolder extends RecyclerView.ViewHolder {

        private MaterialCardView mCardViewArtist;
        private Artist mArtist;
        private TextView mTextViewArtistName;
        private TextView mTextViewNumberOfSongs;
        private ImageView mImageViewArtistImage;


        public ArtistHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewArtistName = itemView.findViewById(R.id.textView_artist_name);
            mTextViewNumberOfSongs = itemView.findViewById(R.id.textView_number_of_song_artist);
            mImageViewArtistImage = itemView.findViewById(R.id.imageView_artist_image);
            mCardViewArtist = itemView.findViewById(R.id.cardView_artist);

            mCardViewArtist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(SongsActivity.newIntent(getContext(), mArtist));
                }
            });

        }

        public void bindArtist(Artist artist) {
            mArtist = artist;
            mTextViewArtistName.setText(artist.getName());
            if (artist.getNumberOfSongs() > 1)
                mTextViewNumberOfSongs.setText(getString(R.string.numberOfSongs, String.valueOf(artist.getNumberOfSongs())));

            //TODO
            //mImageViewArtistImage.setImageDrawable();
        }
    }
}