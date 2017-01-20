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

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import ajlyfe.lectureapp.Adapters.ClassCard;
import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;
import ajlyfe.lectureapp.WriteToDatabase;

public class StudentEnterClassCode extends AppCompatActivity {

    private static final String JOIN_CLASS_URL = "http://www.chs.mcvsd.org/sandbox/set-joinclassstudentdb.php";
    public final String DATA_URL = "http://www.chs.mcvsd.org/sandbox/getClassData.php?classCode=";
    public final String JSON_ARRAY = "result";
    boolean go = true;

    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.setCustomTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_enter_class_code);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button joinClassButton = (Button) findViewById(R.id.JoinClassButton);
        joinClassButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final EditText enterClassCode =  (EditText) findViewById(R.id.editText3);
                String classCodeStudent = enterClassCode.getText().toString();
                if (go) {
                    addClass(classCodeStudent, Utils.getPrefs(Utils.SHARED_PREFERENCES, StudentEnterClassCode.this).getString(Utils.PREF_EMAIL, null));
                }
            }
        });

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getData(final String classCode) {
        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        String url = DATA_URL + classCode;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response, classCode);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StudentEnterClassCode.this, "Invalid class code", Toast.LENGTH_SHORT).show();
                go = false;
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response, String classCode) {
        String classNameData;
        String classDescriptionData;
        String classCodeData;
        String teacherData;

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(JSON_ARRAY);
            JSONObject data = result.getJSONObject(0);

            classNameData = data.getString("className");
            classDescriptionData = data.getString("classDescription");
            classCodeData = data.getString("classCode");
            teacherData = data.getString("teacher");

            if (classCode.equals(classCodeData)) { // Class code is valid double-check
                ArrayList<ClassCard> classCards = Utils.getStudentClassList(StudentEnterClassCode.this);
                classCards.add(new ClassCard(classNameData, classDescriptionData, classCodeData));

                Utils.setStudentClassList(classCards, StudentEnterClassCode.this);

                startActivity(new Intent(StudentEnterClassCode.this, StudentActivityMain.class));
                startActivity(new Intent(StudentEnterClassCode.this, StudentClassPage.class));

                finish();
            } else {
                Toast.makeText(StudentEnterClassCode.this, "Invalid class code", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(StudentEnterClassCode.this, SettingsActivity.class));
                return true;

            case android.R.id.home:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void addClass(final String classCode, String email) {
        class AddClass extends AsyncTask<String, Void, String> {
            private ProgressDialog loading;
            private WriteToDatabase ruc = new WriteToDatabase();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(StudentEnterClassCode.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                if (!s.equals("You have already joined this class.")){
                    getData(classCode);
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                data.put("classCode", params[0]);
                data.put("email", params[1]);

                return ruc.sendPostRequest(JOIN_CLASS_URL, data);
            }
        }
        AddClass ac = new AddClass();
        ac.execute(classCode, email);
    }
}
