package com.example.gamecenter.games;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.example.gamecenter.R;

public class MyService extends Service {

    MediaPlayer musicPlayer;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        musicPlayer = MediaPlayer.create(MyService.this, R.raw.spyxfamily);
        musicPlayer.setLooping(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(musicPlayer.isPlaying()){
            musicPlayer.pause();
        }else{
            musicPlayer.start();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        musicPlayer.stop();
    }
}