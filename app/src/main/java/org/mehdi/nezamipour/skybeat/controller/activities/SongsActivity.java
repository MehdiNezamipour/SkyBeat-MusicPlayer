package org.mehdi.nezamipour.skybeat.controller.activities;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import org.mehdi.nezamipour.skybeat.controller.fragments.SongsFragment;
import org.mehdi.nezamipour.skybeat.models.Album;
import org.mehdi.nezamipour.skybeat.models.Artist;

public class SongsActivity extends SingleFragmentActivity {


    public static final String EXTRA_ALBUM = "album";
    public static final String EXTRA_ARTIST = "artist";

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SongsActivity.class);
        return intent;
    }

    public static Intent newIntent(Context context, Album album) {
        Intent intent = new Intent(context, SongsActivity.class);
        intent.putExtra(EXTRA_ALBUM, album);
        return intent;
    }

    public static Intent newIntent(Context context, Artist artist) {
        Intent intent = new Intent(context, SongsActivity.class);
        intent.putExtra(EXTRA_ARTIST, artist);
        return intent;
    }


    @Override
    protected Fragment createFragment() {
        Album album = (Album) getIntent().getSerializableExtra(EXTRA_ALBUM);
        Artist artist = (Artist) getIntent().getSerializableExtra(EXTRA_ARTIST);

        if (album != null)
            return SongsFragment.newInstance(album);
        else if (artist != null)
            return SongsFragment.newInstance(artist);
        else
            return SongsFragment.newInstance();
    }

}