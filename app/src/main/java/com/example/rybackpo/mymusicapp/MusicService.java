package com.example.rybackpo.mymusicapp;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by rybackpo on 12/7/2015.
 */
public class MusicService extends Service{

    public static final String ACTION_PLAY = "play";
    public static final String ACTION_STOP = "stop";

    private MediaPlayer Mplayer;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String action = intent.getAction();

        if (action.equals(ACTION_PLAY)) {
            String filename = intent.getStringExtra("filename");

            if (Mplayer != null && Mplayer.isPlaying()) {
                Mplayer.stop();
                Mplayer.release();
            }

            try {
                AssetFileDescriptor asset;
                asset = getAssets().openFd(filename);
                Mplayer = new MediaPlayer();
                Mplayer.setDataSource(asset.getFileDescriptor(), asset.getStartOffset(), asset.getLength());
                Mplayer.prepare();
                Mplayer.setLooping(true);
                Mplayer.start();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (action.equals(ACTION_STOP)) {
            if (Mplayer != null && Mplayer.isPlaying()) {
                Mplayer.stop();
            }
        }
        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        Toast.makeText(this, "Stop Music Service", Toast.LENGTH_SHORT).show();
        Mplayer.stop();
        Mplayer.release();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
