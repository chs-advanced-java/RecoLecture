package ajlyfe.lectureapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.audiofx.BassBoost;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = getSharedPreferences(Utils.PREFS_SETTINGS, MODE_PRIVATE);
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
                getSharedPreferences(Utils.PREFS_CLASSES, MODE_PRIVATE).edit().clear().apply();
                getSharedPreferences(Utils.PREFS_SETTINGS, MODE_PRIVATE).edit().clear().apply();

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
                toggleDummyClasses(dummyClassesSwitch.isChecked());
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
        SharedPreferences.Editor editor = getSharedPreferences(Utils.PREFS_SETTINGS, MODE_PRIVATE).edit();
        editor.putBoolean(Utils.PREF_DARK_THEME, darkTheme);
        editor.apply();

        Intent intent = getIntent();
        finish();

        startActivity(intent);
    }

    private void toggleDummyClasses(boolean dummyClassesEnabled) {
        SharedPreferences.Editor editor = getSharedPreferences(Utils.PREFS_SETTINGS, MODE_PRIVATE).edit();
        editor.putBoolean(Utils.PREF_DUMMY_CLASSES, dummyClassesEnabled);
        editor.apply();

        if (dummyClassesEnabled) {
            editor = getSharedPreferences(Utils.PREFS_CLASSES, MODE_PRIVATE).edit();

            Set<String> newSet = new HashSet<>();

            //MUST BE FIRST
            newSet.add(TeacherClassOverview.NULL_CLASS);

            //KEEP SPACES FOR UNIQUE IDENTIFICATION!!!!!!!
            newSet.add("Spanish   3");
            newSet.add("Spanish   2");
            newSet.add("Spanish   1");
            //KEEP SPACES FOR UNIQUE IDENTIFICATION!!!!!!!

            editor.putStringSet(Utils.PREF_CLASS_SET, newSet);
            editor.apply();
        } else {
            SharedPreferences sharedPreferences = Utils.getPrefs(Utils.PREFS_CLASSES, this);
            editor = getSharedPreferences(Utils.PREFS_CLASSES, MODE_PRIVATE).edit();

            Object[] newArray = sharedPreferences.getStringSet(Utils.PREF_CLASS_SET, null).toArray();
            Set<String> newSet = new HashSet<>();
            for (int i = 0; i < newArray.length; i++) {
                if (!newArray[i].toString().contains("Spanish   ")) {
                    newSet.add(newArray[i].toString());
                }
            }
            editor.putStringSet(Utils.PREF_CLASS_SET, newSet);
            editor.apply();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (getSharedPreferences(Utils.PREFS_CLASSES, MODE_PRIVATE).getBoolean(Utils.PREF_IS_TEACHER, false)) {
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
