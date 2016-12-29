package ajlyfe.lectureapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import ajlyfe.lectureapp.Fragment.SettingsFragment;
import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;
public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences preferences;

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
