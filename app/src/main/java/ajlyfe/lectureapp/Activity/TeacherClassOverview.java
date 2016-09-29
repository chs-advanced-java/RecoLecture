package ajlyfe.lectureapp.Activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View;

import ajlyfe.lectureapp.*;

public class TeacherClassOverview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_class_overview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Creating a Class", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        CardView classOneCard = (CardView) findViewById(R.id.classOne);
        CardView classTwoCard = (CardView) findViewById(R.id.classTwo);

        RelativeLayout classTwoLayout = (RelativeLayout) classTwoCard.getChildAt(0);
        TextView classTwoTitle = (TextView) classTwoLayout.getChildAt(0);

        classTwoTitle.setText("Class Two");

        classOneCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherClassOverview.this, TeacherClassView.class);
                intent.putExtra("CLASS_CLICKED", 1);
                startActivity(intent);

            }
        });

        classTwoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherClassOverview.this, TeacherClassView.class);
                intent.putExtra("CLASS_CLICKED", 2);
                startActivity(intent);
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
                startActivity(new Intent(TeacherClassOverview.this, SettingsActivity.class));
                return true;

            case android.R.id.home:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }
}