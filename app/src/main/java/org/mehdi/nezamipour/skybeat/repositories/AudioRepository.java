package org.mehdi.nezamipour.skybeat.repositories;

import android.content.Context;

import org.mehdi.nezamipour.skybeat.models.Album;
import org.mehdi.nezamipour.skybeat.models.Artist;
import org.mehdi.nezamipour.skybeat.models.Audio;
import org.mehdi.nezamipour.skybeat.utils.AudioUtils;

import java.util.ArrayList;
import java.util.List;

public class AudioRepository {

    private static AudioRepository sRepository;
    private List<Audio> mAudios;

    private AudioRepository() {
    }

    public static AudioRepository getInstance() {
        if (sRepository == null)
            sRepository = new AudioRepository();
        return sRepository;
    }

    public List<Audio> getAudios() {
        return mAudios;
    }

    public void setAudios(List<Audio> audios) {
        mAudios = audios;
    }


}
