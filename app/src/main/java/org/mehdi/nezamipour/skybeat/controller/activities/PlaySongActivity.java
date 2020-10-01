package org.mehdi.nezamipour.skybeat.controller.activities;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import org.mehdi.nezamipour.skybeat.controller.fragments.PlaySongFragment;

public class PlaySongActivity extends SingleFragmentActivity {

    public static final String EXTRA_AUDIO_INDEX = "org.mehdi.nezamipour.skybeat.audio";


    public static Intent newIntent(Context context, int audioIndex) {
        Intent intent = new Intent(context, PlaySongActivity.class);
        intent.putExtra(EXTRA_AUDIO_INDEX, audioIndex);
        return intent;
    }


    @Override
    protected Fragment createFragment() {
        int audioIndex = getIntent().getIntExtra(EXTRA_AUDIO_INDEX, -1);
        return PlaySongFragment.newInstance(audioIndex);
    }
}