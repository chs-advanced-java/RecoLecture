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

import ajlyfe.lectureapp.R;

public class SettingsActivity extends AppCompatActivity {

    private static final int PREFERENCE_MODE_PRIVATE = 0;
    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preferenceSettings = getSharedPreferences("Classes", PREFERENCE_MODE_PRIVATE);
        preferenceEditor = preferenceSettings.edit();

        Button signOut = (Button) findViewById(R.id.signOut);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceEditor.putBoolean("loggedIn", false);
                preferenceEditor.commit();
                startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
