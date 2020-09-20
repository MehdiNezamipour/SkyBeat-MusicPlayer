package org.mehdi.nezamipour.skybeat.controller.activities;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import org.mehdi.nezamipour.skybeat.controller.fragments.ArtistsFragment;

public class ArtistsActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ArtistsActivity.class);
        return intent;
    }


    @Override
    protected Fragment createFragment() {
        return ArtistsFragment.newInstance();
    }
}