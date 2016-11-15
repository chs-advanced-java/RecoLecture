package ajlyfe.lectureapp.Adapters;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import ajlyfe.lectureapp.R;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;

import android.media.MediaPlayer.OnPreparedListener;
import android.view.MotionEvent;
import android.widget.MediaController;
import android.widget.TextView;

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

        audioFile = this.getIntent().getStringExtra(AUDIO_FILE_NAME);
        ((TextView)findViewById(R.id.now_playing_text)).setText(audioFile);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);

        mediaController = new MediaController(this);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), getResources().getIdentifier("victory","raw",getPackageName()));

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