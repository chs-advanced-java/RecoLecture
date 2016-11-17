package ajlyfe.lectureapp.Fragment;


import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;

import ajlyfe.lectureapp.Activity.RecordActivity;
import ajlyfe.lectureapp.R;

public class RecordFragment extends Fragment {

    private View view;
    private Button play,stop,record, save;
    private MediaRecorder myAudioRecorder;
    private String outputFile = null;

    public static final String PLACEHOLDER = "bzofghia";

    public RecordFragment() {
        /* Required empty public constructor */
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme_Dark_NoActionBar);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        view = localInflater.inflate(R.layout.fragment_record, container, false);

        doThings();

        return view;
    }

    private void doThings() {
        play = (Button) view.findViewById(R.id.play);
        stop = (Button) view.findViewById(R.id.stop);
        record = (Button) view.findViewById(R.id.record);
        save = (Button) view.findViewById(R.id.save);

        stop.setEnabled(false);
        play.setEnabled(false);
        save.setEnabled(false);

        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/RecoLecture/" + PLACEHOLDER + ".3gp";

        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(outputFile);

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    myAudioRecorder.prepare();
                    myAudioRecorder.start();
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                record.setEnabled(false);
                stop.setEnabled(true);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    myAudioRecorder.stop();
                    myAudioRecorder.release();
                    myAudioRecorder = null;

                    stop.setEnabled(false);
                    play.setEnabled(true);
                    save.setEnabled(true);
                }

                catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) throws IllegalArgumentException,SecurityException,IllegalStateException {
                MediaPlayer m = new MediaPlayer();

                try {
                    m.setDataSource(outputFile);
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    m.prepare();
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                m.start();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .remove(RecordFragment.this)
                        .commit();

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(RecordActivity.ROOT_ACTIVITY, new SaveFragment())
                        .commit();
            }
        });
    }
}
