package ajlyfe.lectureapp.Activity;

import android.content.Intent;
import android.os.Environment;
import android.support.annotation.IdRes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;

import ajlyfe.lectureapp.Fragment.RecordFragment;
import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;

public class RecordActivity extends AppCompatActivity {

    @IdRes public static final int ROOT_ACTIVITY = R.id.activity_record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.setCustomTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecordFragment recordFragment = new RecordFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(ROOT_ACTIVITY, recordFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/RecoLecture/" + RecordFragment.PLACEHOLDER + ".3gp");
        file.delete();
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(RecordActivity.this, SettingsActivity.class));
                return true;

            case android.R.id.home:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
