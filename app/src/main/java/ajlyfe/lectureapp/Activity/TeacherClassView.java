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
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ajlyfe.lectureapp.Adapters.StudentCard;
import ajlyfe.lectureapp.Fragment.FragmentTabFiles;
import ajlyfe.lectureapp.Fragment.FragmentTabStudents;
import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;
import ajlyfe.lectureapp.WriteToDatabase;

public class TeacherClassView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.setCustomTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_class_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        String code = null;
        if (extras != null) {
            String className = extras.getString("CLASS_CLICKED");
            code = extras.getString("CLASS_CODE");
            actionBar.setTitle(className);
        }

        getStudents(code);
    }

    private void setupViewPager(ViewPager viewPager, Bundle bundle) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        Fragment fragmentTabStudents = new FragmentTabStudents();
        fragmentTabStudents.setArguments(bundle);

        adapter.addFragment(fragmentTabStudents, "STUDENTS");
        adapter.addFragment(new FragmentTabFiles(), "FILES");

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
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
                startActivity(new Intent(TeacherClassView.this, SettingsActivity.class));
                return true;

            case android.R.id.home:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void getStudents(final String classCode) {
        class PushClass extends AsyncTask<String, Void, String> {
            private ProgressDialog loading;
            private WriteToDatabase ruc = new WriteToDatabase();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TeacherClassView.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                ArrayList<StudentCard> studentList = new ArrayList<>();

                try {
                    JSONObject result = new JSONObject(s);
                    JSONArray students = result.optJSONArray("result");

                    for (int i = 0; i < students.length(); i++) {
                        JSONObject post = students.optJSONObject(i);
                        studentList.add(new StudentCard(post.optString("fName") + " " + post.optString("lName"), "4/20/17", post.optString("email")));
                    }

                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("CLASS_LIST", studentList);

                    ViewPager viewPager = (ViewPager) findViewById(R.id.classViewViewPager);
                    setupViewPager(viewPager, bundle);

                    TabLayout tabLayout = (TabLayout) findViewById(R.id.classViewTab);
                    tabLayout.setupWithViewPager(viewPager);

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
    }
}
