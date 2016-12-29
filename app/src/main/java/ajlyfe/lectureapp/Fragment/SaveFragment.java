package ajlyfe.lectureapp.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
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

                String text = editText.getText().toString();
                boolean space = false;
                for (int x = 0; x <= text.length() - 1; x++){
                    if (text.charAt(x) == ' ') {
                        space = true;
                    }
                }
                if (text.length() < 1) {
                    TextInputLayout usernameTIL = (TextInputLayout) view.findViewById(R.id.recordingNameHolder);
                    usernameTIL.setErrorEnabled(true);
                    usernameTIL.setError("This field cannot be empty");
                }
                else if (space) {
                    TextInputLayout usernameTIL = (TextInputLayout) view.findViewById(R.id.recordingNameHolder);
                    usernameTIL.setErrorEnabled(true);
                    usernameTIL.setError("File name cannot contain spaces");
                }
                else {
                    renameFile(text);
                    startActivity(new Intent(getActivity(), TeacherMainActivity.class));
                }
            }
        });
    }

    public void renameFile(String newName) {
        File oldFile = new File(Utils.getLecturePath() + RecordFragment.PLACEHOLDER + ".mp3");
        File newFile = new File(Utils.getLecturePath() + newName + ".mp3");

        /*
        File parentDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/RecoLecture/");
        File[] files = parentDir.listFiles();
        */

        oldFile.renameTo(newFile);
    }
}
