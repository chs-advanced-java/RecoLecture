package ajlyfe.lectureapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.audiofx.BassBoost;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import ajlyfe.lectureapp.Adapters.TeacherClassCard;
import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;

import static ajlyfe.lectureapp.Activity.TeacherClassOverview.AUTO_DESCRIPTION;
import static ajlyfe.lectureapp.Activity.TeacherClassOverview.NULL_CLASS;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = getSharedPreferences(Utils.SHARED_PREFERENCES, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(Utils.PREF_DARK_THEME, false);
        boolean useDummyClasses = preferences.getBoolean(Utils.PREF_DUMMY_CLASSES, false);

        if (useDarkTheme) {
            setTheme(R.style.AppTheme_Dark_NoActionBar);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RelativeLayout username = (RelativeLayout) findViewById(R.id.changeUsername);
        RelativeLayout password = (RelativeLayout) findViewById(R.id.changePassword);
        RelativeLayout signOut = (RelativeLayout) findViewById(R.id.signOut);
        final RelativeLayout darkTheme = (RelativeLayout) findViewById(R.id.darkTheme);
        final Switch darkThemeSwitch = (Switch) findViewById(R.id.darkThemeSwitch);
        darkThemeSwitch.setChecked(useDarkTheme);
        final RelativeLayout dummyClasses = (RelativeLayout) findViewById(R.id.dummyClasses);
        final Switch dummyClassesSwitch = (Switch) findViewById(R.id.dummyClassesSwitch);
        dummyClassesSwitch.setChecked(useDummyClasses);


        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: ChangeUsername activity
                //startActivity(new Intent(SettingsActivity.this, ChangeUsername.class));
            }
        });

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: ChangePassword activity
                //startActivity(new Intent(SettingsActivity.this, ChangePassword.class));
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Destroy user
                getSharedPreferences(Utils.SHARED_PREFERENCES, MODE_PRIVATE).edit().clear().apply();
                getSharedPreferences(Utils.SHARED_PREFERENCES, MODE_PRIVATE).edit().clear().apply();

                startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
            }
        });

        darkTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                darkThemeSwitch.setChecked(!darkThemeSwitch.isChecked());
                toggleTheme(darkThemeSwitch.isChecked());
            }
        });

        darkThemeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleTheme(isChecked);
            }
        });

        dummyClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dummyClassesSwitch.setChecked(!dummyClassesSwitch.isChecked());
            }
        });

        dummyClassesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleDummyClasses(isChecked);
            }
        });
    }

    private void toggleTheme(boolean darkTheme) {
        SharedPreferences.Editor editor = getSharedPreferences(Utils.SHARED_PREFERENCES, MODE_PRIVATE).edit();
        editor.putBoolean(Utils.PREF_DARK_THEME, darkTheme);
        editor.apply();

        Intent intent = getIntent();
        finish();

        startActivity(intent);
    }

    private void toggleDummyClasses(boolean dummyClassesEnabled) {
        SharedPreferences.Editor editor = getSharedPreferences(Utils.SHARED_PREFERENCES, MODE_PRIVATE).edit();
        editor.putBoolean(Utils.PREF_DUMMY_CLASSES, dummyClassesEnabled);

        ArrayList<TeacherClassCard> mClasses = Utils.getClassList(SettingsActivity.this);

        assert mClasses != null;
        if (mClasses.size() > 0) {
            mClasses.set(0, new TeacherClassCard(NULL_CLASS, "Header (NULL)"));
        }

        if (dummyClassesEnabled) {
            //mClasses.clear();
            mClasses.set(0, new TeacherClassCard(NULL_CLASS, "Header (NULL)"));
            mClasses.add(new TeacherClassCard("Spanish I", AUTO_DESCRIPTION));
            mClasses.add(new TeacherClassCard("Spanish II", AUTO_DESCRIPTION));
            mClasses.add(new TeacherClassCard("Spanish III", AUTO_DESCRIPTION));
            mClasses.add(new TeacherClassCard("Spanish IV", AUTO_DESCRIPTION));
            mClasses.add(new TeacherClassCard("Spanish V", AUTO_DESCRIPTION));

            Utils.setClassList(mClasses, SettingsActivity.this);
        } else {
            ArrayList<TeacherClassCard> keepMe = new ArrayList<>();

            for (int i = 0; i < mClasses.size(); i++) {
                if (!mClasses.get(i).isDummy()) {
                    keepMe.add(mClasses.get(i));
                }
            }

            mClasses = keepMe;

            Utils.setClassList(mClasses, SettingsActivity.this);
        }

        editor.apply();
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
