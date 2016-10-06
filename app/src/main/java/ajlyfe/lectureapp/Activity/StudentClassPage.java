package ajlyfe.lectureapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import ajlyfe.lectureapp.Adapters.LectureCard;
import ajlyfe.lectureapp.Adapters.LectureCardAdapter;
import ajlyfe.lectureapp.R;

public class StudentClassPage extends AppCompatActivity {

    public ArrayList<LectureCard> lectures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_class_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String className = extras.getString("CLASS_CLICKED");
            if(getSupportActionBar() != null) getSupportActionBar().setTitle(className);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Lookup the recyclerview in activity layout
        RecyclerView recyclerViewLectures = (RecyclerView) findViewById(R.id.recyclerViewLectures);

        // Initialize ArrayList
        lectures = LectureCard.createLectureList(100);
        // Create adapter passing in the sample user data
        LectureCardAdapter adapter = new LectureCardAdapter(lectures);
        // Attach the adapter to the recyclerview to populate items
        recyclerViewLectures.setAdapter(adapter);
        // Set layout manager to position the items
        recyclerViewLectures.setLayoutManager(new LinearLayoutManager(this));
        // That's all!
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
