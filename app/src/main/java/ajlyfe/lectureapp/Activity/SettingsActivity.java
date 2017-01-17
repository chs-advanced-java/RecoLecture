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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import ajlyfe.lectureapp.Fragment.SettingsFragment;
import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private ProgressDialog loading;

    public static final String UPDATE_USER_URL_PT1 = "http://www.chs.mcvsd.org/sandbox/update-username-accountdb.php?username=";
    public static final String UPDATE_USER_URL_PT2 = "&newUsername=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = getSharedPreferences(Utils.SHARED_PREFERENCES, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(Utils.PREF_DARK_THEME, false);

        if (useDarkTheme) {
            setTheme(R.style.AppTheme_Dark_NoActionBar);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.pref_container, new SettingsFragment()).commit();
        }

        TextView version = (TextView) findViewById(R.id.settings_version);
        version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, BSOD.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (getSharedPreferences(Utils.SHARED_PREFERENCES, MODE_PRIVATE).getBoolean(Utils.PREF_IS_TEACHER, false)) {
                //User is teacher
                startActivity(new Intent(SettingsActivity.this, TeacherMainActivity.class));
            } else {
                //User is a student
                startActivity(new Intent(SettingsActivity.this, StudentActivityMain.class));
            }
            finishAffinity();
        }

        return super.onOptionsItemSelected(item);
    }
}
