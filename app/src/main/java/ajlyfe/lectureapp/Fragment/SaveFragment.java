package ajlyfe.lectureapp.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;

import ajlyfe.lectureapp.Activity.TeacherMainActivity;
import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;

public class SaveFragment extends Fragment {

    private View view;
    private EditText editText;
    private Button saveButton;

    public SaveFragment() {
        /* Required empty public constructor */
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = Utils.setCustomAdapterTheme(getActivity(), inflater, R.layout.fragment_save, container, false);

        doThings();

        return view;
    }

    public void doThings() {
        editText = (EditText) view.findViewById(R.id.recordingName);
        saveButton = (Button) view.findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                renameFile(editText.getText().toString());
                startActivity(new Intent(getActivity(), TeacherMainActivity.class));
            }
        });
    }

    public void renameFile(String newName) {
        File oldFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/RecoLecture/" + RecordFragment.PLACEHOLDER + ".3gp");
        File newFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/RecoLecture/" + newName + ".3gp");

        /*
        File parentDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/RecoLecture/");
        File[] files = parentDir.listFiles();
        */

        oldFile.renameTo(newFile);
    }
}
