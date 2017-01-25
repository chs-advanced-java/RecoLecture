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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import ajlyfe.lectureapp.Adapters.ClassCard;
import ajlyfe.lectureapp.Adapters.LectureCard;
import ajlyfe.lectureapp.Adapters.LectureCardAdapter;
import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class StudentClassPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.setCustomTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_class_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String classCode = null;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String className = extras.getString("CLASS_CLICKED");
            classCode = extras.getString("CLASS_CODE");
            if(getSupportActionBar() != null) getSupportActionBar().setTitle(className);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getData(classCode);
    }

    private void getData(String classCode) {
        String url = "http://www.chs.mcvsd.org/sandbox/getLectureList.php?classCode=" + classCode;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSON(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {
        String lectureName;
        String id;
        String teacherName;
        String expirationDate;
        String killLecture;
        ArrayList<LectureCard> lectures = new ArrayList<>();

        try {

            JSONObject result = new JSONObject(response);
            JSONArray lectureList = result.optJSONArray("result");

            for (int i = 0; i < lectureList.length(); i++) {
                JSONObject post = lectureList.optJSONObject(i);
                lectures.add(new LectureCard(post.optString("lectureName"), post.optString("teacherName"), null));
            }

            RecyclerView recyclerViewLectures = (RecyclerView) findViewById(R.id.recyclerViewLectures);
            // Create an adapter passing in the ArrayList from above
            LectureCardAdapter adapter = new LectureCardAdapter(lectures, this, null);
            // Attach the adapter to the RecyclerView
            recyclerViewLectures.setAdapter(adapter);
            // Set layout manager to position the items
            recyclerViewLectures.setLayoutManager(new LinearLayoutManager(this));
            // Set overscroll effect
            OverScrollDecoratorHelper.setUpOverScroll(recyclerViewLectures, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);

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
                startActivity(new Intent(StudentClassPage.this, SettingsActivity.class));
                return true;

            case android.R.id.home:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
