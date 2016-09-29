package ajlyfe.lectureapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.SettingsActivity;

public class TeacherClassView extends AppCompatActivity {

    private int classNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_class_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            classNumber = extras.getInt("CLASS_CLICKED");
        }

        TextView classTitle = (TextView) findViewById(R.id.classTitle);
        switch (classNumber) {
            case 1:
                classTitle.setText("Class One");
                break;

            case 2:
                classTitle.setText("Class Two");
                break;
        }

        Button buttonStudents = (Button) findViewById(R.id.studentButton);
        buttonStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout students = (RelativeLayout) findViewById(R.id.students);
                students.setVisibility(View.VISIBLE);
                RelativeLayout files = (RelativeLayout) findViewById(R.id.files);
                files.setVisibility(View.INVISIBLE);
            }
        });

        Button buttonFiles = (Button) findViewById(R.id.fileButton);
        buttonFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout students = (RelativeLayout) findViewById(R.id.students);
                students.setVisibility(View.INVISIBLE);
                RelativeLayout files = (RelativeLayout) findViewById(R.id.files);
                files.setVisibility(View.VISIBLE);
            }
        });
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
}
