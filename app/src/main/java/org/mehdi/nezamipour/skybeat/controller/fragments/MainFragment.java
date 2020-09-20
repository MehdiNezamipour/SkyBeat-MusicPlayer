package org.mehdi.nezamipour.skybeat.controller.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.mehdi.nezamipour.skybeat.R;
import org.mehdi.nezamipour.skybeat.controller.activities.AlbumsActivity;
import org.mehdi.nezamipour.skybeat.controller.activities.ArtistsActivity;
import org.mehdi.nezamipour.skybeat.controller.activities.SongsActivity;

public class MainFragment extends Fragment {

    private Button mButtonOnlineMusicService;
    private RecyclerView mRecyclerView;
    private MenuAdapter mAdapter;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
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
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findView(view);
        setListeners();
        //initUI
        if (mAdapter == null) {
            mAdapter = new MenuAdapter();
            mRecyclerView.setAdapter(mAdapter);
        }
        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecor);

    }

    private void setListeners() {
        mButtonOnlineMusicService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO LATER : for using network for playing music
            }
        });
    }

    private void findView(View view) {
        mButtonOnlineMusicService = view.findViewById(R.id.button_online_music_service);
        mRecyclerView = view.findViewById(R.id.recyclerView_main_menu);

    }

    public class MenuAdapter extends RecyclerView.Adapter<itemHolder> {

        @NonNull
        @Override
        public itemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(R.layout.main_menu_row_layout, viewGroup, false);
            return new itemHolder(view);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onBindViewHolder(@NonNull itemHolder itemHolder, int i) {
            itemHolder.bindItem();
        }

        @Override
        public int getItemCount() {
            return 8;
        }
    }

    public class itemHolder extends RecyclerView.ViewHolder {

        private ImageView mImageViewIcon;
        private TextView mTextViewTitle;
        private CardView mCardView;

        //TODO LATER
        private TextView mTextViewNumber;

        public itemHolder(@NonNull View itemView) {
            super(itemView);
            mImageViewIcon = itemView.findViewById(R.id.imageView_icon_main_menu);
            mTextViewTitle = itemView.findViewById(R.id.textView_title_main_menu);
            mTextViewNumber = itemView.findViewById(R.id.textView_number_main_menu);
            mCardView = itemView.findViewById(R.id.cardView_main_menu);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void bindItem() {
            switch (getAdapterPosition()) {
                //Playlist fragment
                case 0:
                    mImageViewIcon.setImageDrawable(getActivity().getDrawable(R.drawable.ic_playlist_new));
                    mTextViewTitle.setText("Playlists");
                    mCardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TODO LATER
                        }
                    });
                    break;
                //Songs fragment
                case 1:
                    mImageViewIcon.setImageDrawable(getActivity().getDrawable(R.drawable.ic_songs));
                    mTextViewTitle.setText("Songs");
                    mCardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(SongsActivity.newIntent(getContext()));
                        }
                    });
                    break;
                //Albums fragment
                case 2:
                    mImageViewIcon.setImageDrawable(getActivity().getDrawable(R.drawable.ic_albums));
                    mTextViewTitle.setText("Albums");
                    mCardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(AlbumsActivity.newIntent(getContext()));
                        }
                    });
                    break;
                //Artist fragment
                case 3:
                    mImageViewIcon.setImageDrawable(getActivity().getDrawable(R.drawable.ic_artists));
                    mTextViewTitle.setText("Artists");
                    mCardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(ArtistsActivity.newIntent(getContext()));
                        }
                    });
                    break;
                //Recently fragment
                case 4:
                    mImageViewIcon.setImageDrawable(getActivity().getDrawable(R.drawable.ic_recently));
                    mTextViewTitle.setText("Recently played");
                    mCardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TODO LATER
                        }
                    });
                    break;
                //Setting fragment
                case 5:
                    mImageViewIcon.setImageDrawable(getActivity().getDrawable(R.drawable.ic_settings));
                    mTextViewTitle.setText("Settings");
                    mCardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TODO LATER
                        }
                    });
                    break;
                //Feedback fragment
                case 6:
                    mImageViewIcon.setImageDrawable(getActivity().getDrawable(R.drawable.ic_feedback));
                    mTextViewTitle.setText("Feedback");
                    mCardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TODO LATER
                        }
                    });
                    break;
                //About fragment
                case 7:
                    mImageViewIcon.setImageDrawable(getActivity().getDrawable(R.drawable.ic_info));
                    mTextViewTitle.setText("About");
                    mCardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TODO LATER
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    }

}