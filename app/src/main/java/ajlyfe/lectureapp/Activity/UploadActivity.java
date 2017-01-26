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

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.paolorotolo.appintro.AppIntro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import ajlyfe.lectureapp.Adapters.ClassSelectCard;
import ajlyfe.lectureapp.Adapters.LectureSelectCard;
import ajlyfe.lectureapp.Adapters.LectureSelectCardAdapter;
import ajlyfe.lectureapp.Adapters.StudentSelectCard;
import ajlyfe.lectureapp.Adapters.StudentSelectCardAdapter;
import ajlyfe.lectureapp.Adapters.TeacherClassCard;
import ajlyfe.lectureapp.Fragment.FragmentClass;
import ajlyfe.lectureapp.Fragment.FragmentFile;
import ajlyfe.lectureapp.Fragment.FragmentResult;
import ajlyfe.lectureapp.Fragment.FragmentStudents;
import ajlyfe.lectureapp.Fragment.FragmentUpload;
import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;
import ajlyfe.lectureapp.WriteToDatabase;

public class UploadActivity extends AppIntro {

    private Fragment fragmentUpload;
    private Fragment fragmentResult;
    private Fragment fragmentClass;
    private Fragment fragmentStudents;

    private ArrayList<String> classCodes = new ArrayList<>();

    FragmentFile file;
    FragmentClass classes;

    String lectureID = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Utils.setCustomTheme(this);
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // DO NOT WRITE -> setContentView(R.layout.activity_upload);
        file = new FragmentFile();
        classes = new FragmentClass();
        Fragment fragmentFile = file;
        fragmentClass = classes;
        fragmentStudents = new FragmentStudents();
        fragmentUpload = new FragmentUpload();
        fragmentResult = new FragmentResult();

        addSlide(fragmentFile);
        addSlide(fragmentClass);
        addSlide(fragmentStudents);
        addSlide(fragmentUpload);
        addSlide(fragmentResult);

        setSwipeLock(true);

        showSkipButton(false);
        setProgressButtonEnabled(true);     // Reveal the done button and next arrow
        setImageNextButton(null);           // Purge next button
        nextButton.setClickable(false);     // Banish next button
        // Done button still stays and works

        setColorDoneText(this.getResources().getColor(R.color.colorAccent));
        setSeparatorColor(Color.TRANSPARENT);
        setIndicatorColor(R.color.colorPrimary50, R.color.colorPrimary20);

        setVibrate(false);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        startActivity(new Intent(this, TeacherMainActivity.class));
    }



    @Override
    public void onSlideChanged(@Nullable final Fragment oldFragment, @Nullable final Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);

        if (newFragment != null) {
            final int slideNumber = newFragment.getTag().charAt(newFragment.getTag().length() - 1) - 47;
            final ArrayList<String> paths = new ArrayList<>();
            Button next;

            switch (slideNumber) {
                case 1:
                    final Activity activity1 = newFragment.getActivity();
                    next = (Button) activity1.findViewById(R.id.uploadFileButton);

                    final ArrayList<String> lecturesChecked = new ArrayList<>();
                    final boolean[] checkedSomething = {false};

                    LectureSelectCardAdapter.setLectureCheckedListener(new LectureSelectCardAdapter.OnLectureChecked() {
                        @Override
                        public void onChecked(LectureSelectCard lecture) {
                            checkedSomething[0] = true;
                            lecturesChecked.add(lecture.getFileName());
                            lectureID = lecture.getID();
                            paths.add(Utils.getLecturePath() + lecture.getID() + ".mp3");
                        }
                    });

                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Bundle args = new Bundle();
                            args.putStringArrayList("lecturesCheckedOff", lecturesChecked);
                            fragmentUpload.setArguments(args);

                            Bundle args2 = new Bundle();
                            args2.putStringArrayList("paths", paths);
                            fragmentResult.setArguments(args2);

                            if (checkedSomething[0]) {
                                pager.setCurrentItem(1);
                            } else {
                                Toast.makeText(activity1, "Please select a lecture", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    break;

                case 2:
                    final Activity activity2 = newFragment.getActivity();
                    next = (Button) activity2.findViewById(R.id.uploadClassButton);
                    final ArrayList<ClassSelectCard> classCheckboxes = classes.getAdapterArrayList();

                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            boolean checkedSomething = false;

                            for (int i = 0; i < classCheckboxes.size(); i++) {
                                if (classCheckboxes.get(i).getChecked()) {
                                    checkedSomething = true;
                                    classCodes.add(getClassCode(classCheckboxes.get(i).getClassName()));
                                }
                            }

                            if (checkedSomething) {
                                pager.setCurrentItem(2);
                            } else {
                                Toast.makeText(activity2, "Please select a class.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    break;

                case 3:
                    final Activity activity3 = newFragment.getActivity();
                    next = (Button) activity3.findViewById(R.id.uploadStudentButton);
                    final Button selectAll = (Button) activity3.findViewById(R.id.selectAllButton);
                    final View newView = newFragment.getView();

                    final String classCode = classCodes.get(0);

                    final Activity act = fragmentStudents.getActivity();

                    class PushClass extends AsyncTask<String, Void, String> {
                        private WriteToDatabase ruc = new WriteToDatabase();

                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);

                            ArrayList<StudentSelectCard> studentList = new ArrayList<>();

                            try {
                                JSONObject result = new JSONObject(s);
                                JSONArray students = result.optJSONArray("result");

                                for (int i = 0; i < students.length(); i++) {
                                    JSONObject post = students.optJSONObject(i);
                                    studentList.add(new StudentSelectCard(post.optString("fName") + " " + post.optString("lName")));
                                }

                                RecyclerView recyclerViewStudents = (RecyclerView) newView.findViewById(R.id.recyclerViewStudentSelect);
                                final StudentSelectCardAdapter adapter = new StudentSelectCardAdapter(studentList, newView.getContext());
                                recyclerViewStudents.setAdapter(adapter);
                                recyclerViewStudents.setLayoutManager(new LinearLayoutManager(newView.getContext()));

                                selectAll.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        adapter.toggleAllChecked();
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        protected String doInBackground(String... params) {
                            HashMap<String, String> data = new HashMap<>();
                            data.put("classCode", params[0]);

                            return ruc.sendPostRequest("http://www.chs.mcvsd.org/sandbox/getClassStudents.php", data);
                        }
                    }

                    PushClass pc = new PushClass();
                    pc.execute(classCode);

                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            pager.setCurrentItem(3);
                        }
                    });
                    break;

                case 4:
                    final Activity activity4 = newFragment.getActivity();
                    Button finishButton = (Button) activity4.findViewById(R.id.finishButton);

                    finishButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TODO: Only checks if one of the shits is checked
                            CheckBox checkBox = (CheckBox) activity4.findViewById(R.id.lectureCheckboxCheckbox);

                            if (checkBox.isChecked()) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //creating new thread to handle Http Operations
                                        for (String path : fragmentResult.getArguments().getStringArrayList("paths")) {
                                            uploadFile(path, activity4);
                                        }
                                    }
                                }).start();
                                pager.setCurrentItem(slideNumber);
                            } else {
                                Toast.makeText(activity4, "Please confirm all of the above", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    break;

                case 5:
                    final Activity activity5 = newFragment.getActivity();
                    updateUploadStatus(lectureID);
                    setLectureClass(lectureID, classCodes.get(0));
                    Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_right);
                    ImageView box = (ImageView) activity5.findViewById(R.id.checkCover);
                    anim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    box.startAnimation(anim);
                    break;
            }
        }
    }

    private String getClassCode(String className) {
        String code = null;

        ArrayList<TeacherClassCard> classes = Utils.getTeacherClassList(UploadActivity.this);

        for (TeacherClassCard mClass : classes) {
            if (mClass.getName().equals(className)) {
                code = mClass.getCode();
            }
        }

        return code;
    }

    private int uploadFile(final String path, final Activity activity) {
        int serverResponseCode = 0;

        HttpURLConnection connection;
        DataOutputStream dataOutputStream;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";


        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1024 * 1024;
        File selectedFile = new File(path);

        if (!selectedFile.isFile()){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity.getApplicationContext(), "Source File Doesn't Exist: " + path, Toast.LENGTH_SHORT).show();
                }
            });

            return 0;
        } else {
            try {
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                URL url = new URL("http://www.chs.mcvsd.org/sandbox/UploadToServer.php");
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true); //Allow Inputs
                connection.setDoOutput(true); //Allow Outputs
                connection.setUseCaches(false); //Don't use a cached Copy
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                connection.setRequestProperty("uploaded_file", path);

                dataOutputStream = new DataOutputStream(connection.getOutputStream());

                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + path + "\"" + lineEnd);

                dataOutputStream.writeBytes(lineEnd);

                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0){
                    dataOutputStream.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                serverResponseCode = connection.getResponseCode();
                String serverResponseMessage = connection.getResponseMessage();

                Log.i(activity.getClass().getSimpleName(), "Server Response is: " + serverResponseMessage + ": " + serverResponseCode);

                fileInputStream.close();
                dataOutputStream.flush();
                dataOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity.getApplicationContext(), "File Not Found", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Toast.makeText(activity.getApplicationContext(), "URL error!", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(activity.getApplicationContext(), "Cannot Read/Write File!", Toast.LENGTH_SHORT).show();
            }

            return serverResponseCode;
        }
    }

    private void updateUploadStatus(final String lecID) {
        String url = "http://www.chs.mcvsd.org/sandbox/updateUploaded.php?id=" + lecID;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void setLectureClass(final String lecID, final String classCode) {
        String url = "http://www.chs.mcvsd.org/sandbox/setLectureClass.php?id=" + lecID + "&classCode=" + classCode;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
