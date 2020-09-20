package org.mehdi.nezamipour.skybeat.controller.activities;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import org.mehdi.nezamipour.skybeat.controller.fragments.AlbumsFragment;

public class AlbumsActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AlbumsActivity.class);
        return intent;
    }


    @Override
    protected Fragment createFragment() {
        return AlbumsFragment.newInstance();
    }
}