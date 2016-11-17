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
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;

import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;

public class SettingsActivity extends AppCompatActivity {

    private static final String PREF_DARK_THEME = "useDarkTheme";
    private static final String PREF_NAME_SETTINGS = "settings";
    private static final String PREF_NAME_GENERAL = "Classes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME_SETTINGS, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

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
                getSharedPreferences(PREF_NAME_GENERAL, MODE_PRIVATE).edit().clear().apply();
                getSharedPreferences(PREF_NAME_SETTINGS, MODE_PRIVATE).edit().clear().apply();

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
    }

    private void toggleTheme(boolean darkTheme) {
        SharedPreferences.Editor editor = getSharedPreferences(PREF_NAME_SETTINGS, MODE_PRIVATE).edit();
        editor.putBoolean(PREF_DARK_THEME, darkTheme);
        editor.apply();

        Intent intent = getIntent();
        finish();

        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
