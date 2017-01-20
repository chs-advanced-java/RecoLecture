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

package ajlyfe.lectureapp.Fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import ajlyfe.lectureapp.Activity.TeacherMainActivity;
import ajlyfe.lectureapp.CodeGenerator;
import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;
import ajlyfe.lectureapp.WriteToDatabase;

public class SaveFragment extends Fragment {

    private View view;
    private EditText editText;
    private Button saveButton;
    private RelativeLayout dateHolder;
    private String expirationDate = null;

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
        dateHolder = (RelativeLayout) view.findViewById(R.id.dateHolder);

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-mm-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                expirationDate = sdf.format(myCalendar.getTime());
            }
        };

        dateHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lectureName = editText.getText().toString();

                if (lectureName.length() < 1) {
                    TextInputLayout usernameTIL = (TextInputLayout) view.findViewById(R.id.recordingNameHolder);
                    usernameTIL.setErrorEnabled(true);
                    usernameTIL.setError("This field cannot be empty");
                } else {
                    String id = CodeGenerator.generate();
                    renameFile(id);

                    if (expirationDate != null) {
                        updateDB(lectureName, "xmplCode", "Kevin", id, expirationDate, "1");
                    } else {
                        Toast.makeText(getContext(), "Please pick an expiration date to continue", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void updateDB(String lectureName, String classCode, String teacherName,
                         String id, String expirationDate, String killLecture) {

        class UploadLecture extends AsyncTask<String, Void, String> {
            private ProgressDialog loading;
            private WriteToDatabase ruc = new WriteToDatabase();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getContext(), "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                loading.dismiss();
                Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();

                if (s.equals("Upload successful!"))  {
                    startActivity(new Intent(getActivity(), TeacherMainActivity.class));
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                data.put("lectureName", params[0]);
                data.put("classCode", params[1]);
                data.put("teacherName", params[2]);
                data.put("id", params[3]);
                data.put("expirationDate", params[4]);
                data.put("killLecture", params[5]);

                return ruc.sendPostRequest("http://www.chs.mcvsd.org/sandbox/putLecture.php", data);
            }
        }

        UploadLecture uploadLecture = new UploadLecture();
        uploadLecture.execute(lectureName, classCode, teacherName, id, expirationDate, killLecture);
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