/*
 * Copyright (c) 2017 RecoLecture.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ajlyfe.lectureapp.Activity;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.MediaController;

import java.io.File;

import ajlyfe.lectureapp.R;

public class TestActivity extends AppCompatActivity implements OnPreparedListener, MediaController.MediaPlayerControl{
    private static final String TAG = "AudioPlayer";

    public static final String AUDIO_FILE_NAME = "victory.mp3";

    private MediaPlayer mediaPlayer;
    private MediaController mediaController;
    private String audioFile;

    private Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        String fileName = "victory.mp3";

        File testFile = new File("/sdcard/Recolecture/" + fileName);

        audioFile = "/sdcard/Recolecture/" + fileName;

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);

        mediaController = new MediaController(this);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.fromFile(new File(audioFile)));

        mediaController.setMediaPlayer(this);
        mediaController.setAnchorView(findViewById(R.id.videoView));
        handler.post(new Runnable() {

            public void run() {
                mediaController.setEnabled(true);
                mediaController.show();
            }
        });
        mediaPlayer.start();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaController.hide();
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //the MediaController will hide after 3 seconds - tap the screen to make it appear again
        mediaController.show();
        return false;
    }

    //--MediaPlayerControl methods----------------------------------------------------
    public void start() {
        mediaPlayer.start();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    public void seekTo(int i) {
        mediaPlayer.seekTo(i);
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public int getBufferPercentage() {
        return 0;
    }

    public boolean canPause() {
        return true;
    }

    public boolean canSeekBackward() {
        return true;
    }

    public boolean canSeekForward() {
        return true;
    }
    //--------------------------------------------------------------------------------

    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onPrepared");
        mediaController.setMediaPlayer(this);
        mediaController.setAnchorView(findViewById(R.id.main_audio_view));

        handler.post(new Runnable() {
            public void run() {
                mediaController.setEnabled(true);
                mediaController.show();
            }
        });
    }

    public int getAudioSessionId(){
        return 0;
    }
}