package org.mehdi.nezamipour.skybeat.controller.activities;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import org.mehdi.nezamipour.skybeat.controller.fragments.PlaySongFragment;
import org.mehdi.nezamipour.skybeat.models.Album;
import org.mehdi.nezamipour.skybeat.models.Artist;

public class PlaySongActivity extends SingleFragmentActivity {

    public static final String EXTRA_AUDIO_INDEX = "org.mehdi.nezamipour.skybeat.audio";
    public static final String EXTRA_ALBUM = "album";
    public static final String EXTRA_ARTIST = "artist";

    public static Intent newIntent(Context context, int audioIndex) {
        Intent intent = new Intent(context, PlaySongActivity.class);
        intent.putExtra(EXTRA_AUDIO_INDEX, audioIndex);
        return intent;
    }

    public static Intent newIntent(Context context, int audioIndex, Album album) {
        Intent intent = new Intent(context, PlaySongActivity.class);
        intent.putExtra(EXTRA_AUDIO_INDEX, audioIndex);
        intent.putExtra(EXTRA_ALBUM, album);
        return intent;
    }

    public static Intent newIntent(Context context, int audioIndex, Artist artist) {
        Intent intent = new Intent(context, PlaySongActivity.class);
        intent.putExtra(EXTRA_AUDIO_INDEX, audioIndex);
        intent.putExtra(EXTRA_ARTIST, artist);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        Album album = (Album) getIntent().getSerializableExtra(EXTRA_ALBUM);
        Artist artist = (Artist) getIntent().getSerializableExtra(EXTRA_ARTIST);
        int audioIndex = getIntent().getIntExtra(EXTRA_AUDIO_INDEX, -1);
        if (album != null)
            return PlaySongFragment.newInstance(audioIndex, album);
        else if (artist != null)
            return PlaySongFragment.newInstance(audioIndex, artist);
        else
            return PlaySongFragment.newInstance(audioIndex);
    }
}