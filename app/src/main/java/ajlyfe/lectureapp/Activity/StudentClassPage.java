package ajlyfe.lectureapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import ajlyfe.lectureapp.Adapters.LectureCard;
import ajlyfe.lectureapp.Adapters.LectureCardAdapter;
import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class StudentClassPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.setCustomTheme(this);
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

        // Find and define the RecyclerView in activity's layout
        RecyclerView recyclerViewLectures = (RecyclerView) findViewById(R.id.recyclerViewLectures);
        // Initialize the ArrayList
        ArrayList<LectureCard> lectures = LectureCard.createLectureList(this);
        // Create an adapter passing in the ArrayList from above
        LectureCardAdapter adapter = new LectureCardAdapter(lectures, this);
        // Attach the adapter to the RecyclerView
        recyclerViewLectures.setAdapter(adapter);
        // Set layout manager to position the items
        recyclerViewLectures.setLayoutManager(new LinearLayoutManager(this));
        // Set overscroll effect
        OverScrollDecoratorHelper.setUpOverScroll(recyclerViewLectures, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
        // That's it!
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
