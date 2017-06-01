package util;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.micic.cs330_v04.R;

/**
 * Created by Filip on 01-Jun-17.
 */

public class Mp3Player extends Service {

    private MediaPlayer player;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onStart(Intent intent, int startId){
        super.onStart(intent, startId);
        player = MediaPlayer.create(this, R.raw.pesma);
        player.start();
    }

    public void onDestroy() {
        super.onDestroy();
        player.stop();
    }
}
