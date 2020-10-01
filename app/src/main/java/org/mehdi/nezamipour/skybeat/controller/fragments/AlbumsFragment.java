package org.mehdi.nezamipour.skybeat.controller.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import org.mehdi.nezamipour.skybeat.models.Album;
import org.mehdi.nezamipour.skybeat.repositories.AudioRepository;

import java.util.ArrayList;

public class AlbumsFragment extends Fragment {

    private AudioRepository mRepository;
    private RecyclerView mRecyclerViewAlbum;
    private AlbumsAdapter mAdapter;
    private ArrayList<Album> mAlbums;

    public AlbumsFragment() {
        // Required empty public constructor
    }

    public static AlbumsFragment newInstance() {
        AlbumsFragment fragment = new AlbumsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAlbums = new ArrayList<>();
        mRepository = AudioRepository.getInstance(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_albums, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        mAlbums = mRepository.getAlbumList();
        if (mAdapter == null) {
            mAdapter = new AlbumsAdapter(mAlbums);
            mRecyclerViewAlbum.setAdapter(mAdapter);
        } else {
            mAdapter.setAlbums(mAlbums);
            mAdapter.notifyDataSetChanged();
        }

        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerViewAlbum.addItemDecoration(itemDecor);
    }

    private void findViews(View view) {
        mRecyclerViewAlbum = view.findViewById(R.id.recyclerView_albums);
    }

    public class AlbumsAdapter extends RecyclerView.Adapter<AlbumHolder> {

        ArrayList<Album> mAlbums;

        public AlbumsAdapter(ArrayList<Album> albums) {
            mAlbums = albums;
        }

        public void setAlbums(ArrayList<Album> albums) {
            mAlbums = albums;
        }

        @NonNull
        @Override
        public AlbumHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(R.layout.albums_row_layout, viewGroup, false);
            return new AlbumHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AlbumHolder albumHolder, int position) {
            albumHolder.bindAlbum(mAlbums.get(position));
        }

        @Override
        public int getItemCount() {
            return mAlbums.size();
        }
    }

    public class AlbumHolder extends RecyclerView.ViewHolder {

        private Album mAlbum;
        private MaterialCardView mCardViewAlbum;
        private TextView mTextViewAlbumTitle;
        private TextView mTextViewNumberOfSongs;
        private ImageView mImageViewAlbumImage;

        public AlbumHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewAlbumTitle = itemView.findViewById(R.id.textView_album_title);
            mTextViewNumberOfSongs = itemView.findViewById(R.id.textView_songs_of_album);
            mImageViewAlbumImage = itemView.findViewById(R.id.imageView_album_image);
            mCardViewAlbum = itemView.findViewById(R.id.cardView_album);

        }

        public void bindAlbum(Album album) {
            mAlbum = album;
            mTextViewAlbumTitle.setText(album.getTitle());
            mCardViewAlbum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRepository.setAudios(mAlbum.getAudios(getContext()));
                    startActivity(SongsActivity.newIntent(getContext()));
                }
            });
            int number = album.getSongsNumber(getContext());
            if (number > 1)
                mTextViewNumberOfSongs.setText(getString(R.string.numberOfSongs,
                        String.valueOf(number)));

            Bitmap bm = BitmapFactory.decodeFile(mAlbum.getAlbumArt());
            if (bm != null)
                mImageViewAlbumImage.setImageBitmap(bm);
            else
                mImageViewAlbumImage.setImageResource(R.drawable.no_image_2);
        }
    }


}