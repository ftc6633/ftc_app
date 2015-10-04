package com.ftc6633.utils;

import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by rupello on 10/3/2015.
 */
public class SoundPlayer {
    private MediaPlayer mediaPlayer  = null;

    public SoundPlayer(String path) {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare(); // might take long! (for buffering, etc)
        }catch(IOException e){
            mediaPlayer = null;
        }
    }

    public void play() {
        if(mediaPlayer != null){
            if(!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
        }
    }

    public  void stop() {
        if(mediaPlayer != null) {
            if(mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
        }
    }
}
