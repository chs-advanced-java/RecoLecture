package ajlyfe.lectureapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import ajlyfe.lectureapp.Adapters.ClassCard;
import ajlyfe.lectureapp.Adapters.ClassCardAdapter;
import ajlyfe.lectureapp.Adapters.LectureCard;
import ajlyfe.lectureapp.R;

public class StudentActivityMain extends AppCompatActivity {

    public ArrayList<ClassCard> classes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabStudent);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentActivityMain.this, StudentEnterClassCode.class));
            }
        });

        RecyclerView recyclerViewStudentMain = (RecyclerView) findViewById(R.id.recyclerViewMainStudent);
        // Initialize ArrayList
        classes = ClassCard.createList(3);
        // Create adapter passing in the sample user data
        ClassCardAdapter adapter = new ClassCardAdapter(classes, this);
        // Attach the adapter to the recyclerview to populate items
        recyclerViewStudentMain.setAdapter(adapter);
        // Set layout manager to position the items
        recyclerViewStudentMain.setLayoutManager(new LinearLayoutManager(this));
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
                startActivity(new Intent(StudentActivityMain.this, SettingsActivity.class));
                return true;

            case android.R.id.home:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
